/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.declarative.service.internal.osgi.commands;

import com.liferay.portal.osgi.debug.declarative.service.internal.UnsatisfiedComponentUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Tina Tian
 */
@Component(
	property = {"osgi.command.function=unsatisfied", "osgi.command.scope=ds"},
	service = UnsatisfiedComponentOSGiCommands.class
)
public class UnsatisfiedComponentOSGiCommands {

	public void unsatisfied() {
		System.out.println(
			UnsatisfiedComponentUtil.listUnsatisfiedComponents(
				_serviceComponentRuntime, _bundleContext.getBundles()));
	}

	public void unsatisfied(long bundleId) {
		System.out.println(
			UnsatisfiedComponentUtil.listUnsatisfiedComponents(
				_serviceComponentRuntime, _bundleContext.getBundle(bundleId)));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}