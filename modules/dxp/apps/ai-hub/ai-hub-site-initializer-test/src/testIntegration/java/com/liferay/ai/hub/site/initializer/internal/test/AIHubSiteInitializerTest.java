/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.site.initializer.internal.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import org.junit.After;
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
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testInitialize() throws Exception {
		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());

		_assertListTypeDefinitionExists(
			"L_AI_HUB_INSTRUCTION_DEFINITION_SCOPES");

		_assertObjectDefinitionExists("L_AI_HUB_AGENT_DEFINITION");
		_assertObjectDefinitionExists("L_AI_HUB_CHATBOT");
		_assertObjectDefinitionExists("L_AI_HUB_CONTENT_RETRIEVER");
		_assertObjectDefinitionExists("L_AI_HUB_INSTRUCTION_DEFINITION");
		_assertObjectDefinitionExists("L_AI_HUB_MCP_SERVER");

		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_CHANGE_TONE,
			WorkflowDefinitionConstants.NAME_CHANGE_TONE);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.
				EXTERNAL_REFERENCE_CODE_FIX_SPELLING_AND_GRAMMAR,
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_IMPROVE_WRITING,
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_LIFERAY_SEARCH,
			WorkflowDefinitionConstants.NAME_LIFERAY_SEARCH);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_LONGER,
			WorkflowDefinitionConstants.NAME_MAKE_LONGER);
		_assertWorkflowDefinitionExists(
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_MAKE_SHORTER,
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER);
	}

	private void _assertListTypeDefinitionExists(String externalReferenceCode)
		throws Exception {

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					externalReferenceCode, TestPropsValues.getCompanyId());

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "clickToChat");

		Assert.assertTrue(listTypeEntry.isSystem());
	}

	private void _assertObjectDefinitionExists(String externalReferenceCode)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, TestPropsValues.getCompanyId());

		Assert.assertTrue(objectDefinition.isApproved());
		Assert.assertTrue(objectDefinition.isSystem());
	}

	private void _assertWorkflowDefinitionExists(
			String externalReferenceCode, String name)
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), externalReferenceCode);

		Assert.assertEquals(name, workflowDefinition.getName());

		AccountEntry accountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		Assert.assertEquals(
			accountEntry.getAccountEntryGroupId(),
			workflowDefinition.getGroupId());
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}