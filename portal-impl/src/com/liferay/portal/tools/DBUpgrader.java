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
				"\nCompleted Liferay core upgrade and verify processes in " +
					(stopWatch.getTime() / Time.SECOND) + " seconds");

			System.out.println(
				"Running modules upgrades. Connect to Gogo shell to check " +
					"the status.");
		}
		catch (Exception e) {
			e.printStackTrace();

			System.exit(1);
		}
	}

	public static void upgrade() throws Exception {

		// Disable database caching before upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Disable cache registry");
		}

		CacheRegistryUtil.setActive(false);

		// Check required build number

		checkRequiredBuildNumber(ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER);

		try (Connection connection = DataAccess.getConnection()) {
			if (PortalUpgradeProcess.isInLatestSchemaVersion(connection)) {
				return;
			}
		}

		// Upgrade

		Release release = ReleaseLocalServiceUtil.getRelease(
			ReleaseConstants.DEFAULT_ID);

		int buildNumber = release.getBuildNumber();

		if (_log.isDebugEnabled()) {
			_log.debug("Update build " + buildNumber);
		}

		_checkPermissionAlgorithm();
		_checkReleaseState(_getReleaseState());

		if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
			TransactionsUtil.disableTransactions();
		}

		try {
			buildNumber = _getBuildNumberForMissedUpgradeProcesses(buildNumber);

			StartupHelperUtil.upgradeProcess(buildNumber);
		}
		catch (Exception e) {
			_updateReleaseState(ReleaseConstants.STATE_UPGRADE_FAILURE);

			throw e;
		}
		finally {
			if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
				TransactionsUtil.enableTransactions();
			}
		}

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
	}

	public static void verify() throws Exception {

		// Check release

		Release release = ReleaseLocalServiceUtil.fetchRelease(
			ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

		if (release == null) {
			release = ReleaseLocalServiceUtil.addRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getParentBuildNumber());
		}

		_checkReleaseState(release.getState());

		// Update indexes

		if (StartupHelperUtil.isUpgraded() ||
			PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP) {

			StartupHelperUtil.setDropIndexes(true);

			StartupHelperUtil.updateIndexes();
		}

		// Verify

		if (PropsValues.VERIFY_DATABASE_TRANSACTIONS_DISABLED) {
			TransactionsUtil.disableTransactions();
		}

		try {
			StartupHelperUtil.verifyProcess(release.isVerified());
		}
		catch (Exception e) {
			_updateReleaseState(ReleaseConstants.STATE_VERIFY_FAILURE);

			_log.error(
				"Unable to execute verify process: " + e.getMessage(), e);

			throw e;
		}
		finally {
			if (PropsValues.VERIFY_DATABASE_TRANSACTIONS_DISABLED) {
				TransactionsUtil.enableTransactions();
			}
		}

		// Update indexes

		if (PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP ||
			StartupHelperUtil.isUpgraded()) {

			StartupHelperUtil.updateIndexes(false);
		}

		// Update release

		boolean verified = StartupHelperUtil.isVerified();

		if (release.isVerified()) {
			verified = true;
		}

		release.setBuildNumber(ReleaseInfo.getParentBuildNumber());
		release.setBuildDate(ReleaseInfo.getBuildDate());
		release.setVerified(verified);

		release = ReleaseLocalServiceUtil.updateRelease(release);

		// Enable database caching after verify

		CacheRegistryUtil.setActive(true);

		// Register release service

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistrar<Release> serviceRegistrar =
			registry.getServiceRegistrar(Release.class);

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"build.date", release.getBuildDate()
		).put(
			"build.number", release.getBuildNumber()
		).put(
			"servlet.context.name", release.getServletContextName()
		).build();

		serviceRegistrar.registerService(Release.class, release, properties);
	}

	private static void _checkClassNamesAndResourceActions() {
		if (_log.isDebugEnabled()) {
			_log.debug("Check class names");
		}

		ClassNameLocalServiceUtil.checkClassNames();

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource actions");
		}

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

	private static void _checkReleaseState(int state) throws Exception {
		if (state == ReleaseConstants.STATE_GOOD) {
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
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select state_ from Release_ where releaseId = ?");

			ps.setLong(1, ReleaseConstants.DEFAULT_ID);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("state_");
			}

			throw new IllegalArgumentException(
				"No Release exists with the primary key " +
					ReleaseConstants.DEFAULT_ID);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static long _getResourceCodesCount() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select count(*) from ResourceCode");

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

			return 0;
		}
		catch (Exception e) {
			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

	private static void _updateCompanyKey() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL("update Company set key_ = null");
	}

	private static void _updateReleaseState(int state) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Release_ set modifiedDate = ?, state_ = ? where " +
					"releaseId = ?");

			ps.setDate(1, new Date(System.currentTimeMillis()));
			ps.setInt(2, state);
			ps.setLong(3, ReleaseConstants.DEFAULT_ID);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final Version _VERSION_7010 = new Version(0, 0, 6);

	private static final Log _log = LogFactoryUtil.getLog(DBUpgrader.class);

}