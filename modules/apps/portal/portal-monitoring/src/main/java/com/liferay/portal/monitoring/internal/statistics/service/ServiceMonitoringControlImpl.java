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

package com.liferay.portal.monitoring.internal.statistics.service;

import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;
import com.liferay.portal.monitoring.internal.aop.ServiceMonitorAdvice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	enabled = false, immediate = true, service = ServiceMonitoringControl.class
)
public class ServiceMonitoringControlImpl implements ServiceMonitoringControl {

	@Override
	public void addServiceClass(String className) {
		_serviceClasses.add(className);
	}

	@Override
	public void addServiceClassMethod(
		String className, String methodName, String[] parameterTypes) {

		MethodSignature methodSignature = new MethodSignature(
			className, methodName, parameterTypes);

		_serviceClassMethods.add(methodSignature);
	}

	@Override
	public Set<String> getServiceClasses() {
		return Collections.unmodifiableSet(_serviceClasses);
	}

	@Override
	public Set<MethodSignature> getServiceClassMethods() {
		return Collections.unmodifiableSet(_serviceClassMethods);
	}

	@Override
	public boolean isInclusiveMode() {
		return _inclusiveMode;
	}

	@Override
	public boolean isMonitorServiceRequest() {
		return _monitorServiceRequest;
	}

	@Override
	public void setInclusiveMode(boolean inclusiveMode) {
		_inclusiveMode = inclusiveMode;
	}

	@Override
	public void setMonitorServiceRequest(boolean monitorServiceRequest) {
		if (monitorServiceRequest == _monitorServiceRequest) {
			return;
		}

		synchronized (this) {
			if (monitorServiceRequest == _monitorServiceRequest) {
				return;
			}

			if (_serviceRegistration == null) {
				_serviceRegistration = _bundleContext.registerService(
					ChainableMethodAdvice.class,
					new ServiceMonitorAdvice(this, _dataSampleFactory), null);
			}
			else {
				_serviceRegistration.unregister();

				_serviceRegistration = null;
			}

			_monitorServiceRequest = monitorServiceRequest;
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected synchronized void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private DataSampleFactory _dataSampleFactory;

	private boolean _inclusiveMode = true;
	private volatile boolean _monitorServiceRequest;
	private final Set<String> _serviceClasses = new HashSet<>();
	private final Set<MethodSignature> _serviceClassMethods = new HashSet<>();
	private ServiceRegistration<ChainableMethodAdvice> _serviceRegistration;

}