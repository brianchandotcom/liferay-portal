/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoLogOrderByComparator;

import java.util.ArrayList;
import java.util.Collections;
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
public class KaleoLogLocalServiceTest extends BaseKaleoLocalServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetKaleoInstanceKaleoLogs() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance);

		KaleoLog kaleoLog = addNodeExitKaleoLog(kaleoInstanceToken);

		addWorkflowInstanceEndKaleoLog(kaleoInstance);

		List<KaleoLog> kaleoLogs =
			kaleoLogLocalService.getKaleoInstanceKaleoLogs(
				TestPropsValues.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(), null, 0, 10,
				KaleoLogOrderByComparator.getOrderByComparator(
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator(),
					_kaleoWorkflowModelConverter));

		Assert.assertEquals(kaleoLogs.toString(), 2, kaleoLogs.size());

		kaleoLogs = kaleoLogLocalService.getKaleoInstanceKaleoLogs(
			TestPropsValues.getCompanyId(), kaleoInstance.getKaleoInstanceId(),
			new ArrayList<Integer>() {
				{
					add(WorkflowLog.TRANSITION);
				}
			},
			0, 10,
			KaleoLogOrderByComparator.getOrderByComparator(
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator(),
				_kaleoWorkflowModelConverter));

		Assert.assertEquals(kaleoLogs.toString(), 1, kaleoLogs.size());

		Assert.assertEquals(kaleoLog, kaleoLogs.get(0));
	}

	@Test
	public void testGetKaleoInstanceKaleoLogsCount() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance);

		addNodeExitKaleoLog(kaleoInstanceToken);

		addWorkflowInstanceEndKaleoLog(kaleoInstance);

		Assert.assertEquals(
			2,
			kaleoLogLocalService.getKaleoInstanceKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(), null));

		Assert.assertEquals(
			1,
			kaleoLogLocalService.getKaleoInstanceKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(),
				new ArrayList<Integer>() {
					{
						add(WorkflowLog.TRANSITION);
					}
				}));
	}

	@Test
	public void testGetKaleoTaskInstanceTokenKaleoLogs() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken(kaleoInstance, kaleoInstanceToken);

		KaleoLog kaleoLog = kaleoLogLocalService.addTaskAssignmentKaleoLog(
			Collections.emptyList(), kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);

		addTaskCompletionKaleoLog(kaleoInstance, kaleoTaskInstanceToken);

		List<KaleoLog> kaleoLogs =
			kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogs(
				TestPropsValues.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), null, 0,
				10,
				KaleoLogOrderByComparator.getOrderByComparator(
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator(),
					_kaleoWorkflowModelConverter));

		Assert.assertEquals(kaleoLogs.toString(), 2, kaleoLogs.size());

		kaleoLogs = kaleoLogLocalService.getKaleoInstanceKaleoLogs(
			TestPropsValues.getCompanyId(), kaleoInstance.getKaleoInstanceId(),
			new ArrayList<Integer>() {
				{
					add(WorkflowLog.TASK_ASSIGN);
				}
			},
			0, 10,
			KaleoLogOrderByComparator.getOrderByComparator(
				WorkflowComparatorFactoryUtil.getLogCreateDateComparator(),
				_kaleoWorkflowModelConverter));

		Assert.assertEquals(kaleoLogs.toString(), 1, kaleoLogs.size());

		Assert.assertEquals(kaleoLog, kaleoLogs.get(0));
	}

	@Test
	public void testGetKaleoTaskInstanceTokenKaleoLogsCount() throws Exception {
		KaleoInstance kaleoInstance = addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = addKaleoInstanceToken(
			kaleoInstance);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken(kaleoInstance, kaleoInstanceToken);

		addTaskAssignmentKaleoLog(kaleoInstance, kaleoTaskInstanceToken);

		addTaskCompletionKaleoLog(kaleoInstance, kaleoTaskInstanceToken);

		Assert.assertEquals(
			2,
			kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), null));

		Assert.assertEquals(
			1,
			kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				new ArrayList<Integer>() {
					{
						add(WorkflowLog.TASK_ASSIGN);
					}
				}));
	}

	@Inject(type = KaleoWorkflowModelConverter.class)
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

}