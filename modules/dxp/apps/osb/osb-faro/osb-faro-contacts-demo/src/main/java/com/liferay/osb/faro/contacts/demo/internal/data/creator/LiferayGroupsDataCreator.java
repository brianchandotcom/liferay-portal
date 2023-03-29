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
public class LiferayGroupsDataCreator extends DataCreator {

	public LiferayGroupsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(contactsEngineClient, faroProject, "osbasahdxpraw", "groups");

		_dataSourceId = dataSourceId;
	}

	@Override
	public String getClassName() {
		return "com.liferay.portal.kernel.model.Group";
	}

	@Override
	public String getClassPKFieldName() {
		return "groupId";
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> group = new HashMap<>();

		group.put("dataSourceId", _dataSourceId);
		group.put(
			"fields",
			new HashMap<String, Object>() {
				{
					put("groupId", number.randomNumber(8, false));
					put("name", pokemon.name());
				}
			});

		group.put("id", number.randomNumber(8, false));

		return group;
	}

	private final String _dataSourceId;

}