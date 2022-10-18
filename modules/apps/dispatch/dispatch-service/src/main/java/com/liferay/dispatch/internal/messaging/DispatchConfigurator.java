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

package com.liferay.dispatch.internal.messaging;

import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.exception.DispatchTriggerSchedulerException;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.internal.helper.DispatchTriggerHelper;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = {})
public class DispatchConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DispatchConstants.EXECUTOR_DESTINATION_NAME);

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

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"destination.name", destination.getName()
			).build();

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination, properties);

		_scheduleMemorySchedulerJobs();
	}

	@Deactivate
	protected void deactivate() {
		_unscheduleMemorySchedulerJobs();

		_serviceRegistration.unregister();
	}

	private void _scheduleMemorySchedulerJobs() {
		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.ALL_NODES;

		List<DispatchTrigger> dispatchTriggers =
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, dispatchTaskClusterMode);

		for (DispatchTrigger dispatchTrigger : dispatchTriggers) {
			try {
				_dispatchTriggerHelper.addSchedulerJob(
					dispatchTrigger.getDispatchTriggerId(),
					dispatchTrigger.getCronExpression(),
					dispatchTrigger.getStartDate(),
					dispatchTrigger.getEndDate(),
					dispatchTaskClusterMode.getStorageType(),
					dispatchTrigger.getTimeZoneId());
			}
			catch (DispatchTriggerSchedulerException
						dispatchTriggerSchedulerException) {

				_log.error(dispatchTriggerSchedulerException);
			}
		}
	}

	private void _unscheduleMemorySchedulerJobs() {
		DispatchTaskClusterMode dispatchTaskClusterMode =
			DispatchTaskClusterMode.ALL_NODES;

		List<DispatchTrigger> dispatchTriggers =
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, dispatchTaskClusterMode);

		for (DispatchTrigger dispatchTrigger : dispatchTriggers) {
			try {
				_dispatchTriggerHelper.unscheduleSchedulerJob(
					dispatchTrigger.getDispatchTriggerId(),
					dispatchTaskClusterMode.getStorageType());
			}
			catch (DispatchTriggerSchedulerException
						dispatchTriggerSchedulerException) {

				_log.error(dispatchTriggerSchedulerException);
			}
		}
	}

	private static final int _MAXIMUM_QUEUE_SIZE = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchConfigurator.class);

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private DispatchTriggerHelper _dispatchTriggerHelper;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private ServiceRegistration<Destination> _serviceRegistration;

}