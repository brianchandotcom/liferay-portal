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
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.reports.engine.ByteArrayReportResultContainer;
import com.liferay.portal.reports.engine.ReportDesignRetriever;
import com.liferay.portal.reports.engine.ReportEngine;
import com.liferay.portal.reports.engine.ReportGenerationException;
import com.liferay.portal.reports.engine.ReportRequest;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.reports.engine.console.internal.constants.ReportsEngineDestinationNames;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;
import com.liferay.portal.reports.engine.console.status.ReportStatus;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "destination.name=" + ReportsEngineDestinationNames.REPORT_REQUEST,
	service = MessageListener.class
)
public class ReportRequestMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				ReportsEngineDestinationNames.REPORT_REQUEST);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new ThreadPoolExecutor.CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the graph walker's task queue is at " +
									"its maximum capacity");
					}

					super.rejectedExecution(runnable, threadPoolExecutor);
				}

			};

		destinationConfiguration.setRejectedExecutionHandler(
			rejectedExecutionHandler);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long entryId = GetterUtil.getLong(message.getResponseId());

		ReportRequest reportRequest = (ReportRequest)message.getPayload();

		ReportDesignRetriever reportDesignRetriever =
			reportRequest.getReportDesignRetriever();

		ReportResultContainer reportResultContainer =
			new ByteArrayReportResultContainer(
				reportDesignRetriever.getReportName());

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

	private static final int _MAXIMUM_QUEUE_SIZE = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		ReportRequestMessageListener.class);

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private EntryLocalService _entryLocalService;

	@Reference
	private ReportEngine _reportEngine;

	private ServiceRegistration<Destination> _serviceRegistration;

}