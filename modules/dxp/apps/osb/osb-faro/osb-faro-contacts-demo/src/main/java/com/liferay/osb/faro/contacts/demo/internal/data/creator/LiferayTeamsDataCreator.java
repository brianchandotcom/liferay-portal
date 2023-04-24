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
public class LiferayTeamsDataCreator extends DataCreator {

	public LiferayTeamsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(contactsEngineClient, faroProject, "osbasahdxpraw", "teams");

		_dataSourceId = dataSourceId;
	}

	@Override
	public String getClassName() {
		return "com.liferay.portal.kernel.model.Team";
	}

	@Override
	public String getClassPKFieldName() {
		return "teamId";
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> group = (Map<String, Object>)params[0];

		return HashMapBuilder.<String, Object>put(
			"dataSourceId", _dataSourceId
		).put(
			"fields",
			HashMapBuilder.<String, Object>put(
				"groupId", group.get("groupId")
			).put(
				"name", lordOfTheRings.location()
			).put(
				"teamId", number.randomNumber(8, false)
			).build()
		).put(
			"id", number.randomNumber(8, false)
		).build();
	}

	private final String _dataSourceId;

}