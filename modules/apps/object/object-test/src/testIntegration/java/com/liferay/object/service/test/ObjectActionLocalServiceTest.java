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
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.util.LocalizedMapUtil;
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
import java.util.LinkedList;
import java.util.Queue;

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
		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"Text", true, true, null, "First Name", "firstName",
						true, "String")));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		_originalHttp = (Http)ReflectionTestUtil.getAndSetFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_WEBHOOK),
			"_http",
			ProxyUtil.newProxyInstance(
				Http.class.getClassLoader(), new Class<?>[] {Http.class},
				(proxy, method, arguments) -> {
					_argumentsList.add(arguments);

					return null;
				}));
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_WEBHOOK),
			"_http", _originalHttp);
	}

	@Test
	public void testAddObjectAction() throws Exception {

		// Add object actions

		ObjectAction objectAction1 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "onafteradd"
			).put(
				"url", "https://onafteradd.com"
			).build());
		ObjectAction objectAction2 = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true,
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
			_objectDefinition.getObjectDefinitionId(), true,
			RandomTestUtil.randomString(),
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

		Assert.assertEquals(2, _argumentsList.size());

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
		Assert.assertNull(
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry"));

		Assert.assertEquals("onafteradd", options.getHeader("x-api-key"));
		Assert.assertEquals("https://onafteradd.com", options.getLocation());

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
			"John",
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/objectEntry",
				"JSONObject/values", "Object/firstName"));
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			JSONUtil.getValue(
				payloadJSONObject, "JSONObject/originalObjectEntry",
				"Object/status"));

		Assert.assertEquals("onafterupdate", options.getHeader("x-api-key"));
		Assert.assertEquals("https://onafterupdate.com", options.getLocation());

		// Update object entry

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
	public void testUpdateObjectAction() throws Exception {
		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), true, "Able",
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD,
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).build());

		Assert.assertTrue(objectAction.isActive());
		Assert.assertEquals("Able", objectAction.getName());
		Assert.assertEquals(
			UnicodePropertiesBuilder.put(
				"secret", "0123456789"
			).build(),
			objectAction.getParametersUnicodeProperties());

		objectAction = _objectActionLocalService.updateObjectAction(
			objectAction.getObjectActionId(), false, "Baker",
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).build());

		Assert.assertFalse(objectAction.isActive());
		Assert.assertEquals("Baker", objectAction.getName());
		Assert.assertEquals(
			UnicodePropertiesBuilder.put(
				"secret", "30624700"
			).build(),
			objectAction.getParametersUnicodeProperties());
	}

	private final Queue<Object[]> _argumentsList = new LinkedList<>();

	@Inject
	private JSONFactory _jsonFactory;

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

}