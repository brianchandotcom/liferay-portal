/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.http.HttpInvoker;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFolder;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFolderResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lily Chi
 */
@RunWith(Arquillian.class)
public class ImportAndDeleteObjectEntryPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));
	}

	@Test
	public void testImportAndDeleteObjectEntry() throws Exception {
		_importObjectFolder();
		_importObjectEntry();
		_deleteObjectEntry();
	}

	private String _createObjectEntryJSON() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _OBJECT_ENTRY_COUNT; i++) {
			jsonArray.put(JSONUtil.put("alpha", "foo"));
		}

		return jsonArray.toString();
	}

	private void _deleteObjectEntry() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer(
				"Delete " + _OBJECT_ENTRY_COUNT + " Object Entries")) {

			JSONArray jsonArray = _getObjectEntryIdJSONArray();

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(jsonArray.toString(), "application/json");
			httpInvoker.userNameAndPassword(_USER_NAME_AND_PASSWORD);
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);
			httpInvoker.path(_PATH);

			httpInvoker.invoke();

			long currentObjectEntryCount = _OBJECT_ENTRY_COUNT;

			while (currentObjectEntryCount != 0) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_objectDefinition.getObjectDefinitionId());
			}
		}
	}

	private JSONArray _getObjectEntryIdJSONArray() throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.userNameAndPassword(_USER_NAME_AND_PASSWORD);
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(
			"http://localhost:8080/o/c/foos/?fields=id&pageSize=" +
				_OBJECT_ENTRY_COUNT);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		return (JSONArray)jsonObject.get("items");
	}

	private void _importObjectEntry() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer(
				"Import " + _OBJECT_ENTRY_COUNT + " Object Entries")) {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(_createObjectEntryJSON(), "application/json");
			httpInvoker.userNameAndPassword(_USER_NAME_AND_PASSWORD);
			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
			httpInvoker.path(_PATH);

			httpInvoker.invoke();

			List<ObjectDefinition> objectDefinitions =
				_objectDefinitionLocalService.getCustomObjectDefinitions(0);

			_objectDefinition = objectDefinitions.get(0);

			long currentObjectEntryCount = 0;

			while (currentObjectEntryCount < _OBJECT_ENTRY_COUNT) {
				currentObjectEntryCount =
					_objectEntryLocalService.getObjectEntriesCount(
						_objectDefinition.getObjectDefinitionId());
			}
		}
	}

	private void _importObjectFolder() throws Exception {
		ObjectFolderResource.Builder builder =
			_objectFolderResourceFactory.create();

		ObjectFolderResource objectFolderResource = builder.user(
			TestPropsValues.getUser()
		).build();

		JSONObject objectFolderJSONObject = _jsonFactory.createJSONObject(
			StringUtil.read(
				ImportAndDeleteObjectEntryPerformanceTest.class.
					getClassLoader(),
				"/object-folder-definition.json"));

		ObjectFolder objectFolder = ObjectFolder.toDTO(
			objectFolderJSONObject.toString());

		objectFolder.setName("SampleObjectFolder");

		objectFolderResource.putObjectFolderByExternalReferenceCode(
			objectFolder.getExternalReferenceCode(), objectFolder);
	}

	private static final int _OBJECT_ENTRY_COUNT = 100;

	private static final String _PATH = "http://localhost:8080/o/c/foos/batch";

	private static final String _USER_NAME_AND_PASSWORD =
		"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

	@Inject
	private JSONFactory _jsonFactory;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFolderResource.Factory _objectFolderResourceFactory;

}