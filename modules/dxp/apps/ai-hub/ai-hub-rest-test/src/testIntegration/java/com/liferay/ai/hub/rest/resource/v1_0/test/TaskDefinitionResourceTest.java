/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.rest.client.dto.v1_0.TaskDefinition;
import com.liferay.ai.hub.rest.client.pagination.Page;
import com.liferay.ai.hub.rest.client.pagination.Pagination;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.NoSuchWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author João Victor Alves
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class TaskDefinitionResourceTest
	extends BaseTaskDefinitionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
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
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Override
	@Test
	public void testDeleteTaskDefinition() throws Exception {
		WorkflowDefinition workflowDefinition = _deployWorkflowDefinition();

		long workflowDefinitionId =
			workflowDefinition.getWorkflowDefinitionId();

		taskDefinitionResource.deleteTaskDefinition(workflowDefinitionId);

		AssertUtils.assertFailure(
			NoSuchWorkflowDefinitionException.class,
			NoSuchDefinitionException.class.getName() +
				": No KaleoDefinition exists with the primary key " +
					workflowDefinitionId,
			() -> _workflowDefinitionManager.getWorkflowDefinition(
				workflowDefinitionId));
	}

	@Override
	@Test
	public void testGetTaskDefinitionsPage() throws Exception {
		_testGetTaskDefinitionsPage();
		_testGetTaskDefinitionsPageWithFilter();
	}

	@Ignore
	@Override
	@Test
	public void testGetTaskDefinitionsPageWithPagination() {
	}

	@Ignore
	@Override
	@Test
	public void testGetTaskDefinitionsPageWithSortInteger() throws Exception {
	}

	@Override
	@Test
	public void testPatchTaskDefinitionUpdateActive() throws Exception {
		WorkflowDefinition workflowDefinition = _deployWorkflowDefinition();

		TaskDefinition taskDefinition =
			taskDefinitionResource.patchTaskDefinitionUpdateActive(
				workflowDefinition.getWorkflowDefinitionId(), false);

		Assert.assertFalse(taskDefinition.getActive());

		taskDefinition = taskDefinitionResource.patchTaskDefinitionUpdateActive(
			workflowDefinition.getWorkflowDefinitionId(), true);

		Assert.assertTrue(taskDefinition.getActive());
	}

	@Override
	@Test
	public void testPostTaskDefinitionCopy() throws Exception {
		WorkflowDefinition workflowDefinition1 =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				TestPropsValues.getCompanyId(),
				WorkflowDefinitionConstants.NAME_CHANGE_TONE);

		TaskDefinition taskDefinition =
			taskDefinitionResource.postTaskDefinitionCopy(
				workflowDefinition1.getWorkflowDefinitionId());

		WorkflowDefinition workflowDefinition2 =
			_workflowDefinitionManager.getWorkflowDefinition(
				taskDefinition.getId());

		Assert.assertEquals(
			workflowDefinition1.getDescription(),
			workflowDefinition2.getDescription());
		Assert.assertEquals(
			workflowDefinition1.getContentAsXML(),
			workflowDefinition2.getContentAsXML());

		Assert.assertNotEquals(
			workflowDefinition1.getWorkflowDefinitionId(),
			workflowDefinition2.getWorkflowDefinitionId());
		Assert.assertNotEquals(
			workflowDefinition1.getName(), workflowDefinition2.getName());
		Assert.assertNotEquals(
			workflowDefinition1.getExternalReferenceCode(),
			workflowDefinition2.getExternalReferenceCode());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name", "version"};
	}

	@Override
	protected TaskDefinition testDeleteTaskDefinition_addTaskDefinition()
		throws Exception {

		WorkflowDefinition workflowDefinition = _deployWorkflowDefinition();

		return new TaskDefinition() {
			{
				setId(workflowDefinition::getWorkflowDefinitionId);
			}
		};
	}

	@Override
	protected TaskDefinition testGetTaskDefinitionsPage_addTaskDefinition(
		TaskDefinition taskDefinition) {

		return taskDefinition;
	}

	private WorkflowDefinition _deployWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				TestPropsValues.getCompanyId(), "Single Approver");

		String content = workflowDefinition.getContent();

		return _workflowDefinitionManager.deployWorkflowDefinition(
			null, workflowDefinition.getCompanyId(),
			workflowDefinition.getUserId(), workflowDefinition.getTitle(),
			RandomTestUtil.randomString(), content.getBytes());
	}

	private void _testGetTaskDefinitionsPage() throws Exception {
		Page<TaskDefinition> page =
			taskDefinitionResource.getTaskDefinitionsPage(
				null, null, Pagination.of(1, 10), null);

		assertEquals(
			_systemTaskDefinitions, (List<TaskDefinition>)page.getItems());
	}

	private void _testGetTaskDefinitionsPageWithFilter() throws Exception {

		// Active as 0

		Page<TaskDefinition> page =
			taskDefinitionResource.getTaskDefinitionsPage(
				null, "(active eq 0)", Pagination.of(1, 10), null);

		assertEquals(List.of(), (List<TaskDefinition>)page.getItems());

		// Active as 1

		page = taskDefinitionResource.getTaskDefinitionsPage(
			null, "(active eq 1)", Pagination.of(1, 10), null);

		assertEquals(
			_systemTaskDefinitions, (List<TaskDefinition>)page.getItems());
	}

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	private static final List<TaskDefinition> _systemTaskDefinitions = List.of(
		new TaskDefinition() {
			{
				active = true;
				name = WorkflowDefinitionConstants.NAME_CHANGE_TONE;
				version = 1;
			}
		},
		new TaskDefinition() {
			{
				active = true;
				name =
					WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR;
				version = 1;
			}
		},
		new TaskDefinition() {
			{
				active = true;
				name = WorkflowDefinitionConstants.NAME_IMPROVE_WRITING;
				version = 1;
			}
		},
		new TaskDefinition() {
			{
				active = true;
				name = WorkflowDefinitionConstants.NAME_LIFERAY_SEARCH;
				version = 1;
			}
		},
		new TaskDefinition() {
			{
				active = true;
				name = WorkflowDefinitionConstants.NAME_MAKE_LONGER;
				version = 1;
			}
		},
		new TaskDefinition() {
			{
				active = true;
				name = WorkflowDefinitionConstants.NAME_MAKE_SHORTER;
				version = 1;
			}
		});

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}