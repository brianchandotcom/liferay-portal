/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
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
		CMPTestUtil.getOrAddGroup(ObjectEntryResourceTest.class);

		_projectObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT", TestPropsValues.getCompanyId());
		_projectAssetRelationshipObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT_ASSET_RELATIONSHIP",
					TestPropsValues.getCompanyId());
		_taskObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", TestPropsValues.getCompanyId());
	}

	@Test
	public void testPostObjectEntry() throws Exception {
		DepotEntry depotEntry1 = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), DepotConstants.TYPE_PROJECT,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			409,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_projectObjectDefinition.getRESTContextPath() + "/scopes/" +
					depotEntry1.getGroupId(),
				Http.Method.POST));

		JSONObject projectJSONObject = _addProject();

		DepotEntry depotEntry2 = _depotEntryLocalService.fetchGroupDepotEntry(
			projectJSONObject.getLong("scopeId"));

		Assert.assertEquals(DepotConstants.TYPE_PROJECT, depotEntry2.getType());

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"r_cmpProjectToCMPTasks_c_cmpProjectId",
					projectJSONObject.getLong("id")
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				_taskObjectDefinition.getRESTContextPath() + "/scopes/" +
					depotEntry1.getGroupId(),
				Http.Method.POST));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"r_cmpProjectToCMPTasks_c_cmpProjectERC",
					projectJSONObject.getString("externalReferenceCode")
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				_taskObjectDefinition.getRESTContextPath() + "/scopes/" +
					depotEntry1.getGroupId(),
				Http.Method.POST));

		JSONObject taskJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"r_cmpProjectToCMPTasks_c_cmpProjectERC",
				projectJSONObject.getString("externalReferenceCode")
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			_taskObjectDefinition.getRESTContextPath() + "/scopes/" +
				depotEntry2.getGroupId(),
			Http.Method.POST);

		Assert.assertEquals(
			projectJSONObject.getLong("id"),
			taskJSONObject.getLong("r_cmpProjectToCMPTasks_c_cmpProjectId"));
		Assert.assertEquals(
			projectJSONObject.getLong("scopeId"),
			taskJSONObject.getLong("scopeId"));

		_testPostObjectEntryForProjectAssetRelationship();
	}

	private JSONObject _addProject() throws Exception {
		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"title", RandomTestUtil.randomString()
			).toString(),
			_projectObjectDefinition.getRESTContextPath(), Http.Method.POST);
	}

	private JSONObject _addProjectAssetRelationship(
			String classExternalReferenceCode, String className,
			String groupExternalReferenceCode, JSONObject projectJSONObject)
		throws Exception {

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"classExternalReferenceCode", classExternalReferenceCode
			).put(
				"className", className
			).put(
				"groupExternalReferenceCode", groupExternalReferenceCode
			).put(
				"r_cmpProjectToCMPProjectAssetRelationships_c_cmpProjectId",
				projectJSONObject.getLong("id")
			).toString(),
			_projectAssetRelationshipObjectDefinition.getRESTContextPath() +
				"/scopes/" + projectJSONObject.getLong("scopeId"),
			Http.Method.POST);
	}

	private void _testPostObjectEntryForProjectAssetRelationship()
		throws Exception {

		// The asset in a project

		JSONObject projectJSONObject = _addProject();

		String classExternalReferenceCode = RandomTestUtil.randomString();
		String className = RandomTestUtil.randomString();
		String groupExternalReferenceCode = RandomTestUtil.randomString();

		JSONObject projectAssetRelationshipJSONObject =
			_addProjectAssetRelationship(
				classExternalReferenceCode, className,
				groupExternalReferenceCode, projectJSONObject);

		Assert.assertEquals(
			classExternalReferenceCode,
			projectAssetRelationshipJSONObject.getString(
				"classExternalReferenceCode"));
		Assert.assertEquals(
			className,
			projectAssetRelationshipJSONObject.getString("className"));
		Assert.assertEquals(
			groupExternalReferenceCode,
			projectAssetRelationshipJSONObject.getString(
				"groupExternalReferenceCode"));
		Assert.assertEquals(
			projectJSONObject.getLong("id"),
			projectAssetRelationshipJSONObject.getLong(
				"r_cmpProjectToCMPProjectAssetRelationships_c_cmpProjectId"));
		Assert.assertEquals(
			projectJSONObject.getLong("scopeId"),
			projectAssetRelationshipJSONObject.getLong("scopeId"));

		// The same asset in a different project

		JSONObject anotherProjectJSONObject = _addProject();

		projectAssetRelationshipJSONObject = _addProjectAssetRelationship(
			classExternalReferenceCode, className, groupExternalReferenceCode,
			anotherProjectJSONObject);

		Assert.assertEquals(
			anotherProjectJSONObject.getLong("id"),
			projectAssetRelationshipJSONObject.getLong(
				"r_cmpProjectToCMPProjectAssetRelationships_c_cmpProjectId"));

		// The same asset in the same project

		String bodyJSONString = JSONUtil.put(
			"classExternalReferenceCode", classExternalReferenceCode
		).put(
			"className", className
		).put(
			"groupExternalReferenceCode", groupExternalReferenceCode
		).put(
			"r_cmpProjectToCMPProjectAssetRelationships_c_cmpProjectId",
			anotherProjectJSONObject.getLong("id")
		).toString();

		String restContextPath =
			_projectAssetRelationshipObjectDefinition.getRESTContextPath() +
				"/scopes/" + anotherProjectJSONObject.getLong("scopeId");

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				bodyJSONString, restContextPath, Http.Method.POST));
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectDefinition _projectAssetRelationshipObjectDefinition;
	private ObjectDefinition _projectObjectDefinition;
	private ObjectDefinition _taskObjectDefinition;

}