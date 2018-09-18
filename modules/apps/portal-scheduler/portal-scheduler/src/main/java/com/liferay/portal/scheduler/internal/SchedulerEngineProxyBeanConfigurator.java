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

package com.liferay.portal.scheduler.internal;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.proxy.MessagingProxyInvocationHandler;
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

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
public class SchedulerEngineProxyBeanConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		SchedulerEngineProxyBean schedulerEngineProxyBean =
			new SchedulerEngineProxyBean();

		schedulerEngineProxyBean.setDestinationName(
			DestinationNames.SCHEDULER_ENGINE);
		schedulerEngineProxyBean.setSynchronousDestinationName(
			DestinationNames.SCHEDULER_ENGINE);
		schedulerEngineProxyBean.setSynchronousMessageSenderMode(
			SynchronousMessageSender.Mode.DIRECT);

		InvocationHandlerFactory invocationHandlerFactory =
			MessagingProxyInvocationHandler.getInvocationHandlerFactory();

		InvocationHandler invocationHandler =
			invocationHandlerFactory.createInvocationHandler(
				schedulerEngineProxyBean);

		Class<?> beanClass = schedulerEngineProxyBean.getClass();

		SchedulerEngine schedulerEngine =
			(SchedulerEngine)ProxyUtil.newProxyInstance(
				beanClass.getClassLoader(), beanClass.getInterfaces(),
				invocationHandler);

		Dictionary<String, Object> schedulerEngineDictionary =
			new HashMapDictionary<>();

		schedulerEngineDictionary.put(
			"scheduler.engine.proxy.bean", Boolean.TRUE);

		_schedulerEngineProxyBeanServiceRegistration =
			bundleContext.registerService(
				SchedulerEngine.class, schedulerEngine,
				schedulerEngineDictionary);
	}

	@Deactivate
	protected void deactivate() {
		if (_schedulerEngineProxyBeanServiceRegistration != null) {
			_schedulerEngineProxyBeanServiceRegistration.unregister();
		}
	}

	private ServiceRegistration<SchedulerEngine>
		_schedulerEngineProxyBeanServiceRegistration;

}