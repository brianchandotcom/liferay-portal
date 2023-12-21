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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

				_awaitTermination();
			});
	}

	public static void updateAllIndexes() {
		if (!_processedServletContextNames.contains("portal")) {
			try {
				_updateIndexes(
					"portal", DBResourceUtil.getPortalTablesSQL(),
					DBResourceUtil.getPortalIndexesSQL());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		BundleTracker<Void> bundleTracker = new BundleTracker<>(
			SystemBundleUtil.getBundleContext(), Bundle.ACTIVE,
			new BundleTrackerCustomizer<Void>() {

				@Override
				public Void addingBundle(
					Bundle bundle, BundleEvent bundleEvent) {

					if (BundleUtil.isLiferayServiceBundle(bundle)) {
						try {
							if (!_processedServletContextNames.contains(
									bundle.getSymbolicName())) {

								_updateIndexes(
									bundle.getSymbolicName(),
									DBResourceUtil.getModuleTablesSQL(bundle),
									DBResourceUtil.getModuleIndexesSQL(bundle));
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

							_processedServletContextNames.clear();

							if (!PropsValues.
									DATABASE_INDEXES_UPDATE_IN_BACKGROUND) {

								_awaitTermination();
							}

							return null;
						});

					return null;
				}),
			IndexUpdaterUtil.class.getName() + "-BundleTrackerOpener");
	}

	public static void updateIndexes(Bundle bundle) throws Exception {
		_updateIndexes(
			bundle.getSymbolicName(), DBResourceUtil.getModuleTablesSQL(bundle),
			DBResourceUtil.getModuleIndexesSQL(bundle));

		if (!PropsValues.DATABASE_INDEXES_UPDATE_IN_BACKGROUND) {
			_awaitTermination();
		}
	}

	public static void updatePortalIndexes() {
		try {
			_updateIndexes(
				"portal", DBResourceUtil.getPortalTablesSQL(),
				DBResourceUtil.getPortalIndexesSQL());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
		finally {
			if (!PropsValues.DATABASE_INDEXES_UPDATE_IN_BACKGROUND) {
				_awaitTermination();
			}
		}
	}

	private static void _awaitTermination() {
		for (Future<?> future : _futures) {
			try {
				future.get();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		_futures.clear();
	}

	private static ExecutorService _getExecutorService() {
		return _executorServiceDCLSingleton.getSingleton(
			Executors::newWorkStealingPool);
	}

	private static void _updateIndexes(
		String servletContextName, String tablesSQL, String indexesSQL) {

		_processedServletContextNames.add(servletContextName);

		if ((indexesSQL == null) || (tablesSQL == null)) {
			return;
		}

		DB db = DBManagerUtil.getDB();

		ExecutorService executorService = _getExecutorService();

		_futures.add(
			executorService.submit(
				() -> {
					try {
						db.process(
							companyId -> {
								try {
									String message = new String(
										"Updating portal database indexes");

									if (!servletContextName.equals("portal")) {
										message = new String(
											"Updating database indexes for " +
												servletContextName);
									}

									if (Validator.isNotNull(companyId)) {
										message += " and company " + companyId;
									}

									try (Connection connection =
											DataAccess.getConnection();
										LoggingTimer loggingTimer =
											new LoggingTimer(message)) {

										db.updateIndexes(
											connection, tablesSQL, indexesSQL,
											true);
									}
								}
								catch (Exception exception) {
									_log.error(
										StringBundler.concat(
											"Unable to update database ",
											"indexes for ", servletContextName,
											" due to ",
											exception.getMessage()));
								}
							});
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				}));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

	private static final DCLSingleton<ExecutorService>
		_executorServiceDCLSingleton = new DCLSingleton<>();
	private static final List<Future<?>> _futures =
		Collections.synchronizedList(new ArrayList<Future<?>>());
	private static final Set<String> _processedServletContextNames =
		ConcurrentHashMap.newKeySet();

}