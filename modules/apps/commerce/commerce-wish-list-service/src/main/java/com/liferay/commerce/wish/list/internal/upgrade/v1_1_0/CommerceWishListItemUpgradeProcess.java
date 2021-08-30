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

package com.liferay.commerce.wish.list.internal.upgrade.v1_1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceWishListItemUpgradeProcess extends UpgradeProcess {

	public CommerceWishListItemUpgradeProcess(
		CPDefinitionLocalService cpDefinitionLocalService,
		CPInstanceLocalService cpInstanceLocalService) {

		_cpDefinitionLocalService = cpDefinitionLocalService;
		_cpInstanceLocalService = cpInstanceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_addColumn("CommerceWishListItem", "CPInstanceUuid", "VARCHAR(75)");
		_addColumn("CommerceWishListItem", "CProductId", "LONG");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update CommerceWishListItem set CProductId = ?," +
					"CPInstanceUuid = ? where CPInstanceId = ?");
			Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select distinct CPInstanceId from CommerceWishListItem")) {

			while (resultSet.next()) {
				long cpInstanceId = resultSet.getLong("CPInstanceId");

				CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
					cpInstanceId);

				CPDefinition cpDefinition =
					_cpDefinitionLocalService.getCPDefinition(
						cpInstance.getCPDefinitionId());

				preparedStatement.setLong(1, cpDefinition.getCProductId());

				preparedStatement.setString(2, cpInstance.getCPInstanceUuid());
				preparedStatement.setLong(3, cpInstanceId);

				preparedStatement.execute();
			}
		}

		_dropColumn(CommerceWishListItemModelImpl.TABLE_NAME, "CPDefinitionId");
		_dropColumn(CommerceWishListItemModelImpl.TABLE_NAME, "CPInstanceId");
	}

	private void _addColumn(
			String tableName, String columnName, String columnType)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Adding column %s to table %s", columnName, tableName));
		}

		if (!hasColumn(tableName, columnName)) {
			alterTableAddColumn(tableName, columnName, columnType);
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

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceWishListItemUpgradeProcess.class);

	private final CPDefinitionLocalService _cpDefinitionLocalService;
	private final CPInstanceLocalService _cpInstanceLocalService;

}