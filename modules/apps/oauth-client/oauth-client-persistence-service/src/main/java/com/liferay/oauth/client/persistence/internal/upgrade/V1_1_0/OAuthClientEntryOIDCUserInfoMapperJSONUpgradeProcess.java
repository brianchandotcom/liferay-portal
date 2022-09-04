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

package com.liferay.oauth.client.persistence.internal.upgrade.V1_1_0;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

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
						1, _OIDC_USER_INFO_MAPPER_JSON);
					preparedStatement2.setLong(
						2, resultSet.getLong("oAuthClientEntryId"));

					preparedStatement2.execute();
				}
			}
		}
	}

	private static final String _OIDC_USER_INFO_MAPPER_JSON = JSONUtil.put(
		"address",
		JSONUtil.put(
			"addressType", ""
		).put(
			"city", "address->locality"
		).put(
			"country", "address->country"
		).put(
			"region", "address->region"
		).put(
			"street", "address->street_address"
		).put(
			"zip", "address->postal_code"
		)
	).put(
		"contact", JSONUtil.put("birthdate", "birthdate")
	).put(
		"phone",
		JSONUtil.put(
			"phone", "phone_number"
		).put(
			"phoneType", ""
		)
	).put(
		"user",
		JSONUtil.put(
			"emailAddress", "email"
		).put(
			"firstName", "given_name"
		).put(
			"gender", "gender"
		).put(
			"jobTitle", ""
		).put(
			"languageId", "locale"
		).put(
			"lastName", "family_name"
		).put(
			"middleName", "middle_name"
		).put(
			"roles", ""
		).put(
			"screenName", ""
		)
	).toString();

}