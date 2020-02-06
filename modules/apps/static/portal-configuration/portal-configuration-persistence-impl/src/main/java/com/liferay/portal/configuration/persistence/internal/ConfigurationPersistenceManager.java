/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.configuration.persistence.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.ReloadablePersistenceManager;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.net.URI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import org.apache.felix.cm.NotCachablePersistenceManager;
import org.apache.felix.cm.PersistenceManager;
import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Raymond Augé
 * @author Sampsa Sohlman
 */
public class ConfigurationPersistenceManager
	implements NotCachablePersistenceManager, PersistenceManager,
			   ReloadablePersistenceManager {

	public ConfigurationPersistenceManager(
		BundleContext bundleContext, DataSource dataSource) {

		_bundleContext = bundleContext;
		_dataSource = dataSource;
	}

	@Override
	public void delete(String pid) throws IOException {
		ConfigurationModelListener configurationModelListener = null;

		if (!pid.endsWith("factory") && hasPid(pid)) {
			Dictionary dictionary = getDictionary(pid);

			String pidKey = (String)dictionary.get(
				ConfigurationAdmin.SERVICE_FACTORYPID);

			if (pidKey == null) {
				pidKey = (String)dictionary.get(Constants.SERVICE_PID);
			}

			if (pidKey == null) {
				pidKey = pid;
			}

			configurationModelListener = _getConfigurationModelListener(pidKey);
		}

		if (configurationModelListener != null) {
			configurationModelListener.onBeforeDelete(pid);
		}

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			Dictionary<?, ?> dictionary = _dictionaries.remove(pid);

			if ((dictionary != null) && hasPid(pid)) {
				deleteFromDatabase(pid);
			}
		}
		finally {
			lock.unlock();
		}

		if (configurationModelListener != null) {
			configurationModelListener.onAfterDelete(pid);
		}
	}

	@Override
	public boolean exists(String pid) {
		Lock lock = _readWriteLock.readLock();

		try {
			lock.lock();

			return _dictionaries.containsKey(pid);
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Enumeration<?> getDictionaries() {
		Lock lock = _readWriteLock.readLock();

		try {
			lock.lock();

			return Collections.enumeration(_dictionaries.values());
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public Dictionary<?, ?> load(String pid) {
		Lock lock = _readWriteLock.readLock();

		try {
			lock.lock();

			return _dictionaries.get(pid);
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public void reload(String pid) throws IOException {
		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			_dictionaries.remove(pid);

			if (hasPid(pid)) {
				_dictionaries.put(pid, getDictionary(pid));
			}
		}
		finally {
			lock.unlock();
		}
	}

	public void start() {
		try {
			createConfigurationTable();
		}
		catch (IOException | SQLException exception) {
			populateDictionaries();
		}
	}

	public void stop() {
		_dictionaries.clear();

		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	@Override
	public void store(
			String pid, @SuppressWarnings("rawtypes") Dictionary dictionary)
		throws IOException {

		ConfigurationModelListener configurationModelListener = null;

		if (!pid.endsWith("factory") &&
			(dictionary.get("_felix_.cm.newConfiguration") == null)) {

			String pidKey = (String)dictionary.get(
				ConfigurationAdmin.SERVICE_FACTORYPID);

			if (pidKey == null) {
				pidKey = pid;
			}

			configurationModelListener = _getConfigurationModelListener(pidKey);
		}

		if (configurationModelListener != null) {
			configurationModelListener.onBeforeSave(pid, dictionary);
		}

		Dictionary<Object, Object> newDictionary = _copyDictionary(dictionary);

		String fileName = (String)newDictionary.get(
			_FELIX_FILE_INSTALL_FILENAME);

		if (fileName != null) {
			File file = new File(URI.create(fileName));

			newDictionary.put(_FELIX_FILE_INSTALL_FILENAME, file.getName());
		}

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			storeInDatabase(pid, newDictionary);

			if (fileName != null) {
				newDictionary.put(_FELIX_FILE_INSTALL_FILENAME, fileName);
			}

			_dictionaries.put(pid, newDictionary);
		}
		finally {
			lock.unlock();
		}

		if (configurationModelListener != null) {
			configurationModelListener.onAfterSave(pid, dictionary);
		}
	}

	protected void createConfigurationTable() throws IOException, SQLException {
		try (Connection connection = _dataSource.getConnection();
			Statement statement = connection.createStatement()) {

			statement.executeUpdate(
				_db.buildSQL(
					"create table Configuration_ (configurationId " +
						"VARCHAR(255) not null primary key, dictionary TEXT)"));
		}
	}

	protected void deleteFromDatabase(String pid) throws IOException {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_db.buildSQL(
					"delete from Configuration_ where configurationId = ?"))) {

			preparedStatement.setString(1, pid);

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			throw new IOException(sqlException);
		}
	}

	protected Dictionary<?, ?> getDictionary(String pid) throws IOException {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_db.buildSQL(
					"select dictionary from Configuration_ where " +
						"configurationId = ?"))) {

			preparedStatement.setString(1, pid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return toDictionary(resultSet.getString(1));
				}
			}

			return _emptyDictionary;
		}
		catch (SQLException sqlException) {
			return ReflectionUtil.throwException(sqlException);
		}
	}

	protected boolean hasPid(String pid) {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_db.buildSQL(
					"select count(*) from Configuration_ where " +
						"configurationId = ?"))) {

			preparedStatement.setString(1, pid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				int count = 0;

				if (resultSet.next()) {
					count = resultSet.getInt(1);
				}

				if (count > 0) {
					return true;
				}
			}

			return false;
		}
		catch (IOException | SQLException exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	protected void populateDictionaries() {
		try (Connection connection = _dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				_db.buildSQL(
					"select configurationId, dictionary from Configuration_"),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String pid = resultSet.getString(1);

				Dictionary<String, String> dictionary = _verifyDictionary(
					pid, resultSet.getString(2));

				if (dictionary != null) {
					_dictionaries.putIfAbsent(pid, dictionary);
				}
			}
		}
		catch (IOException | SQLException exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	protected void store(ResultSet resultSet, Dictionary<?, ?> dictionary)
		throws IOException, SQLException {

		OutputStream outputStream = new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(outputStream, dictionary);

		resultSet.updateString(2, outputStream.toString());
	}

	protected void storeInDatabase(String pid, Dictionary<?, ?> dictionary)
		throws IOException {

		UnsyncByteArrayOutputStream outputStream =
			new UnsyncByteArrayOutputStream();

		ConfigurationHandler.write(outputStream, dictionary);

		try (Connection connection = _dataSource.getConnection()) {
			connection.setAutoCommit(false);

			try (PreparedStatement ps1 = connection.prepareStatement(
					_db.buildSQL(
						"update Configuration_ set dictionary = ? where " +
							"configurationId = ?"))) {

				ps1.setString(1, outputStream.toString());
				ps1.setString(2, pid);

				if (ps1.executeUpdate() == 0) {
					try (PreparedStatement ps2 = connection.prepareStatement(
							_db.buildSQL(
								"insert into Configuration_ (" +
									"configurationId, dictionary) values (?, " +
										"?)"))) {

						ps2.setString(1, pid);
						ps2.setString(2, outputStream.toString());

						ps2.executeUpdate();
					}
				}
			}

			connection.commit();
		}
		catch (SQLException sqlException) {
			ReflectionUtil.throwException(sqlException);
		}
	}

	@SuppressWarnings("unchecked")
	protected Dictionary<String, String> toDictionary(String dictionaryString)
		throws IOException {

		if (dictionaryString == null) {
			return new HashMapDictionary<>();
		}

		Dictionary<String, String> dictionary = ConfigurationHandler.read(
			new UnsyncByteArrayInputStream(
				dictionaryString.getBytes(StringPool.UTF8)));

		String fileName = dictionary.get(_FELIX_FILE_INSTALL_FILENAME);

		if (fileName != null) {
			File file = new File(
				PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, fileName);

			file = file.getAbsoluteFile();

			URI uri = file.toURI();

			dictionary.put(_FELIX_FILE_INSTALL_FILENAME, uri.toString());
		}

		return dictionary;
	}

	private Dictionary<Object, Object> _copyDictionary(
		Dictionary<?, ?> dictionary) {

		Dictionary<Object, Object> newDictionary = new HashMapDictionary<>();

		Enumeration<?> keys = dictionary.keys();

		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();

			newDictionary.put(key, dictionary.get(key));
		}

		return newDictionary;
	}

	private ConfigurationModelListener _getConfigurationModelListener(
		String configurationModelClassName) {

		if (_serviceTrackerMap == null) {
			_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, ConfigurationModelListener.class,
				"model.class.name");
		}

		return _serviceTrackerMap.getService(configurationModelClassName);
	}

	private Dictionary<String, String> _verifyDictionary(
			String pid, String dictionaryString)
		throws IOException {

		if (dictionaryString == null) {
			return new HashMapDictionary<>();
		}

		Dictionary<String, String> dictionary = ConfigurationHandler.read(
			new UnsyncByteArrayInputStream(
				dictionaryString.getBytes(StringPool.UTF8)));

		String felixFileInstallFileName = dictionary.get(
			_FELIX_FILE_INSTALL_FILENAME);

		if (felixFileInstallFileName == null) {
			return dictionary;
		}

		boolean needSave = false;

		if (dictionary.get(_SERVIE_BUNDLE_LOCATION) == null) {
			dictionary.put(_SERVIE_BUNDLE_LOCATION, "?");

			needSave = true;
		}

		File configFile = null;

		if (felixFileInstallFileName.startsWith("file:")) {
			configFile = new File(URI.create(felixFileInstallFileName));

			dictionary.put(_FELIX_FILE_INSTALL_FILENAME, configFile.getName());

			storeInDatabase(pid, dictionary);

			dictionary.put(
				_FELIX_FILE_INSTALL_FILENAME, felixFileInstallFileName);

			needSave = false;
		}
		else {
			configFile = new File(
				PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
				felixFileInstallFileName);

			configFile = configFile.getAbsoluteFile();

			URI uri = configFile.toURI();

			dictionary.put(_FELIX_FILE_INSTALL_FILENAME, uri.toString());
		}

		if (needSave) {
			storeInDatabase(pid, dictionary);
		}

		String ignore = dictionary.get("configuration.cleaner.ignore");

		if (!Boolean.valueOf(ignore) && !configFile.exists()) {
			deleteFromDatabase(pid);

			return null;
		}

		return dictionary;
	}

	private static final String _FELIX_FILE_INSTALL_FILENAME =
		"felix.fileinstall.filename";

	private static final String _SERVIE_BUNDLE_LOCATION =
		"service.bundleLocation";

	private static final Dictionary<?, ?> _emptyDictionary =
		new HashMapDictionary<>();

	private final BundleContext _bundleContext;
	private final DataSource _dataSource;
	private DB _db = DBManagerUtil.getDB();
	private final ConcurrentMap<String, Dictionary<?, ?>> _dictionaries =
		new ConcurrentHashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock(
		true);
	private ServiceTrackerMap<String, ConfigurationModelListener>
		_serviceTrackerMap;

}