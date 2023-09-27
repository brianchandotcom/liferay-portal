/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.constants;

import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Arthur Chan
 */
public class OAuthClientEntryConstants {

	public static final String OIDC_USER_INFO_MAPPER_JSON = JSONUtil.put(
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
		"contact",
		JSONUtil.put(
			"birthdate", "birthdate"
		).put(
			"gender", "gender"
		)
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
			"jobTitle", ""
		).put(
			"languageId", "locale"
		).put(
			"lastName", "family_name"
		).put(
			"middleName", "middle_name"
		).put(
			"screenName", ""
		)
	).put(
		"users_roles", JSONUtil.put("roles", "")
	).toString();

}