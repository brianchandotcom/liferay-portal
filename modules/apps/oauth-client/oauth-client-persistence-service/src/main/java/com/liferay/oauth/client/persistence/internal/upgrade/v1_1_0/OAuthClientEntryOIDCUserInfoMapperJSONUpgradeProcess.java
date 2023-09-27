/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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