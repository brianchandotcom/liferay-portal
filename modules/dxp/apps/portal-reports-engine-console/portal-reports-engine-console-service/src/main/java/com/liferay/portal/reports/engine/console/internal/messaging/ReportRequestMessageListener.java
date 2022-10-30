/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.ReportDesignRetriever;
import com.liferay.portal.reports.engine.ReportEngine;
import com.liferay.portal.reports.engine.ReportGenerationException;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;
import com.liferay.portal.reports.engine.console.status.ReportStatus;

/**
 * @author Michael C. Han
 */
public class ReportRequestMessageListener extends BaseMessageListener {

	public ReportRequestMessageListener(
		EntryLocalService entryLocalService, ReportEngine reportEngine,
		ReportResultContainer reportResultContainer) {

		_entryLocalService = entryLocalService;
		_reportEngine = reportEngine;
		_reportResultContainer = reportResultContainer;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long entryId = GetterUtil.getLong(message.getResponseId());

		ReportRequest reportRequest = (ReportRequest)message.getPayload();

		ReportDesignRetriever reportDesignRetriever =
			reportRequest.getReportDesignRetriever();

		ReportResultContainer reportResultContainer =
			_reportResultContainer.clone(reportDesignRetriever.getReportName());

		try {
			_reportEngine.execute(reportRequest, reportResultContainer);
		}
		catch (ReportGenerationException reportGenerationException) {
			_log.error("Unable to generate report", reportGenerationException);

			reportResultContainer.setReportGenerationException(
				reportGenerationException);
		}
		finally {
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
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReportRequestMessageListener.class);

	private final EntryLocalService _entryLocalService;
	private final ReportEngine _reportEngine;
	private final ReportResultContainer _reportResultContainer;

}