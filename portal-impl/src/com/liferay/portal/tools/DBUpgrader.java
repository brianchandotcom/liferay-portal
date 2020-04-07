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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.transaction.TransactionsUtil;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalClassPathUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyGroup;
import com.liferay.portal.verify.VerifyProperties;
import com.liferay.portal.verify.VerifyResourcePermissions;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistrar;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DBUpgrader {

	public static void checkReleaseState() throws Exception {
		if (_getReleaseState() == ReleaseConstants.STATE_GOOD) {
			return;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("The database contains changes from a previous upgrade ");
		sb.append("attempt that failed. Please restore the old database and ");
		sb.append("file system and retry the upgrade. A patch may be ");
		sb.append("required if the upgrade failed due to a bug or an ");
		sb.append("unforeseen data permutation that resulted from a corrupt ");
		sb.append("database.");

		throw new IllegalStateException(sb.toString());
	}

	public static void checkRequiredBuildNumber(int requiredBuildNumber)
		throws PortalException {

		Release release = ReleaseLocalServiceUtil.getRelease(
			ReleaseConstants.DEFAULT_ID);

		int buildNumber = release.getBuildNumber();

		if (buildNumber > ReleaseInfo.getParentBuildNumber()) {
			StringBundler sb = new StringBundler(6);

			sb.append("Attempting to deploy an older Liferay Portal version. ");
			sb.append("Current build number is ");
			sb.append(buildNumber);
			sb.append(" and attempting to deploy number ");
			sb.append(ReleaseInfo.getParentBuildNumber());
			sb.append(".");

			throw new IllegalStateException(sb.toString());
		}
		else if (buildNumber < requiredBuildNumber) {
			String msg =
				"You must first upgrade to Liferay Portal " +
					requiredBuildNumber;

			System.out.println(msg);

			throw new RuntimeException(msg);
		}
	}

	public static void main(String[] args) {
		try {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			PortalClassPathUtil.initializeClassPaths(null);

			InitUtil.initWithSpring(true, false);

			StartupHelperUtil.printPatchLevel();

			upgrade();

			_checkClassNamesAndResourceActions();

			verify();

			DependencyManagerSyncUtil.sync();

			DLFileEntryTypeLocalServiceUtil.getBasicDocumentDLFileEntryType();

			_registerModuleServiceLifecycle("database.initialized");

			InitUtil.registerContext();

			_registerModuleServiceLifecycle("portal.initialized");

			_registerModuleServiceLifecycle("portlets.initialized");

			System.out.println(
				"\nCompleted Liferay core upgrade process in " +
					(stopWatch.getTime() / Time.SECOND) + " seconds");

			System.out.println(
				"Running modules upgrades. Connect to Gogo shell to check " +
					"the status.");
		}
		catch (Exception exception) {
			exception.printStackTrace();

			System.exit(1);
		}
	}

	public static void upgrade() throws Exception {

		// Disable database caching before upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Disable cache registry");
		}

		// Check required build number

		checkRequiredBuildNumber(ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER);

		try (Connection connection = DataAccess.getConnection()) {
			if (PortalUpgradeProcess.isInLatestSchemaVersion(connection)) {
				return;
			}
		}

		CacheRegistryUtil.setActive(false);

		// Upgrade

		Release release = ReleaseLocalServiceUtil.getRelease(
			ReleaseConstants.DEFAULT_ID);

		int buildNumber = release.getBuildNumber();

		if (_log.isDebugEnabled()) {
			_log.debug("Update build " + buildNumber);
		}

		_checkPermissionAlgorithm();
		checkReleaseState();

		if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
			TransactionsUtil.disableTransactions();
		}

		try {
			buildNumber = _getBuildNumberForMissedUpgradeProcesses(buildNumber);

			StartupHelperUtil.upgradeProcess(buildNumber);
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

		// Update indexes

		StartupHelperUtil.updateIndexes(true);

		// Update Release

		_updateReleaseBuildInfo();

		// Reload SQL

		CustomSQLUtil.reloadCustomSQL();
		SQLTransformer.reloadSQLTransformer();

		// Update company key

		if (StartupHelperUtil.isUpgraded()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Update company key");
			}

			_updateCompanyKey();
		}

		// Clear the caches only if the upgrade process was run

		if (_log.isDebugEnabled()) {
			_log.debug("Clear cache if upgrade process was run");
		}

		if (StartupHelperUtil.isUpgraded()) {
			PortalCacheHelperUtil.clearPortalCaches(
				PortalCacheManagerNames.MULTI_VM);
		}

		// Enable database caching after upgrade

		CacheRegistryUtil.setActive(true);

		// Register release service

		_registerReleaseService();
	}

	public static void verify() throws VerifyException {
		VerifyProperties verifyProperties = new VerifyProperties();

		verifyProperties.verify();

		VerifyGroup verifyGroup = new VerifyGroup();

		verifyGroup.verify();

		VerifyResourcePermissions verifyResourcePermissions =
			new VerifyResourcePermissions();

		verifyResourcePermissions.verify();
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

		ResourceActionLocalServiceUtil.checkResourceActions();
	}

	private static void _checkPermissionAlgorithm() throws Exception {
		long count = _getResourceCodesCount();

		if (count == 0) {
			return;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("Permission conversion to algorithm 6 has not been ");
		sb.append("completed. Please complete the conversion prior to ");
		sb.append("starting the portal. The conversion process is available ");
		sb.append("in portal versions starting with 5203 and prior to ");
		sb.append(ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER);
		sb.append(".");

		throw new IllegalStateException(sb.toString());
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

	private static int _getReleaseState() throws Exception {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select state_ from Release_ where releaseId = ?")) {

			ps.setLong(1, ReleaseConstants.DEFAULT_ID);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("state_");
				}
			}

			throw new IllegalArgumentException(
				"No Release exists with the primary key " +
					ReleaseConstants.DEFAULT_ID);
		}
	}

	private static long _getResourceCodesCount() throws Exception {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select count(*) from ResourceCode");
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1);
			}

			return 0;
		}
		catch (Exception exception) {
			return 0;
		}
	}

	private static void _registerModuleServiceLifecycle(
		String moduleServiceLifecycle) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"module.service.lifecycle", moduleServiceLifecycle
		).put(
			"service.vendor", ReleaseInfo.getVendor()
		).put(
			"service.version", ReleaseInfo.getVersion()
		).build();

		registry.registerService(
			ModuleServiceLifecycle.class,
			new ModuleServiceLifecycle() {
			},
			properties);
	}

	private static void _registerReleaseService() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistrar<Release> serviceRegistrar =
			registry.getServiceRegistrar(Release.class);

		Release release = ReleaseLocalServiceUtil.fetchRelease(
			ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"build.date", release.getBuildDate()
		).put(
			"build.number", release.getBuildNumber()
		).put(
			"servlet.context.name", release.getServletContextName()
		).build();

		serviceRegistrar.registerService(Release.class, release, properties);
	}

	private static void _updateCompanyKey() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("update CompanyInfo set key_ = null");
	}

	private static void _updateReleaseBuildInfo() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(
				"update Release_ set buildNumber = ?, buildDate = ? where " +
					"releaseId = ?")) {

			ps.setInt(1, ReleaseInfo.getParentBuildNumber());

			java.util.Date buildDate = ReleaseInfo.getBuildDate();

			ps.setDate(2, new Date(buildDate.getTime()));

			ps.setLong(3, ReleaseConstants.DEFAULT_ID);

			ps.executeUpdate();
		}
	}

	private static void _updateReleaseState(int state) throws Exception {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"update Release_ set modifiedDate = ?, state_ = ? where " +
					"releaseId = ?")) {

			ps.setDate(1, new Date(System.currentTimeMillis()));
			ps.setInt(2, state);
			ps.setLong(3, ReleaseConstants.DEFAULT_ID);

			ps.executeUpdate();
		}
	}

	private static final Version _VERSION_7010 = new Version(0, 0, 6);

	private static final Log _log = LogFactoryUtil.getLog(DBUpgrader.class);

}