/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Fábio Alves
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-17564"), @FeatureFlag("LPD-58677")}
)
@RunWith(Arquillian.class)
public class ViewAssigneeFieldComponentSectionFragmentRendererTest
	extends BaseComponentSectionFragmentRendererTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_taskObjectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		_taskObjectEntry = _objectEntryLocalService.addObjectEntry(
			projectObjectEntry.getGroupId(), projectObjectEntry.getUserId(),
			_taskObjectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"r_cmpProjectToCMPTasks_c_cmpProjectId",
				projectObjectEntry.getObjectEntryId()
			).put(
				"title", RandomTestUtil.randomString()
			).build(),
			serviceContext);

		httpServletRequest = getHttpServletRequest(
			_taskObjectDefinition, _taskObjectEntry);
	}

	@Test
	public void testGetProps() throws Exception {
		Map<String, Object> props = getProps();

		Assert.assertEquals("Assignee", props.get("label"));
		Assert.assertEquals("ObjectField_assignTo", props.get("name"));
		Assert.assertEquals("/o/cmp/assignee-context/", props.get("searchURL"));
		Assert.assertTrue((Boolean)props.get("visible"));

		ClassName className = _classNameLocalService.getClassName(
			Role.class.getName());
		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		_assertAssigneeFieldValue(
			className.getClassNameId(), role.getRoleId(),
			role.getExternalReferenceCode(), null, role.getName(), "role");

		className = _classNameLocalService.getClassName(User.class.getName());
		User user = _userLocalService.getUser(_taskObjectEntry.getUserId());

		_assertAssigneeFieldValue(
			className.getClassNameId(), user.getUserId(),
			user.getExternalReferenceCode(), user.getPortraitURL(themeDisplay),
			user.getFullName(), "user");
	}

	@Override
	protected FragmentRenderer getFragmentRenderer() {
		return _fragmentRenderer;
	}

	private void _assertAssigneeFieldValue(
			long classNameId, long classPK,
			String expectedExternalReferenceCode, String expectedImage,
			String expectedName, String expectedType)
		throws Exception {

		_taskObjectEntry = _objectEntryLocalService.partialUpdateObjectEntry(
			_taskObjectEntry.getUserId(), _taskObjectEntry.getObjectEntryId(),
			_taskObjectEntry.getObjectEntryFolderId(),
			HashMapBuilder.<String, Serializable>put(
				"assignTo",
				HashMapBuilder.put(
					"classNameId", classNameId
				).put(
					"classPK", classPK
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(
					_taskObjectDefinition.getClassName());

		httpServletRequest.setAttribute(
			LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					layoutDisplayPageProvider.getClassName(),
					_taskObjectEntry.getObjectEntryId())));

		JSONObject jsonObject = _jsonFactory.createJSONObject(getProps());

		JSONAssert.assertEquals(
			JSONUtil.put(
				"externalReferenceCode", expectedExternalReferenceCode
			).put(
				"image", expectedImage
			).put(
				"name", expectedName
			).put(
				"type", expectedType
			).toString(),
			String.valueOf(jsonObject.getJSONObject("value")), true);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cmp.site.initializer.internal.fragment.renderer.ViewAssigneeFieldComponentSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ObjectDefinition _taskObjectDefinition;
	private ObjectEntry _taskObjectEntry;

	@Inject
	private UserLocalService _userLocalService;

}