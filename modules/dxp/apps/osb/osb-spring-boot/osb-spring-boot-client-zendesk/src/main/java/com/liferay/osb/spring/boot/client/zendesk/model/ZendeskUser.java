/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class ZendeskUser {

	public ZendeskUser(JSONObject jsonObject) {
		_emailAddress = jsonObject.getString("email");
		_role = jsonObject.getString("role");
		_zendeskUserId = jsonObject.getLong("id");
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getRole() {
		return _role;
	}

	public long getZendeskUserId() {
		return _zendeskUserId;
	}

	public boolean isEndUser() {
		if (_role.equals("end-user")) {
			return true;
		}

		return false;
	}

	private final String _emailAddress;
	private final String _role;
	private final long _zendeskUserId;

}