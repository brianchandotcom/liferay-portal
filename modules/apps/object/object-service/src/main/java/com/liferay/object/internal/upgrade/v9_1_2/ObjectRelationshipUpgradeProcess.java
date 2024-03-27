/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.object.internal.upgrade.v9_0_3;

import com.liferay.object.internal.model.listener.UserModelListener;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Thalles Montenegro
 */
public class ObjectRelationshipUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			SQLTransformer.transform(
				StringBundler.concat(
					"SELECT d.objectDefinitionId " +
					"FROM ObjectDefinition d " +
					"LEFT JOIN User_ u ON d.userId = u.userId " +
					"WHERE u.userId IS NULL;")));

			 PreparedStatement preparedStatement2 = connection.prepareStatement(
				 SQLTransformer.transform(
					 "SELECT u.userId FROM User_ u where u.screenName = 'default-service-account'"
				 )
			 );

			 PreparedStatement preparedStatement3 = connection.prepareStatement(
				 SQLTransformer.transform(
						 "UPDATE ObjectDefinition set userId = ? WHERE objectDefinitionId = ?")
			 );

			 ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			ResultSet resultSet2 = preparedStatement2.executeQuery();

			while(resultSet1.next()) {

				long objectDefinitionId = resultSet1.getLong("objectDefinitionId");

				long defaultAccountUserId = resultSet2.getLong("userId");

				preparedStatement3.setLong(1, defaultAccountUserId);
				preparedStatement3.setLong(2, objectDefinitionId);

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}
}