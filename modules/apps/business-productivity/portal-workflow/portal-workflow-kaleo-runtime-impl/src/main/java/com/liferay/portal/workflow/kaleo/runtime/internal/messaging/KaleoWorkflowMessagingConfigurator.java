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

import com.liferay.portal.kernel.concurrent.CallerRunsPolicy;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.workflow.kaleo.runtime.internal.timer.messaging.TimerMessageListener;
import com.liferay.portal.workflow.kaleo.runtime.messaging.DestinationNames;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = KaleoWorkflowMessagingConfigurator.class)
public class KaleoWorkflowMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		registerKaleoGraphWalkerDestination();

		registerWorkflowDefinitionLinkDestination();

		registerWorkflowTimerDestination();

		SchedulerEventMessageListenerWrapper
			schedulerEventMessageListenerWrapper =
				new SchedulerEventMessageListenerWrapper();

		schedulerEventMessageListenerWrapper.setMessageListener(
			_timerMessageListener);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", DestinationNames.WORKFLOW_TIMER);

		_schedulerEventMessageListenerServiceRegistration =
			_bundleContext.registerService(
				MessageListener.class, schedulerEventMessageListenerWrapper,
				properties);
	}

	@Deactivate
	protected void deactivate() {
		if (!_serviceRegistrations.isEmpty()) {
			for (ServiceRegistration<Destination> serviceRegistration :
					_serviceRegistrations.values()) {

				Destination destination = _bundleContext.getService(
					serviceRegistration.getReference());

				serviceRegistration.unregister();

				destination.destroy();
			}

			_serviceRegistrations.clear();
		}

		if (_schedulerEventMessageListenerServiceRegistration != null) {
			_schedulerEventMessageListenerServiceRegistration.unregister();

			_schedulerEventMessageListenerServiceRegistration = null;
		}

		_bundleContext = null;
	}

	protected void registerDestination(
		DestinationConfiguration kaleoGraphWalkerDestinationConfiguration) {

		Destination destination = _destinationFactory.createDestination(
			kaleoGraphWalkerDestinationConfiguration);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", destination.getName());

		ServiceRegistration<Destination> serviceRegistration =
			_bundleContext.registerService(
				Destination.class, destination, properties);

		_serviceRegistrations.put(destination.getName(), serviceRegistration);
	}

	protected void registerKaleoGraphWalkerDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.KALEO_GRAPH_WALKER);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy() {

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

		registerDestination(destinationConfiguration);
	}

	protected void registerWorkflowDefinitionLinkDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				DestinationNames.WORKFLOW_DEFINITION_LINK);

		registerDestination(destinationConfiguration);
	}

	protected void registerWorkflowTimerDestination() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.WORKFLOW_TIMER);

		destinationConfiguration.setMaximumQueueSize(_MAXIMUM_QUEUE_SIZE);

		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy() {

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

		registerDestination(destinationConfiguration);
	}

	private static final int _MAXIMUM_QUEUE_SIZE = 200;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoWorkflowMessagingConfigurator.class);

	private BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<MessageListener>
		_schedulerEventMessageListenerServiceRegistration;
	private final Map<String, ServiceRegistration<Destination>>
		_serviceRegistrations = new HashMap<>();

	@Reference
	private TimerMessageListener _timerMessageListener;

}