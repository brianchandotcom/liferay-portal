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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.util.ClassLoaderUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This abstract class should be extended for startup processes that verify the
 * integrity of the database. They can be added as part of
 * <code>com.liferay.portal.verify.VerifyProcessSuite</code> or be executed
 * independently by being set in the portal.properties file. Each of these
 * processes should not cause any problems if run multiple times.
 *
 * @author Alexander Chow
 * @author Hugo Huijser
 */
public abstract class VerifyProcess extends BaseDBProcess {

	public static final int ALWAYS = -1;

	public static final int NEVER = 0;

	public static final int ONCE = 1;

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
	 *         the value in {@link
	 *         com.liferay.portal.kernel.util.ReleaseInfo#getBuildNumber}
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

	protected Set<String> getPortalTableNames() throws Exception {
		if (_portalTableNames != null) {
			return _portalTableNames;
		}

		ClassLoader classLoader = ClassLoaderUtil.getContextClassLoader();

		String sql = StringUtil.read(
			classLoader,
			"com/liferay/portal/tools/sql/dependencies/portal-tables.sql");

		Matcher matcher = _createTablePattern.matcher(sql);

		Set<String> tableNames = new HashSet<String>();

		while (matcher.find()) {
			String match = matcher.group(1);

			tableNames.add(StringUtil.toLowerCase(match));
		}

		_portalTableNames = tableNames;

		return tableNames;
	}

	protected boolean isPortalTableName(String tableName) throws Exception {
		Set<String> portalTableNames = getPortalTableNames();

		return portalTableNames.contains(StringUtil.toLowerCase(tableName));
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyProcess.class);

	private final Pattern _createTablePattern = Pattern.compile(
		"create table (\\S*) \\(");
	private Set<String> _portalTableNames;

}