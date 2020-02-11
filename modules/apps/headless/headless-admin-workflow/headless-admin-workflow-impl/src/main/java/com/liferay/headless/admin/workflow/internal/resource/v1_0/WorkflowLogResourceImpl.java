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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.Role;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowLog;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.workflow.internal.dto.v1_0.util.RoleUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowLogResource;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowLogManager;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.definition.LogType;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-log.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowLogResource.class
)
public class WorkflowLogResourceImpl extends BaseWorkflowLogResourceImpl {

	@Override
	public Page<WorkflowLog> getWorkflowInstanceWorkflowLogsPage(
			Long workflowInstanceId, String[] types, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowLogManager.getWorkflowLogsByWorkflowInstance(
					contextCompany.getCompanyId(), workflowInstanceId,
					_toLogTypes(types), pagination.getStartPosition(),
					pagination.getEndPosition(),
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator(
						false)),
				this::_toWorkflowLog),
			pagination,
			_workflowLogManager.getWorkflowLogCountByWorkflowInstance(
				contextCompany.getCompanyId(), workflowInstanceId,
				_toLogTypes(types)));
	}

	@Override
	public WorkflowLog getWorkflowLog(Long workflowLogId) throws Exception {
		return _toWorkflowLog(
			_kaleoWorkflowModelConverter.toWorkflowLog(
				_kaleoLogLocalService.getKaleoLog(workflowLogId)));
	}

	@Override
	public Page<WorkflowLog> getWorkflowTaskWorkflowLogsPage(
			Long workflowTaskId, String[] types, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowLogManager.getWorkflowLogsByWorkflowTask(
					contextCompany.getCompanyId(), workflowTaskId,
					_toLogTypes(types), pagination.getStartPosition(),
					pagination.getEndPosition(),
					WorkflowComparatorFactoryUtil.getLogCreateDateComparator(
						false)),
				this::_toWorkflowLog),
			pagination,
			_workflowLogManager.getWorkflowLogCountByWorkflowTask(
				contextCompany.getCompanyId(), workflowTaskId,
				_toLogTypes(types)));
	}

	private String _toLogTypeName(WorkflowLog.Type type) {
		if (type == WorkflowLog.Type.TASK_ASSIGN) {
			return LogType.TASK_ASSIGNMENT.name();
		}
		else if (type == WorkflowLog.Type.TASK_COMPLETION) {
			return LogType.TASK_COMPLETION.name();
		}
		else if (type == WorkflowLog.Type.TASK_UPDATE) {
			return LogType.TASK_UPDATE.name();
		}
		else if (type == WorkflowLog.Type.TRANSITION) {
			return LogType.NODE_EXIT.name();
		}

		return null;
	}

	private List<Integer> _toLogTypes(String[] types) {
		return Stream.of(
			types
		).map(
			WorkflowLog.Type::create
		).map(
			this::_toLogTypeName
		).map(
			KaleoLogUtil::convert
		).distinct(
		).collect(
			Collectors.toList()
		);
	}

	private Role _toRole(long roleId) throws Exception {
		com.liferay.portal.kernel.model.Role role = _roleLocalService.getRole(
			roleId);

		if (role == null) {
			return null;
		}

		return RoleUtil.toRole(
			contextAcceptLanguage.isAcceptAllLanguages(),
			contextAcceptLanguage.getPreferredLocale(), _portal, role,
			_userLocalService.getUserById(role.getUserId()));
	}

	private WorkflowLog _toWorkflowLog(
			com.liferay.portal.kernel.workflow.WorkflowLog workflowLog)
		throws Exception {

		return new WorkflowLog() {
			{
				auditPerson = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(workflowLog.getAuditUserId()));
				commentLog = workflowLog.getComment();
				dateCreated = workflowLog.getCreateDate();
				id = workflowLog.getWorkflowLogId();
				person = CreatorUtil.toCreator(
					_portal,
					_userLocalService.fetchUser(workflowLog.getUserId()));
				previousPerson = CreatorUtil.toCreator(
					_portal,
					_userLocalService.fetchUser(
						workflowLog.getPreviousUserId()));
				previousRole = _toRole(workflowLog.getPreviousRoleId());
				previousState = workflowLog.getPreviousState();
				role = _toRole(workflowLog.getRoleId());
				state = workflowLog.getState();
				type = _toWorkflowLogType(
					KaleoLogUtil.convert(workflowLog.getType()));
				workflowTaskId = workflowLog.getWorkflowTaskId();
			}
		};
	}

	private WorkflowLog.Type _toWorkflowLogType(String type) {
		if (type == LogType.NODE_EXIT.name()) {
			return WorkflowLog.Type.TRANSITION;
		}
		else if (type == LogType.TASK_ASSIGNMENT.name()) {
			return WorkflowLog.Type.TASK_ASSIGN;
		}
		else if (type == LogType.TASK_COMPLETION.name()) {
			return WorkflowLog.Type.TASK_COMPLETION;
		}
		else if (type == LogType.TASK_UPDATE.name()) {
			return WorkflowLog.Type.TASK_UPDATE;
		}

		return null;
	}

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowLogManager _workflowLogManager;

}