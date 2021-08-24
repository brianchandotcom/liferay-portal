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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.DefaultWorkflowDefinition;
import com.liferay.portal.kernel.workflow.DefaultWorkflowInstance;
import com.liferay.portal.kernel.workflow.DefaultWorkflowLog;
import com.liferay.portal.kernel.workflow.DefaultWorkflowNode;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTask;
import com.liferay.portal.kernel.workflow.DefaultWorkflowTransition;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowNode;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.export.DefinitionExporter;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.runtime.integration.internal.util.LazyWorkflowTaskAssigneeList;
import com.liferay.portal.workflow.kaleo.runtime.integration.internal.util.WorkflowTaskAssigneesSupplier;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTransitionLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = KaleoWorkflowModelConverter.class)
public class KaleoWorkflowModelConverterImpl
	implements KaleoWorkflowModelConverter {

	@Override
	public List<WorkflowTaskAssignee> getWorkflowTaskAssignees(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		WorkflowTaskAssigneesSupplier workflowTaskAssigneesSupplier =
			new WorkflowTaskAssigneesSupplier(kaleoTaskInstanceToken);

		return workflowTaskAssigneesSupplier.get();
	}

	@Override
	public WorkflowDefinition toWorkflowDefinition(
		KaleoDefinition kaleoDefinition) {

		DefaultWorkflowDefinition defaultWorkflowDefinition =
			new DefaultWorkflowDefinition();

		defaultWorkflowDefinition.setActive(kaleoDefinition.isActive());
		defaultWorkflowDefinition.setCompanyId(kaleoDefinition.getCompanyId());

		String content = kaleoDefinition.getContent();

		if (Validator.isNull(content)) {
			try {
				content = _definitionExporter.export(
					kaleoDefinition.getKaleoDefinitionId());

				kaleoDefinition.setContent(content);

				_kaleoDefinitionLocalService.updateKaleoDefinition(
					kaleoDefinition);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export definition to string", exception);
				}
			}
		}

		defaultWorkflowDefinition.setContent(content);
		defaultWorkflowDefinition.setCreateDate(
			kaleoDefinition.getCreateDate());
		defaultWorkflowDefinition.setDescription(
			kaleoDefinition.getDescription());
		defaultWorkflowDefinition.setModifiedDate(
			kaleoDefinition.getModifiedDate());
		defaultWorkflowDefinition.setName(kaleoDefinition.getName());
		defaultWorkflowDefinition.setScope(kaleoDefinition.getScope());
		defaultWorkflowDefinition.setTitle(kaleoDefinition.getTitle());
		defaultWorkflowDefinition.setUserId(kaleoDefinition.getUserId());
		defaultWorkflowDefinition.setVersion(kaleoDefinition.getVersion());
		defaultWorkflowDefinition.setWorkflowDefinitionId(
			kaleoDefinition.getKaleoDefinitionId());

		try {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
					kaleoDefinition.getVersion() + StringPool.PERIOD + 0);

			List<KaleoNode> kaleoNodes =
				_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
					kaleoDefinitionVersion.getKaleoDefinitionVersionId());

			defaultWorkflowDefinition.setWorkflowNodes(
				Stream.of(
					kaleoNodes
				).flatMap(
					List::stream
				).map(
					kaleoNode -> {
						DefaultWorkflowNode defaultWorkflowNode =
							new DefaultWorkflowNode();

						defaultWorkflowNode.setName(kaleoNode.getName());

						WorkflowNode.Type workflowNodeType =
							WorkflowNode.Type.valueOf(kaleoNode.getType());

						if (Objects.equals(
								workflowNodeType, WorkflowNode.Type.STATE)) {

							if (kaleoNode.isInitial()) {
								workflowNodeType =
									WorkflowNode.Type.INITIAL_STATE;
							}
							else if (kaleoNode.isTerminal()) {
								workflowNodeType =
									WorkflowNode.Type.TERMINAL_STATE;
							}
						}

						defaultWorkflowNode.setType(workflowNodeType);

						return defaultWorkflowNode;
					}
				).collect(
					Collectors.toList()
				));

			List<KaleoTransition> kaleoTransitions =
				_kaleoTransitionLocalService.
					getKaleoDefinitionVersionKaleoTransitions(
						kaleoDefinitionVersion.getKaleoDefinitionVersionId());

			defaultWorkflowDefinition.setWorkflowTransitions(
				Stream.of(
					kaleoTransitions
				).flatMap(
					List::stream
				).map(
					kaleoTransition -> {
						DefaultWorkflowTransition defaultWorkflowTransition =
							new DefaultWorkflowTransition();

						defaultWorkflowTransition.setName(
							kaleoTransition.getName());
						defaultWorkflowTransition.setSourceNodeName(
							kaleoTransition.getSourceKaleoNodeName());
						defaultWorkflowTransition.setTargetNodeName(
							kaleoTransition.getTargetKaleoNodeName());

						return defaultWorkflowTransition;
					}
				).collect(
					Collectors.toList()
				));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		return defaultWorkflowDefinition;
	}

	@Override
	public WorkflowDefinition toWorkflowDefinition(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException {

		DefaultWorkflowDefinition defaultWorkflowDefinition =
			new DefaultWorkflowDefinition();

		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.getKaleoDefinition();

			defaultWorkflowDefinition.setActive(kaleoDefinition.isActive());
			defaultWorkflowDefinition.setScope(kaleoDefinition.getScope());
			defaultWorkflowDefinition.setWorkflowDefinitionId(
				kaleoDefinition.getKaleoDefinitionId());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}

			defaultWorkflowDefinition.setActive(false);
			defaultWorkflowDefinition.setScope(
				WorkflowDefinitionConstants.SCOPE_ALL);
		}

		String content = kaleoDefinitionVersion.getContent();

		if (Validator.isNull(content)) {
			try {
				content = _definitionExporter.export(
					kaleoDefinitionVersion.getCompanyId(),
					kaleoDefinitionVersion.getName(),
					getVersion(kaleoDefinitionVersion.getVersion()));

				kaleoDefinitionVersion.setContent(content);

				_kaleoDefinitionVersionLocalService.
					updateKaleoDefinitionVersion(kaleoDefinitionVersion);
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to export definition to string",
						portalException);
				}
			}
		}

		defaultWorkflowDefinition.setContent(content);
		defaultWorkflowDefinition.setCreateDate(
			kaleoDefinitionVersion.getCreateDate());
		defaultWorkflowDefinition.setDescription(
			kaleoDefinitionVersion.getDescription());
		defaultWorkflowDefinition.setModifiedDate(
			kaleoDefinitionVersion.getModifiedDate());
		defaultWorkflowDefinition.setName(kaleoDefinitionVersion.getName());
		defaultWorkflowDefinition.setTitle(kaleoDefinitionVersion.getTitle());
		defaultWorkflowDefinition.setUserId(kaleoDefinitionVersion.getUserId());
		defaultWorkflowDefinition.setVersion(
			getVersion(kaleoDefinitionVersion.getVersion()));

		return defaultWorkflowDefinition;
	}

	@Override
	public WorkflowInstance toWorkflowInstance(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken)
		throws PortalException {

		return toWorkflowInstance(kaleoInstance, kaleoInstanceToken, null);
	}

	@Override
	public WorkflowInstance toWorkflowInstance(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		DefaultWorkflowInstance defaultWorkflowInstance =
			new DefaultWorkflowInstance();

		defaultWorkflowInstance.setEndDate(kaleoInstance.getCompletionDate());
		defaultWorkflowInstance.setStartDate(kaleoInstance.getCreateDate());

		KaleoNode currentKaleoNode = kaleoInstanceToken.getCurrentKaleoNode();

		List<String> currentKaleoNodeNames = new ArrayList<>();

		if (Objects.equals(currentKaleoNode.getType(), NodeType.FORK.name())) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(kaleoInstanceToken.getCompanyId());

			for (KaleoInstanceToken currentKaleoInstanceToken :
					_kaleoInstanceTokenLocalService.getKaleoInstanceTokens(
						kaleoInstance.getKaleoInstanceId())) {

				KaleoNode kaleoNode =
					currentKaleoInstanceToken.getCurrentKaleoNode();

				if (!Objects.equals(
						kaleoNode.getType(), NodeType.FORK.name())) {

					currentKaleoNodeNames.add(kaleoNode.getName());
				}
			}
		}
		else {
			currentKaleoNodeNames.add(currentKaleoNode.getName());
		}

		defaultWorkflowInstance.setCurrentNodeNames(currentKaleoNodeNames);

		if (workflowContext != null) {
			defaultWorkflowInstance.setWorkflowContext(workflowContext);
		}
		else {
			defaultWorkflowInstance.setWorkflowContext(
				WorkflowContextUtil.convert(
					kaleoInstance.getWorkflowContext()));
		}

		defaultWorkflowInstance.setWorkflowDefinitionName(
			kaleoInstance.getKaleoDefinitionName());
		defaultWorkflowInstance.setWorkflowDefinitionVersion(
			kaleoInstance.getKaleoDefinitionVersion());
		defaultWorkflowInstance.setWorkflowInstanceId(
			kaleoInstance.getKaleoInstanceId());

		return defaultWorkflowInstance;
	}

	@Override
	public WorkflowLog toWorkflowLog(KaleoLog kaleoLog) {
		DefaultWorkflowLog defaultWorkflowLog = new DefaultWorkflowLog();

		defaultWorkflowLog.setAuditUserId(kaleoLog.getUserId());
		defaultWorkflowLog.setComment(kaleoLog.getComment());
		defaultWorkflowLog.setCreateDate(kaleoLog.getCreateDate());
		defaultWorkflowLog.setPreviousState(
			kaleoLog.getPreviousKaleoNodeName());

		long previousAssigneeClassPK = kaleoLog.getPreviousAssigneeClassPK();

		if (previousAssigneeClassPK > 0) {
			String previousAssigneeClassName =
				kaleoLog.getPreviousAssigneeClassName();

			if (previousAssigneeClassName.equals(Role.class.getName())) {
				defaultWorkflowLog.setPreviousRoleId(previousAssigneeClassPK);
			}
			else {
				defaultWorkflowLog.setPreviousUserId(previousAssigneeClassPK);
			}
		}

		long currentAssigneeClassPK = kaleoLog.getCurrentAssigneeClassPK();

		if (currentAssigneeClassPK > 0) {
			String currentAssigneeClassName =
				kaleoLog.getCurrentAssigneeClassName();

			if (currentAssigneeClassName.equals(Role.class.getName())) {
				defaultWorkflowLog.setRoleId(currentAssigneeClassPK);
			}
			else {
				defaultWorkflowLog.setUserId(currentAssigneeClassPK);
			}
		}

		defaultWorkflowLog.setState(kaleoLog.getKaleoNodeName());
		defaultWorkflowLog.setType(KaleoLogUtil.convert(kaleoLog.getType()));
		defaultWorkflowLog.setWorkflowLogId(kaleoLog.getKaleoLogId());
		defaultWorkflowLog.setWorkflowTaskId(
			kaleoLog.getKaleoTaskInstanceTokenId());

		return defaultWorkflowLog;
	}

	@Override
	public WorkflowTask toWorkflowTask(
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		DefaultWorkflowTask defaultWorkflowTask = new DefaultWorkflowTask();

		defaultWorkflowTask.setCreateDate(
			kaleoTaskInstanceToken.getCreateDate());
		defaultWorkflowTask.setCompletionDate(
			kaleoTaskInstanceToken.getCompletionDate());

		KaleoTask kaleoTask = kaleoTaskInstanceToken.getKaleoTask();

		defaultWorkflowTask.setDescription(kaleoTask.getDescription());

		defaultWorkflowTask.setDueDate(kaleoTaskInstanceToken.getDueDate());
		defaultWorkflowTask.setName(kaleoTask.getName());

		if (workflowContext != null) {
			defaultWorkflowTask.setOptionalAttributes(workflowContext);
		}
		else {
			defaultWorkflowTask.setOptionalAttributes(
				WorkflowContextUtil.convert(
					kaleoTaskInstanceToken.getWorkflowContext()));
		}

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionVersion.getKaleoDefinition();

		defaultWorkflowTask.setWorkflowDefinitionId(
			kaleoDefinition.getKaleoDefinitionId());

		KaleoInstanceToken kaleoInstanceToken =
			kaleoTaskInstanceToken.getKaleoInstanceToken();

		KaleoInstance kaleoInstance = kaleoInstanceToken.getKaleoInstance();

		defaultWorkflowTask.setWorkflowDefinitionName(
			kaleoInstance.getKaleoDefinitionName());
		defaultWorkflowTask.setWorkflowDefinitionVersion(
			kaleoInstance.getKaleoDefinitionVersion());
		defaultWorkflowTask.setWorkflowInstanceId(
			kaleoInstance.getKaleoInstanceId());

		List<WorkflowTaskAssignee> workflowTaskAssignees =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				_kaleoTaskAssignmentInstanceLocalService);

		defaultWorkflowTask.setWorkflowTaskAssignees(workflowTaskAssignees);

		defaultWorkflowTask.setWorkflowTaskId(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		return defaultWorkflowTask;
	}

	protected int getVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return versionParts[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoWorkflowModelConverterImpl.class);

	@Reference
	private DefinitionExporter _definitionExporter;

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTransitionLocalService _kaleoTransitionLocalService;

}