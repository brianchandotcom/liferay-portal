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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
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

		Map<String, String> headers = new HashMap<>();

		headers.put("Content-Type", ContentTypes.APPLICATION_JSON);
		headers.put("OSB-Asah-Data-Source-ID", _dataSourceId);
		headers.put("X-Forwarded-For", internet.publicIpV4Address());

		options.setHeaders(headers);

		options.setLocation(_OSB_ASAH_PUBLISHER_URL + "/dxp-entities");
		options.setPost(true);

		try {
			options.setBody(
				_objectMapper.writeValueAsString(objects),
				ContentTypes.APPLICATION_JSON, StandardCharsets.UTF_8.name());

			HttpUtil.URLtoString(options);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> dxpEntity = new HashMap<>();

		dxpEntity.put("action", "update");
		dxpEntity.put("osbAsahDataSourceId", _dataSourceId);

		Map<String, Object> liferayUser = new HashMap<>();

		liferayUser.put("birthday", dateAndTime.past(18250, TimeUnit.DAYS));
		liferayUser.put("createDate", System.currentTimeMillis());

		String firstName = name.firstName();
		String lastName = name.lastName();

		liferayUser.put(
			"emailAddress",
			internet.emailAddress(firstName + StringPool.PERIOD + lastName));

		liferayUser.put("firstName", firstName);

		String gender = "male";

		if (bool.bool()) {
			gender = "female";
		}

		liferayUser.put("gender", gender);

		liferayUser.put("jobTitle", company.profession());
		liferayUser.put("lastName", lastName);
		liferayUser.put("modifiedDate", System.currentTimeMillis());
		liferayUser.put("screenName", firstName + StringPool.PERIOD + lastName);
		liferayUser.put("timeZoneId", "UTC");
		liferayUser.put("userId", number.randomNumber(8, false));
		liferayUser.put("uuid", internet.uuid());

		dxpEntity.put("objectJSONObject", liferayUser);

		dxpEntity.put("type", "com.liferay.portal.kernel.model.User");

		return dxpEntity;
	}

	private static final String _OSB_ASAH_PUBLISHER_URL = System.getenv(
		"OSB_ASAH_PUBLISHER_URL");

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayUsersDataCreator.class);

	private final String _dataSourceId;
	private final ObjectMapper _objectMapper = new ObjectMapper();

}