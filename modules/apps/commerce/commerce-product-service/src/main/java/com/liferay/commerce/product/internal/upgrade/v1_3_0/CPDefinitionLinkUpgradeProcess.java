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

package com.liferay.commerce.product.internal.upgrade.v1_3_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ethan Bustad
 */
public class CPDefinitionLinkUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn(
			CPDefinitionLinkModelImpl.class,
			CPDefinitionLinkModelImpl.TABLE_NAME, "CProductId", "LONG");

		_renameColumn(
			CPDefinitionLinkModelImpl.class,
			CPDefinitionLinkModelImpl.TABLE_NAME, "CPDefinitionId1",
			"CPDefinitionId LONG");

		try (PreparedStatement ps = connection.prepareStatement(
				"update CPDefinitionLink set CProductId = ? where " +
					"CPDefinitionId2 = ?");
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("select * from CPDefinitionLink")) {

			while (rs.next()) {
				long cpDefinitionId2 = rs.getLong("CPDefinitionId2");

				long cProductId = _getCProductId(cpDefinitionId2);

				ps.setLong(1, cProductId);

				ps.setLong(2, cpDefinitionId2);

				ps.execute();
			}
		}

		dropColumn(CPDefinitionLinkModelImpl.TABLE_NAME, "CPDefinitionId2");
	}

	private long _getCProductId(long cpDefinitionId) throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select CProductId from CPDefinition where CPDefinitionId = " +
					cpDefinitionId)) {

			if (rs.next()) {
				return rs.getLong("CProductId");
			}
		}

		return 0;
	}

	private void _renameColumn(
			Class<?> tableClass, String tableName, String oldColumnName,
			String newColumnName)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Renaming column %s to %s in table %s", oldColumnName,
					newColumnName, tableName));
		}

		String newColumnSimpleName = StringUtil.extractFirst(
			newColumnName, StringPool.SPACE);

		if (!hasColumn(tableName, newColumnSimpleName)) {
			alter(
				tableClass, new AlterColumnName(oldColumnName, newColumnName));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already exists on table %s", newColumnName,
						tableName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionLinkUpgradeProcess.class);

}