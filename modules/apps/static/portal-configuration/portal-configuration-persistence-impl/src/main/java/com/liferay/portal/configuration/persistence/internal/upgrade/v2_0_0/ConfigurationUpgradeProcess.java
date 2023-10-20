/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.persistence.internal.upgrade.v2_0_0;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalInstances;

import java.io.IOException;
import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Dictionary;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Luis Ortiz
 */
public class ConfigurationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (PortalInstances.getDefaultCompanyId() ==
				CompanyThreadLocal.getCompanyId()) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select configurationId, dictionary from " +
							"Configuration_");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					String pid = resultSet.getString(1);

					String dictionaryString = resultSet.getString(2);

					ScopedConfig scopedConfig = _getScopedConfig(
						dictionaryString, pid);

					if ((scopedConfig != null) &&
						!_configApplies(scopedConfig)) {

						_configurations.add(scopedConfig);

						_removeConfiguration(scopedConfig.getKey());
					}
				}
			}

			long[] companyIds = PortalInstances.getCompanyIds();

			_atomicInteger.set(companyIds.length - 1);

			return;
		}

		DBPartitionUtil.replaceByTable(connection, false, "Configuration_");

		for (ScopedConfig scopedConfig : _configurations) {
			if (_configApplies(scopedConfig)) {
				_insertConfiguration(
					scopedConfig.getDictionary(), scopedConfig.getKey());

				String scopeValue = scopedConfig.getScope(
				).getValue();

				if (scopeValue.equals(
						ExtendedObjectClassDefinition.Scope.COMPANY.
							getValue()) ||
					scopeValue.equals(
						ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

					_configurations.remove(scopedConfig);
				}
			}
		}

		int remainingCompanies = _atomicInteger.decrementAndGet();

		if (remainingCompanies == 0) {
			for (ScopedConfig scopedConfig : _configurations) {
				String scopeValue = scopedConfig.getScope(
				).getValue();

				if (scopeValue.equals(
						ExtendedObjectClassDefinition.Scope.COMPANY.
							getValue())) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Configuration with pid ",
								scopedConfig.getKey(),
								" applicable to company ",
								(String)scopedConfig.getScopePK(),
								" has been removed as the company does not ",
								"exists"));
					}
				}

				if (scopeValue.equals(
						ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Configuration with pid ",
								scopedConfig.getKey(), " applicable to group ",
								(String)scopedConfig.getScopePK(),
								" has been removed as the group does not ",
								"exists"));
					}
				}
			}
		}
	}

	@Override
	protected boolean isSkipUpgradeProcess() {
		if (!DBPartition.isPartitionEnabled()) {
			return true;
		}

		return false;
	}

	private boolean _configApplies(ScopedConfig scopedConfig)
		throws SQLException {

		String configScopeValue = scopedConfig.getScope(
		).getValue();

		if (configScopeValue.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			if (CompanyThreadLocal.getCompanyId() ==
					(long)scopedConfig.getScopePK()) {

				return true;
			}

			return false;
		}

		if (configScopeValue.equals(
				ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select companyId from Group_ where groupId = ?")) {

				preparedStatement.setLong(1, (long)scopedConfig.getScopePK());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						return true;
					}
				}
			}

			return false;
		}

		if (configScopeValue.equals(
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
					getValue())) {

			return true;
		}

		return false;
	}

	private ScopedConfig _getScopedConfig(String dictionary, String key)
		throws IOException {

		Dictionary<Object, Object> dictionaryMap = ConfigurationHandler.read(
			new UnsyncByteArrayInputStream(
				dictionary.getBytes(StringPool.UTF8)));

		Object value = dictionaryMap.get(
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey());

		if (value != null) {
			return new ScopedConfig(
				dictionary, key, (long)value,
				ExtendedObjectClassDefinition.Scope.COMPANY);
		}

		value = dictionaryMap.get(
			ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey());

		if (value != null) {
			return new ScopedConfig(
				dictionary, key, (long)value,
				ExtendedObjectClassDefinition.Scope.GROUP);
		}

		value = dictionaryMap.get(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
				getPropertyKey());

		if (value != null) {
			return new ScopedConfig(
				dictionary, key, (String)value,
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE);
		}

		return null;
	}

	private void _insertConfiguration(String dictionary, String pid)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				_db.buildSQL(
					"insert into Configuration_ (configurationId, dictionary" +
						") values (?, ?)"))) {

			preparedStatement.setString(1, pid);
			preparedStatement.setString(2, dictionary);

			preparedStatement.executeUpdate();
		}
	}

	private void _removeConfiguration(String pid)
		throws IOException, SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				_db.buildSQL(
					"delete from Configuration_ where configurationId = ?"))) {

			preparedStatement.setString(1, pid);

			preparedStatement.executeUpdate();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationUpgradeProcess.class);

	private static final AtomicInteger _atomicInteger = new AtomicInteger();
	private static final CopyOnWriteArrayList<ScopedConfig> _configurations =
		new CopyOnWriteArrayList<>();

	private final DB _db = DBManagerUtil.getDB();

	private class ScopedConfig {

		public ScopedConfig(
			String dictionary, String key, Serializable scopePK,
			ExtendedObjectClassDefinition.Scope scope) {

			_dictionary = dictionary;
			_key = key;
			_scopePK = scopePK;
			_scope = scope;
		}

		public String getDictionary() {
			return _dictionary;
		}

		public String getKey() {
			return _key;
		}

		public ExtendedObjectClassDefinition.Scope getScope() {
			return _scope;
		}

		public Serializable getScopePK() {
			return _scopePK;
		}

		private final String _dictionary;
		private final String _key;
		private final ExtendedObjectClassDefinition.Scope _scope;
		private final Serializable _scopePK;

	}

}