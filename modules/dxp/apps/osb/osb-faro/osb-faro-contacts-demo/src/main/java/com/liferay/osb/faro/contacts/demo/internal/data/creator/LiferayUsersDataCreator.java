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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.util.FaroPropsValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;

import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Kong
 */
public class LiferayUsersDataCreator extends DataCreator {

	public LiferayUsersDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(contactsEngineClient, faroProject, "osbasahdxpraw", "users");

		_dataSourceId = dataSourceId;
	}

	public String getDataSourceId() {
		return _dataSourceId;
	}

	@Override
	protected void addData(List<Map<String, Object>> objects) {
		Http.Options options = new Http.Options();

		options.setHeaders(
			HashMapBuilder.put(
				"Content-Type", ContentTypes.APPLICATION_JSON
			).put(
				"OSB-Asah-Data-Source-ID", _dataSourceId
			).put(
				"X-Forwarded-For", internet.publicIpV4Address()
			).build());

		options.setLocation(
			FaroPropsValues.OSB_ASAH_PUBLISHER_URL + "/dxp-entities");
		options.setPost(true);

		try {
			options.setBody(
				_objectMapper.writeValueAsString(objects),
				ContentTypes.APPLICATION_JSON, StandardCharsets.UTF_8.name());

			HttpUtil.URLtoString(options);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		String firstName = name.firstName();
		String lastName = name.lastName();

		String gender = "male";

		if (bool.bool()) {
			gender = "female";
		}

		return HashMapBuilder.<String, Object>put(
			"action", "update"
		).put(
			"objectJSONObject",
			HashMapBuilder.<String, Object>put(
				"birthday", dateAndTime.past(18250, TimeUnit.DAYS)
			).put(
				"createDate", System.currentTimeMillis()
			).put(
				"emailAddress",
				internet.emailAddress(firstName + StringPool.PERIOD + lastName)
			).put(
				"firstName", firstName
			).put(
				"gender", gender
			).put(
				"jobTitle", company.profession()
			).put(
				"lastName", lastName
			).put(
				"modifiedDate", System.currentTimeMillis()
			).put(
				"screenName", firstName + StringPool.PERIOD + lastName
			).put(
				"timeZoneId", "UTC"
			).put(
				"userId", number.randomNumber(8, false)
			).put(
				"uuid", internet.uuid()
			).build()
		).put(
			"osbAsahDataSourceId", _dataSourceId
		).put(
			"type", "com.liferay.portal.kernel.model.User"
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayUsersDataCreator.class);

	private final String _dataSourceId;
	private final ObjectMapper _objectMapper = new ObjectMapper();

}