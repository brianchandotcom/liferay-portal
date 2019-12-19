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
	immediate = true,
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