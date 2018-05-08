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

package com.liferay.portal.security.audit.wiring.internal;

import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.proxy.MessagingProxyInvocationHandler;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.spring.aop.InvocationHandlerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.audit.AuditRouterProxyBean;

import java.lang.reflect.InvocationHandler;

import java.util.Dictionary;

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
	enabled = false, immediate = true,
	service = AuditRouterProxyBeanConfigurator.class
)
public class AuditRouterProxyBeanConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		AuditRouterProxyBean auditRouterProxyBean = new AuditRouterProxyBean();

		auditRouterProxyBean.setDestinationName(DestinationNames.AUDIT);
		auditRouterProxyBean.setSynchronousDestinationName(
			DestinationNames.AUDIT);
		auditRouterProxyBean.setSynchronousMessageSenderMode(
			SynchronousMessageSender.Mode.DIRECT);

		InvocationHandlerFactory invocationHandlerFactory =
			MessagingProxyInvocationHandler.getInvocationHandlerFactory();

		InvocationHandler invocationHandler =
			invocationHandlerFactory.createInvocationHandler(
				auditRouterProxyBean);

		Class<?> beanClass = auditRouterProxyBean.getClass();

		AuditRouter auditRouter = (AuditRouter)ProxyUtil.newProxyInstance(
			beanClass.getClassLoader(), beanClass.getInterfaces(),
			invocationHandler);

		Dictionary<String, Object> schedulerEngineDictionary =
			new HashMapDictionary<>();

		schedulerEngineDictionary.put("audit.router.proxy", Boolean.TRUE);

		_auditRouterSesrviceRegistration = bundleContext.registerService(
			AuditRouter.class, auditRouter, schedulerEngineDictionary);
	}

	@Deactivate
	protected void deactivate() {
		if (_auditRouterSesrviceRegistration != null) {
			_auditRouterSesrviceRegistration.unregister();
		}
	}

	@Reference(
		service = ProxyMessageListener.class,
		target = "(destination.name=" + DestinationNames.AUDIT + ")",
		unbind = "-"
	)
	protected void setProxyMessageListener(
		ProxyMessageListener proxyMessageListener) {
	}

	private ServiceRegistration<AuditRouter> _auditRouterSesrviceRegistration;

}