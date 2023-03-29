/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class SalesforceAuditEventsDataCreator extends DataCreator {

	public SalesforceAuditEventsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String typeName) {

		super(
			contactsEngineClient, faroProject, "osbasahsalesforceraw",
			"audit-events");

		_typeName = typeName;
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> auditEvent = new HashMap<>();

		Map<String, Object> salesforceObject = (Map<String, Object>)params[0];

		auditEvent.put("additionalInfo", salesforceObject);
		auditEvent.put("dataSourceId", salesforceObject.get("dataSourceId"));

		auditEvent.put("dateCreated", formatDate(new Date()));
		auditEvent.put("eventType", "UPDATE");
		auditEvent.put("recordId", salesforceObject.get("id"));
		auditEvent.put("typeName", _typeName);

		return auditEvent;
	}

	private final String _typeName;

}