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

package com.liferay.portal.tools;

import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.module.util.ServiceLatch;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.transaction.TransactionsUtil;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.log.UpgradeLogContext;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.VerifyProcessSuite;
import com.liferay.portal.verify.VerifyProperties;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;

import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.core.Appender;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DBUpgrader {

	public static void checkReleaseState() throws Exception {
		if (_getReleaseColumnValue("state_") == ReleaseConstants.STATE_GOOD) {
			return;
		}

		if (StartupHelperUtil.isUpgrading()) {
			try (Connection connection = DataAccess.getConnection()) {
				if (PortalUpgradeProcess.supportsRetry(connection)) {
					System.out.println("Retrying upgrade");

					return;
				}
			}
		}

		throw new IllegalStateException(
			StringBundler.concat(
				"The database contains changes from a previous upgrade ",
				"attempt that failed. Please restore the old database and ",
				"file system and retry the upgrade. A patch may be required ",
				"if the upgrade failed due to a bug or an unforeseen data ",
				"permutation that resulted from a corrupt database."));
	}

	public static void checkRequiredBuildNumber(int requiredBuildNumber)
		throws Exception {

		int buildNumber = _getReleaseColumnValue("buildNumber");

		if (buildNumber > ReleaseInfo.getParentBuildNumber()) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Attempting to deploy an older Liferay Portal version. ",
					"Current build number is ", buildNumber,
					" and attempting to deploy number ",
					ReleaseInfo.getParentBuildNumber(), "."));
		}
		else if (buildNumber < requiredBuildNumber) {
			String msg =
				"You must first upgrade to Liferay Portal " +
					requiredBuildNumber;

			System.out.println(msg);

			throw new RuntimeException(msg);
		}
	}

	public static long getUpgradeTime() {
		if (_stopWatch == null) {
			return 0;
		}

		return _stopWatch.getTime();
	}

	public static boolean isUpgradeClient() {
		return _upgradeClient;
	}

	public static boolean isUpgradeDatabaseAutoRunEnabled() {
		if (PortalRunMode.isTestMode()) {
			if (PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME.contains("hsql")) {
				return false;
			}

			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.UPGRADE_DATABASE_AUTO_RUN));
		}

		return _UPGRADE_DATABASE_AUTO_RUN;
	}

	public static void main(String[] args) {
		String result = "Completed";

		_upgradeClient = true;

		try {
			_initUpgradeStopwatch();

			PortalClassPathUtil.initializeClassPaths(null);

			InitUtil.initWithSpring(
				ListUtil.fromArray(
					PropsUtil.getArray(PropsKeys.SPRING_CONFIGS)),
				true, false, () -> StartupHelperUtil.setUpgrading(true));

			StartupHelperUtil.printPatchLevel();

			upgradePortal();

			InitUtil.registerContext();

			upgradeModules();

			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			Collection<?> collection = bundleContext.getServiceReferences(
				Store.class, "(default=true)");

			if (collection.isEmpty()) {
				throw new IllegalStateException("Missing default Store");
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			result = "Failed";
		}
		finally {
			StartupHelperUtil.setUpgrading(false);

			System.out.println(
				StringBundler.concat(
					"\n", result, " Liferay upgrade process in ",
					_stopWatch.getTime() / Time.SECOND, " seconds"));
		}

		System.out.println("Exiting DBUpgrader#main(String[]).");
	}

	public static void startUpgradeLogAppender() {
		if (_stopWatch == null) {
			_initUpgradeStopwatch();
		}

		ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

		serviceLatch.<Appender>waitFor(
			StringBundler.concat(
				"(&(appender.name=UpgradeLogAppender)(objectClass=",
				Appender.class.getName(), "))"),
			appender -> {
				_appender = appender;

				_appender.start();
			});
		serviceLatch.openOn(
			() -> {
			});
	}

	public static void stopUpgradeLogAppender() {
		if (_appender != null) {
			_stopWatch.stop();

			_appender.stop();
		}

		if (_appenderServiceReference != null) {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			bundleContext.ungetService(_appenderServiceReference);
		}
	}

	public static void upgradeModules() {
		_registerModuleServiceLifecycle("portal.initialized");

		DependencyManagerSyncUtil.sync();

		PortalCacheHelperUtil.clearPortalCaches(
			PortalCacheManagerNames.MULTI_VM);

		_registerModuleServiceLifecycle("portlets.initialized");
	}

	public static void upgradePortal() throws Exception {
		try {
			UpgradeLogContext.setContext(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			VerifyProperties.verify();

			if (FeatureFlagManagerUtil.isEnabled("LPS-157670")) {
				checkRequiredBuildNumber(
					ReleaseInfo.RELEASE_6_1_0_BUILD_NUMBER);
			}
			else {
				checkRequiredBuildNumber(
					ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER);
			}

			checkReleaseState();

			int buildNumber = _getReleaseColumnValue("buildNumber");

			try (Connection connection = DataAccess.getConnection()) {
				if (PortalUpgradeProcess.isInLatestSchemaVersion(connection) &&
					(buildNumber == ReleaseInfo.getParentBuildNumber())) {

					_checkClassNamesAndResourceActions();

					return;
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Disable cache registry");
			}

			CacheRegistryUtil.setActive(false);

			if (_log.isDebugEnabled()) {
				_log.debug("Update build " + buildNumber);
			}

			if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
				TransactionsUtil.disableTransactions();
			}

			try {
				buildNumber = _getBuildNumberForMissedUpgradeProcesses(
					buildNumber);

				StartupHelperUtil.upgradeProcess(buildNumber);

				_updateReleaseState(ReleaseConstants.STATE_GOOD);
			}
			catch (Exception exception) {
				_updateReleaseState(ReleaseConstants.STATE_UPGRADE_FAILURE);

				throw exception;
			}
			finally {
				if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
					TransactionsUtil.enableTransactions();
				}
			}

			IndexUpdaterUtil.updatePortalIndexes();

			_updateReleaseBuildInfo();

			CustomSQLUtil.reloadCustomSQL();
			SQLTransformer.reloadSQLTransformer();

			if (_log.isDebugEnabled()) {
				_log.debug("Update company key");
			}

			_updateCompanyKey();

			PortalCacheHelperUtil.clearPortalCaches(
				PortalCacheManagerNames.MULTI_VM);

			CacheRegistryUtil.setActive(true);

			_checkClassNamesAndResourceActions();

			verify();

			DLFileEntryTypeLocalServiceUtil.getBasicDocumentDLFileEntryType();
		}
		finally {
			UpgradeLogContext.clearContext();
		}
	}

	public static void verify() throws Exception {
		VerifyProcessSuite verifyProcessSuite = new VerifyProcessSuite();

		verifyProcessSuite.verify();
	}

	private static void _checkClassNamesAndResourceActions() {
		if (_log.isDebugEnabled()) {
			_log.debug("Check class names");
		}

		ClassNameLocalServiceUtil.checkClassNames();

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource actions");
		}

		StartupHelperUtil.initResourceActions();
	}

	private static int _getBuildNumberForMissedUpgradeProcesses(int buildNumber)
		throws Exception {

		if (buildNumber == ReleaseInfo.RELEASE_7_0_10_BUILD_NUMBER) {
			try (Connection connection = DataAccess.getConnection()) {
				Version schemaVersion =
					PortalUpgradeProcess.getCurrentSchemaVersion(connection);

				if (!schemaVersion.equals(_VERSION_7010)) {
					return ReleaseInfo.RELEASE_7_0_1_BUILD_NUMBER;
				}
			}
		}

		return buildNumber;
	}

	private static int _getReleaseColumnValue(String columnName)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select " + columnName +
					" from Release_ where releaseId = ?")) {

			preparedStatement.setLong(1, ReleaseConstants.DEFAULT_ID);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(columnName);
				}
			}

			throw new IllegalArgumentException(
				"No Release exists with the primary key " +
					ReleaseConstants.DEFAULT_ID);
		}
	}

	private static void _initUpgradeStopwatch() {
		_stopWatch = new StopWatch();

		_stopWatch.start();
	}

	private static void _registerModuleServiceLifecycle(
		String moduleServiceLifecycle) {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			ModuleServiceLifecycle.class,
			new ModuleServiceLifecycle() {
			},
			HashMapDictionaryBuilder.<String, Object>put(
				"module.service.lifecycle", moduleServiceLifecycle
			).put(
				"service.vendor", ReleaseInfo.getVendor()
			).put(
				"service.version", ReleaseInfo.getVersion()
			).build());
	}

	private static void _updateCompanyKey() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("update CompanyInfo set key_ = null");
	}

	private static void _updateReleaseBuildInfo() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Release_ set buildNumber = ?, buildDate = ? where " +
					"releaseId = ?")) {

			preparedStatement.setInt(1, ReleaseInfo.getParentBuildNumber());

			java.util.Date buildDate = ReleaseInfo.getBuildDate();

			preparedStatement.setDate(2, new Date(buildDate.getTime()));

			preparedStatement.setLong(3, ReleaseConstants.DEFAULT_ID);

			preparedStatement.executeUpdate();
		}
	}

	private static void _updateReleaseState(int state) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Release_ set modifiedDate = ?, state_ = ? where " +
					"releaseId = ?")) {

			preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
			preparedStatement.setInt(2, state);
			preparedStatement.setLong(3, ReleaseConstants.DEFAULT_ID);

			preparedStatement.executeUpdate();
		}
	}

	private static final boolean _UPGRADE_DATABASE_AUTO_RUN;

	private static final Version _VERSION_7010 = new Version(0, 0, 6);

	private static final Log _log = LogFactoryUtil.getLog(DBUpgrader.class);

	private static volatile Appender _appender;
	private static volatile ServiceReference<Appender>
		_appenderServiceReference;
	private static volatile StopWatch _stopWatch;
	private static volatile boolean _upgradeClient;

	static {
		if (PropsValues.JDBC_DEFAULT_DRIVER_CLASS_NAME.contains("hsql")) {
			_UPGRADE_DATABASE_AUTO_RUN = false;
		}
		else {
			_UPGRADE_DATABASE_AUTO_RUN = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.UPGRADE_DATABASE_AUTO_RUN));
		}
	}

}