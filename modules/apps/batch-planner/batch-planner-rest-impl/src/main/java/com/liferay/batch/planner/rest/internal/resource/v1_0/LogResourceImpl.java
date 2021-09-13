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

package com.liferay.batch.planner.rest.internal.resource.v1_0;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.rest.dto.v1_0.Log;
import com.liferay.batch.planner.rest.resource.v1_0.LogResource;
import com.liferay.batch.planner.service.BatchPlannerLogService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Matija Petanjek
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/log.properties",
	scope = ServiceScope.PROTOTYPE, service = LogResource.class
)
public class LogResourceImpl extends BaseLogResourceImpl {

	public Page<Log> getPlanLogsPage(Long id, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_batchPlannerLogService.getBatchPlannerLogs(
					id, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toLog),
			pagination, _batchPlannerLogService.getBatchPlannerLogsCount(id));
	}

	private Log _toLog(BatchPlannerLog batchPlannerLog) {
		return new Log() {
			{
				dispatchTriggerExternalReferenceCode =
					batchPlannerLog.getDispatchTriggerERC();
				exportTaskExternalReferenceCode =
					batchPlannerLog.getBatchEngineExportTaskERC();
				id = batchPlannerLog.getBatchPlannerLogId();
				importTaskExternalReferenceCode =
					batchPlannerLog.getBatchEngineImportTaskERC();
				planId = batchPlannerLog.getBatchPlannerPlanId();
				size = batchPlannerLog.getSize();
				status = batchPlannerLog.getStatus();
				total = batchPlannerLog.getTotal();
			}
		};
	}

	@Reference
	private BatchPlannerLogService _batchPlannerLogService;

}