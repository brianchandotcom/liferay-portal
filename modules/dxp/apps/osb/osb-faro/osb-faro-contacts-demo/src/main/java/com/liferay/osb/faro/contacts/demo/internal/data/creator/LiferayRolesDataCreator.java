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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class LiferayRolesDataCreator extends DataCreator {

	public LiferayRolesDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(contactsEngineClient, faroProject, "osbasahdxpraw", "roles");

		_dataSourceId = dataSourceId;
	}

	@Override
	public String getClassName() {
		return "com.liferay.portal.kernel.model.Role";
	}

	@Override
	public String getClassPKFieldName() {
		return "roleId";
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> role = new HashMap<>();

		role.put("dataSourceId", _dataSourceId);
		role.put(
			"fields",
			new HashMap<String, Object>() {
				{
					put("name", job.position());
					put("roleId", number.randomNumber(8, false));
				}
			});

		role.put("id", number.randomNumber(8, false));

		return role;
	}

	private final String _dataSourceId;

}