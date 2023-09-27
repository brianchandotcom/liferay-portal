/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TransitionResourceTest extends BaseTransitionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseTransitionResourceTestCase.setUpClass();

		_workflowDefinition =
			WorkflowDefinitionTestUtil.addWorkflowDefinition();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), ObjectReviewedTestUtil.addObjectReviewed(),
			_workflowDefinition);
	}

	@Override
	@Test
	public void testGetWorkflowInstanceNextTransitionsPage() throws Exception {
		Page<Transition> page =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				_workflowInstance.getId(), Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Transition() {
					{
						label = "Approve";
						name = "approve";
					}
				},
				new Transition() {
					{
						label = "Reject";
						name = "reject";
					}
				}),
			(List<Transition>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowInstanceNextTransitionsPageWithPagination()
		throws Exception {

		Page<Transition> page1 =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				_workflowInstance.getId(), Pagination.of(1, 1));

		List<Transition> transitions1 = (List<Transition>)page1.getItems();

		Assert.assertEquals(transitions1.toString(), 1, transitions1.size());

		Page<Transition> page2 =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				_workflowInstance.getId(), Pagination.of(2, 1));

		Assert.assertEquals(2, page2.getTotalCount());

		List<Transition> transitions2 = (List<Transition>)page2.getItems();

		Assert.assertEquals(transitions2.toString(), 1, transitions2.size());

		Page<Transition> page3 =
			transitionResource.getWorkflowInstanceNextTransitionsPage(
				_workflowInstance.getId(), Pagination.of(1, 2));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Transition() {
					{
						label = "Approve";
						name = "approve";
					}
				},
				new Transition() {
					{
						label = "Reject";
						name = "reject";
					}
				}),
			(List<Transition>)page3.getItems());
	}

	@Override
	@Test
	public void testGetWorkflowTaskNextTransitionsPage() throws Exception {
		Page<Transition> page =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId(),
				Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Transition() {
					{
						label = "Approve";
						name = "approve";
					}
				},
				new Transition() {
					{
						label = "Reject";
						name = "reject";
					}
				}),
			(List<Transition>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetWorkflowTaskNextTransitionsPageWithPagination()
		throws Exception {

		Long workflowTaskId =
			testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId();

		Page<Transition> page1 =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(1, 1));

		List<Transition> transitions1 = (List<Transition>)page1.getItems();

		Assert.assertEquals(transitions1.toString(), 1, transitions1.size());

		Page<Transition> page2 =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(2, 1));

		Assert.assertEquals(2, page2.getTotalCount());

		List<Transition> transitions2 = (List<Transition>)page2.getItems();

		Assert.assertEquals(transitions2.toString(), 1, transitions2.size());

		Page<Transition> page3 =
			transitionResource.getWorkflowTaskNextTransitionsPage(
				workflowTaskId, Pagination.of(1, 2));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Transition() {
					{
						label = "Approve";
						name = "approve";
					}
				},
				new Transition() {
					{
						label = "Reject";
						name = "reject";
					}
				}),
			(List<Transition>)page3.getItems());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"label", "name"};
	}

	@Override
	protected Long testGetWorkflowTaskNextTransitionsPage_getWorkflowTaskId()
		throws Exception {

		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		return workflowTask.getId();
	}

	private static WorkflowDefinition _workflowDefinition;
	private static WorkflowInstance _workflowInstance;

}