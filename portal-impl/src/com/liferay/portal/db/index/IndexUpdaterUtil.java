/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.index;

import com.liferay.petra.concurrent.DCLSingleton;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Ricardo Couso
 */
public class IndexUpdaterUtil {

	public static void destroy() {
		_executorServiceDCLSingleton.destroy(
			executorService -> {
				executorService.shutdown();

				for (Future<?> future : _futures) {
					try {
						future.get();
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				}

				_futures.clear();
			});
	}

	public static void updateAllIndexes() {
		if (!_updatedBundleSymbolicNames.contains("portal")) {
			updatePortalIndexes();
		}

		BundleTracker<Void> bundleTracker = new BundleTracker<>(
			SystemBundleUtil.getBundleContext(), Bundle.ACTIVE,
			new BundleTrackerCustomizer<Void>() {

				@Override
				public Void addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					if (BundleUtil.isLiferayServiceBundle(bundle)) {
						try {
							if (!_updatedBundleSymbolicNames.contains(
									bundle.getSymbolicName())) {

								updateIndexes(bundle);
							}
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

		ExecutorService executorService = _getExecutorService();

		db.process(
			companyId -> _futures.add(
				executorService.submit(
					() -> {
						String message = new String(
							"Updating database indexes for " +
								bundle.getSymbolicName());

						if (Validator.isNotNull(companyId)) {
							message += " and company " + companyId;
						}

						try {
							try (Connection connection =
									DataAccess.getConnection();
								LoggingTimer loggingTimer = new LoggingTimer(
									message)) {

								db.updateIndexes(
									connection, tablesSQL, indexesSQL, true);
							}
						}
						catch (Exception exception) {
							_log.error(exception);
						}
					})));

		_updatedBundleSymbolicNames.add(bundle.getSymbolicName());
	}

	public static void updatePortalIndexes() {
		DB db = DBManagerUtil.getDB();

		ExecutorService executorService = _getExecutorService();

		try {
			db.process(
				companyId -> _futures.add(
					executorService.submit(
						() -> {
							String message = new String(
								"Updating portal database indexes");

							if (Validator.isNotNull(companyId)) {
								message += " for company " + companyId;
							}

							try (Connection connection =
									DataAccess.getConnection();
								LoggingTimer loggingTimer = new LoggingTimer(
									message)) {

								_updatePortalIndexes(db, connection);
							}
							catch (Exception exception) {
								_log.error(exception);
							}
						})));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		_updatedBundleSymbolicNames.add("portal");
	}

	private static ExecutorService _getExecutorService() {
		return _executorServiceDCLSingleton.getSingleton(
			Executors::newWorkStealingPool);
	}

	private static void _updatePortalIndexes(DB db, Connection connection)
		throws Exception {

		db.updateIndexes(
			connection, DBResourceUtil.getPortalTablesSQL(),
			DBResourceUtil.getPortalIndexesSQL(), true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

	private static final DCLSingleton<ExecutorService>
		_executorServiceDCLSingleton = new DCLSingleton<>();
	private static final List<Future<?>> _futures =
		Collections.synchronizedList(new ArrayList<Future<?>>());
	private static final Set<String> _updatedBundleSymbolicNames =
		new HashSet<>();

}