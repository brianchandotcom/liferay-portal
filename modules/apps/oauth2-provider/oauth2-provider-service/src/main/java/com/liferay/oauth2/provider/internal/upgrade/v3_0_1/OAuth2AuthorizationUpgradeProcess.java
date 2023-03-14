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

package com.liferay.oauth2.provider.internal.upgrade.v3_0_1;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Time;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * @author Jonathan McCann
 */
public class OAuth2AuthorizationUpgradeProcess extends UpgradeProcess {

	public OAuth2AuthorizationUpgradeProcess(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		OAuth2ProviderConfiguration oAuth2ProviderConfiguration =
			_configurationProvider.getSystemConfiguration(
				OAuth2ProviderConfiguration.class);

		int expiredAuthorizationsAfterlifeDuration = Math.max(
			oAuth2ProviderConfiguration.
				expiredAuthorizationsAfterlifeDuration(),
			0);

		long expiredAuthorizationsAfterlifeDurationMillis =
			expiredAuthorizationsAfterlifeDuration * Time.SECOND;

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		timestamp.setTime(
			timestamp.getTime() - expiredAuthorizationsAfterlifeDurationMillis);

		String sql = StringBundler.concat(
			"create table TEMP_TABLE as (",
			"select * from OAuth2Authorization where ",
			"(accessTokenExpirationDate >= ?) or ((refreshTokenExpirationDate ",
			"is not null) and (refreshTokenExpirationDate >= ?)))");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setTimestamp(1, timestamp);
			preparedStatement.setTimestamp(2, timestamp);

			preparedStatement.execute();
		}

		runSQL(
			StringBundler.concat(
				"create table TEMP_TABLE2 as (",
				"select * from OA2Auths_OA2ScopeGrants where ",
				"oAuth2AuthorizationId in (select oAuth2AuthorizationId from ",
				"TEMP_TABLE))"));

		runSQL("drop table OAuth2Authorization");
		runSQL("drop table OA2Auths_OA2ScopeGrants");

		runSQL("rename table TEMP_TABLE to OAuth2Authorization");
		runSQL("rename table TEMP_TABLE2 to OA2Auths_OA2ScopeGrants");
	}

	private final ConfigurationProvider _configurationProvider;

}