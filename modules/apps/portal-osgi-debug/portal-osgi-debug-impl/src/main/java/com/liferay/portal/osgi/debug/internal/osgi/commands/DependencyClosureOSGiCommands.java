/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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