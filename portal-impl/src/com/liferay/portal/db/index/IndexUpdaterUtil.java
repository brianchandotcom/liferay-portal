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

package com.liferay.portal.db.index;

import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.module.util.BundleUtil;

import java.sql.Connection;
import java.sql.SQLException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Ricardo Couso
 */
public class IndexUpdaterUtil {

	public static void updateAllIndexes() {
		updatePortalIndexes();

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				_updateModulesIndexes();

				return null;
			});
	}

	public static void updateIndexes(Bundle bundle) throws Exception {
		String indexesSQL = DBResourceUtil.getModuleIndexesSQL(bundle);
		String tablesSQL = DBResourceUtil.getModuleTablesSQL(bundle);

		if ((indexesSQL == null) || (tablesSQL == null)) {
			return;
		}

		DB db = DBManagerUtil.getDB();

		db.process(
			companyId -> {
				String message = new String(
					"Updating database indexes for " +
						bundle.getSymbolicName());

				if (Validator.isNotNull(companyId) && _log.isInfoEnabled()) {
					message += " and company " + companyId;
				}

				try (Connection connection = DataAccess.getConnection();
					LoggingTimer loggingTimer = new LoggingTimer(message)) {

					db.updateIndexes(connection, tablesSQL, indexesSQL, true);
				}
			});
	}

	public static void updatePortalIndexes() {
		DB db = DBManagerUtil.getDB();

		try {
			db.process(
				companyId -> {
					String message = new String(
						"Updating portal database indexes");

					if (Validator.isNotNull(companyId) &&
						_log.isInfoEnabled()) {

						message += " for company " + companyId;
					}

					try (Connection connection = DataAccess.getConnection();
						LoggingTimer loggingTimer = new LoggingTimer(message)) {

						_updatePortalIndexes(db, connection);
					}
					catch (SQLException sqlException) {
						if (_log.isWarnEnabled()) {
							_log.warn(sqlException);
						}
					}
				});
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private static void _updateModulesIndexes() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (BundleUtil.isLiferayServiceBundle(bundle)) {
				try {
					updateIndexes(bundle);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}
	}

	private static void _updatePortalIndexes(DB db, Connection connection)
		throws Exception {

		db.updateIndexes(
			connection, DBResourceUtil.getPortalTablesSQL(),
			DBResourceUtil.getPortalIndexesSQL(), true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

}