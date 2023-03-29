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
public class FaroInfoOrganizationsDataCreator extends DataCreator {

	public FaroInfoOrganizationsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject) {

		super(
			contactsEngineClient, faroProject, "osbasahfaroinfo",
			"organizations");
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> faroInfoOrganization = new HashMap<>();

		Map<String, Object> organization = (Map<String, Object>)params[0];

		faroInfoOrganization.put(
			"dataSourceId", organization.get("osbAsahDataSourceId"));

		faroInfoOrganization.put("dateCreated", formatDate(new Date()));
		faroInfoOrganization.put(
			"dateModified", organization.get("modifiedDate"));
		faroInfoOrganization.put("id", number.randomNumber(8, false));
		faroInfoOrganization.put("name", organization.get("name"));
		faroInfoOrganization.put(
			"nameTreePath", organization.get("nameTreePath"));
		faroInfoOrganization.put(
			"organizationPK", organization.get("organizationId"));
		faroInfoOrganization.put("parentName", organization.get("parentName"));
		faroInfoOrganization.put(
			"parentOrganizationPK", organization.get("parentOrganizationId"));
		faroInfoOrganization.put("type", organization.get("type"));

		return faroInfoOrganization;
	}

}