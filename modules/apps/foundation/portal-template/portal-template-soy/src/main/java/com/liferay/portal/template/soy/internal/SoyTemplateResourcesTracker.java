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

import com.liferay.portal.kernel.template.TemplateResource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Bruno Basto
 */
public class SoyTemplateResourcesTracker {

	public static void close() {
		_bundleTracker.close();
	}

	public static List<TemplateResource> getAllTemplateResources() {
		return _templateResources;
	}

	public static Bundle getBundle(long bundleId) {
		return _bundleMap.get(bundleId);
	}

	public static Collection<Bundle> getBundles() {
		return _bundleMap.values();
	}

	public static void open(BundleContext bundleContext) {
		int stateMask = Bundle.ACTIVE | Bundle.RESOLVED;

		_bundleTracker = new BundleTracker<>(
			bundleContext, stateMask,
			new SoyCapabilityBundleTrackerCustomizer(
				"soy", _bundleMap, _templateResources));

		_bundleTracker.open();
	}

	private static final Map<Long, Bundle> _bundleMap =
		new ConcurrentHashMap<>();
	private static BundleTracker<List<BundleCapability>> _bundleTracker;
	private static final List<TemplateResource> _templateResources =
		new CopyOnWriteArrayList<>();

}