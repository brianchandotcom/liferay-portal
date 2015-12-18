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

package com.liferay.portal.configuration.persistence;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.io.ReaderInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import org.apache.felix.cm.NotCachablePersistenceManager;
import org.apache.felix.cm.PersistenceManager;
import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Raymond Augé
 */
public class ConfigurationPersistenceManager
	implements NotCachablePersistenceManager, PersistenceManager,
			   ReloadablePersistenceManager {

	@Override
	public void delete(final String pid) throws IOException {
		if (System.getSecurityManager() != null) {
			try {
				AccessController.doPrivileged(
					new PrivilegedExceptionAction<Void>() {

						@Override
						public Void run() throws Exception {
							doDelete(pid);

							return null;
						}

					});
			}
			catch (PrivilegedActionException pae) {
				throw (IOException)pae.getException();
			}
		}
		else {
			doDelete(pid);
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
				Dictionary<?, ?> dictionary = getDictionary(pid);

				_dictionaries.put(pid, dictionary);
			}
		}
		finally {
			lock.unlock();
		}
	}

	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	public void start() {
		Lock readLock = _readWriteLock.readLock();
		Lock writeLock = _readWriteLock.writeLock();

		try {
			readLock.lock();

			if (!hasConfigurationTable()) {
				readLock.unlock();
				writeLock.lock();

				try {
					createConfigurationTable();
				}
				finally {
					readLock.lock();
					writeLock.unlock();
				}
			}

			populateDictionaries();
		}
		finally {
			readLock.unlock();
		}
	}

	public void stop() {
		_dictionaries.clear();
	}

	@Override
	public void store(
			final String pid,
			@SuppressWarnings("rawtypes") final Dictionary dictionary)
		throws IOException {

		if (System.getSecurityManager() != null) {
			try {
				AccessController.doPrivileged(
					new PrivilegedExceptionAction<Void>() {

						@Override
						public Void run() throws Exception {
							doStore(pid, dictionary);

							return null;
						}

					});
			}
			catch (PrivilegedActionException pae) {
				throw (IOException)pae.getException();
			}
		}
		else {
			doStore(pid, dictionary);
		}
	}

	protected String buildSQL(String sql) throws IOException {
		DB db = DBManagerUtil.getDB();

		return db.buildSQL(sql);
	}

	protected void cleanUp(
		Connection connection, Statement statement, ResultSet resultSet) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		catch (SQLException sqle) {
			ReflectionUtil.throwException(sqle);
		}
		finally {
			try {
				if (statement != null) {
					statement.close();
				}
			}
			catch (SQLException sqle) {
				ReflectionUtil.throwException(sqle);
			}
			finally {
				try {
					if (connection != null) {
						connection.close();
					}
				}
				catch (SQLException sqle) {
					ReflectionUtil.throwException(sqle);
				}
			}
		}
	}

	protected void createConfigurationTable() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			statement = connection.createStatement();

			statement.executeUpdate(
				buildSQL(
					"create table Configuration_ (configurationId " +
						"VARCHAR(255) not null primary key, dictionary TEXT)"));
		}
		catch (IOException | SQLException e) {
			ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(connection, statement, resultSet);
		}
	}

	protected void deleteFromDatabase(String pid) throws IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection,
				"delete from Configuration_ where configurationId = ?");

			preparedStatement.setString(1, pid);

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqle) {
			throw new IOException(sqle);
		}
		finally {
			cleanUp(connection, preparedStatement, null);
		}
	}

	protected void doDelete(String pid) throws IOException {
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
	}

	protected void doStore(
			String pid, @SuppressWarnings("rawtypes") Dictionary dictionary)
		throws IOException {

		Lock lock = _readWriteLock.writeLock();

		try {
			lock.lock();

			storeInDatabase(pid, dictionary);

			_dictionaries.put(pid, dictionary);
		}
		finally {
			lock.unlock();
		}
	}

	protected Dictionary<?, ?> getDictionary(String pid) throws IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection,
				"select dictionary from Configuration_ where " +
					"configurationId = ?");

			preparedStatement.setString(1, pid);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return toDictionary(resultSet.getString(1));
			}

			return _emptyDictionary;
		}
		catch (SQLException sqle) {
			return ReflectionUtil.throwException(sqle);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected boolean hasConfigurationTable() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection, "select count(*) from Configuration_");

			resultSet = preparedStatement.executeQuery();

			int count = 0;

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			if (count >= 0) {
				return true;
			}

			return false;
		}
		catch (IOException | SQLException e) {
			return false;
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected boolean hasPid(String pid) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = prepareStatement(
				connection,
				"select count(*) from Configuration_ where " +
					"configurationId = ?");

			preparedStatement.setString(1, pid);

			resultSet = preparedStatement.executeQuery();

			int count = 0;

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			if (count > 0) {
				return true;
			}

			return false;
		}
		catch (IOException | SQLException e) {
			return ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected void populateDictionaries() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();

			preparedStatement = connection.prepareStatement(
				buildSQL(
					"select configurationId, dictionary from Configuration_ " +
						"ORDER BY configurationId ASC"),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String pid = resultSet.getString(1);
				String dictionaryString = resultSet.getString(2);

				_dictionaries.putIfAbsent(pid, toDictionary(dictionaryString));
			}
		}
		catch (IOException | SQLException e) {
			ReflectionUtil.throwException(e);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected PreparedStatement prepareStatement(
			Connection connection, String sql)
		throws IOException, SQLException {

		return connection.prepareStatement(buildSQL(sql));
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

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = _dataSource.getConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(
				buildSQL(
					"select configurationId, dictionary from Configuration_ " +
						"where configurationId = ?"),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			preparedStatement.setString(1, pid);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				resultSet.updateString(2, outputStream.toString());

				resultSet.updateRow();
			}
			else {
				preparedStatement = prepareStatement(
					connection,
					"insert into Configuration_ (configurationId, " +
						"dictionary) values (?, ?)");

				preparedStatement.setString(1, pid);
				preparedStatement.setString(2, outputStream.toString());

				preparedStatement.executeUpdate();
			}

			connection.commit();
		}
		catch (SQLException sqle) {
			ReflectionUtil.throwException(sqle);
		}
		finally {
			cleanUp(connection, preparedStatement, resultSet);

			outputStream.close();
		}
	}

	protected Dictionary<?, ?> toDictionary(String dictionaryString)
		throws IOException {

		InputStream inputStream = new ReaderInputStream(
			new StringReader(dictionaryString));

		return ConfigurationHandler.read(inputStream);
	}

	private static final Dictionary<?, ?> _emptyDictionary = new Hashtable<>();

	private DataSource _dataSource;
	private final ConcurrentMap<String, Dictionary<?, ?>> _dictionaries =
		new ConcurrentHashMap<>();
	private final ReadWriteLock _readWriteLock = new ReentrantReadWriteLock(
		true);

}