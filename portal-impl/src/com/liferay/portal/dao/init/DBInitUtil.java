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

package com.liferay.portal.dao.init;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class DBInitUtil {

	public static void init() {
		DB db = DBManagerUtil.getDB();

		if (_checkDefaultRelease()) {
			_setSupportsStringCaseSensitiveQuery(db);

			return;
		}

		try {
			db.runSQL(
				"alter table Release_ add schemaVersion VARCHAR(75) null");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage());
			}
		}

		try {
			db.runSQL("alter table Release_ add state_ INTEGER");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage());
			}
		}

		if (_checkDefaultRelease()) {
			_setSupportsStringCaseSensitiveQuery(db);

			return;
		}

		// Create tables and populate with default data

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SCHEMA_RUN_ENABLED))) {

			_createTablesAndPopulate(db);

			_setSupportsStringCaseSensitiveQuery(db);
		}
	}

	private static void _addReleaseInfo() throws Exception {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				StringBundler.concat(
					"insert into Release_ (releaseId, createDate, ",
					"modifiedDate, servletContextName, schemaVersion, ",
					"buildNumber, verified) values (",
					ReleaseConstants.DEFAULT_ID, ", ?, ?, ?, ?, ?, ?)"))) {

			Date now = new Date(System.currentTimeMillis());

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
	}

	private static boolean _checkDefaultRelease() {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select buildNumber, schemaVersion, state_ from Release_ " +
					"where releaseId = " + ReleaseConstants.DEFAULT_ID);
			ResultSet rs = ps.executeQuery()) {

			if (!rs.next()) {
				_addReleaseInfo();
			}

			return true;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage(), e);
			}
		}

		return false;
	}

	private static void _createTablesAndPopulate(DB db) {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Create tables and populate with default data");
			}

			db.runSQLTemplate("portal-tables.sql", false);
			db.runSQLTemplate("portal-data-common.sql", false);
			db.runSQLTemplate("portal-data-counter.sql", false);
			db.runSQLTemplate("indexes.sql", false);
			db.runSQLTemplate("sequences.sql", false);

			_addReleaseInfo();

			StartupHelperUtil.setDbNew(true);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
	}

	private static boolean _hasDefaultReleaseWithTestString(String testString) {
		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(
				"select count(*) from Release_ where releaseId = ? and " +
					"testString = ?")) {

			ps.setLong(1, ReleaseConstants.DEFAULT_ID);
			ps.setString(2, testString);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next() && (rs.getInt(1) > 0)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}

		return false;
	}

	private static void _setSupportsStringCaseSensitiveQuery(DB db) {
		if (!_hasDefaultReleaseWithTestString(ReleaseConstants.TEST_STRING)) {
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

			if (!_hasDefaultReleaseWithTestString(
					ReleaseConstants.TEST_STRING)) {

				throw new SystemException(
					"Release_ table was not initialized properly");
			}
		}

		if (_hasDefaultReleaseWithTestString(
				StringUtil.toUpperCase(ReleaseConstants.TEST_STRING))) {

			db.setSupportsStringCaseSensitiveQuery(false);
		}
		else {
			db.setSupportsStringCaseSensitiveQuery(true);
		}
	}

	private DBInitUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(DBInitUtil.class);

}