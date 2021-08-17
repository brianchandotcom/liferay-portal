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

package com.liferay.headless.admin.batch.planner.internal.dto.v1_0.converter;

import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.headless.admin.batch.planner.dto.v1_0.LogEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matija Petanjek
 */
@Component(
	property = "dto.class.name=com.liferay.batch.planner.model.BatchPlannerLog",
	service = {DTOConverter.class, LogEntryDTOConverter.class}
)
public class LogEntryDTOConverter
	implements DTOConverter<BatchPlannerLog, LogEntry> {

	@Override
	public String getContentType() {
		return LogEntry.class.getSimpleName();
	}

	@Override
	public LogEntry toDTO(BatchPlannerLog batchPlannerLog) throws Exception {
		return new LogEntry() {
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

	public List<LogEntry> toDTOs(List<BatchPlannerLog> batchPlannerLogs)
		throws Exception {

		List<LogEntry> logEntries = new ArrayList<>();

		for (BatchPlannerLog batchPlannerLog : batchPlannerLogs) {
			logEntries.add(toDTO(batchPlannerLog));
		}

		return logEntries;
	}

}