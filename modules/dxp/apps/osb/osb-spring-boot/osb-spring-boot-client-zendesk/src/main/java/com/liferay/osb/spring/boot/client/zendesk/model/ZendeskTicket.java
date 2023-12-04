/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class ZendeskTicket {

	public static final String STATUS_CLOSED = "closed";

	public ZendeskTicket(JSONObject jsonObject) {
		_status = jsonObject.getString("status");
		_zendeskOrganizationId = jsonObject.getLong("organization_id");
		_zendeskTicketId = jsonObject.getLong("id");
	}

	public String getStatus() {
		return _status;
	}

	public long getZendeskOrganizationId() {
		return _zendeskOrganizationId;
	}

	public long getZendeskTicketId() {
		return _zendeskTicketId;
	}

	public boolean isClosed() {
		if (_status.equals(STATUS_CLOSED)) {
			return true;
		}

		return false;
	}

	private final String _status;
	private final long _zendeskOrganizationId;
	private final long _zendeskTicketId;

}