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

package com.liferay.portal.template.soy.internal;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Bruno Basto
 */
public class SoyCapabilityBundleTrackerCustomizer
	implements BundleTrackerCustomizer<List<BundleCapability>> {

	public SoyCapabilityBundleTrackerCustomizer(
		String namespace, Map<Long, Bundle> bundleMap) {

		_bundleMap = bundleMap;
	}

	@Override
	public List<BundleCapability> addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("soy");

		_bundleMap.put(bundle.getBundleId(), bundle);

		return bundleCapabilities;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<BundleCapability> bundleCapabilities) {

		removedBundle(bundle, bundleEvent, bundleCapabilities);

		List<BundleCapability> newBundleCapabilities = addingBundle(
			bundle, bundleEvent);

		bundleCapabilities.clear();

		bundleCapabilities.addAll(newBundleCapabilities);
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		List<BundleCapability> bundleCapabilities) {

		_bundleMap.remove(bundle.getBundleId());
	}

	private final Map<Long, Bundle> _bundleMap;

}