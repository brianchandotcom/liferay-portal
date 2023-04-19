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
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.resource.v1_0.test.util.HTTPTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.internal.resource.v1_0.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.util.WebAppPool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class OpenAPIResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		WebAppPool.put(
			_company.getCompanyId(), WebKeys.PORTLET_CATEGORY,
			new PortletCategory());
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)));
	}

	@FeatureFlags("LPS-180090")
	@Test
	public void testGetActionsOpenAPI() throws Exception {
		String objectActionName = RandomTestUtil.randomString();

		List<String> availableActions = Arrays.asList(
			"get", "permissions", "replace", "update", "delete",
			objectActionName);

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			objectActionName, ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			new UnicodeProperties());

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		JSONObject actionsJSONObject = jsonObject.getJSONObject(
			"components"
		).getJSONObject(
			"schemas"
		).getJSONObject(
			_objectDefinition1.getShortName()
		).getJSONObject(
			"properties"
		).getJSONObject(
			"actions"
		).getJSONObject(
			"properties"
		);

		for (String action : availableActions) {
			Assert.assertNotNull(actionsJSONObject.get(action));
		}

		_objectActionLocalService.deleteObjectAction(objectAction);
	}

	@Test
	public void testGetNestedEntityInObjectRelationship() throws Exception {
		_testGetNestedEntityInObjectRelationship(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testGetNestedEntityInObjectRelationship(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testGetOpenAPI() throws Exception {
		_user = UserTestUtil.addUser(_company);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)),
			ObjectDefinitionConstants.SCOPE_COMPANY, _user.getUserId());

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, "/openapi", Http.Method.GET);

		JSONArray jsonArray = jsonObject.getJSONArray(
			_objectDefinition1.getRESTContextPath());

		Assert.assertEquals(1, jsonArray.length());
		Assert.assertEquals(
			"http://localhost:8080/o" +
				_objectDefinition1.getRESTContextPath() + "/openapi.yaml",
			jsonArray.get(0));

		jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));
		Assert.assertNull(
			jsonObject.getJSONArray(_objectDefinition2.getRESTContextPath()));

		JSONObject schemasJSONObject = jsonObject.getJSONObject(
			"components"
		).getJSONObject(
			"schemas"
		);

		Assert.assertNotNull(
			schemasJSONObject.getJSONObject("TaxonomyCategoryBrief"));

		JSONObject propertiesJSONObject = schemasJSONObject.getJSONObject(
			_objectDefinition1.getShortName()
		).getJSONObject(
			"properties"
		);

		Assert.assertNotNull(propertiesJSONObject.getJSONObject("keywords"));
		Assert.assertNotNull(
			propertiesJSONObject.getJSONObject("taxonomyCategoryBriefs"));
		Assert.assertNotNull(
			propertiesJSONObject.getJSONObject("taxonomyCategoryIds"));

		jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition2.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	@Test
	public void testGetOpenAPIWithCategorizationDisabled() throws Exception {
		_objectDefinition1.setEnableCategorization(false);

		_objectDefinition1 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition1);

		try {
			_user = UserTestUtil.addUser(_company);

			JSONObject jsonObject = HTTPTestUtil.invoke(
				null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
				Http.Method.GET);

			JSONObject schemasJSONObject = jsonObject.getJSONObject(
				"components"
			).getJSONObject(
				"schemas"
			);

			Assert.assertNull(
				schemasJSONObject.getJSONObject("TaxonomyCategoryBrief"));

			JSONObject propertiesJSONObject = schemasJSONObject.getJSONObject(
				_objectDefinition1.getShortName()
			).getJSONObject(
				"properties"
			);

			Assert.assertNull(propertiesJSONObject.getJSONObject("keywords"));
			Assert.assertNull(
				propertiesJSONObject.getJSONObject("taxonomyCategoryBriefs"));
			Assert.assertNull(
				propertiesJSONObject.getJSONObject("taxonomyCategoryIds"));
		}
		finally {
			_objectDefinition1.setEnableCategorization(true);

			_objectDefinition1 =
				_objectDefinitionLocalService.updateObjectDefinition(
					_objectDefinition1);
		}
	}

	private String _getNestedEntitySchema(
		JSONObject jsonObject, ObjectRelationship objectRelationship,
		ObjectDefinition objectDefinition) {

		String nestedEntitySchema;

		JSONObject nestedEntitySchemaJSONObject = jsonObject.getJSONObject(
			"components"
		).getJSONObject(
			"schemas"
		).getJSONObject(
			objectDefinition.getShortName()
		).getJSONObject(
			"properties"
		).getJSONObject(
			objectRelationship.getName()
		);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
			(objectDefinition.getObjectDefinitionId() ==
				_objectDefinition2.getObjectDefinitionId())) {

			nestedEntitySchema = (String)nestedEntitySchemaJSONObject.get(
				"$ref");
		}
		else {
			nestedEntitySchema =
				(String)nestedEntitySchemaJSONObject.getJSONObject(
					"items"
				).get(
					"$ref"
				);
		}

		return StringUtil.extractLast(nestedEntitySchema, "/");
	}

	private void _testGetNestedEntityInObjectRelationship(
			String objectRelationshipType)
		throws Exception {

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)));

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition1, _objectDefinition2,
				TestPropsValues.getUserId(), objectRelationshipType);

		JSONObject jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));

		Assert.assertEquals(
			_getNestedEntitySchema(
				jsonObject, objectRelationship, _objectDefinition1),
			_objectDefinition2.getShortName());

		jsonObject = HTTPTestUtil.invoke(
			null, _objectDefinition2.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));

		Assert.assertEquals(
			_getNestedEntitySchema(
				jsonObject, objectRelationship, _objectDefinition2),
			_objectDefinition1.getShortName());
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static Company _company;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private User _user;

}