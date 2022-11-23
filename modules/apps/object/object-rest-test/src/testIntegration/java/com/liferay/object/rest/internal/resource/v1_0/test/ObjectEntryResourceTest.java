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

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1,
					false)));

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_2,
					false)));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testExecuteStandaloneObjectAction() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-166918", "true"
			).build());

		Queue<Object[]> argumentsQueue = new LinkedList<>();

		ObjectScriptingExecutor objectScriptingExecutor =
			(ObjectScriptingExecutor)ReflectionTestUtil.getAndSetFieldValue(
				_objectActionExecutorRegistry.getObjectActionExecutor(
					ObjectActionExecutorConstants.KEY_GROOVY),
				"_objectScriptingExecutor",
				ProxyUtil.newProxyInstance(
					ObjectScriptingExecutor.class.getClassLoader(),
					new Class<?>[] {ObjectScriptingExecutor.class},
					(proxy, method, arguments) -> {
						argumentsQueue.add(arguments);

						return Collections.emptyMap();
					}));

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true, null,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"script", "println \"Hello World\""
			).build());

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				String.valueOf(_objectEntry1.getObjectEntryId()),
				"/object-actions/", objectAction.getName()),
			Http.Method.PUT);

		Assert.assertEquals(1, argumentsQueue.size());

		_assertObjectScriptingExecutorArguments(argumentsQueue.poll());

		HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry1.getExternalReferenceCode(), "/object-actions/",
				objectAction.getName()),
			Http.Method.PUT);

		Assert.assertEquals(1, argumentsQueue.size());

		_assertObjectScriptingExecutorArguments(argumentsQueue.poll());

		ReflectionTestUtil.setFieldValue(
			_objectActionExecutorRegistry.getObjectActionExecutor(
				ObjectActionExecutorConstants.KEY_GROOVY),
			"_objectScriptingExecutor", objectScriptingExecutor);

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-166918", "false"
			).build());
	}

	@Test
	public void testGetNestedFieldDetailsInOneToManyRelationship()
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition1, _objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationshipLocalService.addObjectRelationshipMappingTableValues(
			TestPropsValues.getUserId(),
			objectRelationship.getObjectRelationshipId(),
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ServiceContextTestUtil.getServiceContext());

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), "?nestedFields=r_",
				objectRelationship.getName(), "_",
				_objectDefinition1.getPKObjectFieldName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2,
			itemJSONObject.getString(_OBJECT_FIELD_NAME_2));

		JSONObject relatedObjectJSONObject = itemJSONObject.getJSONObject(
			StringBundler.concat(
				"r_", objectRelationship.getName(), "_",
				StringUtil.replaceLast(
					_objectDefinition1.getPKObjectFieldName(), "Id", "")));

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1,
			relatedObjectJSONObject.getString(_OBJECT_FIELD_NAME_1));

		_objectRelationshipLocalService.
			deleteObjectRelationshipMappingTableValues(
				objectRelationship.getObjectRelationshipId(),
				_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey());

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@Test
	public void testPutByExternalReferenceCodeManyToManyRelationship()
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition1, _objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry1.getExternalReferenceCode(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				_objectEntry2.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry2.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2, jsonObject.getString(_OBJECT_FIELD_NAME_2));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				_objectEntry1.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry1.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1, jsonObject.getString(_OBJECT_FIELD_NAME_1));

		jsonObject = HTTPTestUtil.invoke(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				objectRelationship.getName(), StringPool.SLASH,
				RandomTestUtil.randomString()),
			Http.Method.PUT);

		Assert.assertThat(
			jsonObject.getString("title"),
			CoreMatchers.containsString("No ObjectEntry exists with the key"));

		_objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	private void _assertObjectScriptingExecutorArguments(Object[] arguments)
		throws Exception {

		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1
			).put(
				"createDate", _objectEntry1.getCreateDate()
			).put(
				"creator", String.valueOf(TestPropsValues.getUserId())
			).put(
				"externalReferenceCode",
				_objectEntry1.getExternalReferenceCode()
			).put(
				"id", _objectEntry1.getObjectEntryId()
			).put(
				"modifiedDate", _objectEntry1.getModifiedDate()
			).put(
				"status", _objectEntry1.getStatus()
			).build(),
			arguments[0]);
		Assert.assertEquals(Collections.emptySet(), arguments[1]);
		Assert.assertEquals("println \"Hello World\"", arguments[2]);
	}

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_1 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_VALUE_2 =
		RandomTestUtil.randomString();

	@Inject
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}