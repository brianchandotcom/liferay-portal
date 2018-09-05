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

package com.liferay.workflow.apio.internal.architect.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManager;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.workflow.apio.architect.identifier.WorkflowLogIdentifier;
import com.liferay.workflow.apio.architect.identifier.WorkflowTaskIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose a WorkflowLog resources through
 * a web API. The resources are mapped from the internal model {@link
 * WorkflowLog}.
 *
 * @author Sarai Díaz
 */
@Component
public class WorkflowLogNestedCollectionResource
	implements NestedCollectionResource
		<WorkflowLog, Long, WorkflowLogIdentifier, Long,
		 WorkflowTaskIdentifier> {

	@Override
	public NestedCollectionRoutes<WorkflowLog, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<WorkflowLog, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	@Override
	public String getName() {
		return "workflow-logs";
	}

	@Override
	public ItemRoutes<WorkflowLog, Long> itemRoutes(
		ItemRoutes.Builder<WorkflowLog, Long> builder) {

		return builder.addGetter(
			this::_getWorkflowLog
		).build();
	}

	@Override
	public Representor<WorkflowLog> representor(
		Representor.Builder<WorkflowLog, Long> builder) {

		return builder.types(
			"WorkflowLog"
		).identifier(
			WorkflowLog::getWorkflowLogId
		).addDate(
			"dateCreated", WorkflowLog::getCreateDate
		).addLinkedModel(
			"auditPerson", PersonIdentifier.class, WorkflowLog::getAuditUserId
		).addLinkedModel(
			"person", PersonIdentifier.class, WorkflowLog::getUserId
		).addLinkedModel(
			"previousPerson", PersonIdentifier.class,
			WorkflowLog::getPreviousUserId
		).addLinkedModel(
			"task", WorkflowTaskIdentifier.class, WorkflowLog::getWorkflowTaskId
		).addString(
			"commentLog", WorkflowLog::getComment
		).addString(
			"previousState", WorkflowLog::getPreviousState
		).addString(
			"state", WorkflowLog::getState
		).addString(
			"type", this::_getWorkflowLogType
		).build();
	}

	private PageItems<WorkflowLog> _getPageItems(
			Pagination pagination, Long workflowTaskId, Company company)
		throws WorkflowException {

		List<WorkflowLog> workflowLogs =
			_workflowLogManager.getWorkflowLogsByWorkflowTask(
				company.getCompanyId(), workflowTaskId, null,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
		int count = _workflowLogManager.getWorkflowLogCountByWorkflowTask(
			company.getCompanyId(), workflowTaskId, null);

		return new PageItems<>(workflowLogs, count);
	}

	private WorkflowLog _getWorkflowLog(Long workflowLogId)
		throws PortalException {

		KaleoLog kaleoLog = _kaleoLogLocalService.getKaleoLog(workflowLogId);

		return _kaleoWorkflowModelConverter.toWorkflowLog(kaleoLog);
	}

	private String _getWorkflowLogType(WorkflowLog workflowLog) {
		return KaleoLogUtil.convert(workflowLog.getType());
	}

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference
	private WorkflowLogManager _workflowLogManager;

}