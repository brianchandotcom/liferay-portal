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

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.aop.Skip;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManager;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManagerUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DBUpgrader {

	public static void main(String[] args) {
		try {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			InitUtil.initWithSpring(true);

			upgrade();
			verify();

			System.out.println(
				"\nCompleted upgrade and verify processes in " +
					(stopWatch.getTime() / Time.SECOND) + " seconds");

			System.exit(0);
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

		// Check release

		int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

		if (buildNumber > ReleaseInfo.getParentBuildNumber()) {
			StringBundler sb = new StringBundler(6);

			sb.append("Attempting to deploy an older Liferay Portal version. ");
			sb.append("Current build version is ");
			sb.append(buildNumber);
			sb.append(" and attempting to deploy version ");
			sb.append(ReleaseInfo.getParentBuildNumber());
			sb.append(".");

			throw new IllegalStateException(sb.toString());
		}
		else if (buildNumber < ReleaseInfo.RELEASE_5_2_3_BUILD_NUMBER) {
			String msg = "You must first upgrade to Liferay Portal 5.2.3";

			System.out.println(msg);

			throw new RuntimeException(msg);
		}

		// Upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Update build " + buildNumber);
		}

		_checkPermissionAlgorithm();
		_checkReleaseState(_getReleaseState());

		if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
			_disableTransactions();
		}

		try {
			StartupHelperUtil.upgradeProcess(buildNumber);
		}
		catch (Exception e) {
			_updateReleaseState(ReleaseConstants.STATE_UPGRADE_FAILURE);

			throw e;
		}
		finally {
			if (PropsValues.UPGRADE_DATABASE_TRANSACTIONS_DISABLED) {
				_enableTransactions();
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

		// Check class names

		if (_log.isDebugEnabled()) {
			_log.debug("Check class names");
		}

		ClassNameLocalServiceUtil.checkClassNames();

		// Check resource actions

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource actions");
		}

		ResourceActionLocalServiceUtil.checkResourceActions();

		// Delete temporary images

		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		_deleteTempImages();

		// Clear the caches only if the upgrade process was run

		if (_log.isDebugEnabled()) {
			_log.debug("Clear cache if upgrade process was run");
		}

		if (StartupHelperUtil.isUpgraded()) {
			MultiVMPoolUtil.clear();
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

		if (PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP) {
			StartupHelperUtil.setDropIndexes(true);

			StartupHelperUtil.updateIndexes();
		}
		else if (StartupHelperUtil.isUpgraded()) {
			StartupHelperUtil.updateIndexes();
		}

		// Verify

		if (PropsValues.VERIFY_DATABASE_TRANSACTIONS_DISABLED) {
			_disableTransactions();
		}

		boolean newBuildNumber = false;

		if (ReleaseInfo.getBuildNumber() > release.getBuildNumber()) {
			newBuildNumber = true;
		}

		try {
			StartupHelperUtil.verifyProcess(
				newBuildNumber, release.isVerified());
		}
		catch (Exception e) {
			_updateReleaseState(ReleaseConstants.STATE_VERIFY_FAILURE);

			throw e;
		}
		finally {
			if (PropsValues.VERIFY_DATABASE_TRANSACTIONS_DISABLED) {
				_enableTransactions();
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

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), ReleaseInfo.getParentBuildNumber(),
			ReleaseInfo.getBuildDate(), verified);

		// Enable database caching after verify

		CacheRegistryUtil.setActive(true);
	}

	private static void _checkPermissionAlgorithm() throws Exception {
		long count = _getResourceCodesCount();

		if (count == 0) {
			return;
		}

		StringBundler sb = new StringBundler(8);

		sb.append("Permission conversion to algorithm 6 has not been ");
		sb.append("completed. Please complete the conversion prior to ");
		sb.append("starting the portal. The conversion process is ");
		sb.append("available in portal versions starting with ");
		sb.append(ReleaseInfo.RELEASE_5_2_3_BUILD_NUMBER);
		sb.append(" and prior to ");
		sb.append(ReleaseInfo.RELEASE_6_2_0_BUILD_NUMBER);
		sb.append(".");

		throw new IllegalStateException(sb.toString());
	}

	private static void _checkReleaseState(int state) throws Exception {
		if (state == ReleaseConstants.STATE_GOOD) {
			return;
		}

		StringBundler sb = new StringBundler(6);

		sb.append("The database contains changes from a previous ");
		sb.append("upgrade attempt that failed. Please restore the old ");
		sb.append("database and file system and retry the upgrade. A ");
		sb.append("patch may be required if the upgrade failed due to a");
		sb.append(" bug or an unforeseen data permutation that resulted ");
		sb.append("from a corrupt database.");

		throw new IllegalStateException(sb.toString());
	}

	private static void _deleteTempImages() throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(_DELETE_TEMP_IMAGES_1);
		db.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	private static void _disableTransactions() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Disable transactions");
		}

		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = false;

		Field field = ReflectionUtil.getDeclaredField(
			ServiceBeanAopCacheManager.class, "_annotations");

		field.set(
			null,
			new HashMap<MethodInvocation, Annotation[]>() {

				@Override
				public Annotation[] get(Object key) {
					return _annotations;
				}

				private Annotation[] _annotations = new Annotation[] {
					new Skip() {

						@Override
						public Class<? extends Annotation> annotationType() {
							return Skip.class;
						}

					}
				};

			}
		);
	}

	private static void _enableTransactions() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Enable transactions");
		}

		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SPRING_HIBERNATE_SESSION_DELEGATED));

		Field field = ReflectionUtil.getDeclaredField(
			ServiceBeanAopCacheManager.class, "_annotations");

		field.set(
			null, new ConcurrentHashMap<MethodInvocation, Annotation[]>());

		ServiceBeanAopCacheManagerUtil.reset();
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
				int count = rs.getInt(1);

				return count;
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

	private static void _updateCompanyKey() throws Exception {
		DB db = DBFactoryUtil.getDB();

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

	private static final String _DELETE_TEMP_IMAGES_1 =
		"delete from Image where imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage where tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"delete from JournalArticleImage where tempImage = TRUE";

	private static final Log _log = LogFactoryUtil.getLog(DBUpgrader.class);

}