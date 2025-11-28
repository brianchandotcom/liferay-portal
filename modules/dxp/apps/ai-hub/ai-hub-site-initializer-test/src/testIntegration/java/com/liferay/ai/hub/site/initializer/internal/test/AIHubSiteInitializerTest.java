/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class AIHubSiteInitializerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void test() throws Exception {
		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer("ai-hub-initializer");

		siteInitializer.initialize(_group.getGroupId());

		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_CHANGE_TONE,
			WorkflowDefinitionConstants.NAME_CHANGE_TONE);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.
				EXTERNAL_REFERENCE_CODE_CHAT_MESSAGE_PIPELINE,
			WorkflowDefinitionConstants.NAME_CHAT_MESSAGE_PIPELINE);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.
				EXTERNAL_REFERENCE_CODE_FIX_SPELLING_AND_GRAMMAR,
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_IMPROVE_WRITING,
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_LONGER,
			WorkflowDefinitionConstants.NAME_MAKE_LONGER);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_SHORTER,
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER);
	}

	private void _assertWorkflowDefinitionExists(
			String externalReferenceCode, String name)
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				externalReferenceCode, _group.getCompanyId());

		Assert.assertEquals(name, workflowDefinition.getName());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}