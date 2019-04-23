/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDuplicateNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDurationException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionNameException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class WorkflowMetricsSLADefinitionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddSLADefinition() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDuplicateNameException.class)
	public void testAddSLADefinitionDuplicateName() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));

		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration1() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 0, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration2() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, -1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionNameException.class)
	public void testAddSLADefinitionMissingName() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testUpdateSLADefinition() throws Exception {
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinition.add(workflowMetricsSLADefinition);

		Assert.assertNotNull(workflowMetricsSLADefinition);

		workflowMetricsSLADefinition =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				updateWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinition.getPrimaryKey(), "Abc",
					StringPool.BLANK, 1, new String[0], new String[] {""},
					new String[] {""},
					ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(workflowMetricsSLADefinition);
	}

	@Test(expected = WorkflowMetricsSLADefinitionDuplicateNameException.class)
	public void testUpdateSLADefinitionDuplicateName() throws Exception {
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinition.add(workflowMetricsSLADefinition);

		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Def", StringPool.BLANK, 1, 0, new String[0],
					new String[] {""}, new String[] {""},
					ServiceContextTestUtil.getServiceContext()));

		WorkflowMetricsSLADefinitionLocalServiceUtil.
			updateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinition.getPrimaryKey(), "Def",
				StringPool.BLANK, 1, new String[0], new String[] {""},
				new String[] {""}, ServiceContextTestUtil.getServiceContext());
	}

	@DeleteAfterTestRun
	private final List<WorkflowMetricsSLADefinition>
		_workflowMetricsSLADefinition = new ArrayList<>();

}