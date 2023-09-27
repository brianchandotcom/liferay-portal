/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.declarative.service.internal;

import com.liferay.portal.osgi.debug.SystemChecker;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Shuyang Zhou
 */
@Component(service = SystemChecker.class)
public class DeclarativeServiceSoftCircularDependencySystemChecker
	implements SystemChecker {

	@Override
	public String check() {
		return SoftCircularDependencyUtil.listSoftCircularDependencies(
			_serviceComponentRuntime, _bundleContext);
	}

	@Override
	public String getName() {
		return "Declarative Service Soft Circular Dependency Checker";
	}

	@Override
	public String getOSGiCommand() {
		return "ds:softCircularDependency";
	}

	@Override
	public String toString() {
		return getName();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}