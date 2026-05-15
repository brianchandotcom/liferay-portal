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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
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
			"L_AI_HUB_CRAWLER_JOB_STATUSES", "abandoned", "dispatched",
			"failed", "queued", "running", "succeeded");
		_assertListTypeDefinitionExists(
			"L_AI_HUB_INSTRUCTION_DEFINITION_SCOPES", "clickToChat", "cms",
			"everywhere");
		_assertListTypeDefinitionExists(
			"L_AI_HUB_MODEL_ARMOR_TEMPLATE_CONFIDENCE_LEVELS", "high",
			"lowAndAbove", "mediumAndAbove");
		_assertListTypeDefinitionExists(
			"L_AI_HUB_MODEL_ARMOR_TEMPLATE_GUARDRAIL_TYPES", "input", "output");
		_assertListTypeDefinitionExists(
			"L_AI_HUB_MODEL_ARMOR_TEMPLATE_RAI_LEVELS", "high", "lowAndAbove",
			"mediumAndAbove", "none");

		_assertObjectDefinitionExists("L_AI_HUB_AGENT_DEFINITION");
		_assertObjectDefinitionExists("L_AI_HUB_CHATBOT");
		_assertObjectDefinitionExists("L_AI_HUB_CONTENT_RETRIEVER");
		_assertObjectDefinitionExists("L_AI_HUB_CRAWLER_JOB");
		_assertObjectDefinitionExists("L_AI_HUB_INSTRUCTION_DEFINITION");
		_assertObjectDefinitionExists("L_AI_HUB_MCP_SERVER");
		_assertObjectDefinitionExists("L_AI_HUB_MODEL_ARMOR_TEMPLATE");

		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_ACCOUNT_TO_L_AI_HUB_AGENT_DEFINITIONS", "L_ACCOUNT",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_ACCOUNT_TO_L_AI_HUB_CONTENT_RETRIEVERS", "L_ACCOUNT",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_ACCOUNT_TO_L_AI_HUB_CRAWLER_JOBS", "L_ACCOUNT",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_ACCOUNT_TO_L_AI_HUB_MCP_SERVERS", "L_ACCOUNT",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_ACCOUNT_TO_L_AI_HUB_MODEL_ARMOR_TEMPLATES", "L_ACCOUNT",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
			"L_AI_HUB_AGENT_DEFINITIONS_TO_L_AI_HUB_CONTENT_RETRIEVERS",
			"L_AI_HUB_AGENT_DEFINITION",
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_AI_HUB_AGENT_DEFINITIONS_TO_L_AI_HUB_MODEL_ARMOR_TEMPLATES",
			"L_AI_HUB_AGENT_DEFINITION",
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_assertObjectRelationshipExists(
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			"L_AI_HUB_CONTENT_RETRIEVER_TO_L_AI_HUB_CRAWLER_JOBS",
			"L_AI_HUB_CONTENT_RETRIEVER",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

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

	private void _assertListTypeDefinitionExists(
			String externalReferenceCode, String... listTypeEntryKeys)
		throws Exception {

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					externalReferenceCode, TestPropsValues.getCompanyId());

		for (String listTypeEntryKey : listTypeEntryKeys) {
			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.getListTypeEntry(
					listTypeDefinition.getListTypeDefinitionId(),
					listTypeEntryKey);

			Assert.assertTrue(listTypeEntry.isSystem());
		}
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

	private void _assertObjectRelationshipExists(
			String deletionType, String externalReferenceCode,
			String objectDefinitionExternalReferenceCode, String type)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode,
					TestPropsValues.getCompanyId());

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByExternalReferenceCode(
					externalReferenceCode,
					objectDefinition.getObjectDefinitionId());

		Assert.assertEquals(deletionType, objectRelationship.getDeletionType());
		Assert.assertEquals(type, objectRelationship.getType());
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
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}