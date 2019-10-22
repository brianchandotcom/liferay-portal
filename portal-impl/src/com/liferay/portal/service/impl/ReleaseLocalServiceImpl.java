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

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.NoSuchReleaseException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.upgrade.OlderVersionException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.service.base.ReleaseLocalServiceBaseImpl;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ReleaseLocalServiceImpl extends ReleaseLocalServiceBaseImpl {

	@Override
	public Release addRelease(String servletContextName, int buildNumber) {
		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.create(ReleaseConstants.DEFAULT_ID);
		}
		else {
			long releaseId = counterLocalService.increment();

			release = releasePersistence.create(releaseId);
		}

		Date now = new Date();

		release.setCreateDate(now);
		release.setModifiedDate(now);

		release.setServletContextName(servletContextName);
		release.setBuildNumber(buildNumber);

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release.setTestString(ReleaseConstants.TEST_STRING);
		}

		releasePersistence.update(release);

		return release;
	}

	@Override
	public Release addRelease(String servletContextName, String schemaVersion) {
		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.create(ReleaseConstants.DEFAULT_ID);
		}
		else {
			long releaseId = counterLocalService.increment();

			release = releasePersistence.create(releaseId);
		}

		Date now = new Date();

		release.setCreateDate(now);
		release.setModifiedDate(now);

		release.setServletContextName(servletContextName);
		release.setSchemaVersion(schemaVersion);

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release.setTestString(ReleaseConstants.TEST_STRING);
		}

		releasePersistence.update(release);

		return release;
	}

	@Override
	public void createTablesAndPopulate() {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Create tables and populate with default data");
			}

			DB db = DBManagerUtil.getDB();

			db.runSQLTemplate("portal-tables.sql", false);
			db.runSQLTemplate("portal-data-common.sql", false);
			db.runSQLTemplate("portal-data-counter.sql", false);
			db.runSQLTemplate("indexes.sql", false);
			db.runSQLTemplate("sequences.sql", false);

			addReleaseInfo();

			StartupHelperUtil.setDbNew(true);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
	}

	@Override
	public Release fetchRelease(String servletContextName) {
		if (Validator.isNull(servletContextName)) {
			throw new IllegalArgumentException("Servlet context name is null");
		}

		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.fetchByPrimaryKey(
				ReleaseConstants.DEFAULT_ID);
		}
		else {
			release = releasePersistence.fetchByServletContextName(
				servletContextName);
		}

		return release;
	}

	@Override
	@Transactional
	public int getBuildNumberOrCreate() throws PortalException {

		// Gracefully add version column

		DB db = DBManagerUtil.getDB();

		try {
			db.runSQL(
				"alter table Release_ add schemaVersion VARCHAR(75) null");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage());
			}
		}

		// Get release build number

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select buildNumber from Release_ where releaseId = " +
					ReleaseConstants.DEFAULT_ID);
			ResultSet rs = ps.executeQuery()) {

			int buildNumber = 0;

			if (rs.next()) {
				buildNumber = rs.getInt("buildNumber");
			}
			else {
				buildNumber = addReleaseInfo();
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Build number " + buildNumber);
			}

			// Gracefully add state_ column

			try {
				db.runSQL("alter table Release_ add state_ INTEGER");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			testSupportsStringCaseSensitiveQuery();

			return buildNumber;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		// Create tables and populate with default data

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SCHEMA_RUN_ENABLED))) {

			createTablesAndPopulate();

			testSupportsStringCaseSensitiveQuery();

			Release release = fetchRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			return release.getBuildNumber();
		}

		throw new NoSuchReleaseException("The database needs to be populated");
	}

	@Override
	public Release updateRelease(
			long releaseId, String schemaVersion, int buildNumber,
			Date buildDate, boolean verified)
		throws PortalException {

		Release release = releasePersistence.findByPrimaryKey(releaseId);

		release.setModifiedDate(new Date());
		release.setSchemaVersion(schemaVersion);
		release.setBuildNumber(buildNumber);
		release.setBuildDate(buildDate);
		release.setVerified(verified);

		releasePersistence.update(release);

		return release;
	}

	@Override
	public void updateRelease(
			String servletContextName, List<UpgradeProcess> upgradeProcesses,
			int buildNumber, int previousBuildNumber, boolean indexOnUpgrade)
		throws PortalException {

		if (buildNumber <= 0) {
			_log.error(
				"Skipping upgrade processes for " + servletContextName +
					" because \"release.info.build.number\" is not specified");

			return;
		}

		Release release = releaseLocalService.fetchRelease(servletContextName);

		if (release == null) {
			release = releaseLocalService.addRelease(
				servletContextName, previousBuildNumber);
		}

		if (buildNumber == release.getBuildNumber()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping upgrade processes for " + servletContextName +
						" because it is already up to date");
			}
		}
		else if (buildNumber < release.getBuildNumber()) {
			throw new OlderVersionException(
				"Skipping upgrade processes for " + servletContextName +
					" because you are trying to upgrade with an older version");
		}
		else {
			UpgradeProcessUtil.upgradeProcess(
				release.getBuildNumber(), upgradeProcesses, indexOnUpgrade);
		}

		releaseLocalService.updateRelease(
			release.getReleaseId(), release.getSchemaVersion(), buildNumber,
			null, true);
	}

	@Override
	public void updateRelease(
			String servletContextName, List<UpgradeProcess> upgradeProcesses,
			Properties unfilteredPortalProperties)
		throws Exception {

		int buildNumber = GetterUtil.getInteger(
			unfilteredPortalProperties.getProperty(
				PropsKeys.RELEASE_INFO_BUILD_NUMBER));

		int previousBuildNumber = GetterUtil.getInteger(
			unfilteredPortalProperties.getProperty(
				PropsKeys.RELEASE_INFO_PREVIOUS_BUILD_NUMBER),
			buildNumber);

		boolean indexOnUpgrade = GetterUtil.getBoolean(
			unfilteredPortalProperties.getProperty(PropsKeys.INDEX_ON_UPGRADE),
			PropsValues.INDEX_ON_UPGRADE);

		updateRelease(
			servletContextName, upgradeProcesses, buildNumber,
			previousBuildNumber, indexOnUpgrade);
	}

	@Override
	public void updateRelease(
		String servletContextName, String schemaVersion,
		String previousSchemaVersion) {

		Release release = releaseLocalService.fetchRelease(servletContextName);

		if (release == null) {
			if (previousSchemaVersion.equals("0.0.0")) {
				release = releaseLocalService.addRelease(
					servletContextName, previousSchemaVersion);
			}
			else {
				throw new IllegalStateException(
					"Unable to update release because it does not exist");
			}
		}

		String currentSchemaVersion = release.getSchemaVersion();

		if (Validator.isNull(currentSchemaVersion)) {
			currentSchemaVersion = "0.0.0";
		}

		if (!previousSchemaVersion.equals(currentSchemaVersion)) {
			StringBundler sb = new StringBundler(5);

			sb.append("Unable to update release because the previous schema ");
			sb.append("version ");
			sb.append(previousSchemaVersion);
			sb.append(" does not match the expected schema version ");
			sb.append(currentSchemaVersion);

			throw new IllegalStateException(sb.toString());
		}

		release.setSchemaVersion(schemaVersion);

		releasePersistence.update(release);
	}

	protected int addReleaseInfo() throws Exception {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				StringBundler.concat(
					"insert into Release_ (releaseId, createDate, ",
					"modifiedDate, servletContextName, schemaVersion, ",
					"buildNumber, verified) values (",
					ReleaseConstants.DEFAULT_ID, ", ?, ?, ?, ?, ?, ?)"))) {

			java.sql.Date now = new java.sql.Date(System.currentTimeMillis());

			ps.setDate(1, now);
			ps.setDate(2, now);

			ps.setString(3, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			Version latestSchemaVersion =
				PortalUpgradeProcess.getLatestSchemaVersion();

			ps.setString(4, latestSchemaVersion.toString());

			ps.setInt(5, ReleaseInfo.getBuildNumber());
			ps.setBoolean(6, false);

			ps.executeUpdate();
		}

		return ReleaseInfo.getBuildNumber();
	}

	protected void testSupportsStringCaseSensitiveQuery() {
		DB db = DBManagerUtil.getDB();

		int count = testSupportsStringCaseSensitiveQuery(
			ReleaseConstants.TEST_STRING);

		if (count == 0) {
			try {
				db.runSQL(
					"alter table Release_ add testString VARCHAR(1024) null");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			try {
				db.runSQL(
					"update Release_ set testString = '" +
						ReleaseConstants.TEST_STRING + "'");
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e.getMessage());
				}
			}

			count = testSupportsStringCaseSensitiveQuery(
				ReleaseConstants.TEST_STRING);
		}

		if (count == 0) {
			throw new SystemException(
				"Release_ table was not initialized properly");
		}

		count = testSupportsStringCaseSensitiveQuery(
			StringUtil.toUpperCase(ReleaseConstants.TEST_STRING));

		if (count == 0) {
			db.setSupportsStringCaseSensitiveQuery(true);
		}
		else {
			db.setSupportsStringCaseSensitiveQuery(false);
		}
	}

	protected int testSupportsStringCaseSensitiveQuery(String testString) {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select count(*) from Release_ where releaseId = ? and " +
					"testString = ?")) {

			ps.setLong(1, ReleaseConstants.DEFAULT_ID);
			ps.setString(2, testString);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseLocalServiceImpl.class);

}