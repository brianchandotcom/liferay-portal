/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_0_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.workflow.kaleo.definition.RoleAssignment;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.Serializable;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class DDLFormRecordToDDMFormInstanceRecordUpgradeClassNamesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setUserId(TestPropsValues.getUserId());

		_kaleoInstance = _kaleoInstanceLocalService.addKaleoInstance(
			1, 1, RandomTestUtil.randomString(), 1,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
				_CLASS_NAME_DDL_FORM_RECORD
			).put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				(Serializable)serviceContext
			).build(),
			serviceContext);

		_kaleoNode = _kaleoNodeLocalService.addKaleoNode(
			_kaleoInstance.getKaleoDefinitionId(),
			_kaleoInstance.getKaleoDefinitionVersionId(),
			new Task(RandomTestUtil.randomString(), StringPool.BLANK),
			serviceContext);

		_kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.addKaleoInstanceToken(
				_kaleoNode.getKaleoNodeId(),
				_kaleoInstance.getKaleoDefinitionId(),
				_kaleoInstance.getKaleoDefinitionVersionId(),
				_kaleoInstance.getKaleoInstanceId(), 0,
				WorkflowContextUtil.convert(
					_kaleoInstance.getWorkflowContext()),
				serviceContext);

		_kaleoTaskAssignment =
			_kaleoTaskAssignmentLocalService.addKaleoTaskAssignment(
				KaleoTask.class.getName(), RandomTestUtil.nextLong(),
				_kaleoNode.getKaleoDefinitionId(),
				_kaleoNode.getKaleoDefinitionVersionId(),
				new RoleAssignment(
					RoleConstants.ADMINISTRATOR,
					RoleConstants.TYPE_REGULAR_LABEL),
				serviceContext);

		_kaleoTaskAssignment.setAssigneeClassName(_CLASS_NAME_DDL_FORM_RECORD);

		_kaleoTaskAssignment =
			_kaleoTaskAssignmentLocalService.updateKaleoTaskAssignment(
				_kaleoTaskAssignment);

		_kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
				_kaleoInstanceToken.getKaleoInstanceTokenId(), 1,
				RandomTestUtil.randomString(),
				Collections.singleton(_kaleoTaskAssignment), null,
				WorkflowContextUtil.convert(
					_kaleoInstance.getWorkflowContext()),
				serviceContext);

		_kaleoTaskAssignmentInstance =
			_kaleoTaskAssignmentInstanceLocalService.
				addKaleoTaskAssignmentInstance(
					_kaleoTaskInstanceToken.getGroupId(),
					_kaleoTaskInstanceToken, _CLASS_NAME_DDL_FORM_RECORD,
					RandomTestUtil.nextLong(), serviceContext);

		_kaleoLog = _kaleoLogLocalService.addTaskAssignmentKaleoLog(
			Collections.singletonList(_kaleoTaskAssignmentInstance),
			_kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(_kaleoInstance.getWorkflowContext()),
			serviceContext);
	}

	@Test
	public void testUpgrade() throws Exception {
		_assertClassNames(_CLASS_NAME_DDL_FORM_RECORD);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_UPGRADE_PROCESS, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME_UPGRADE_PROCESS);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		_kaleoInstance = _kaleoInstanceLocalService.fetchKaleoInstance(
			_kaleoInstance.getKaleoInstanceId());
		_kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.fetchKaleoInstanceToken(
				_kaleoInstanceToken.getKaleoInstanceTokenId());
		_kaleoLog = _kaleoLogLocalService.fetchKaleoLog(
			_kaleoLog.getKaleoLogId());
		_kaleoTaskAssignment =
			_kaleoTaskAssignmentLocalService.fetchKaleoTaskAssignment(
				_kaleoTaskAssignment.getKaleoTaskAssignmentId());
		_kaleoTaskAssignmentInstance =
			_kaleoTaskAssignmentInstanceLocalService.
				fetchKaleoTaskAssignmentInstance(
					_kaleoTaskAssignmentInstance.
						getKaleoTaskAssignmentInstanceId());
		_kaleoTaskInstanceToken =
			_kaleoTaskInstanceTokenLocalService.fetchKaleoTaskInstanceToken(
				_kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		_assertClassNames(_CLASS_NAME_DDM_FORM_INSTANCE_RECORD);
	}

	private void _assertClassNames(String expectedClassName) {
		Assert.assertEquals(expectedClassName, _kaleoInstance.getClassName());
		Assert.assertEquals(
			expectedClassName, _kaleoInstanceToken.getClassName());
		Assert.assertEquals(
			expectedClassName, _kaleoLog.getCurrentAssigneeClassName());
		Assert.assertEquals(
			expectedClassName, _kaleoLog.getPreviousAssigneeClassName());
		Assert.assertEquals(
			expectedClassName, _kaleoTaskAssignment.getAssigneeClassName());
		Assert.assertEquals(
			expectedClassName,
			_kaleoTaskAssignmentInstance.getAssigneeClassName());
		Assert.assertEquals(
			expectedClassName, _kaleoTaskInstanceToken.getClassName());

		_assertEntryClassName(
			expectedClassName, _kaleoInstance.getWorkflowContext());
		_assertEntryClassName(
			expectedClassName, _kaleoLog.getWorkflowContext());
		_assertEntryClassName(
			expectedClassName, _kaleoTaskInstanceToken.getWorkflowContext());
	}

	private void _assertEntryClassName(
		String expectedEntryClassName, String workflowContext) {

		Assert.assertEquals(
			expectedEntryClassName,
			MapUtil.getString(
				WorkflowContextUtil.convert(workflowContext),
				"entryClassName"));
	}

	private static final String _CLASS_NAME_DDL_FORM_RECORD =
		"com.liferay.dynamic.data.lists.model.DDLFormRecord";

	private static final String _CLASS_NAME_DDM_FORM_INSTANCE_RECORD =
		"com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord";

	private static final String _CLASS_NAME_UPGRADE_PROCESS =
		"com.liferay.portal.workflow.kaleo.internal.upgrade.v4_0_1." +
			"DDLFormRecordToDDMFormInstanceRecordUpgradeClassNames";

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.internal.upgrade.registry.KaleoServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private KaleoInstance _kaleoInstance;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@DeleteAfterTestRun
	private KaleoInstanceToken _kaleoInstanceToken;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@DeleteAfterTestRun
	private KaleoLog _kaleoLog;

	@Inject
	private KaleoLogLocalService _kaleoLogLocalService;

	@DeleteAfterTestRun
	private KaleoNode _kaleoNode;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@DeleteAfterTestRun
	private KaleoTaskAssignment _kaleoTaskAssignment;

	@DeleteAfterTestRun
	private KaleoTaskAssignmentInstance _kaleoTaskAssignmentInstance;

	@Inject
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Inject
	private KaleoTaskAssignmentLocalService _kaleoTaskAssignmentLocalService;

	@DeleteAfterTestRun
	private KaleoTaskInstanceToken _kaleoTaskInstanceToken;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}