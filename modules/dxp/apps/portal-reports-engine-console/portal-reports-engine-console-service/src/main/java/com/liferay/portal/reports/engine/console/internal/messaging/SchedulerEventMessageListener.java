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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.reports.engine.console.configuration.ReportsPortletMessagingConfiguration;
import com.liferay.portal.reports.engine.console.internal.constants.ReportsEngineDestinationNames;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 */
@Component(
	configurationPid = "com.liferay.portal.reports.engine.console.configuration.ReportsPortletMessagingConfiguration",
	property = "destination.name=" + ReportsEngineDestinationNames.REPORTS_SCHEDULER_EVENT,
	service = MessageListener.class
)
public class SchedulerEventMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		ReportsPortletMessagingConfiguration
			reportsPortletMessagingConfiguration =
				ConfigurableUtil.createConfigurable(
					ReportsPortletMessagingConfiguration.class, properties);

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				ReportsEngineDestinationNames.REPORTS_SCHEDULER_EVENT);

		destinationConfiguration.setMaximumQueueSize(
			reportsPortletMessagingConfiguration.reportMessageQueueSize());

		RejectedExecutionHandler rejectedExecutionHandler =
			new ThreadPoolExecutor.CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the report console's task queue is " +
									"at its maximum capacity");
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
		long entryId = message.getLong("entryId");
		String reportName = message.getString("reportName");

		_entryLocalService.generateReport(entryId, reportName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerEventMessageListener.class);

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private EntryLocalService _entryLocalService;

	private ServiceRegistration<Destination> _serviceRegistration;

}