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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.List;

import org.junit.AfterClass;
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
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name", "version"};
	}

	@Override
	protected TaskDefinition testGetTaskDefinitionsPage_addTaskDefinition(
		TaskDefinition taskDefinition) {

		return taskDefinition;
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

}