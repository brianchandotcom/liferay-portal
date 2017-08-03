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

package com.liferay.portal.reports.engine.console.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.ReportGenerationException;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;
import com.liferay.portal.reports.engine.console.status.ReportStatus;

/**
 * @author Gavin Wan
 */
public class AdminMessageListener extends BaseMessageListener {

	public AdminMessageListener(EntryLocalService entryLocalService) {
		_entryLocalService = entryLocalService;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		ReportResultContainer reportResultContainer =
			(ReportResultContainer)message.getPayload();

		long entryId = GetterUtil.getLong(message.getResponseId());

		if (reportResultContainer.hasError()) {
			ReportGenerationException reportGenerationException =
				reportResultContainer.getReportGenerationException();

			_entryLocalService.updateEntryStatus(
				entryId, ReportStatus.ERROR,
				reportGenerationException.getMessage());
		}
		else {
			_entryLocalService.updateEntry(
				entryId, reportResultContainer.getReportName(),
				reportResultContainer.getResults());
		}
	}

	private final EntryLocalService _entryLocalService;

}