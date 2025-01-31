/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class ZendeskTicket {

	public static final String STATUS_CLOSED = "closed";

	public ZendeskTicket(JSONObject jsonObject) {
		JSONArray customFieldsJSONArray = jsonObject.getJSONArray(
			"custom_fields");

		Map<Long, String> customFields = new HashMap<>();

		if (customFieldsJSONArray != null) {
			for (int i = 0; i < customFieldsJSONArray.length(); i++) {
				JSONObject customFieldJSONObject =
					customFieldsJSONArray.getJSONObject(i);

				customFields.put(
					customFieldJSONObject.getLong("id"),
					customFieldJSONObject.optString("value"));
			}
		}

		_customFields = customFields;
		_requesterId = jsonObject.getLong("requester_id");
		_status = jsonObject.getString("status");
		_subject = jsonObject.getString("subject");

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		Set<String> tags = new HashSet<>();

		if (tagsJSONArray != null) {
			for (int i = 0; i < tagsJSONArray.length(); i++) {
				tags.add(tagsJSONArray.getString(i));
			}
		}

		_tags = tags;
		_zendeskOrganizationId = jsonObject.getLong("organization_id");
		_zendeskTicketId = jsonObject.getLong("id");
	}

	public Map<Long, String> getCustomFields() {
		return _customFields;
	}

	public long getRequesterId() {
		return _requesterId;
	}

	public String getStatus() {
		return _status;
	}

	public String getSubject() {
		return _subject;
	}

	public Set<String> getTags() {
		return _tags;
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

	private final Map<Long, String> _customFields;
	private final long _requesterId;
	private final String _status;
	private final String _subject;
	private final Set<String> _tags;
	private final long _zendeskOrganizationId;
	private final long _zendeskTicketId;

}