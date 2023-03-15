/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.internal.upgrade.v15_0_0;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Marcos Martins
 */
public class UpgradeFaroProjectUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select faroProjectId, createTime, subscription from " +
					"OSBFaro_FaroProject")) {

			try (Statement statement = connection.createStatement();
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					JSONObject subscriptionJSONObject =
						JSONFactoryUtil.createJSONObject(
							resultSet.getString("subscription"));

					long startDate = subscriptionJSONObject.getLong(
						"startDate", 0);

					if (startDate != 0) {
						continue;
					}

					subscriptionJSONObject.put(
						"startDate", resultSet.getLong("createTime"));

					statement.addBatch(
						String.format(
							"update OSBFaro_FaroProject set subscription = '" +
								"%s' where faroProjectId = %s",
							subscriptionJSONObject.toString(),
							resultSet.getLong("faroProjectId")));
				}

				statement.executeBatch();
			}
		}
	}

}