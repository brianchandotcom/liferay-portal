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

package com.liferay.portal.scheduler.internal.messaging.config;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.proxy.MessagingProxyInvocationHandler;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.spring.aop.InvocationHandlerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true, service = SchedulerProxyMessagingConfigurator.class
)
public class SchedulerProxyMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				DestinationNames.SCHEDULER_ENGINE);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> destinationDictionary =
			new HashMapDictionary<>();

		destinationDictionary.put("destination.name", destination.getName());

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination, destinationDictionary);

		destination.register(_proxyMessageListener);

		SchedulerEngineProxyBean schedulerEngineProxyBean =
			new SchedulerEngineProxyBean();

		schedulerEngineProxyBean.setDestinationName(
			DestinationNames.SCHEDULER_ENGINE);
		schedulerEngineProxyBean.setSynchronousDestinationName(
			DestinationNames.SCHEDULER_ENGINE);
		schedulerEngineProxyBean.setSynchronousMessageSenderMode(
			SynchronousMessageSender.Mode.DIRECT);

		schedulerEngineProxyBean.afterPropertiesSet();

		InvocationHandlerFactory invocationHandlerFactory =
			MessagingProxyInvocationHandler.getInvocationHandlerFactory();

		InvocationHandler invocationHandler =
			invocationHandlerFactory.createInvocationHandler(
				schedulerEngineProxyBean);

		Class<?> beanClass = schedulerEngineProxyBean.getClass();

		Thread thread = Thread.currentThread();

		SchedulerEngine schedulerEngineProxy =
			(SchedulerEngine)ProxyUtil.newProxyInstance(
				thread.getContextClassLoader(), beanClass.getInterfaces(),
				invocationHandler);

		Dictionary<String, Object> schedulerEngineDictionary =
			new HashMapDictionary<>();

		schedulerEngineDictionary.put("isProxy", Boolean.TRUE);

		_schedulerEngineServiceRegistration = bundleContext.registerService(
			SchedulerEngine.class, schedulerEngineProxy,
			schedulerEngineDictionary);
	}

	@Deactivate
	protected void deactivate() {
		if (_destinationServiceRegistration != null) {
			_destinationServiceRegistration.unregister();
		}

		if (_schedulerEngineServiceRegistration != null) {
			_schedulerEngineServiceRegistration.unregister();
		}
	}

	@Reference(unbind = "-")
	protected void setDestinationFactory(
		DestinationFactory destinationFactory) {

		_destinationFactory = destinationFactory;
	}

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
	}

	@Reference(
		service = ProxyMessageListener.class,
		target = "(destination.name=" + DestinationNames.SCHEDULER_ENGINE + ")",
		unbind = "-"
	)
	protected void setProxyMessageListener(
		ProxyMessageListener proxyMessageListener) {

		_proxyMessageListener = proxyMessageListener;
	}

	private volatile DestinationFactory _destinationFactory;
	private volatile ServiceRegistration<Destination>
		_destinationServiceRegistration;
	private volatile ProxyMessageListener _proxyMessageListener;
	private volatile ServiceRegistration<SchedulerEngine>
		_schedulerEngineServiceRegistration;

}