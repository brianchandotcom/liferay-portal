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

package com.liferay.oauth.client.persistence.internal.upgrade.v1_1_0;

import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Arthur Chan
 */
public class OAuthClientEntryOIDCUserInfoMapperJSONUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select oAuthClientEntryId from OAuthClientEntry");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							"update OAuthClientEntry set " +
								"oidcUserInfoMapperJSON = ? WHERE " +
									"oAuthClientEntryId = ?")) {

					preparedStatement2.setString(
						1,
						OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
					preparedStatement2.setLong(
						2, resultSet.getLong("oAuthClientEntryId"));

					preparedStatement2.execute();
				}
			}
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"OAuthClientEntry", "oidcUserInfoMapperJSON VARCHAR(3999) null")
		};
	}

}