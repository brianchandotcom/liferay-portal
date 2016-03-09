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

package com.liferay.portal.security.audit.wiring.internal.messaging;

import com.liferay.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.concurrent.CallerRunsPolicy;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.security.audit.configuration.AuditConfiguration;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.configuration.AuditConfiguration",
	immediate = true, service = AuditMessagingConfigurator.class
)
public class AuditMessagingConfigurator {

	@Activate
	protected void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();

		Dictionary<String, Object> properties =
			componentContext.getProperties();

		AuditConfiguration auditConfiguration =
			ConfigurableUtil.createConfigurable(
				AuditConfiguration.class, properties);

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.AUDIT);

		destinationConfiguration.setMaximumQueueSize(
			auditConfiguration.auditMessageMaxQueueSize());

		RejectedExecutionHandler rejectedExecutionHandler =
			new CallerRunsPolicy() {

				@Override
				public void rejectedExecution(
					Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

					if (_log.isWarnEnabled()) {
						_log.warn(
							"The current thread will handle the request " +
								"because the audit router's task queue is at " +
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
			new HashMapDictionary<>();

		destinationProperties.put("destination.name", destination.getName());

		_destinationServiceRegistration = _bundleContext.registerService(
			Destination.class, destination, destinationProperties);

		destination.register(_proxyMessageListener);
	}

	@Deactivate
	protected void deactivate() {
		if (_destinationServiceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_destinationServiceRegistration.getReference());

			_destinationServiceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	@Modified
	protected void modified(ComponentContext componentContext) {
		deactivate();

		activate(componentContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AuditMessagingConfigurator.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private volatile ServiceRegistration<Destination>
		_destinationServiceRegistration;

	@Reference(
		service = ProxyMessageListener.class,
		target = "(destination.name=" + DestinationNames.AUDIT + ")"
	)
	private ProxyMessageListener _proxyMessageListener;

}