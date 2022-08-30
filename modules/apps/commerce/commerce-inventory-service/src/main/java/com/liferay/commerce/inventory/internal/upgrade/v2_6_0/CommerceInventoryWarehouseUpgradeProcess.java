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

package com.liferay.commerce.inventory.internal.upgrade.v2_6_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Crescenzo Rega
 */
public class CommerceInventoryWarehouseUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterColumnType("CIWarehouse", "description", "STRING null");
		alterColumnType("CIWarehouse", "name", "STRING null");

		_updateLocalizedFileds();
	}

	private void _updateLocalizedFileds() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
			"update CIWarehouse set description = ?, name = ? where " +
			"CIWarehouseId = ?");

			 Statement s = connection.createStatement();
			 ResultSet resultSet = s.executeQuery(
				 "select CIWarehouseId, companyId, description, name from " +
				 "CIWarehouse")) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				String defaultLanguageId =
					UpgradeProcessUtil.getDefaultLanguageId(companyId);

				String descriptionXML = "";

				String description = resultSet.getString("description");

				if (description != null) {
					descriptionXML = LocalizationUtil.getXml(
						HashMapBuilder.put(
							defaultLanguageId, description
						).build(),
						defaultLanguageId, "description");
				}

				preparedStatement.setString(1, descriptionXML);

				String nameXML = "";

				String name = resultSet.getString("name");

				if (name != null) {
					nameXML = LocalizationUtil.getXml(
						HashMapBuilder.put(
							defaultLanguageId, name
						).build(),
						defaultLanguageId, "name");
				}

				preparedStatement.setString(2, nameXML);

				long commerceInventoryWarehouseId = resultSet.getLong(
					"CIWarehouseId");

				preparedStatement.setLong(3, commerceInventoryWarehouseId);

				preparedStatement.execute();
			}
		}
	}

}