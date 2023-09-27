/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.missing.component.internal.osgi.commands;

import com.liferay.portal.osgi.debug.missing.component.internal.MissingComponentUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Matthew Tambara
 */
@Component(
	property = {
		"osgi.command.function=missingComponent", "osgi.command.scope=ds"
	},
	service = MissingComponentOSGiCommands.class
)
public class MissingComponentOSGiCommands {

	public void missingComponent() {
		System.out.println(
			MissingComponentUtil.scan(
				_bundleContext, _serviceComponentRuntime));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}