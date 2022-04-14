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

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	property = {"osgi.command.function=dc", "osgi.command.scope=system"},
	service = DependencyClosureOSGiCommands.class
)
public class DependencyClosureOSGiCommands {

	public void dc(long bundleId, long... additionalBundleIds) {
		List<Bundle> bundles = new ArrayList<>();

		bundles.add(_bundleContext.getBundle(bundleId));

		for (long additionalBundleId : additionalBundleIds) {
			bundles.add(_bundleContext.getBundle(additionalBundleId));
		}

		System.out.println(_frameworkWiring.getDependencyClosure(bundles));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		Bundle systemBundle = bundleContext.getBundle(0);

		_frameworkWiring = systemBundle.adapt(FrameworkWiring.class);
	}

	private BundleContext _bundleContext;
	private FrameworkWiring _frameworkWiring;

}