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
				Timestamp modifiedDate = resultSet.getTimestamp("modifiedDate");
				String accessTokenJSON = resultSet.getString("accessToken");

				try {
					_upgradeOpenIdConnectSession(
						openIdConnectSessionId, modifiedDate, accessTokenJSON);
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
			String accessTokenJSON)
		throws Exception {

		AccessToken accessToken = AccessToken.parse(
			JSONObjectUtils.parse(accessTokenJSON));

		long accessTokenLifetime = accessToken.getLifetime() * Time.SECOND;

		if (accessTokenLifetime == 0) {
			accessTokenLifetime = Time.HOUR;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update OpenIdConnectSession set accessTokenExpirationDate = " +
					"? WHERE openIdConnectSessionId = ?")) {

			preparedStatement.setTimestamp(
				1, new Timestamp(modifiedDate.getTime() + accessTokenLifetime));
			preparedStatement.setLong(2, openIdConnectSessionId);

			preparedStatement.execute();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectSessionUpgradeProcess.class);

}