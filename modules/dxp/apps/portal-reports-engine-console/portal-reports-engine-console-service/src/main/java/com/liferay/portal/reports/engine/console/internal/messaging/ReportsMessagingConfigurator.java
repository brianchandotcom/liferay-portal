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
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.reports.engine.ByteArrayReportResultContainer;
import com.liferay.portal.reports.engine.ReportEngine;
import com.liferay.portal.reports.engine.console.internal.constants.ReportsEngineDestinationNames;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = {})
public class ReportsMessagingConfigurator {

	@Activate
	protected void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();

		MessageListener reportRequestMessageListener =
			new ReportRequestMessageListener(
				_entryLocalService, _reportEngine,
				new ByteArrayReportResultContainer());

		_messageListeners.put(
			ReportsEngineDestinationNames.REPORT_REQUEST,
			reportRequestMessageListener);

		for (String destinationName : _messageListeners.keySet()) {
			_registerDestination(destinationName);
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> destinationServiceRegistration :
				_destinationServiceRegistrations) {

			destinationServiceRegistration.unregister();
		}

		_destinationServiceRegistrations.clear();

		for (ServiceRegistration<MessageListener>
				messageListenerServiceRegistration :
					_messageListenerServiceRegistrations) {

			messageListenerServiceRegistration.unregister();
		}

		_messageListeners.clear();

		_messageListenerServiceRegistrations.clear();

		_bundleContext = null;
	}

	private void _registerDestination(String destinationName) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				destinationName);

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

		Dictionary<String, Object> destinationProperties =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		ServiceRegistration<Destination> destinationServiceRegistration =
			_bundleContext.registerService(
				Destination.class, destination, destinationProperties);

		_destinationServiceRegistrations.add(destinationServiceRegistration);

		MessageListener messageListener = _messageListeners.get(
			destinationName);

		destination.register(messageListener);

		ServiceRegistration<MessageListener>
			messageListenerServiceRegistration = _bundleContext.registerService(
				MessageListener.class, messageListener, destinationProperties);

		_messageListenerServiceRegistrations.add(
			messageListenerServiceRegistration);
	}

	private static final int _MAXIMUM_QUEUE_SIZE = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		ReportsMessagingConfigurator.class);

	private BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private final List<ServiceRegistration<Destination>>
		_destinationServiceRegistrations = new ArrayList<>();

	@Reference
	private EntryLocalService _entryLocalService;

	private final Map<String, MessageListener> _messageListeners =
		new ConcurrentHashMap<>();
	private final List<ServiceRegistration<MessageListener>>
		_messageListenerServiceRegistrations = new ArrayList<>();

	@Reference
	private ReportEngine _reportEngine;

}