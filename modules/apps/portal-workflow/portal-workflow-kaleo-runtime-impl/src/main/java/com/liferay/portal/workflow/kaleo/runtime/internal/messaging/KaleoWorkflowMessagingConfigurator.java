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

package com.liferay.portal.workflow.kaleo.runtime.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.workflow.kaleo.runtime.constants.KaleoRuntimeDestinationNames;
import com.liferay.portal.workflow.kaleo.runtime.internal.timer.messaging.TimerMessageListener;

import java.util.Dictionary;
import java.util.HashMap;
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
 * @author Michael C. Han
 */
@Component(service = KaleoWorkflowMessagingConfigurator.class)
public class KaleoWorkflowMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_registerWorkflowDefinitionLinkDestination();

		_registerWorkflowTimerDestination();

		_registerSchedulerEventMessageListener();
	}

	@Deactivate
	protected void deactivate() {
		_unregisterKaleoWorkflowDestinations();

		_unregisterWorkflowMessageListeners();

		_unregisterSchedulerEventMessageListener();

		_bundleContext = null;
	}

	private void _registerDestination(
		DestinationConfiguration destinationConfiguration) {

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		ServiceRegistration<Destination> serviceRegistration =
			_bundleContext.registerService(
				Destination.class, destination, properties);

		_serviceRegistrations.put(destination.getName(), serviceRegistration);
	}

	private void _registerSchedulerEventMessageListener() {
		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		schedulerEventMessageListenerWrapper.setMessageListener(
			_timerMessageListener);

		_schedulerEventMessageListenerServiceRegistration =
			_bundleContext.registerService(
				MessageListener.class, schedulerEventMessageListenerWrapper,
				HashMapDictionaryBuilder.<String, Object>put(
					"destination.name",
					KaleoRuntimeDestinationNames.WORKFLOW_TIMER
				).build());
	}

	private void _registerWorkflowDefinitionLinkDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				KaleoRuntimeDestinationNames.WORKFLOW_DEFINITION_LINK);

		_registerDestination(destinationConfiguration);
	}

	private void _registerWorkflowTimerDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				KaleoRuntimeDestinationNames.WORKFLOW_TIMER);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new ThreadPoolExecutor.CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the workflow timer task queue is at " +
									"its maximum capacity");
					}

					super.rejectedExecution(runnable, threadPoolExecutor);
				}

			};

		destinationConfiguration.setRejectedExecutionHandler(
			rejectedExecutionHandler);

		_registerDestination(destinationConfiguration);
	}

	private void _unregisterKaleoWorkflowDestinations() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations.values()) {

			Destination destination = _bundleContext.getService(
				serviceRegistration.getReference());

			serviceRegistration.unregister();

			destination.destroy();
		}

		_serviceRegistrations.clear();
	}

	private void _unregisterSchedulerEventMessageListener() {
		if (_schedulerEventMessageListenerServiceRegistration == null) {
			return;
		}

		_schedulerEventMessageListenerServiceRegistration.unregister();

		_schedulerEventMessageListenerServiceRegistration = null;
	}

	private void _unregisterWorkflowMessageListeners() {
		for (Map.Entry<String, MessageListener> entry :
				_proxyMessageListeners.entrySet()) {

			_messageBus.unregisterMessageListener(
				entry.getKey(), entry.getValue());
		}

		_proxyMessageListeners.clear();
	}

	private static final int _MAXIMUM_QUEUE_SIZE = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoWorkflowMessagingConfigurator.class);

	private BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private MessageBus _messageBus;

	private final Map<String, MessageListener> _proxyMessageListeners =
		new HashMap<>();
	private ServiceRegistration<MessageListener>
		_schedulerEventMessageListenerServiceRegistration;
	private final Map<String, ServiceRegistration<Destination>>
		_serviceRegistrations = new HashMap<>();

	@Reference
	private TimerMessageListener _timerMessageListener;

}