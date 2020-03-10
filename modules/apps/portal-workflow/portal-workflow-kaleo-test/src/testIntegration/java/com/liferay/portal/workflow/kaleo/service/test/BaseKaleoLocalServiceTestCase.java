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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoActionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import org.junit.Before;

/**
 * @author Inácio Nery
 */
public abstract class BaseKaleoLocalServiceTestCase {

	@Before
	public void setUp() throws Exception {
		_setUpServiceContext();
	}

	protected KaleoAction addKaleoAction(
			KaleoInstance kaleoInstance, KaleoNode kaleoNode)
		throws Exception {

		KaleoAction kaleoAction = kaleoActionLocalService.addKaleoAction(
			KaleoNode.class.getName(), kaleoNode.getKaleoNodeId(),
			kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(), kaleoNode.getName(),
			new Action(
				StringUtil.randomString(), StringUtil.randomString(),
				"onAssignment", StringPool.BLANK, "groovy", StringPool.BLANK,
				0),
			serviceContext);

		_kaleoActions.add(kaleoAction);

		return kaleoAction;
	}

	protected KaleoDefinition addKaleoDefinition()
		throws IOException, PortalException {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.addKaleoDefinition(
				StringUtil.randomString(), StringUtil.randomString(),
				StringUtil.randomString(),
				_read("legal-marketing-definition.xml"), 1, serviceContext);

		_kaleoDefinitions.add(kaleoDefinition);

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), serviceContext);

		return kaleoDefinition;
	}

	protected KaleoInstance addKaleoInstance() throws Exception {
		Map<String, Serializable> workflowContext =
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
				(Serializable)BlogsEntry.class.getName()
			).put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(_addBlogsEntry().getEntryId())
			).put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				(Serializable)serviceContext
			).build();

		KaleoInstance kaleoInstance =
			_kaleoInstanceLocalService.addKaleoInstance(
				1, 1, "Test", 1, workflowContext, serviceContext);

		_kaleoInstances.add(kaleoInstance);

		return kaleoInstance;
	}

	protected KaleoInstanceToken addKaleoInstanceToken(
			KaleoInstance kaleoInstance)
		throws Exception {

		KaleoNode kaleoNode = addKaleoNode(kaleoInstance);

		KaleoInstanceToken kaleoInstanceToken =
			_kaleoInstanceTokenLocalService.addKaleoInstanceToken(
				kaleoNode.getKaleoNodeId(),
				kaleoInstance.getKaleoDefinitionId(),
				kaleoInstance.getKaleoDefinitionVersionId(),
				kaleoInstance.getKaleoInstanceId(), 0,
				WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
				serviceContext);

		_kaleoInstanceTokens.add(kaleoInstanceToken);

		return kaleoInstanceToken;
	}

	protected KaleoNode addKaleoNode(KaleoInstance kaleoInstance)
		throws Exception {

		KaleoNode kaleoNode = _kaleoNodeLocalService.addKaleoNode(
			kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(),
			new Task("task", StringPool.BLANK), serviceContext);

		_kaleoNodes.add(kaleoNode);

		return kaleoNode;
	}

	protected KaleoTaskInstanceToken addKaleoTaskInstanceToken(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken)
		throws Exception {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
				kaleoInstanceToken.getKaleoInstanceTokenId(), 1, "task",
				Collections.emptyList(), null,
				WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
				serviceContext);

		_kaleoTaskInstanceTokens.add(kaleoTaskInstanceToken);

		return kaleoTaskInstanceToken;
	}

	protected KaleoLog addNodeExitKaleoLog(
			KaleoInstanceToken kaleoInstanceToken)
		throws PortalException {

		KaleoLog kaleoLog = kaleoLogLocalService.addNodeExitKaleoLog(
			kaleoInstanceToken, kaleoInstanceToken.getCurrentKaleoNode(),
			serviceContext);

		_kaleoLogs.add(kaleoLog);

		return kaleoLog;
	}

	protected KaleoLog addTaskAssignmentKaleoLog(
			KaleoInstance kaleoInstance,
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		KaleoLog kaleoLog = kaleoLogLocalService.addTaskAssignmentKaleoLog(
			Collections.emptyList(), kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);

		_kaleoLogs.add(kaleoLog);

		return kaleoLog;
	}

	protected KaleoLog addTaskCompletionKaleoLog(
			KaleoInstance kaleoInstance,
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		KaleoLog kaleoLog = kaleoLogLocalService.addTaskCompletionKaleoLog(
			kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);

		_kaleoLogs.add(kaleoLog);

		return kaleoLog;
	}

	protected KaleoLog addWorkflowInstanceEndKaleoLog(
			KaleoInstance kaleoInstance)
		throws Exception, PortalException {

		KaleoLog kaleoLog = kaleoLogLocalService.addWorkflowInstanceEndKaleoLog(
			addKaleoInstanceToken(kaleoInstance), serviceContext);

		_kaleoLogs.add(kaleoLog);

		return kaleoLog;
	}

	protected void deactivateKaleoDefinition(KaleoDefinition kaleoDefinition)
		throws PortalException {

		_kaleoDefinitionLocalService.deactivateKaleoDefinition(
			kaleoDefinition.getName(), kaleoDefinition.getVersion(),
			serviceContext);
	}

	protected void deleteKaleoDefinition(KaleoDefinition kaleoDefinition)
		throws PortalException {

		_kaleoDefinitionLocalService.deleteKaleoDefinition(
			kaleoDefinition.getName(), serviceContext);

		_kaleoDefinitions.remove(kaleoDefinition);
	}

	protected KaleoDefinition updateKaleoDefinition(
			KaleoDefinition kaleoDefinition)
		throws IOException, PortalException {

		kaleoDefinition = _kaleoDefinitionLocalService.updatedKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), StringUtil.randomString(),
			StringUtil.randomString(), kaleoDefinition.getContent(),
			serviceContext);

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), serviceContext);

		return kaleoDefinition;
	}

	@Inject
	protected KaleoActionLocalService kaleoActionLocalService;

	@Inject
	protected KaleoLogLocalService kaleoLogLocalService;

	@Inject
	protected KaleoTaskInstanceTokenLocalService
		kaleoTaskInstanceTokenLocalService;

	protected ServiceContext serviceContext;

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

	private String _read(String name) throws IOException {
		ClassLoader classLoader =
			BaseKaleoLocalServiceTestCase.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/workflow/kaleo/dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
	}

	private void _setUpServiceContext() throws PortalException {
		serviceContext = new ServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setUserId(TestPropsValues.getUserId());
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private final List<KaleoAction> _kaleoActions = new ArrayList<>();

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@DeleteAfterTestRun
	private final List<KaleoDefinition> _kaleoDefinitions = new ArrayList<>();

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@DeleteAfterTestRun
	private final List<KaleoInstance> _kaleoInstances = new ArrayList<>();

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@DeleteAfterTestRun
	private final List<KaleoInstanceToken> _kaleoInstanceTokens =
		new ArrayList<>();

	@DeleteAfterTestRun
	private final List<KaleoLog> _kaleoLogs = new ArrayList<>();

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@DeleteAfterTestRun
	private final List<KaleoNode> _kaleoNodes = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<KaleoTaskInstanceToken> _kaleoTaskInstanceTokens =
		new ArrayList<>();

}