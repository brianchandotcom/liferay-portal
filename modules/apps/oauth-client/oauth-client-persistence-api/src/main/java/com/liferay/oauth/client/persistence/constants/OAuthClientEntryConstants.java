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