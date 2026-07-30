/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.sql.Connection;

import java.util.List;

/**
 * @author Adam Brandizzi
 * @author Alberto Chaparro
 */
public class UpgradeTestUtil {

	public static List<IndexMetadata> dropUniqueIndexes(
			Connection connection, String tableName, String columnName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		List<IndexMetadata> indexMetadatas = db.getIndexMetadatas(
			connection, tableName, columnName, true);

		for (IndexMetadata indexMetadata : indexMetadatas) {
			db.runSQL(connection, indexMetadata.getDropSQL());
		}

		return indexMetadatas;
	}

	public static UpgradeProcess getUpgradeStep(
		UpgradeStepRegistrator upgradeStepRegistrator,
		String upgradeStepClassName) {

		SearchRegistry searchRegistry = new SearchRegistry(
			upgradeStepClassName);

		upgradeStepRegistrator.register(searchRegistry);

		return searchRegistry.getUpgradeSteps()[0];
	}

	public static UpgradeProcess[] getUpgradeSteps(
		UpgradeStepRegistrator upgradeStepRegistrator,
		Version toSchemaVersion) {

		SearchRegistry searchRegistry = new SearchRegistry(toSchemaVersion);

		upgradeStepRegistrator.register(searchRegistry);

		return searchRegistry.getUpgradeSteps();
	}

	private static class SearchRegistry
		implements UpgradeStepRegistrator.Registry {

		public SearchRegistry(String upgradeStepClassName) {
			_upgradeStepClassName = upgradeStepClassName;
		}

		public SearchRegistry(Version toSchemaVersion) {
			_toSchemaVersion = toSchemaVersion;
		}

		public UpgradeProcess[] getUpgradeSteps() {
			return _upgradeSteps;
		}

		@Override
		public void register(
			String fromSchemaVersionString, String toSchemaVersionString,
			UpgradeStep... upgradeSteps) {

			for (UpgradeStep upgradeStep : upgradeSteps) {
				if (_upgradeStepClassName != null) {
					Class<?> clazz = upgradeStep.getClass();

					String className = clazz.getName();

					if (className.contains(_upgradeStepClassName)) {
						_upgradeSteps = ArrayUtil.append(
							_upgradeSteps, (UpgradeProcess)upgradeStep);

						break;
					}
				}
				else {
					if (toSchemaVersionString.equals(
							_toSchemaVersion.toString())) {

						_upgradeSteps = ArrayUtil.append(
							_upgradeSteps, (UpgradeProcess)upgradeStep);
					}
				}
			}
		}

		private Version _toSchemaVersion;
		private String _upgradeStepClassName;
		private UpgradeProcess[] _upgradeSteps = new UpgradeProcess[0];

	}

}