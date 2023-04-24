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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Date;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class LiferayOrganizationsDataCreator extends DataCreator {

	public LiferayOrganizationsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(
			contactsEngineClient, faroProject, "osbasahdxpraw",
			"organizations");

		_dataSourceId = dataSourceId;

		_faroInfoOrganizationsDataCreator =
			new FaroInfoOrganizationsDataCreator(
				contactsEngineClient, faroProject);
	}

	@Override
	public void execute() {
		super.execute();

		for (Object object : getObjects()) {
			_faroInfoOrganizationsDataCreator.create(new Object[] {object});
		}

		_faroInfoOrganizationsDataCreator.execute();
	}

	@Override
	public String getClassName() {
		return "com.liferay.portal.kernel.model.Organization";
	}

	@Override
	public String getClassPKFieldName() {
		return "organizationId";
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		String name = (String)params[0];

		Map<String, Object> parentOrganization = (Map<String, Object>)params[1];

		return HashMapBuilder.<String, Object>put(
			"id", number.randomNumber(8, false)
		).put(
			"modifiedDate", formatDate(new Date())
		).put(
			"name", name
		).put(
			"nameTreePath", _getNameTreePath(name, parentOrganization)
		).put(
			"organizationId", number.randomNumber(8, false)
		).put(
			"osbAsahDataSourceId", _dataSourceId
		).put(
			"parentName",
			parentOrganization.getOrDefault("name", StringPool.BLANK)
		).put(
			"parentOrganizationId",
			parentOrganization.getOrDefault("organizationId", StringPool.BLANK)
		).put(
			"type", "organization"
		).build();
	}

	private String _getNameTreePath(
		String name, Map<String, Object> parentOrganization) {

		if (parentOrganization.isEmpty()) {
			return name;
		}

		return parentOrganization.get("nameTreePath") + " > " + name;
	}

	private final String _dataSourceId;
	private final FaroInfoOrganizationsDataCreator
		_faroInfoOrganizationsDataCreator;

}