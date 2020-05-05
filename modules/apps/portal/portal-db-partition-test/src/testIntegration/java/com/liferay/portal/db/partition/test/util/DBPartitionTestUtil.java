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

package com.liferay.portal.db.partition.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.init.DBInitUtil;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Alberto Chaparro
 */
public class DBPartitionTestUtil {

	public static void disableDBPartition() {
		if (_DATABASE_PARTITION_ENABLED) {
			return;
		}

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", _currentDataSource);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", false);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_INSTANCE_ID",
			StringPool.BLANK);
		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource", _currentDataSource);
	}

	public static void enableDBPartition() throws SQLException {
		if (_DATABASE_PARTITION_ENABLED) {
			return;
		}

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", true);

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_INSTANCE_ID",
			_DB_PARTITION_INSTANCE_ID);

		DBPartitionUtil.setDefaultCompanyId(PortalUtil.getDefaultCompanyId());

		DataSource dbPartitionDataSource = DBPartitionUtil.wrapDataSource(
			_currentDataSource);

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", dbPartitionDataSource);
		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource", dbPartitionDataSource);
	}

	public static String getSchemaName(long companyId) {
		if (_DATABASE_PARTITION_ENABLED) {
			return PropsUtil.get("database.partition.instance.id") +
				StringPool.UNDERLINE + companyId;
		}

		return _DB_PARTITION_INSTANCE_ID + StringPool.UNDERLINE + companyId;
	}

	private static final boolean _DATABASE_PARTITION_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get("database.partition.enabled"));

	private static final String _DB_PARTITION_INSTANCE_ID = "dbPartitionTest";

	private static final DataSource _currentDataSource =
		ReflectionTestUtil.getFieldValue(DBInitUtil.class, "_dataSource");

}