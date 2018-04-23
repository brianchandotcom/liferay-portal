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

package com.liferay.portal.osgi.debug.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.debug.ComponentScanner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = ComponentScannerTracker.class)
public class ComponentScannerTracker
	implements ServiceTrackerCustomizer<ComponentScanner, ComponentScanner> {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, ComponentScanner.class, this);
	}

	@Override
	public ComponentScanner addingService(
		ServiceReference<ComponentScanner> serviceReference) {

		ComponentScanner componentScanner = _bundleContext.getService(
			serviceReference);

		StringBundler sb = new StringBundler(4);

		sb.append(componentScanner.getName());
		sb.append(" is ready for use. You can run \"");
		sb.append(componentScanner.getOSGiCommand());
		sb.append("\" in gogoshell to get scan result.");

		if (_log.isInfoEnabled()) {
			_log.info(sb.toString());
		}

		String scanResult = componentScanner.scan();

		if (Validator.isNull(scanResult)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					componentScanner.getName() +
						" scan result: All components are satisfied.");
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					componentScanner.getName() + " scan result: " + scanResult);
			}
		}

		return componentScanner;
	}

	@Override
	public void modifiedService(
		ServiceReference<ComponentScanner> serviceReference,
		ComponentScanner componentScanner) {
	}

	@Override
	public void removedService(
		ServiceReference<ComponentScanner> serviceReference,
		ComponentScanner componentScanner) {

		_bundleContext.ungetService(serviceReference);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference(
		target = ModuleServiceLifecycle.COMPONENT_SCAN_READY, unbind = "-"
	)
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ComponentScannerTracker.class);

	private BundleContext _bundleContext;
	private ServiceTracker<ComponentScanner, ComponentScanner> _serviceTracker;

}