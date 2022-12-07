/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.ObjectActionErrorMessageException;
import com.liferay.object.exception.ObjectActionLabelException;
import com.liferay.object.exception.ObjectActionNameException;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import jodd.util.StringUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class ObjectActionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-166918", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-166918", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"First Name", "firstName", true)));

		_originalHttp = (Http)_getAndSetFieldValue(
			Http.class, "_http", ObjectActionExecutorConstants.KEY_WEBHOOK);
		_originalObjectScriptingExecutor =
			(ObjectScriptingExecutor)_getAndSetFieldValue(
				ObjectScriptingExecutor.class, "_objectScriptingExecutor",
				ObjectActionExecutorConstants.KEY_GROOVY);
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_WEBHOOK),
			"_http", _originalHttp);
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_GROOVY),
			"_objectScriptingExecutor", _originalObjectScriptingExecutor);
	}

	@Test
	public void testAddObjectAction() throws Exception {

		// Add object actions

		try {
			_addObjectAction(
				StringPool.BLANK, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_STANDALONE);

			Assert.fail();
		}
		catch (ObjectActionErrorMessageException
					objectActionErrorMessageException) {

			Assert.assertEquals(
				"Error message is null for locale " +
					LocaleUtil.US.getDisplayName(),
				objectActionErrorMessageException.getMessage());
		}

		try {
			_addObjectAction(
				StringPool.BLANK, StringPool.BLANK,
				RandomTestUtil.randomString(),
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD);

			Assert.fail();
		}
		catch (ObjectActionLabelException objectActionLabelException) {
			Assert.assertEquals(
				"Label is null for locale " + LocaleUtil.US.getDisplayName(),
				objectActionLabelException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name is null", objectActionNameException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(42),
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name must be less than 41 characters",
				objectActionNameException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"Abl e", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectActionNameException.getMessage());
		}

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				"Abl-e", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Name must only contain letters and digits",
				objectActionNameException.getMessage());
		}

		String name = RandomTestUtil.randomString();

		ObjectAction objectAction1 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			name, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build());

		try {
			_addObjectAction(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				name, ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE);

			Assert.fail();
		}
		catch (ObjectActionNameException objectActionNameException) {
			Assert.assertEquals(
				"Duplicate name " + name,
				objectActionNameException.getMessage());
		}

		ObjectAction objectAction2 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterdelete"
			).put(
				"url", "https://onafterdelete.com"
			).build());
		ObjectAction objectAction3 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterupdate"
			).put(
				"url", "https://onafterupdate.com"
			).build());
		ObjectAction objectAction4 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build());

		_publishCustomObjectDefinition();

		// Add object entry

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		// On after create

		_assertWebhookObjectActionExecutorArguments(
			"John", ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null,
			WorkflowConstants.STATUS_DRAFT);

		// Execute standalone action

		_executeStandaloneAction(
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				String.valueOf(objectEntry.getObjectEntryId()),
				"/object-actions/", objectAction4.getName()));

		_assertGroovyObjectActionExecutorArguments("John", objectEntry);

		// Update object entry

		Assert.assertEquals(0, _argumentsList.size());

		objectEntry = _objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		// On after update

		_assertWebhookObjectActionExecutorArguments(
			"João", ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, "John",
			WorkflowConstants.STATUS_APPROVED);

		// Execute standalone action

		_executeStandaloneAction(
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(), "/object-actions/",
				objectAction4.getName()));

		_assertGroovyObjectActionExecutorArguments("João", objectEntry);

		// Delete object entry

		Assert.assertEquals(0, _argumentsList.size());

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		// On after remove

		_assertWebhookObjectActionExecutorArguments(
			"João", ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, null,
			WorkflowConstants.STATUS_APPROVED);

		// Delete object actions

		_objectActionLocalService.deleteObjectAction(objectAction1);
		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
		_objectActionLocalService.deleteObjectAction(objectAction4);
	}

	@Test
	public void testAddObjectActionWithConditionExpression() throws Exception {
		_publishCustomObjectDefinition();

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			"equals(firstName, \"João\")", RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build());

		// Add object entry with unsatisfied condition

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		Assert.assertNull(_argumentsList.poll());

		// Add object entry with satisfied condition

		objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		objectEntry = _objectEntryLocalService.deleteObjectEntry(objectEntry);

		_assertGroovyObjectActionExecutorArguments("João", objectEntry);

		_objectActionLocalService.deleteObjectAction(objectAction);
	}

	@Test
	public void testAddObjectActionWithMoreThanOneObjectEntry()
		throws Exception {

		_publishCustomObjectDefinition();

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build());

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry1 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);

		Assert.assertEquals(1, _argumentsList.size());

		ObjectEntry objectEntry2 = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_objectEntryLocalService.deleteObjectEntry(objectEntry2);

		Assert.assertEquals(2, _argumentsList.size());

		_objectActionLocalService.deleteObjectAction(objectAction);
	}

	@Test
	public void testUpdateObjectAction() throws Exception {
		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			"equals(firstName, \"John\")", "Able Description",
			LocalizedMapUtil.getLocalizedMap("Able Error Message"),
			LocalizedMapUtil.getLocalizedMap("Able Label"), "Able",
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafteradd.com"
			).build());

		_assertObjectAction(
			true, "equals(firstName, \"John\")", "Able Description",
			LocalizedMapUtil.getLocalizedMap("Able Error Message"),
			LocalizedMapUtil.getLocalizedMap("Able Label"), "Able",
			objectAction, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafteradd.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		objectAction = _objectActionLocalService.updateObjectAction(
			objectAction.getObjectActionId(), false,
			"equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Baker Label"), "Baker",
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build());

		_assertObjectAction(
			false, "equals(firstName, \"João\")", "Baker Description",
			LocalizedMapUtil.getLocalizedMap("Baker Error Message"),
			LocalizedMapUtil.getLocalizedMap("Baker Label"), "Baker",
			objectAction, ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);

		_publishCustomObjectDefinition();

		objectAction = _objectActionLocalService.updateObjectAction(
			objectAction.getObjectActionId(), true,
			"equals(firstName, \"John\")", "Charlie Description",
			LocalizedMapUtil.getLocalizedMap("Charlie Error Message"),
			LocalizedMapUtil.getLocalizedMap("Charlie Label"), "Charlie",
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafterdelete.com"
			).build());

		_assertObjectAction(
			true, "equals(firstName, \"John\")", "Charlie Description",
			LocalizedMapUtil.getLocalizedMap("Charlie Error Message"),
			LocalizedMapUtil.getLocalizedMap("Charlie Label"), "Baker",
			objectAction, ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			ObjectActionConstants.STATUS_NEVER_RAN);
	}

	private void _addObjectAction(
			String errorMessage, String label, String name,
			String objectActionTriggerKey)
		throws Exception {

		_objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(errorMessage),
			LocalizedMapUtil.getLocalizedMap(label), name,
			ObjectActionExecutorConstants.KEY_GROOVY, objectActionTriggerKey,
			new UnicodeProperties());
	}

	private void _assertGroovyObjectActionExecutorArguments(
		String firstName, ObjectEntry objectEntry) {

		Assert.assertEquals(1, _argumentsList.size());

		Object[] arguments = _argumentsList.poll();

		Map<String, Object> inputObjects = (Map<String, Object>)arguments[0];

		Assert.assertEquals(
			objectEntry.getExternalReferenceCode(),
			inputObjects.get("externalReferenceCode"));
		Assert.assertEquals(firstName, inputObjects.get("firstName"));
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), inputObjects.get("id"));

		Assert.assertEquals(Collections.emptySet(), arguments[1]);
		Assert.assertEquals("println \"Hello World\"", arguments[2]);
	}

	private void _assertObjectAction(
		boolean active, String conditionExpression, String description,
		Map<Locale, String> errorMessageMap, Map<Locale, String> labelMap,
		String name, ObjectAction objectAction, String objectActionExecutorKey,
		String objectActionTriggerKey,
		UnicodeProperties parametersUnicodeProperties, int status) {

		Assert.assertEquals(active, objectAction.isActive());
		Assert.assertEquals(
			conditionExpression, objectAction.getConditionExpression());
		Assert.assertEquals(description, objectAction.getDescription());
		Assert.assertEquals(errorMessageMap, objectAction.getErrorMessageMap());
		Assert.assertEquals(labelMap, objectAction.getLabelMap());
		Assert.assertEquals(name, objectAction.getName());
		Assert.assertEquals(
			objectActionExecutorKey, objectAction.getObjectActionExecutorKey());
		Assert.assertEquals(
			objectActionTriggerKey, objectAction.getObjectActionTriggerKey());
		Assert.assertEquals(
			parametersUnicodeProperties,
			objectAction.getParametersUnicodeProperties());
		Assert.assertEquals(status, objectAction.getStatus());
	}

	private void _assertWebhookObjectActionExecutorArguments(
			String firstName, String objectActionTriggerKey,
			String originalFirstName, int status)
		throws Exception {

		Assert.assertEquals(1, _argumentsList.size());

		Object[] arguments = _argumentsList.poll();

		Http.Options options = (Http.Options)arguments[0];

		Assert.assertEquals(
			StringUtil.toLowerCase(objectActionTriggerKey),
			options.getHeader("x-api-key"));
		Assert.assertEquals(
			"https://" + StringUtil.toLowerCase(objectActionTriggerKey) +
				".com",
			options.getLocation());

		Http.Body body = options.getBody();

		Assert.assertEquals(StringPool.UTF8, body.getCharset());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, body.getContentType());

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
			body.getContent());

		Assert.assertEquals(
			objectActionTriggerKey,
			payloadJSONObject.getString("objectActionTriggerKey"));
		Assert.assertEquals(
			status,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			firstName,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			firstName,
			JSONUtil.getValue(
				payloadJSONObject,
				"JSONObject/objectEntryDTO" + _objectDefinition.getShortName(),
				"JSONObject/properties", "Object/firstName"));

		if (StringUtil.equals(
				objectActionTriggerKey,
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE)) {

			Assert.assertEquals(
				originalFirstName,
				JSONUtil.getValue(
					payloadJSONObject, "JSONObject/originalObjectEntry",
					"JSONObject/values", "Object/firstName"));
		}
		else {
			Assert.assertNull(
				JSONUtil.getValue(
					payloadJSONObject, "JSONObject/originalObjectEntry"));
		}
	}

	private void _executeStandaloneAction(String endpoint) throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.setLocation("http://localhost:8080/o/" + endpoint);
		options.setMethod(Http.Method.PUT);

		HttpUtil.URLtoString(options);
	}

	private Object _getAndSetFieldValue(
		Class<?> clazz, String fieldName, String objectActionExecutorKey) {

		return ReflectionTestUtil.getAndSetFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				objectActionExecutorKey),
			fieldName,
			ProxyUtil.newProxyInstance(
				clazz.getClassLoader(), new Class<?>[] {clazz},
				(proxy, method, arguments) -> {
					_argumentsList.add(arguments);

					if (Objects.equals(
							method.getDeclaringClass(),
							ObjectScriptingExecutor.class) &&
						Objects.equals(method.getName(), "execute")) {

						return Collections.emptyMap();
					}

					return null;
				}));
	}

	private void _publishCustomObjectDefinition() throws Exception {
		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId());
	}

	private final Queue<Object[]> _argumentsList = new LinkedList<>();

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectActionEngine _objectActionEngine;

	@Inject
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private Http _originalHttp;
	private ObjectScriptingExecutor _originalObjectScriptingExecutor;

}