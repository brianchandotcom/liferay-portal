/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools;

import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.db.partition.DBPartitionUtil;
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
		try (Connection connection = DataAccess.getConnection()) {
			if (PortalUpgradeProcess.getCurrentState(connection) ==
					ReleaseConstants.STATE_GOOD) {

				return;
			}

			if (StartupHelperUtil.isUpgrading() &&
				PortalUpgradeProcess.supportsRetry(connection)) {

				System.out.println("Retrying upgrade");

				return;
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

		int buildNumber = _getBuildNumber();

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

			upgradeModules(false);

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

	public static void upgradeModules(boolean autoUpgrade) {
		_registerModuleServiceLifecycle("portal.initialized");

		if (!autoUpgrade) {
			DependencyManagerSyncUtil.sync();
		}

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

			int buildNumber = _getBuildNumber();

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

				try (Connection connection = DataAccess.getConnection()) {
					PortalUpgradeProcess.updateState(
						connection, ReleaseConstants.STATE_GOOD);
				}
			}
			catch (Exception exception) {
				try (Connection connection = DataAccess.getConnection()) {
					PortalUpgradeProcess.updateState(
						connection, ReleaseConstants.STATE_UPGRADE_FAILURE);
				}

				throw exception;
			}
			finally {
				if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
					TransactionsUtil.enableTransactions();
				}
			}

			IndexUpdaterUtil.updatePortalIndexes();

			try (Connection connection = DataAccess.getConnection()) {
				PortalUpgradeProcess.updateBuildInfo(connection);
			}

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

		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> ClassNameLocalServiceUtil.checkClassNames());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource actions");
		}

		StartupHelperUtil.initResourceActions();
	}

	private static int _getBuildNumber() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			return PortalUpgradeProcess.getCurrentBuildNumber(connection);
		}
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