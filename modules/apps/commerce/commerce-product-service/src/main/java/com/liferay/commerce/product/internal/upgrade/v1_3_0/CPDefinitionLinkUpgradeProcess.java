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

import com.liferay.commerce.product.model.impl.CPDefinitionLinkModelImpl;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ethan Bustad
 */
public class CPDefinitionLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn("CPDefinitionLink", "CProductId", "LONG");

		alterColumnName(
			"CPDefinitionLink", "CPDefinitionId1", "CPDefinitionId LONG");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update CPDefinitionLink set CProductId = ? where " +
					"CPDefinitionId2 = ?");
			Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select * from CPDefinitionLink")) {

			while (resultSet.next()) {
				long cpDefinitionId2 = resultSet.getLong("CPDefinitionId2");

				preparedStatement.setLong(1, _getCProductId(cpDefinitionId2));
				preparedStatement.setLong(2, cpDefinitionId2);

				preparedStatement.execute();
			}
		}

		alterTableDropColumn(
			CPDefinitionLinkModelImpl.TABLE_NAME, "CPDefinitionId2");
	}

	private long _getCProductId(long cpDefinitionId) throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select CProductId from CPDefinition where CPDefinitionId = " +
					cpDefinitionId)) {

			if (resultSet.next()) {
				return resultSet.getLong("CProductId");
			}
		}

		return 0;
	}

}