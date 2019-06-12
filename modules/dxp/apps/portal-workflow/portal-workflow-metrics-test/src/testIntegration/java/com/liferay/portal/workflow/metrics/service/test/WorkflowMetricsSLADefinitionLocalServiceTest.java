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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDuplicateNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDurationException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStartNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStopNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionTimeframeException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_kaleoDefinition = KaleoDefinitionLocalServiceUtil.getKaleoDefinition(
			"Single Approver", ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddSLADefinition1() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testAddSLADefinition2() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey() + ":enter"},
					new String[] {_getTerminalNodeKey() + ":leave"},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDuplicateNameException.class)
	public void testAddSLADefinitionDuplicateName() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));

		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration1() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 0, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration2() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, -1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionDurationException.class)
	public void testAddSLADefinitionDuration3() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, -1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey() + ":enter"},
					new String[] {_getTerminalNodeKey() + ":leave"},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionTimeframeException.class)
	public void testAddSLADefinitionInvalidPauseNodeKeys() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[] {"0"},
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionTimeframeException.class)
	public void testAddSLADefinitionInvalidStartNodeKeys1() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {"0"}, new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionStartNodeKeysException.class)
	public void testAddSLADefinitionInvalidStartNodeKeys2() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[0], new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionTimeframeException.class)
	public void testAddSLADefinitionInvalidStopNodeKeys1() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()}, new String[] {"0"},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionStopNodeKeysException.class)
	public void testAddSLADefinitionInvalidStopNodeKeys2() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()}, new String[0],
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test(expected = WorkflowMetricsSLADefinitionNameException.class)
	public void testAddSLADefinitionMissingName() throws Exception {
		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testUpdateSLADefinition() throws Exception {
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinition.add(workflowMetricsSLADefinition);

		Assert.assertNotNull(workflowMetricsSLADefinition);

		workflowMetricsSLADefinition =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				updateWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinition.getPrimaryKey(), "Abc",
					StringPool.BLANK, 1, "", new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					WorkflowConstants.STATUS_APPROVED,
					ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(workflowMetricsSLADefinition);
	}

	@Test(expected = WorkflowMetricsSLADefinitionDuplicateNameException.class)
	public void testUpdateSLADefinitionDuplicateName() throws Exception {
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Abc", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinition.add(workflowMetricsSLADefinition);

		_workflowMetricsSLADefinition.add(
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				addWorkflowMetricsSLADefinition(
					"Def", StringPool.BLANK, 1, "",
					_kaleoDefinition.getPrimaryKey(), new String[0],
					new String[] {_getInitialNodeKey()},
					new String[] {_getTerminalNodeKey()},
					ServiceContextTestUtil.getServiceContext()));

		WorkflowMetricsSLADefinitionLocalServiceUtil.
			updateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinition.getPrimaryKey(), "Def",
				StringPool.BLANK, 1, "", new String[0], new String[] {""},
				new String[] {""}, WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext());
	}

	private String _getInitialNodeKey() throws Exception {
		if (Validator.isNull(_initialNodeKey)) {
			KaleoDefinitionVersion latestKaleoDefinitionVersion =
				KaleoDefinitionVersionLocalServiceUtil.
					getLatestKaleoDefinitionVersion(
						_kaleoDefinition.getCompanyId(),
						_kaleoDefinition.getName());

			List<KaleoNode> kaleoNodes =
				KaleoNodeLocalServiceUtil.getKaleoDefinitionVersionKaleoNodes(
					latestKaleoDefinitionVersion.getKaleoDefinitionVersionId());

			_initialNodeKey = kaleoNodes.stream(
			).filter(
				KaleoNode::isInitial
			).findFirst(
			).map(
				KaleoNode::getKaleoNodeId
			).map(
				String::valueOf
			).orElseGet(
				() -> StringPool.BLANK
			);
		}

		return _initialNodeKey;
	}

	private String _getTerminalNodeKey() throws Exception {
		if (Validator.isNull(_terminalNodeKey)) {
			KaleoDefinitionVersion latestKaleoDefinitionVersion =
				KaleoDefinitionVersionLocalServiceUtil.
					getLatestKaleoDefinitionVersion(
						_kaleoDefinition.getCompanyId(),
						_kaleoDefinition.getName());

			List<KaleoNode> kaleoNodes =
				KaleoNodeLocalServiceUtil.getKaleoDefinitionVersionKaleoNodes(
					latestKaleoDefinitionVersion.getKaleoDefinitionVersionId());

			_terminalNodeKey = kaleoNodes.stream(
			).filter(
				KaleoNode::isTerminal
			).findFirst(
			).map(
				KaleoNode::getKaleoNodeId
			).map(
				String::valueOf
			).orElseGet(
				() -> StringPool.BLANK
			);
		}

		return _terminalNodeKey;
	}

	private static KaleoDefinition _kaleoDefinition;

	private String _initialNodeKey;
	private String _terminalNodeKey;

	@DeleteAfterTestRun
	private final List<WorkflowMetricsSLADefinition>
		_workflowMetricsSLADefinition = new ArrayList<>();

}