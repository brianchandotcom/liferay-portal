/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.object.rest.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
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

		PortalInstances.initCompany(_company);
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

		// Collection actions

		_testGetActionsOpenAPI(
			Arrays.asList(
				"create", "createBatch", "deleteBatch", "updateBatch"),
			"Page" + _objectDefinition1.getShortName());

		// Individual actions

		String objectActionName = RandomTestUtil.randomString();

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			objectActionName, ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			new UnicodeProperties());

		_testGetActionsOpenAPI(
			Arrays.asList(
				"delete", "get", "permissions", "replace", "update",
				objectActionName),
			_objectDefinition1.getShortName());

		// Permission actions

		_testGetActionsOpenAPI(
			Arrays.asList("delete", "get", "permissions", "replace", "update"),
			"PagePermission");

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
	public void testGetObjectRelationshipEndpoints() throws Exception {

		// Active

		_testGetObjectRelationshipEndpoints(
			true, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_testGetObjectRelationshipEndpoints(
			true, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		// Inactive

		_testGetObjectRelationshipEndpoints(
			false, ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_testGetObjectRelationshipEndpoints(
			false, ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
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

		_testGetOpenAPI(_objectDefinition1, _objectDefinition2);

		_siteScopedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME,
						false)),
				ObjectDefinitionConstants.SCOPE_SITE,
				TestPropsValues.getUserId());

		_testGetOpenAPI(_siteScopedObjectDefinition, _objectDefinition2);
	}

	@Ignore
	@Test
	public void testGetOpenAPIWithCategorizationDisabled() throws Exception {
		_objectDefinition1.setEnableCategorization(false);

		_objectDefinition1 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition1);

		try {
			_user = UserTestUtil.addUser(_company);

			JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
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

	private void _assertObjectRelationshipEndpoints(
		boolean active, JSONObject jsonObject,
		ObjectRelationship objectRelationship) {

		if (active) {
			Assert.assertNotNull(
				jsonObject.getJSONObject(
					StringBundler.concat(
						"/{",
						StringUtil.lowerCaseFirstLetter(
							_objectDefinition1.getShortName()),
						"Id}/", objectRelationship.getName())));
			Assert.assertNotNull(
				jsonObject.getJSONObject(
					StringBundler.concat(
						"/{",
						StringUtil.lowerCaseFirstLetter(
							_objectDefinition1.getShortName()),
						"Id}/", objectRelationship.getName(), "/{",
						StringUtil.lowerCaseFirstLetter(
							_objectDefinition2.getShortName()),
						"Id}")));
		}
		else {
			Assert.assertNull(
				jsonObject.getJSONObject(
					StringBundler.concat(
						"/{",
						StringUtil.lowerCaseFirstLetter(
							_objectDefinition1.getShortName()),
						"Id}/", objectRelationship.getName())));
			Assert.assertNull(
				jsonObject.getJSONObject(
					StringBundler.concat(
						"/{",
						StringUtil.lowerCaseFirstLetter(
							_objectDefinition1.getShortName()),
						"Id}/", objectRelationship.getName(), "/{",
						StringUtil.lowerCaseFirstLetter(
							_objectDefinition2.getShortName()),
						"Id}")));
		}
	}

	private String _getNestedEntitySchema(
		boolean active, JSONObject jsonObject,
		ObjectRelationship objectRelationship,
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

		if (!active && (nestedEntitySchemaJSONObject == null)) {
			return null;
		}

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

	private void _testGetActionsOpenAPI(List<String> actions, String schemaName)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		JSONObject actionsJSONObject = jsonObject.getJSONObject(
			"components"
		).getJSONObject(
			"schemas"
		).getJSONObject(
			schemaName
		).getJSONObject(
			"properties"
		).getJSONObject(
			"actions"
		).getJSONObject(
			"properties"
		);

		for (String action : actions) {
			JSONObject actionJSONObject = actionsJSONObject.getJSONObject(
				action);

			Assert.assertNotNull(actionJSONObject.get("properties"));
		}
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

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));

		Assert.assertEquals(
			_getNestedEntitySchema(
				true, jsonObject, objectRelationship, _objectDefinition1),
			_objectDefinition2.getShortName());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, _objectDefinition2.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));

		Assert.assertEquals(
			_getNestedEntitySchema(
				true, jsonObject, objectRelationship, _objectDefinition2),
			_objectDefinition1.getShortName());
	}

	private void _testGetObjectRelationshipEndpoints(
			boolean active, String objectRelationshipType)
		throws Exception {

		if (active) {
			_objectDefinition2 =
				ObjectDefinitionTestUtil.publishObjectDefinition(
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							"Text", "String", true, true, null,
							RandomTestUtil.randomString(), _OBJECT_FIELD_NAME,
							false)));
		}
		else {
			_objectDefinition2 =
				ObjectDefinitionTestUtil.addCustomObjectDefinition(
					Collections.singletonList(
						ObjectFieldUtil.createObjectField(
							"Text", "String", true, true, null,
							RandomTestUtil.randomString(), _OBJECT_FIELD_NAME,
							false)));
		}

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_objectDefinition1, _objectDefinition2,
				TestPropsValues.getUserId(), objectRelationshipType);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, _objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));

		if (active) {
			Assert.assertEquals(
				_getNestedEntitySchema(
					active, jsonObject, objectRelationship, _objectDefinition1),
				_objectDefinition2.getShortName());
		}
		else {
			Assert.assertNull(
				_getNestedEntitySchema(
					active, jsonObject, objectRelationship,
					_objectDefinition1));
		}

		_assertObjectRelationshipEndpoints(
			active, jsonObject.getJSONObject("paths"), objectRelationship);
	}

	private void _testGetOpenAPI(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "/openapi", Http.Method.GET);

		JSONArray jsonArray = jsonObject.getJSONArray(
			objectDefinition1.getRESTContextPath());

		Assert.assertEquals(1, jsonArray.length());
		Assert.assertEquals(
			"http://localhost:8080/o" + objectDefinition1.getRESTContextPath() +
				"/openapi.yaml",
			jsonArray.get(0));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, objectDefinition1.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertNotNull(jsonObject.getString("openapi"));
		Assert.assertNull(
			jsonObject.getJSONArray(objectDefinition2.getRESTContextPath()));

		JSONObject schemasJSONObject = jsonObject.getJSONObject(
			"components"
		).getJSONObject(
			"schemas"
		);

		Assert.assertNotNull(
			schemasJSONObject.getJSONObject("TaxonomyCategoryBrief"));

		JSONObject propertiesJSONObject = schemasJSONObject.getJSONObject(
			objectDefinition1.getShortName()
		).getJSONObject(
			"properties"
		);

		Assert.assertNull(propertiesJSONObject.getJSONObject("createDate"));
		Assert.assertNotNull(propertiesJSONObject.getJSONObject("keywords"));
		Assert.assertNull(propertiesJSONObject.getJSONObject("modifiedDate"));
		Assert.assertNotNull(
			propertiesJSONObject.getJSONObject("taxonomyCategoryBriefs"));
		Assert.assertNotNull(
			propertiesJSONObject.getJSONObject("taxonomyCategoryIds"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, objectDefinition2.getRESTContextPath() + "/openapi.json",
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
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
	private ObjectDefinition _siteScopedObjectDefinition;

	@DeleteAfterTestRun
	private User _user;

}