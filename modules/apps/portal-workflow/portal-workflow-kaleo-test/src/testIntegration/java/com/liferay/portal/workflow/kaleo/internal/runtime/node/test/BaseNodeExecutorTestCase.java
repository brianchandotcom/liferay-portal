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

package com.liferay.portal.workflow.kaleo.internal.runtime.node.test;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.test.BaseKaleoLocalServiceTestCase;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.junit.Before;

/**
 * @author Feliphe Marinho
 */
public abstract class BaseNodeExecutorTestCase {

	@Before
	public void setUp() throws Exception {
		_setUpCreateWorkflowContext();
		_setUpServiceContext();
	}

	protected BlogsEntry addBlogsEntry() throws PortalException {
		return blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext());
	}

	protected KaleoDefinition addKaleoDefinition()
		throws IOException, PortalException {

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.addKaleoDefinition(
				StringUtil.randomString(), StringUtil.randomString(),
				StringUtil.randomString(),
				_read("single-approver-definition.xml"), StringPool.BLANK, 1,
				serviceContext);

		_kaleoDefinitionLocalService.activateKaleoDefinition(
			kaleoDefinition.getKaleoDefinitionId(), serviceContext);

		return kaleoDefinition;
	}

	protected KaleoInstance addKaleoInstance() throws Exception {
		return _kaleoInstanceLocalService.addKaleoInstance(
			1, 1, "Test", 1, workflowContext, serviceContext);
	}

	protected KaleoInstanceToken addKaleoInstanceToken(
			KaleoInstance kaleoInstance)
		throws Exception {

		KaleoNode kaleoNode = addKaleoNode(
			kaleoInstance, new Task("review", StringPool.BLANK));

		return _kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			kaleoNode.getKaleoNodeId(), kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(),
			kaleoInstance.getKaleoInstanceId(), 0,
			WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext()),
			serviceContext);
	}

	protected KaleoNode addKaleoNode(KaleoInstance kaleoInstance, Task task)
		throws Exception {

		return _kaleoNodeLocalService.addKaleoNode(
			kaleoInstance.getKaleoDefinitionId(),
			kaleoInstance.getKaleoDefinitionVersionId(), task, serviceContext);
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

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	@Inject
	protected KaleoTaskInstanceTokenLocalService
		kaleoTaskInstanceTokenLocalService;

	protected ServiceContext serviceContext;
	protected Map<String, Serializable> workflowContext;

	private String _read(String name) throws IOException {
		ClassLoader classLoader =
			BaseKaleoLocalServiceTestCase.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/workflow/kaleo/dependencies/" + name)) {

			return StringUtil.read(inputStream);
		}
	}

	private void _setUpCreateWorkflowContext() throws Exception {
		workflowContext = HashMapBuilder.<String, Serializable>put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
			BlogsEntry.class.getName()
		).put(
			WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
			String.valueOf(addBlogsEntry().getEntryId())
		).put(
			WorkflowConstants.CONTEXT_SERVICE_CONTEXT,
			ServiceContextTestUtil.getServiceContext()
		).build();
	}

	private void _setUpServiceContext() {
		serviceContext = (ServiceContext)workflowContext.get(
			WorkflowConstants.CONTEXT_SERVICE_CONTEXT);
	}

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

}