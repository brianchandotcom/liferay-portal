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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
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

import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Inácio Nery
 */
public abstract class BaseKaleoLocalServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_setUpServiceContext();
	}

	protected KaleoAction addKaleoAction(
			KaleoInstance kaleoInstance, KaleoNode kaleoNode)
		throws Exception {

		return kaleoActionLocalService.addKaleoAction(
			KaleoNode.class.getName(), kaleoNode.getKaleoNodeId(),
			kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(), kaleoNode.getName(),
			new Action(
				StringUtil.randomString(), StringUtil.randomString(),
				"onAssignment", StringPool.BLANK, "groovy", StringPool.BLANK,
				0),
			serviceContext);
	}

	protected KaleoDefinition addKaleoDefinition()
		throws IOException, PortalException {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.addKaleoDefinition(
				StringUtil.randomString(), StringUtil.randomString(),
				StringUtil.randomString(),
				_read("legal-marketing-definition.xml"), StringPool.BLANK, 1,
				serviceContext);

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), serviceContext);

		return kaleoDefinition;
	}

	protected KaleoInstance addKaleoInstance() throws Exception {
		return _kaleoInstanceLocalService.addKaleoInstance(
			1, 1, "Test", 1,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
				(Serializable)BlogsEntry.class.getName()
			).put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(_addBlogsEntry().getEntryId())
			).put(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
				(Serializable)serviceContext
			).build(),
			serviceContext);
	}

	protected KaleoInstanceToken addKaleoInstanceToken(
			KaleoInstance kaleoInstance)
		throws Exception {

		KaleoNode kaleoNode = addKaleoNode(kaleoInstance);

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			kaleoNode.getKaleoNodeId(), kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(),
			kaleoInstance.getKaleoInstanceId(), 0,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);
	}

	protected KaleoNode addKaleoNode(KaleoInstance kaleoInstance)
		throws Exception {

		return _kaleoNodeLocalService.addKaleoNode(
			kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(),
			new Task("task", StringPool.BLANK), serviceContext);
	}

	protected KaleoTaskInstanceToken addKaleoTaskInstanceToken(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken)
		throws Exception {

		return kaleoTaskInstanceTokenLocalService.addKaleoTaskInstanceToken(
			kaleoInstanceToken.getKaleoInstanceTokenId(), 1, "task",
			Collections.emptyList(), null,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);
	}

	protected KaleoLog addNodeExitKaleoLog(
			KaleoInstanceToken kaleoInstanceToken)
		throws PortalException {

		return kaleoLogLocalService.addNodeExitKaleoLog(
			kaleoInstanceToken, kaleoInstanceToken.getCurrentKaleoNode(),
			serviceContext);
	}

	protected KaleoLog addTaskAssignmentKaleoLog(
			KaleoInstance kaleoInstance,
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		return kaleoLogLocalService.addTaskAssignmentKaleoLog(
			Collections.emptyList(), kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);
	}

	protected KaleoLog addTaskCompletionKaleoLog(
			KaleoInstance kaleoInstance,
			KaleoTaskInstanceToken kaleoTaskInstanceToken)
		throws PortalException {

		return kaleoLogLocalService.addTaskCompletionKaleoLog(
			kaleoTaskInstanceToken, StringPool.BLANK,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);
	}

	protected KaleoLog addWorkflowInstanceEndKaleoLog(
			KaleoInstance kaleoInstance)
		throws Exception, PortalException {

		return kaleoLogLocalService.addWorkflowInstanceEndKaleoLog(
			addKaleoInstanceToken(kaleoInstance), serviceContext);
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

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext());
	}

	private String _read(String name) throws IOException {
		ClassLoader classLoader =
			BaseKaleoLocalServiceTestCase.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/workflow/kaleo/dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
	}

	private void _setUpServiceContext() throws Exception {
		serviceContext = new ServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setUserId(TestPropsValues.getUserId());
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

}