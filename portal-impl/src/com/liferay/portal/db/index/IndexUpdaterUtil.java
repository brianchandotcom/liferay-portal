/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.index;

import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.concurrent.FutureTask;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Ricardo Couso
 */
public class IndexUpdaterUtil {

	public static void updateAllIndexes() {
		updatePortalIndexes();

		BundleTracker<Void> bundleTracker = new BundleTracker<>(
			SystemBundleUtil.getBundleContext(), Bundle.ACTIVE,
			new BundleTrackerCustomizer<Void>() {

				@Override
				public Void addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					if (BundleUtil.isLiferayServiceBundle(bundle)) {
						try {
							updateIndexes(bundle);
						}
						catch (Exception exception) {
							_log.error(exception);
						}
					}

					return null;
				}

				@Override
				public void modifiedBundle(
					Bundle bundle, BundleEvent bundleEvent, Void tracked) {
				}

				@Override
				public void removedBundle(
					Bundle bundle, BundleEvent bundleEvent, Void tracked) {
				}

			});

		DependencyManagerSyncUtil.registerSyncFutureTask(
			new FutureTask<>(
				() -> {
					bundleTracker.open();

					DependencyManagerSyncUtil.registerSyncCallable(
						() -> {
							bundleTracker.close();

							return null;
						});

					return null;
				}),
			IndexUpdaterUtil.class.getName() + "-BundleTrackerOpener");
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

	private static void _updatePortalIndexes(DB db, Connection connection)
		throws Exception {

		db.updateIndexes(
			connection, DBResourceUtil.getPortalTablesSQL(),
			DBResourceUtil.getPortalIndexesSQL(), true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

}