/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v2_1_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Time;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Arthur Chan
 */
public class OpenIdConnectSessionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select openIdConnectSessionId, modifiedDate, accessToken " +
					"from OpenIdConnectSession");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long openIdConnectSessionId = resultSet.getLong(
					"openIdConnectSessionId");

				try {
					_upgradeOpenIdConnectSession(
						openIdConnectSessionId,
						resultSet.getTimestamp("modifiedDate"),
						AccessToken.parse(
							JSONObjectUtils.parse(
								resultSet.getString("accessToken"))));
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}

					runSQL(
						"delete from OpenIdConnectSession where " +
							"openIdConnectSessionId = " +
								openIdConnectSessionId);
				}
			}
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"OpenIdConnectSession", "accessTokenExpirationDate DATE null")
		};
	}

	private void _upgradeOpenIdConnectSession(
			long openIdConnectSessionId, Timestamp modifiedDate,
			AccessToken accessToken)
		throws Exception {

		long accessTokenLifetime = accessToken.getLifetime() * Time.SECOND;

		if (accessTokenLifetime == 0) {
			accessTokenLifetime = Time.HOUR;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update OpenIdConnectSession set accessTokenExpirationDate = " +
					"? where openIdConnectSessionId = ?")) {

			preparedStatement.setTimestamp(
				1, new Timestamp(modifiedDate.getTime() + accessTokenLifetime));
			preparedStatement.setLong(2, openIdConnectSessionId);

			preparedStatement.execute();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionUpgradeProcess.class);

}