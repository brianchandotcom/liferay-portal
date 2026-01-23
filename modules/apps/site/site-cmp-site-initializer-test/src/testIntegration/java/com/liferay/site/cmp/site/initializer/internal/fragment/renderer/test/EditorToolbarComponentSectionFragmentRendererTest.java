/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.fragment.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
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
public class EditorToolbarComponentSectionFragmentRendererTest
	extends BaseComponentSectionFragmentRendererTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetProps() throws Exception {
		String redirect = "/redirect-url";

		mockHttpServletRequest.setParameter("redirect", redirect);

		Map<String, Object> props = getProps();

		Assert.assertEquals("/redirect-url", props.get("backURL"));

		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPathFriendlyURLPublic(),
				GroupConstants.CMS_FRIENDLY_URL, "/e/project/",
				PortalUtil.getClassNameId(
					projectObjectDefinition.getClassName()),
				StringPool.SLASH, projectObjectEntry.getObjectEntryId()),
			props.get("formSubmitURL"));

		Assert.assertEquals("New Project", props.get("title"));

		ObjectDefinition taskObjectDefinition =
			objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		ObjectEntry taskObjectEntry = _objectEntryLocalService.addObjectEntry(
			projectObjectEntry.getGroupId(), projectObjectEntry.getUserId(),
			taskObjectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"r_cmpProjectToCMPTasks_c_cmpProjectId",
				projectObjectEntry.getObjectEntryId()
			).build(),
			serviceContext);

		mockHttpServletRequest = getMockHttpServletRequest(
			taskObjectDefinition, taskObjectEntry);

		mockHttpServletRequest.setParameter("redirect", redirect);

		props = getProps();

		Assert.assertEquals("/redirect-url", props.get("backURL"));

		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPathFriendlyURLPublic(),
				GroupConstants.CMS_FRIENDLY_URL, "/e/task/",
				PortalUtil.getClassNameId(taskObjectDefinition.getClassName()),
				StringPool.SLASH, taskObjectEntry.getObjectEntryId()),
			props.get("formSubmitURL"));

		Assert.assertEquals("New Task", props.get("title"));

		mockHttpServletRequest = getMockHttpServletRequest(
			taskObjectDefinition, taskObjectEntry);

		mockHttpServletRequest.setParameter(
			"isCreateTaskGlobalTaskListPage", "true");

		mockHttpServletRequest.setParameter("redirect", redirect);

		props = getProps();

		Assert.assertEquals("/redirect-url", props.get("backURL"));

		Assert.assertEquals(redirect, props.get("formSubmitURL"));

		Assert.assertEquals("New Task", props.get("title"));

		mockHttpServletRequest = getMockHttpServletRequest(
			taskObjectDefinition, taskObjectEntry);

		mockHttpServletRequest.setParameter(
			"isCreateProjectGlobalTaskListPage", "true");

		mockHttpServletRequest.setParameter("redirect", redirect);

		props = getProps();

		Assert.assertEquals("/redirect-url", props.get("backURL"));

		Assert.assertEquals(
			StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				GroupConstants.CMS_FRIENDLY_URL,
				"/add_task?objectDefinitionId=",
				taskObjectDefinition.getObjectDefinitionId(), "&plid=",
				themeDisplay.getPlid(), "&projectGroupId=",
				taskObjectEntry.getGroupId(), "&projectId=",
				taskObjectEntry.getObjectEntryId(), "&redirect=", redirect,
				"&isCreateTaskGlobalTaskListPage=true"),
			props.get("formSubmitURL"));

		Assert.assertEquals("New Task", props.get("title"));
	}

	@Override
	protected FragmentRenderer getFragmentRenderer() {
		return _fragmentRenderer;
	}

	@Inject(
		filter = "component.name=com.liferay.site.cmp.site.initializer.internal.fragment.renderer.EditorToolbarComponentSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}