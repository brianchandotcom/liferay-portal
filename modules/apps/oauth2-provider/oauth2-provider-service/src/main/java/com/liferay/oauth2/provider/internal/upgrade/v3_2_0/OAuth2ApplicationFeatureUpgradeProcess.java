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

package com.liferay.oauth2.provider.internal.upgrade.v3_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marta Medio
 */
public class OAuth2ApplicationFeatureUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String sql =
			"select oAuth2ApplicationId, features from OAuth2Application " +
				"where features is not NULL";

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				List<String> features = Arrays.asList(
					StringUtil.split(resultSet.getString("features")));

				if (features.contains("token_introspection")) {
					long oAuth2ApplicationId = resultSet.getLong(
						"oAuth2ApplicationId");

					_updateTokenIntrospectionFeature(
						oAuth2ApplicationId, features);
				}
			}
		}
	}

	private void _updateTokenIntrospectionFeature(
			long oAuth2ApplicationId, List<String> features)
		throws SQLException {

		String sql =
			"update OAuth2Application set features = ? where " +
				"oAuth2ApplicationId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			features.replaceAll(
				feature -> {
					if (feature.equals("token_introspection")) {
						return "token.introspection";
					}

					return feature;
				});

			preparedStatement.setString(1, StringUtil.merge(features));

			preparedStatement.setLong(2, oAuth2ApplicationId);

			preparedStatement.execute();
		}
	}

}