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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.debug.SystemChecker;

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
@Component(immediate = true)
public class SystemCheckerTracker
	implements ServiceTrackerCustomizer<SystemChecker, SystemChecker> {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			_bundleContext, SystemChecker.class, this);

		_serviceTracker.open();
	}

	@Override
	public SystemChecker addingService(
		ServiceReference<SystemChecker> serviceReference) {

		SystemChecker systemChecker = _bundleContext.getService(
			serviceReference);

		StringBundler sb = new StringBundler(4);

		sb.append(systemChecker.getName());
		sb.append(" is ready for use. You can run \"");
		sb.append(systemChecker.getOSGiCommand());
		sb.append("\" in gogoshell to check result.");

		if (_log.isInfoEnabled()) {
			_log.info(sb.toString());
		}

		String result = systemChecker.check();

		if (Validator.isNull(result)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					systemChecker.getName() +
						" check result: No issue is found.");
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(systemChecker.getName() + " check result: " + result);
			}
		}

		return systemChecker;
	}

	@Override
	public void modifiedService(
		ServiceReference<SystemChecker> serviceReference,
		SystemChecker systemChecker) {
	}

	@Override
	public void removedService(
		ServiceReference<SystemChecker> serviceReference,
		SystemChecker systemChecker) {

		_bundleContext.ungetService(serviceReference);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemCheckerTracker.class);

	private BundleContext _bundleContext;
	private ServiceTracker<SystemChecker, SystemChecker> _serviceTracker;

}