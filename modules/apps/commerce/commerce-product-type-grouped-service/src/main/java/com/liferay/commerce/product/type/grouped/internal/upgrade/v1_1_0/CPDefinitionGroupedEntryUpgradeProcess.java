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

package com.liferay.commerce.product.type.grouped.internal.upgrade.v1_1_0;

import com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ethan Bustad
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionGroupedEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addColumn(
			CPDefinitionGroupedEntryModelImpl.class,
			CPDefinitionGroupedEntryModelImpl.TABLE_NAME, "entryCProductId",
			"LONG");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement s = null;

		try {
			ps = connection.prepareStatement(
				"update CPDefinitionGroupedEntry set entryCProductId = ? " +
					"where entryCPDefinitionId = ?");

			s = connection.createStatement();

			rs = s.executeQuery("select * from CPDefinitionGroupedEntry");

			while (rs.next()) {
				long entryCPDefinitionId = rs.getLong("entryCPDefinitionId");

				long cProductId = _getCProductId(entryCPDefinitionId);

				ps.setLong(1, cProductId);

				ps.setLong(2, entryCPDefinitionId);

				ps.execute();
			}
		}
		finally {
			DataAccess.cleanUp(ps);
			DataAccess.cleanUp(s, rs);
		}

		_dropColumn(
			CPDefinitionGroupedEntryModelImpl.TABLE_NAME,
			"entryCPDefinitionId");
	}

	private void _addColumn(
			Class<?> entityClass, String tableName, String columnName,
			String columnType)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Adding column %s to table %s", columnName, tableName));
		}

		if (!hasColumn(tableName, columnName)) {
			alter(
				entityClass,
				new AlterTableAddColumn(
					columnName + StringPool.SPACE + columnType));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already exists on table %s", columnName,
						tableName));
			}
		}
	}

	private void _dropColumn(String tableName, String columnName)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Dropping column %s from table %s", columnName, tableName));
		}

		if (hasColumn(tableName, columnName)) {
			runSQL(
				StringBundler.concat(
					"alter table ", tableName, " drop column ", columnName));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already does not exist on table %s",
						columnName, tableName));
			}
		}
	}

	private long _getCProductId(long cpDefinitionId) throws Exception {
		Statement s = null;
		ResultSet rs = null;

		try {
			s = connection.createStatement();

			rs = s.executeQuery(
				"select CProductId from CPDefinition where CPDefinitionId = " +
					cpDefinitionId);

			if (rs.next()) {
				return rs.getLong("CProductId");
			}
		}
		finally {
			DataAccess.cleanUp(s, rs);
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionGroupedEntryUpgradeProcess.class);

}