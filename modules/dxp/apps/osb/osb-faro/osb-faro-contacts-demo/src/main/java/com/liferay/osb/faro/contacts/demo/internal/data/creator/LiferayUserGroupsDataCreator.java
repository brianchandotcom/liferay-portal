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

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class LiferayUserGroupsDataCreator extends DataCreator {

	public LiferayUserGroupsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(
			contactsEngineClient, faroProject, "osbasahdxpraw", "user-groups");

		_dataSourceId = dataSourceId;
	}

	@Override
	public String getClassName() {
		return "com.liferay.portal.kernel.model.UserGroup";
	}

	@Override
	public String getClassPKFieldName() {
		return "userGroupId";
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		return HashMapBuilder.<String, Object>put(
			"dataSourceId", _dataSourceId
		).put(
			"fields",
			HashMapBuilder.<String, Object>put(
				"name", country.name()
			).put(
				"userGroupId", number.randomNumber(8, false)
			)
		).put(
			"id", number.randomNumber(8, false)
		).build();
	}

	private final String _dataSourceId;

}