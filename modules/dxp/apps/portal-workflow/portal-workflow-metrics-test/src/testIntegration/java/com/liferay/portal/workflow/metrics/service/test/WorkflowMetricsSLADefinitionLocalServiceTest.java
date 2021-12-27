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
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDuplicateNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDurationException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStartNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStopNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionTimeframeException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowMetricsSLADefinitionLocalServiceTest
	extends BaseWorkflowMetricsTestCase {

	@Test
	public void testAddSLADefinition1() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddSLADefinition2() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition) + ":enter"},
				new String[] {
					getTerminalNodeKey(workflowDefinition) + ":leave"
				},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionDuplicateNameException.class)
	public void testAddSLADefinitionDuplicateName() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration1() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 0, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration2() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, -1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration3() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, -1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition) + ":enter"},
				new String[] {
					getTerminalNodeKey(workflowDefinition) + ":leave"
				},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionTimeframeException.class)
	public void testAddSLADefinitionInvalidPauseNodeKeys() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc",
				new String[] {"0"},
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionTimeframeException.class)
	public void testAddSLADefinitionInvalidStartNodeKeys1() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {"0"},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionStartNodeKeysException.class)
	public void testAddSLADefinitionInvalidStartNodeKeys2() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(), new String[0],
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionTimeframeException.class)
	public void testAddSLADefinitionInvalidStopNodeKeys1() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {"0"}, ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionStopNodeKeysException.class)
	public void testAddSLADefinitionInvalidStopNodeKeys2() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[0], ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = WorkflowMetricsSLADefinitionNameException.class)
	public void testAddSLADefinitionMissingName() throws Exception {
		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, StringPool.BLANK,
				new String[0], workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testUpdateSLADefinition() throws Exception {
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
					workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(workflowMetricsSLADefinition);

		workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				updateWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinition.getPrimaryKey(),
					StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					WorkflowConstants.STATUS_APPROVED,
					ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(workflowMetricsSLADefinition);
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			workflowMetricsSLADefinition.getStatus());

		workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				updateWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinition.getPrimaryKey(),
					StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					WorkflowConstants.STATUS_DRAFT,
					ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(workflowMetricsSLADefinition);
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			workflowMetricsSLADefinition.getStatus());
	}

	@Test(expected = WorkflowMetricsSLADefinitionDuplicateNameException.class)
	public void testUpdateSLADefinitionDuplicateName() throws Exception {
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 1, "Abc", new String[0],
					workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinitionLocalService.
			addWorkflowMetricsSLADefinition(
				StringPool.BLANK, StringPool.BLANK, 1, "Def", new String[0],
				workflowDefinition.getWorkflowDefinitionId(),
				new String[] {getInitialNodeKey(workflowDefinition)},
				new String[] {getTerminalNodeKey(workflowDefinition)},
				ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinitionLocalService.
			updateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinition.getPrimaryKey(), StringPool.BLANK,
				StringPool.BLANK, 1, "Def", new String[0], new String[] {""},
				new String[] {""}, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}