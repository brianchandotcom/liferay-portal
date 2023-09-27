/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Date;
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
		Map<String, Object> salesforceObject = (Map<String, Object>)params[0];

		return HashMapBuilder.<String, Object>put(
			"additionalInfo", salesforceObject
		).put(
			"dataSourceId", salesforceObject.get("dataSourceId")
		).put(
			"dateCreated", formatDate(new Date())
		).put(
			"eventType", "UPDATE"
		).put(
			"recordId", salesforceObject.get("id")
		).put(
			"typeName", _typeName
		).build();
	}

	private final String _typeName;

}