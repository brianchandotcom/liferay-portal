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

package com.liferay.portal.scheduler.quartz.internal;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = QuartzSchemaManager.class)
public class QuartzSchemaManager {

	@Activate
	protected void activate() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from QUARTZ_JOB_DETAILS");

			rs = ps.executeQuery();

			if (rs.next()) {
				return;
			}
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			con = DataAccess.getConnection();

			populateSchema(con);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	@Reference(unbind = "-")
	protected void setInfrastructureUtil(
		InfrastructureUtil infrastructureUtil) {
	}

	private void populateSchema(Connection con) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/META-INF/sql/quartz-tables.sql");

		if (inputStream == null) {
			throw new SystemException(
				"Unable to read /META-INF/sql/quartz-tables.sql");
		}

		String template = StringUtil.read(inputStream);

		DB db = DBManagerUtil.getDB();

		boolean autoCommit = con.getAutoCommit();

		try {
			con.setAutoCommit(false);

			db.runSQLTemplateString(con, template, false, false);

			con.commit();
		}
		catch (Exception e) {
			con.rollback();

			throw e;
		}
		finally {
			con.setAutoCommit(autoCommit);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QuartzSchemaManager.class);

}