/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_1_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Thalles Montenegro
 */
public class ObjectRelationshipUpgradeProcess extends UpgradeProcess {

	public ObjectRelationshipUpgradeProcess(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _updateUserIdOnObjectDefinition(company.getCompanyId()));
	}

	private long _getDefaultServiceAccountId(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select userId from User_ where screenName = " +
					"'default-service-account' and companyId = ?")) {

			preparedStatement.setLong(1, companyId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("userId");
			}

			throw new UpgradeException("Default service account is null");
		}
	}

	private void _updateUserIdOnObjectDefinition(long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			StringBundler.concat(
				"select ObjectDefinition.objectDefinitionId from ",
				"ObjectDefinition left join User_ on ObjectDefinition.userId ",
				"= User_.userId where User_.userId is null and ",
				"ObjectDefinition.companyId = ?"));

			 PreparedStatement preparedStatement2 = connection.prepareStatement(
				 "update ObjectDefinition set userId = ? where " +
				 "objectDefinitionId = ? and companyId = ?")) {

			preparedStatement1.setLong(1, companyId);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				long userId = _getDefaultServiceAccountId(companyId);

				preparedStatement2.setLong(1, userId);

				preparedStatement2.setLong(
					2, resultSet1.getLong("objectDefinitionId"));
				preparedStatement2.setLong(3, companyId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final CompanyLocalService _companyLocalService;

}