/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

/**
 * This abstract class should be extended for startup processes that verify the
 * integrity of the database. They can be added as part of
 * <code>com.liferay.portal.verify.VerifyProcessSuite</code> or be executed
 * independently by being set in the portal.properties file. Each of these
 * processes should not cause any problems if run multiple times.
 *
 * @author Alexander Chow
 */
public abstract class VerifyProcess {

	public static final int ALWAYS = -1;

	public static final int NEVER = 0;

	public static final int ONCE = 1;

	public void runSQL(String template) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(template);
	}

	public void runSQL(String[] templates) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(templates);
	}

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		DB db = DBFactoryUtil.getDB();

		db.runSQLTemplate(path);
	}

	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		DB db = DBFactoryUtil.getDB();

		db.runSQLTemplate(path, failOnError);
	}

	public void verify() throws VerifyException {
		try {
			if (_log.isInfoEnabled()) {
				_log.info("Verifying " + getClass().getName());
			}

			doVerify();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	public void verify(VerifyProcess verifyProcess) throws VerifyException {
		verifyProcess.verify();
	}

	protected void doVerify() throws Exception {
	}

	/**
	 * @return the portal build number before {@link
	 *         com.liferay.portal.tools.DBUpgrader} has a chance to update it to
	 *         the value in {@link ReleaseInfo#getBuildNumber}
	 */
	protected int getBuildNumber() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select buildNumber from Release_ where servletContextName " +
					"= ?");

			ps.setString(1, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			rs = ps.executeQuery();

			rs.next();

			return rs.getInt(1);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected Set<String> getTableNames() throws IOException {
		if (_tableNames != null) {
			return _tableNames;
		}

		ClassLoader classLoader = PACLClassLoaderUtil.getContextClassLoader();

		String sql = StringUtil.read(
			classLoader,
			"com/liferay/portal/tools/sql/dependencies/portal-tables.sql");

		Matcher matcher = _pattern.matcher(sql);

		Set<String> tableNames = new HashSet<String>();

		while (matcher.find()) {
			String match = matcher.group(1);

			tableNames.add(match.toLowerCase());
		}

		_tableNames = tableNames;

		return tableNames;
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyProcess.class);

	private static Pattern _pattern = Pattern.compile(
		"create table (\\S*) \\(");

	private Set<String> _tableNames;

}