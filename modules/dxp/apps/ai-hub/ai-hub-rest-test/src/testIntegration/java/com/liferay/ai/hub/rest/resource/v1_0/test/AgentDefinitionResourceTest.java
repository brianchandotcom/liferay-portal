/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.rest.client.dto.v1_0.AgentDefinition;
import com.liferay.ai.hub.rest.client.dto.v1_0.Variable;
import com.liferay.ai.hub.rest.client.pagination.Page;
import com.liferay.ai.hub.rest.client.pagination.Pagination;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.NoSuchWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class AgentDefinitionResourceTest
	extends BaseAgentDefinitionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), TestPropsValues.getUserId());

		_dtoConverterContext = new DefaultDTOConverterContext(
			false, Map.of(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());

		AccountEntry aiHubAccountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			aiHubAccountEntry.getAccountEntryId(), TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Override
	@Test
	public void testDeleteAgentDefinitionByExternalReferenceCode()
		throws Exception {

		AgentDefinition agentDefinition = _addAgentDefinition();

		ObjectDefinition objectDefinition = _getObjectDefinition();

		ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
			TestPropsValues.getCompanyId(), _dtoConverterContext,
			agentDefinition.getExternalReferenceCode(), objectDefinition, null);

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(),
				agentDefinition.getWorkflowDefinitionName(), 1);

		agentDefinitionResource.deleteAgentDefinitionByExternalReferenceCode(
			agentDefinition.getExternalReferenceCode());

		AssertUtils.assertFailure(
			NoSuchObjectEntryException.class,
			StringBundler.concat(
				"No ObjectEntry exists with the key {externalReferenceCode=",
				objectEntry.getExternalReferenceCode(),
				", groupId=0, companyId=", TestPropsValues.getCompanyId(),
				", objectDefinitionId=",
				objectDefinition.getObjectDefinitionId(), "}"),
			() -> _objectEntryManager.getObjectEntry(
				TestPropsValues.getCompanyId(), _dtoConverterContext,
				agentDefinition.getExternalReferenceCode(), objectDefinition,
				null));
		AssertUtils.assertFailure(
			NoSuchWorkflowDefinitionException.class,
			NoSuchDefinitionException.class.getName() +
				": No KaleoDefinition exists with the primary key " +
					workflowDefinition.getWorkflowDefinitionId(),
			() -> _workflowDefinitionManager.getWorkflowDefinition(
				workflowDefinition.getWorkflowDefinitionId()));
	}

	@Override
	@Test
	public void testGetAgentDefinitionsPage() throws Exception {
		_testGetAgentDefinitionsPage();
		_testGetAgentDefinitionsPageWithFilter();
	}

	@Ignore
	@Override
	@Test
	public void testGetAgentDefinitionsPageWithPagination() {
	}

	@Override
	@Test
	public void testPatchAgentDefinitionByExternalReferenceCodeUpdateActive()
		throws Exception {

		AgentDefinition agentDefinition = _addAgentDefinition();

		agentDefinition =
			agentDefinitionResource.
				patchAgentDefinitionByExternalReferenceCodeUpdateActive(
					agentDefinition.getExternalReferenceCode(), false);

		Assert.assertFalse(agentDefinition.getActive());

		agentDefinition =
			agentDefinitionResource.
				patchAgentDefinitionByExternalReferenceCodeUpdateActive(
					agentDefinition.getExternalReferenceCode(), true);

		Assert.assertTrue(agentDefinition.getActive());
	}

	@Override
	@Test
	public void testPostAgentDefinitionByExternalReferenceCodeCopy()
		throws Exception {

		ObjectEntry objectEntry1 = _objectEntryManager.getObjectEntry(
			TestPropsValues.getCompanyId(), _dtoConverterContext,
			WorkflowDefinitionConstants.EXTERNAL_REFERENCE_CODE_CHANGE_TONE,
			_getObjectDefinition(), null);
		WorkflowDefinition workflowDefinition1 =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				TestPropsValues.getCompanyId(),
				WorkflowDefinitionConstants.NAME_CHANGE_TONE);

		AgentDefinition agentDefinition =
			agentDefinitionResource.
				postAgentDefinitionByExternalReferenceCodeCopy(
					WorkflowDefinitionConstants.
						EXTERNAL_REFERENCE_CODE_CHANGE_TONE);

		ObjectEntry objectEntry2 = _objectEntryManager.getObjectEntry(
			TestPropsValues.getCompanyId(), _dtoConverterContext,
			agentDefinition.getExternalReferenceCode(), _getObjectDefinition(),
			null);

		Assert.assertEquals(
			objectEntry1.getPropertyValue("active"),
			objectEntry2.getPropertyValue("active"));
		Assert.assertEquals(
			objectEntry1.getPropertyValue("description"),
			objectEntry2.getPropertyValue("description"));
		Assert.assertNotEquals(
			objectEntry1.getPropertyValue("externalReferenceCode"),
			objectEntry2.getPropertyValue("externalReferenceCode"));
		Assert.assertEquals(
			objectEntry1.getPropertyValue("inputVariables"),
			objectEntry2.getPropertyValue("inputVariables"));
		Assert.assertEquals(
			objectEntry1.getPropertyValue("outputVariable"),
			objectEntry2.getPropertyValue("outputVariable"));
		Assert.assertNotEquals(
			objectEntry1.getPropertyValue("title_i18n"),
			objectEntry2.getPropertyValue("title_i18n"));
		Assert.assertNotEquals(
			objectEntry1.getPropertyValue("workflowDefinitionName"),
			objectEntry2.getPropertyValue("workflowDefinitionName"));

		WorkflowDefinition workflowDefinition2 =
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(),
				agentDefinition.getWorkflowDefinitionName(), 1);

		Assert.assertEquals(
			workflowDefinition1.getContentAsXML(),
			workflowDefinition2.getContentAsXML());
		Assert.assertEquals(
			workflowDefinition1.getDescription(),
			workflowDefinition2.getDescription());
		Assert.assertNotEquals(
			workflowDefinition1.getExternalReferenceCode(),
			workflowDefinition2.getExternalReferenceCode());
		Assert.assertNotEquals(
			workflowDefinition1.getName(), workflowDefinition2.getName());
		Assert.assertNotEquals(
			workflowDefinition1.getWorkflowDefinitionId(),
			workflowDefinition2.getWorkflowDefinitionId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "externalReferenceCode", "inputVariables",
			"outputVariable", "version", "workflowDefinitionName"
		};
	}

	@Override
	protected AgentDefinition
			testBatchEngineDeleteImportTask_addAgentDefinition()
		throws Exception {

		return _addAgentDefinition();
	}

	@Override
	protected AgentDefinition
			testDeleteAgentDefinitionByExternalReferenceCode_addAgentDefinition()
		throws Exception {

		return _addAgentDefinition();
	}

	@Override
	protected AgentDefinition testGetAgentDefinitionsPage_addAgentDefinition(
		AgentDefinition agentDefinition) {

		return agentDefinition;
	}

	private AgentDefinition _addAgentDefinition() throws Exception {
		return agentDefinitionResource.
			postAgentDefinitionByExternalReferenceCodeCopy(
				WorkflowDefinitionConstants.
					EXTERNAL_REFERENCE_CODE_CHANGE_TONE);
	}

	private ObjectDefinition _getObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.getObjectDefinition(
			TestPropsValues.getCompanyId(), "AIHubAgentDefinition");
	}

	private void _testGetAgentDefinitionsPage() throws Exception {
		Page<AgentDefinition> page =
			agentDefinitionResource.getAgentDefinitionsPage(
				null, null, Pagination.of(1, 10), null);

		assertEquals(
			_systemAgentDefinitions, (List<AgentDefinition>)page.getItems());
	}

	private void _testGetAgentDefinitionsPageWithFilter() throws Exception {

		// Active as false

		Page<AgentDefinition> page =
			agentDefinitionResource.getAgentDefinitionsPage(
				null, "(active eq false)", Pagination.of(1, 10), null);

		assertEquals(List.of(), (List<AgentDefinition>)page.getItems());

		// Active as true

		page = agentDefinitionResource.getAgentDefinitionsPage(
			null, "(active eq true)", Pagination.of(1, 10), null);

		assertEquals(
			_systemAgentDefinitions, (List<AgentDefinition>)page.getItems());
	}

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;

	private static DTOConverterContext _dtoConverterContext;

	@Inject
	private static DTOConverterRegistry _dtoConverterRegistry;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	private static final List<AgentDefinition> _systemAgentDefinitions =
		List.of(
			new AgentDefinition() {
				{
					active = true;
					externalReferenceCode =
						WorkflowDefinitionConstants.
							EXTERNAL_REFERENCE_CODE_CHANGE_TONE;
					inputVariables = new Variable[] {
						new Variable() {
							{
								name = "text";
								type = "string";
							}
						},
						new Variable() {
							{
								name = "tone";
								type = "string";
							}
						}
					};
					outputVariable = new Variable() {
						{
							name = "rewrittenText";
							type = "string";
						}
					};
					version = 1;
					workflowDefinitionName =
						WorkflowDefinitionConstants.NAME_CHANGE_TONE;
				}
			},
			new AgentDefinition() {
				{
					active = true;
					externalReferenceCode =
						WorkflowDefinitionConstants.
							EXTERNAL_REFERENCE_CODE_FIX_SPELLING_AND_GRAMMAR;
					inputVariables = new Variable[] {
						new Variable() {
							{
								name = "text";
								type = "string";
							}
						}
					};
					outputVariable = new Variable() {
						{
							name = "rewrittenText";
							type = "string";
						}
					};
					version = 1;
					workflowDefinitionName =
						WorkflowDefinitionConstants.
							NAME_FIX_SPELLING_AND_GRAMMAR;
				}
			},
			new AgentDefinition() {
				{
					active = true;
					externalReferenceCode =
						WorkflowDefinitionConstants.
							EXTERNAL_REFERENCE_CODE_IMPROVE_WRITING;
					inputVariables = new Variable[] {
						new Variable() {
							{
								name = "text";
								type = "string";
							}
						}
					};
					outputVariable = new Variable() {
						{
							name = "rewrittenText";
							type = "string";
						}
					};
					version = 1;
					workflowDefinitionName =
						WorkflowDefinitionConstants.NAME_IMPROVE_WRITING;
				}
			},
			new AgentDefinition() {
				{
					active = true;
					externalReferenceCode =
						WorkflowDefinitionConstants.
							EXTERNAL_REFERENCE_CODE_LIFERAY_SEARCH;
					inputVariables = new Variable[] {
						new Variable() {
							{
								name = "request";
								type = "string";
							}
						}
					};
					outputVariable = new Variable() {
						{
							name = "response";
							type = "string";
						}
					};
					version = 1;
					workflowDefinitionName =
						WorkflowDefinitionConstants.NAME_LIFERAY_SEARCH;
				}
			},
			new AgentDefinition() {
				{
					active = true;
					externalReferenceCode =
						WorkflowDefinitionConstants.
							EXTERNAL_REFERENCE_CODE_MAKE_LONGER;
					inputVariables = new Variable[] {
						new Variable() {
							{
								name = "text";
								type = "string";
							}
						}
					};
					outputVariable = new Variable() {
						{
							name = "rewrittenText";
							type = "string";
						}
					};
					version = 1;
					workflowDefinitionName =
						WorkflowDefinitionConstants.NAME_MAKE_LONGER;
				}
			},
			new AgentDefinition() {
				{
					active = true;
					externalReferenceCode =
						WorkflowDefinitionConstants.
							EXTERNAL_REFERENCE_CODE_MAKE_SHORTER;
					inputVariables = new Variable[] {
						new Variable() {
							{
								name = "text";
								type = "string";
							}
						}
					};
					outputVariable = new Variable() {
						{
							name = "rewrittenText";
							type = "string";
						}
					};
					version = 1;
					workflowDefinitionName =
						WorkflowDefinitionConstants.NAME_MAKE_SHORTER;
				}
			});

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(filter = "object.entry.manager.storage.type=default")
	private ObjectEntryManager _objectEntryManager;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}