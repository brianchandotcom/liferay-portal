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

package com.liferay.object.internal.upgrade.v3_27_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Carolina Barbosa
 */
public class ObjectActionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select objectActionId, companyId, name from ObjectAction");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectAction set label = ? where objectActionId " +
						"= ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setString(
					1,
					LocalizationUtil.getXml(
						new LocalizedValuesMap() {
							{
								put(
									LocaleUtil.fromLanguageId(
										UpgradeProcessUtil.getDefaultLanguageId(
											resultSet.getLong("companyId"))),
									resultSet.getString("name"));
							}
						},
						"Label"));
				preparedStatement2.setLong(
					2, resultSet.getLong("objectActionId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"ObjectAction", "errorMessage STRING null", "label STRING null")
		};
	}

}