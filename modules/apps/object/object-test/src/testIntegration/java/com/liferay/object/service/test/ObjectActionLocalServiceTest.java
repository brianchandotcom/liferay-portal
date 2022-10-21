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
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_objectActionIdsThreadLocal = ReflectionTestUtil.getFieldValue(
			_objectActionEngine, "_objectActionIdsThreadLocal");

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			_objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					"First Name", "firstName", true)));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		_originalHttp = (Http)_getAndSetFieldValue(
			Http.class, "_http", ObjectActionExecutorConstants.KEY_WEBHOOK);
		_originalObjectScriptingExecutor =
			(ObjectScriptingExecutor)_getAndSetFieldValue(
				ObjectScriptingExecutor.class, "_objectScriptingExecutor",
				ObjectActionExecutorConstants.KEY_GROOVY);
	}

	@After
	public void tearDown() {
		_objectActionIdsThreadLocal.remove();

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

		ObjectAction objectAction1 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build());
		ObjectAction objectAction2 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
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
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			UnicodePropertiesBuilder.put(
				"secret", "onafterupdate"
			).put(
				"url", "https://onafterupdate.com"
			).build());

		// Add object entry

		Assert.assertEquals(0, _argumentsList.size());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "John"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(1, _argumentsList.size());

		// On after create

		Object[] arguments = _argumentsList.poll();

		Http.Options options = (Http.Options)arguments[0];

		Http.Body body = options.getBody();

		Assert.assertEquals(StringPool.UTF8, body.getCharset());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, body.getContentType());

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject(
			body.getContent());

		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			payloadJSONObject.getString("objectActionTriggerKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"John",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			"John",
			JSONUtil.getValue(
				payloadJSONObject,
				"JSONObject/objectEntryDTO" + _objectDefinition.getShortName(),
				"JSONObject/properties", "Object/firstName"));
		Assert.assertNull(
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry"));

		Assert.assertEquals("onafteradd", options.getHeader("x-api-key"));
		Assert.assertEquals("https://onafteradd.com", options.getLocation());

		// Update object entry

		_objectActionIdsThreadLocal.remove();

		Assert.assertEquals(0, _argumentsList.size());

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", "João"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(1, _argumentsList.size());

		// On after update

		arguments = _argumentsList.poll();

		options = (Http.Options)arguments[0];

		body = options.getBody();

		Assert.assertEquals(StringPool.UTF8, body.getCharset());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, body.getContentType());

		payloadJSONObject = _jsonFactory.createJSONObject(body.getContent());

		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			payloadJSONObject.getString("objectActionTriggerKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"João",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			"João",
			JSONUtil.getValue(
				payloadJSONObject,
				"JSONObject/objectEntryDTO" + _objectDefinition.getShortName(),
				"JSONObject/properties", "Object/firstName"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"Object/status"));
		Assert.assertEquals(
			"John",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"JSONObject/values", "Object/firstName"));

		Assert.assertEquals("onafterupdate", options.getHeader("x-api-key"));
		Assert.assertEquals("https://onafterupdate.com", options.getLocation());

		// Delete object entry

		_objectActionIdsThreadLocal.remove();

		Assert.assertEquals(0, _argumentsList.size());

		_objectEntryLocalService.deleteObjectEntry(objectEntry);

		Assert.assertEquals(1, _argumentsList.size());

		// On after remove

		arguments = _argumentsList.poll();

		options = (Http.Options)arguments[0];

		body = options.getBody();

		Assert.assertEquals(StringPool.UTF8, body.getCharset());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, body.getContentType());

		payloadJSONObject = _jsonFactory.createJSONObject(body.getContent());

		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			payloadJSONObject.getString("objectActionTriggerKey"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry", "Object/status"));
		Assert.assertEquals(
			"João",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			"João",
			JSONUtil.getValue(
				payloadJSONObject,
				"JSONObject/objectEntryDTO" + _objectDefinition.getShortName(),
				"JSONObject/properties", "Object/firstName"));
		Assert.assertNull(
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry"));

		Assert.assertEquals("onafterdelete", options.getHeader("x-api-key"));
		Assert.assertEquals("https://onafterdelete.com", options.getLocation());

		// Delete object actions

		_objectActionLocalService.deleteObjectAction(objectAction1);
		_objectActionLocalService.deleteObjectAction(objectAction2);
		_objectActionLocalService.deleteObjectAction(objectAction3);
	}

	@Test
	public void testAddObjectActionWithConditionExpression() throws Exception {
		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			"equals(firstName, \"João\")", RandomTestUtil.randomString(),
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

		Object[] arguments = _argumentsList.poll();

		Assert.assertEquals(
			HashMapBuilder.putAll(
				objectEntry.getModelAttributes()
			).put(
				"creator", objectEntry.getUserName()
			).put(
				"currentUserId", TestPropsValues.getUserId()
			).put(
				"firstName", "João"
			).put(
				"id", objectEntry.getObjectEntryId()
			).build(),
			arguments[0]);
		Assert.assertEquals(Collections.emptySet(), arguments[1]);
		Assert.assertEquals("println \"Hello World\"", arguments[2]);

		_objectActionLocalService.deleteObjectAction(objectAction);
	}

	@Test
	public void testUpdateObjectAction() throws Exception {
		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			"equals(firstName, \"John\")", "Able Description", "Able",
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafteradd.com"
			).build());

		Assert.assertTrue(objectAction.isActive());
		Assert.assertEquals(
			"equals(firstName, \"John\")",
			objectAction.getConditionExpression());
		Assert.assertEquals("Able Description", objectAction.getDescription());
		Assert.assertEquals("Able", objectAction.getName());
		Assert.assertEquals(
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			objectAction.getObjectActionExecutorKey());
		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			objectAction.getObjectActionTriggerKey());
		Assert.assertEquals(
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).put(
				"url", "https://onafteradd.com"
			).build(),
			objectAction.getParametersUnicodeProperties());
		Assert.assertEquals(
			ObjectActionConstants.STATUS_NEVER_RAN, objectAction.getStatus());

		objectAction = _objectActionLocalService.updateObjectAction(
			objectAction.getObjectActionId(), false,
			"equals(firstName, \"João\")", "Baker Description", "Baker",
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build());

		Assert.assertFalse(objectAction.isActive());
		Assert.assertEquals(
			"equals(firstName, \"João\")",
			objectAction.getConditionExpression());
		Assert.assertEquals("Baker Description", objectAction.getDescription());
		Assert.assertEquals("Baker", objectAction.getName());
		Assert.assertEquals(
			ObjectActionExecutorConstants.KEY_GROOVY,
			objectAction.getObjectActionExecutorKey());
		Assert.assertEquals(
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE,
			objectAction.getObjectActionTriggerKey());
		Assert.assertEquals(
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).put(
				"url", "https://onafterdelete.com"
			).build(),
			objectAction.getParametersUnicodeProperties());
		Assert.assertEquals(
			ObjectActionConstants.STATUS_NEVER_RAN, objectAction.getStatus());
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

	private final Queue<Object[]> _argumentsList = new LinkedList<>();

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectActionEngine _objectActionEngine;

	@Inject
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	private ThreadLocal<Set<Long>> _objectActionIdsThreadLocal;

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