/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.osgi;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author Raymond Augé
 */
public class ServiceListener
	extends BaseListener implements org.osgi.framework.ServiceListener {

	public ServiceListener(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void serviceChanged(ServiceEvent serviceEvent) {
		try {
			int type = serviceEvent.getType();

			if (type == ServiceEvent.MODIFIED) {
				serviceEventModified(serviceEvent);
			}
			else if (type == ServiceEvent.REGISTERED) {
				serviceEventRegistered(serviceEvent);
			}
			else if (type == ServiceEvent.UNREGISTERING) {
				serviceEventUnregistering(serviceEvent);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getLogMessage(
		String state, ServiceReference<?> serviceReference) {

		String message = StringUtil.merge(
			(String[])serviceReference.getProperty(Constants.OBJECTCLASS));

		return getLogMessage(state, message);
	}

	protected void registerServiceWrapper(ServiceReference<?> serviceReference)
		throws Exception {

		Bundle bundle = serviceReference.getBundle();

		String serviceType = (String)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_TYPE);

		Class<?> serviceTypeClass = bundle.loadClass(serviceType);

		Object nextService = _bundleContext.getService(serviceReference);

		Class<?> serviceImplClass = nextService.getClass();

		Method serviceImplWrapperMethod = null;

		try {
			serviceImplWrapperMethod = serviceImplClass.getMethod(
				"setWrapped".concat(serviceTypeClass.getSimpleName()),
				serviceTypeClass);
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException(
				"Implementation must extend " + serviceTypeClass.getName() +
					"Wrapper",
				nsme);
		}

		Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

		if (!(serviceProxy instanceof Advised)) {
			throw new IllegalArgumentException(
				"Service must be an instance of Advised");
		}

		if (!ProxyUtil.isProxyClass(serviceProxy.getClass())) {
			return;
		}

		AdvisedSupport advisedSupport = ServiceWrapperUtil.getAdvisedSupport(
			serviceProxy);

		Object previousService = ServiceWrapperUtil.getTarget(advisedSupport);

		serviceImplWrapperMethod.invoke(nextService, previousService);

		ClassLoader classLoader = serviceTypeClass.getClassLoader();

		Object nextTarget = ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {serviceTypeClass},
			new ClassLoaderBeanHandler(nextService, classLoader));

		TargetSource nextTargetSource = new SingletonTargetSource(nextTarget);

		advisedSupport.setTargetSource(nextTargetSource);

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(Constants.SERVICE_RANKING, -1);

		properties.put(OSGiConstants.PORTAL_SERVICE, Boolean.TRUE);
		properties.put(OSGiConstants.PORTAL_SERVICE_PREVIOUS, Boolean.TRUE);

		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				serviceType, previousService, properties);

		_serviceRegistrations.put(
			serviceRegistration.getReference(), serviceRegistration);
	}

	protected void serviceEventModified(ServiceEvent serviceEvent)
		throws Exception {

		ServiceReference<?> serviceReference =
			serviceEvent.getServiceReference();

		if (_log.isInfoEnabled()) {
			_log.info(getLogMessage("[MODIFIED]", serviceReference));
		}
	}

	protected void serviceEventRegistered(ServiceEvent serviceEvent)
		throws Exception {

		ServiceReference<?> serviceReference =
			serviceEvent.getServiceReference();

		if (_log.isInfoEnabled()) {
			_log.info(getLogMessage("[REGISTERED]", serviceReference));
		}

		Boolean portalServiceWrapper = (Boolean)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_WRAPPER);

		if ((portalServiceWrapper != null) && portalServiceWrapper) {
			registerServiceWrapper(serviceReference);
		}
	}

	protected void serviceEventUnregistering(ServiceEvent serviceEvent)
		throws Exception {

		ServiceReference<?> serviceReference =
			serviceEvent.getServiceReference();

		if (_log.isInfoEnabled()) {
			_log.info(getLogMessage("[UNREGISTERING]", serviceReference));
		}

		Boolean portalServiceWrapper = (Boolean)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_WRAPPER);

		if ((portalServiceWrapper != null) && portalServiceWrapper) {
			unregisterServiceWrapper(serviceReference);
		}
	}
	protected void unregisterServiceWrapper(
			ServiceReference<?> serviceReference)
		throws Exception {

		String serviceType = (String)serviceReference.getProperty(
			OSGiConstants.PORTAL_SERVICE_TYPE);

		ServiceReference<?>[] previousServiceReferences =
			_bundleContext.getServiceReferences(
				serviceType, _PREVIOUS_SERVICE_FILTER);

		if ((previousServiceReferences == null) ||
			(previousServiceReferences.length == 0)) {

			return;
		}

		ServiceReference<?> previousServiceReference =
			previousServiceReferences[0];

		Object previousService = _bundleContext.getService(
			previousServiceReference);

		Object serviceProxy = PortalBeanLocatorUtil.locate(serviceType);

		AdvisedSupport advisedSupport = ServiceWrapperUtil.getAdvisedSupport(
			serviceProxy);

		TargetSource previousTargetSource = new SingletonTargetSource(
			previousService);

		advisedSupport.setTargetSource(previousTargetSource);

		ServiceRegistration<?> serviceRegistration = _serviceRegistrations.get(
			previousServiceReference);

		serviceRegistration.unregister();

		_serviceRegistrations.remove(previousServiceReference);
	}

	private static final String _PREVIOUS_SERVICE_FILTER =
		"(&(portal.service=*)(portal.service.previous=*)(service.ranking=-1))";

	private static Log _log = LogFactoryUtil.getLog(ServiceListener.class);

	private BundleContext _bundleContext;
	private Map<ServiceReference<?>, ServiceRegistration<?>>
		_serviceRegistrations =
			new HashMap<ServiceReference<?>, ServiceRegistration<?>>();

}