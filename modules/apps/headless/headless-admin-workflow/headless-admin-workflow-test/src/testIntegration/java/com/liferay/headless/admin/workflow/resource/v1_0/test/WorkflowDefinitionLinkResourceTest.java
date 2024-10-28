/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinitionLink;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowDefinitionLinkResourceTest
	extends BaseWorkflowDefinitionLinkResourceTestCase {

	public WorkflowDefinitionLink addWorkflowDefinitionLink(
			Long workflowDefinitionId,
			WorkflowDefinitionLink workflowDefinitionLink)
		throws Exception {

		return workflowDefinitionLinkResource.postWorkflowDefinitionLink(
			workflowDefinitionId, workflowDefinitionLink);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workflowDefinition =
			WorkflowDefinitionTestUtil.addWorkflowDefinition();

		_objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(
				UserLocalServiceUtil.getUser(TestPropsValues.getUserId())));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}

		if (_workflowDefinition != null) {
			_kaleoDefinitionLocalService.deleteKaleoDefinition(
				_workflowDefinition.getId());
		}
	}

	@Override
	@Test
	public void testGetWorkflowDefinitionLinks() throws Exception {
		Long workflowDefinitionId = _workflowDefinition.getId();

		Page<WorkflowDefinitionLink> page =
			workflowDefinitionLinkResource.getWorkflowDefinitionLinks(
				workflowDefinitionId, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		WorkflowDefinitionLink workflowDefinitionLink1 =
			addWorkflowDefinitionLink(
				workflowDefinitionId, randomWorkflowDefinitionLink());

		WorkflowDefinitionLink workflowDefinitionLink2 =
			addWorkflowDefinitionLink(
				workflowDefinitionId, randomWorkflowDefinitionLink());

		page = workflowDefinitionLinkResource.getWorkflowDefinitionLinks(
			workflowDefinitionId, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			workflowDefinitionLink1,
			(List<WorkflowDefinitionLink>)page.getItems());
		assertContains(
			workflowDefinitionLink2,
			(List<WorkflowDefinitionLink>)page.getItems());
		assertValid(
			page,
			testGetWorkflowDefinitionLinks_getExpectedActions(
				workflowDefinitionId));

		WorkflowDefinitionLinkLocalServiceUtil.deleteWorkflowDefinitionLink(
			workflowDefinitionLink1.getId());
		WorkflowDefinitionLinkLocalServiceUtil.deleteWorkflowDefinitionLink(
			workflowDefinitionLink2.getId());
	}

	@Override
	@Test
	public void testPostWorkflowDefinitionLink() throws Exception {
		WorkflowDefinitionLink randomWorkflowDefinitionLink =
			randomWorkflowDefinitionLink();

		WorkflowDefinitionLink postWorkflowDefinitionLink =
			addWorkflowDefinitionLink(
				_workflowDefinition.getId(), randomWorkflowDefinitionLink);

		randomWorkflowDefinitionLink.setWorkflowDefinitionName(
			_workflowDefinition.getName());
		randomWorkflowDefinitionLink.setWorkflowDefinitionVersion(
			Integer.valueOf(_workflowDefinition.getVersion()));

		assertEquals(randomWorkflowDefinitionLink, postWorkflowDefinitionLink);
		assertValid(postWorkflowDefinitionLink);
	}

	@Override
	protected WorkflowDefinitionLink randomWorkflowDefinitionLink()
		throws Exception {

		return new WorkflowDefinitionLink() {
			{
				className = _objectDefinition.getClassName();
				groupId = testGroup.getGroupId();
			}
		};
	}

	private static ObjectDefinition _objectDefinition;
	private static WorkflowDefinition _workflowDefinition;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}