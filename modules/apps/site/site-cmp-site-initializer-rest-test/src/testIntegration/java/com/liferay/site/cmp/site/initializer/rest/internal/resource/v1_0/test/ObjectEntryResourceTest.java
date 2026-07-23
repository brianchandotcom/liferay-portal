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
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cmp.site.initializer.test.util.CMPTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

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

		_basicWebContentObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", TestPropsValues.getCompanyId());
		_projectLinkObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT_LINK", TestPropsValues.getCompanyId());
		_projectObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_PROJECT", TestPropsValues.getCompanyId());
		_taskObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", TestPropsValues.getCompanyId());
	}

	@Test
	public void testPostProjectLinkObjectEntry() throws Exception {

		// Link basic web content in a project

		ObjectEntry basicWebContentObjectEntry =
			_addBasicWebContentObjectEntry();

		String classExternalReferenceCode =
			basicWebContentObjectEntry.getExternalReferenceCode();
		String className = basicWebContentObjectEntry.getModelClassName();

		Group group = _groupLocalService.getGroup(
			basicWebContentObjectEntry.getGroupId());

		String groupExternalReferenceCode = group.getExternalReferenceCode();

		JSONObject projectObjectEntryJSONObject1 = _postProjectObjectEntry();

		JSONObject projectLinkObjectEntryJSONObject =
			_postProjectLinkObjectEntry(
				classExternalReferenceCode, className,
				groupExternalReferenceCode, projectObjectEntryJSONObject1);

		Assert.assertEquals(
			classExternalReferenceCode,
			projectLinkObjectEntryJSONObject.getString(
				"classExternalReferenceCode"));
		Assert.assertEquals(
			className, projectLinkObjectEntryJSONObject.getString("className"));
		Assert.assertEquals(
			groupExternalReferenceCode,
			projectLinkObjectEntryJSONObject.getString(
				"groupExternalReferenceCode"));
		Assert.assertEquals(
			projectObjectEntryJSONObject1.getLong("id"),
			projectLinkObjectEntryJSONObject.getLong(
				"r_cmpProjectToCMPProjectLinks_c_cmpProjectId"));
		Assert.assertEquals(
			projectObjectEntryJSONObject1.getLong("scopeId"),
			projectLinkObjectEntryJSONObject.getLong("scopeId"));

		// Link the same basic web content in a different project

		JSONObject projectObjectEntryJSONObject2 = _postProjectObjectEntry();

		projectLinkObjectEntryJSONObject = _postProjectLinkObjectEntry(
			classExternalReferenceCode, className, groupExternalReferenceCode,
			projectObjectEntryJSONObject2);

		Assert.assertEquals(
			projectObjectEntryJSONObject2.getLong("id"),
			projectLinkObjectEntryJSONObject.getLong(
				"r_cmpProjectToCMPProjectLinks_c_cmpProjectId"));

		// Link the same basic web content in the same project

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"classExternalReferenceCode", classExternalReferenceCode
				).put(
					"className", className
				).put(
					"groupExternalReferenceCode", groupExternalReferenceCode
				).put(
					"r_cmpProjectToCMPProjectLinks_c_cmpProjectId",
					projectObjectEntryJSONObject2.getLong("id")
				).toString(),
				_projectLinkObjectDefinition.getRESTContextPath() + "/scopes/" +
					projectObjectEntryJSONObject2.getLong("scopeId"),
				Http.Method.POST));
	}

	@Test
	public void testPostProjectObjectEntry() throws Exception {
		DepotEntry depotEntry = _addProjectDepotEntry();

		Assert.assertEquals(
			409,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_projectObjectDefinition.getRESTContextPath() + "/scopes/" +
					depotEntry.getGroupId(),
				Http.Method.POST));

		JSONObject projectObjectEntryJSONObject = _postProjectObjectEntry();

		depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
			projectObjectEntryJSONObject.getLong("scopeId"));

		Assert.assertEquals(DepotConstants.TYPE_PROJECT, depotEntry.getType());
	}

	@Test
	public void testPostTaskObjectEntry() throws Exception {

		// Different project scope

		DepotEntry depotEntry = _addProjectDepotEntry();

		JSONObject projectObjectEntryJSONObject = _postProjectObjectEntry();

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"r_cmpProjectToCMPTasks_c_cmpProjectId",
					projectObjectEntryJSONObject.getLong("id")
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				_taskObjectDefinition.getRESTContextPath() + "/scopes/" +
					depotEntry.getGroupId(),
				Http.Method.POST));
		Assert.assertEquals(
			404,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"r_cmpProjectToCMPTasks_c_cmpProjectERC",
					projectObjectEntryJSONObject.getString(
						"externalReferenceCode")
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				_taskObjectDefinition.getRESTContextPath() + "/scopes/" +
					depotEntry.getGroupId(),
				Http.Method.POST));

		// Same project scope

		JSONObject taskObjectEntryJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"r_cmpProjectToCMPTasks_c_cmpProjectERC",
				projectObjectEntryJSONObject.getString("externalReferenceCode")
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			_taskObjectDefinition.getRESTContextPath() + "/scopes/" +
				projectObjectEntryJSONObject.getLong("scopeId"),
			Http.Method.POST);

		Assert.assertEquals(
			projectObjectEntryJSONObject.getLong("id"),
			taskObjectEntryJSONObject.getLong(
				"r_cmpProjectToCMPTasks_c_cmpProjectId"));
		Assert.assertEquals(
			projectObjectEntryJSONObject.getLong("scopeId"),
			taskObjectEntryJSONObject.getLong("scopeId"));
	}

	private ObjectEntry _addBasicWebContentObjectEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), TestPropsValues.getUserId(),
			_basicWebContentObjectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
					RandomTestUtil.randomString()
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private DepotEntry _addProjectDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), DepotConstants.TYPE_PROJECT,
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	private JSONObject _postProjectLinkObjectEntry(
			String classExternalReferenceCode, String className,
			String groupExternalReferenceCode,
			JSONObject projectObjectEntryJSONObject)
		throws Exception {

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"classExternalReferenceCode", classExternalReferenceCode
			).put(
				"className", className
			).put(
				"groupExternalReferenceCode", groupExternalReferenceCode
			).put(
				"r_cmpProjectToCMPProjectLinks_c_cmpProjectId",
				projectObjectEntryJSONObject.getLong("id")
			).toString(),
			_projectLinkObjectDefinition.getRESTContextPath() + "/scopes/" +
				projectObjectEntryJSONObject.getLong("scopeId"),
			Http.Method.POST);
	}

	private JSONObject _postProjectObjectEntry() throws Exception {
		JSONObject projectObjectEntryJSONObject =
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"title", RandomTestUtil.randomString()
				).toString(),
				_projectObjectDefinition.getRESTContextPath(),
				Http.Method.POST);

		_depotEntries.add(
			_depotEntryLocalService.fetchGroupDepotEntry(
				projectObjectEntryJSONObject.getLong("scopeId")));

		return projectObjectEntryJSONObject;
	}

	private ObjectDefinition _basicWebContentObjectDefinition;

	@DeleteAfterTestRun
	private List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectDefinition _projectLinkObjectDefinition;
	private ObjectDefinition _projectObjectDefinition;
	private ObjectDefinition _taskObjectDefinition;

}