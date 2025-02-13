/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_5_0;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Tavares
 */
public class ObjectEntryDefaultLanguageIdUpgradeProcess extends UpgradeProcess {

	public ObjectEntryDefaultLanguageIdUpgradeProcess(
		GroupLocalService groupLocalService) {

		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId, objectDefinitionId, scope from " +
					"ObjectDefinition");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"update ObjectEntry set defaultLanguageId = ? where " +
					"objectDefinitionId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select distinct groupId from ObjectEntry where " +
					"objectDefinitionId = ?");
			PreparedStatement preparedStatement4 = connection.prepareStatement(
				"update ObjectEntry set defaultLanguageId = ? where groupId " +
					"= ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long objectDefinitionId = resultSet.getLong(
					"objectDefinitionId");
				String scope = resultSet.getString("scope");

				if (scope.equals(ObjectDefinitionConstants.SCOPE_COMPANY)) {
					preparedStatement2.setString(
						1,
						UpgradeProcessUtil.getDefaultLanguageId(
							resultSet.getLong("companyId")));
					preparedStatement2.setLong(2, objectDefinitionId);

					preparedStatement2.addBatch();
				}
				else {
					preparedStatement3.setLong(1, objectDefinitionId);

					try (ResultSet resultSet2 =
							preparedStatement3.executeQuery()) {

						while (resultSet2.next()) {
							long groupId = resultSet2.getLong("groupId");

							Group group = _groupLocalService.fetchGroup(
								groupId);

							if (group == null) {
								continue;
							}

							preparedStatement4.setString(
								1, group.getDefaultLanguageId());
							preparedStatement4.setLong(2, groupId);

							preparedStatement4.addBatch();
						}
					}
				}
			}

			preparedStatement2.executeBatch();

			preparedStatement4.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"ObjectEntry", "defaultLanguageId VARCHAR(75) null")
		};
	}

	private final GroupLocalService _groupLocalService;

}