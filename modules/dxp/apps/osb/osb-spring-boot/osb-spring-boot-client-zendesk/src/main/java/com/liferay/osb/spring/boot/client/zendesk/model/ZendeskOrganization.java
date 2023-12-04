/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class ZendeskOrganization {

	public ZendeskOrganization(JSONObject jsonObject) {
		JSONObject zendeskOrganizationFieldsJSONObject =
			jsonObject.getJSONObject("organization_fields");

		_accountKey = zendeskOrganizationFieldsJSONObject.getString(
			"account_key");

		_zendeskOrganizationId = jsonObject.getLong("id");
	}

	public String getAccountKey() {
		return _accountKey;
	}

	public long getZendeskOrganizationId() {
		return _zendeskOrganizationId;
	}

	private final String _accountKey;
	private final long _zendeskOrganizationId;

}