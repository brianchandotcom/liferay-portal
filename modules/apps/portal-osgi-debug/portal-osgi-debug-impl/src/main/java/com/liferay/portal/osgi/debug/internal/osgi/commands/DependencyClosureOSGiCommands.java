/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.debug.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = {
		"osgi.command.function=dc", "osgi.command.function=idc",
		"osgi.command.scope=system"
	},
	service = OSGiCommands.class
)
public class DependencyClosureOSGiCommands implements OSGiCommands {

	public void dc(long bundleId, long... additionalBundleIds) {
		List<Bundle> bundles = new ArrayList<>();

		bundles.add(_bundleContext.getBundle(bundleId));

		for (long additionalBundleId : additionalBundleIds) {
			bundles.add(_bundleContext.getBundle(additionalBundleId));
		}

		System.out.println(_frameworkWiring.getDependencyClosure(bundles));
	}

	public void idc(
		boolean runtime, long bundleId, long... additionalBundleIds) {

		Queue<Bundle> queue = new LinkedList<>();

		queue.add(_bundleContext.getBundle(bundleId));

		for (long additionalBundleId : additionalBundleIds) {
			queue.add(_bundleContext.getBundle(additionalBundleId));
		}

		Set<Bundle> invertDependencyClosureBundles = Collections.newSetFromMap(
			new TreeMap<>());

		Bundle currentBundle = null;

		while ((currentBundle = queue.poll()) != null) {
			BundleWiring bundleWiring = currentBundle.adapt(BundleWiring.class);

			for (BundleWire bundleWire : bundleWiring.getRequiredWires(null)) {
				BundleWiring providerBundleWiring =
					bundleWire.getProviderWiring();

				Bundle providerBundle = providerBundleWiring.getBundle();

				if (invertDependencyClosureBundles.add(providerBundle)) {
					queue.add(providerBundle);
				}
			}
		}

		if (runtime) {
			invertDependencyClosureBundles.addAll(
				_frameworkWiring.getDependencyClosure(
					invertDependencyClosureBundles));
		}

		System.out.println(invertDependencyClosureBundles);
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