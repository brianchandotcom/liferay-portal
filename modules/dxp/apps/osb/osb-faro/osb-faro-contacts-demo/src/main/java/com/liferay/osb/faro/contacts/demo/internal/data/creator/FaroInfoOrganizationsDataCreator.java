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