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
public class FaroInfoOrganizationsDataCreator extends DataCreator {

	public FaroInfoOrganizationsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject) {

		super(
			contactsEngineClient, faroProject, "osbasahfaroinfo",
			"organizations");
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> organization = (Map<String, Object>)params[0];

		return HashMapBuilder.<String, Object>put(
			"dataSourceId", organization.get("osbAsahDataSourceId")
		).put(
			"dateCreated", formatDate(new Date())
		).put(
			"dateModified", organization.get("modifiedDate")
		).put(
			"id", number.randomNumber(8, false)
		).put(
			"name", organization.get("name")
		).put(
			"nameTreePath", organization.get("nameTreePath")
		).put(
			"organizationPK", organization.get("organizationId")
		).put(
			"parentName", organization.get("parentName")
		).put(
			"parentOrganizationPK", organization.get("parentOrganizationId")
		).put(
			"type", organization.get("type")
		).build();
	}

}