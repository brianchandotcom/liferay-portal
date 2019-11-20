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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoLogOrderByComparator;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class KaleoLogLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		_setUpServiceContext();
	}

	@Test
	public void testGetKaleoInstanceKaleoLogs() throws Exception {
		KaleoInstance kaleoInstance = _addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = _addKaleoInstanceToken(
			kaleoInstance);

		KaleoLog kaleoLog = _kaleoLogLocalService.addNodeExitKaleoLog(
			kaleoInstanceToken, kaleoInstanceToken.getCurrentKaleoNode(),
			_serviceContext);

		_kaleoLogLocalService.addWorkflowInstanceEndKaleoLog(
			_addKaleoInstanceToken(kaleoInstance), _serviceContext);

		List<KaleoLog> kaleoLogs =
			_kaleoLogLocalService.getKaleoInstanceKaleoLogs(
				TestPropsValues.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(), null, 0, 10,
				KaleoLogOrderByComparator.getOrderByComparator(
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator(),
					_kaleoWorkflowModelConverter));

		Assert.assertEquals(kaleoLogs.toString(), 2, kaleoLogs.size());

		kaleoLogs = _kaleoLogLocalService.getKaleoInstanceKaleoLogs(
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
		KaleoInstance kaleoInstance = _addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = _addKaleoInstanceToken(
			kaleoInstance);

		_kaleoLogLocalService.addNodeExitKaleoLog(
			kaleoInstanceToken, kaleoInstanceToken.getCurrentKaleoNode(),
			_serviceContext);

		_kaleoLogLocalService.addWorkflowInstanceEndKaleoLog(
			_addKaleoInstanceToken(kaleoInstance), _serviceContext);

		Assert.assertEquals(
			2,
			_kaleoLogLocalService.getKaleoInstanceKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(), null));

		Assert.assertEquals(
			1,
			_kaleoLogLocalService.getKaleoInstanceKaleoLogsCount(
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
		KaleoInstance kaleoInstance = _addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = _addKaleoInstanceToken(
			kaleoInstance);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_addKaleoTaskInstanceToken(kaleoInstance, kaleoInstanceToken);

		KaleoLog kaleoLog = _kaleoLogLocalService.addTaskAssignmentKaleoLog(
			Collections.emptyList(), kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			_serviceContext);

		_kaleoLogLocalService.addTaskCompletionKaleoLog(
			kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			_serviceContext);

		List<KaleoLog> kaleoLogs =
			_kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogs(
				TestPropsValues.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), null, 0,
				10,
				KaleoLogOrderByComparator.getOrderByComparator(
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator(),
					_kaleoWorkflowModelConverter));

		Assert.assertEquals(kaleoLogs.toString(), 2, kaleoLogs.size());

		kaleoLogs = _kaleoLogLocalService.getKaleoInstanceKaleoLogs(
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
		KaleoInstance kaleoInstance = _addKaleoInstance();

		KaleoInstanceToken kaleoInstanceToken = _addKaleoInstanceToken(
			kaleoInstance);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_addKaleoTaskInstanceToken(kaleoInstance, kaleoInstanceToken);

		_kaleoLogLocalService.addTaskAssignmentKaleoLog(
			Collections.emptyList(), kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			_serviceContext);

		_kaleoLogLocalService.addTaskCompletionKaleoLog(
			kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			_serviceContext);

		Assert.assertEquals(
			2,
			_kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), null));

		Assert.assertEquals(
			1,
			_kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogsCount(
				TestPropsValues.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
				new ArrayList<Integer>() {
					{
						add(WorkflowLog.TASK_ASSIGN);
					}
				}));
	}

	private BlogsEntry _addBlogsEntry() throws PortalException {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.petra.mail.MailEngine", Level.OFF)) {

			BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
				TestPropsValues.getUserId(), StringUtil.randomString(),
				StringUtil.randomString(), new Date(),
				ServiceContextTestUtil.getServiceContext());

			_blogsEntries.add(blogsEntry);

			return blogsEntry;
		}
	}

	private KaleoInstance _addKaleoInstance() throws Exception {
		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
				BlogsEntry.class.getName()
			).put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				() -> {
					BlogsEntry blogsEntry = _addBlogsEntry();

					return String.valueOf(blogsEntry.getEntryId());
				}
			).build();

		return _kaleoInstanceLocalService.addKaleoInstance(
			1, "Test", 1, workflowContext, _serviceContext);
	}

	private KaleoInstanceToken _addKaleoInstanceToken(
			KaleoInstance kaleoInstance)
		throws Exception {

		KaleoNode kaleoNode = _kaleoNodeLocalService.addKaleoNode(
			kaleoInstance.getKaleoDefinitionVersionId(),
			new Task("task", StringPool.BLANK), _serviceContext);

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			kaleoNode.getKaleoNodeId(),
			kaleoInstance.getKaleoDefinitionVersionId(),
			kaleoInstance.getKaleoInstanceId(), 0,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			_serviceContext);
	}

	private KaleoTaskInstanceToken _addKaleoTaskInstanceToken(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken)
		throws Exception {

		return _kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoInstanceToken.getKaleoInstanceTokenId(), 1, "task",
			Collections.emptyList(), null,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			_serviceContext);
	}

	private void _setUpServiceContext() throws PortalException {
		_serviceContext = new ServiceContext();

		_serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		_serviceContext.setUserId(TestPropsValues.getUserId());
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoLogLocalService _kaleoLogLocalService;

	@DeleteAfterTestRun
	private final List<KaleoLog> _kaleoLogs = new ArrayList<>();

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Inject(type = KaleoWorkflowModelConverter.class)
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	private ServiceContext _serviceContext;

}