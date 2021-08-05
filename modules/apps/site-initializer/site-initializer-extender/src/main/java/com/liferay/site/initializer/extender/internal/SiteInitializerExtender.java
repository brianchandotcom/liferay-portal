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

package com.liferay.site.initializer.extender.internal;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = {})
public class SiteInitializerExtender
	implements BundleTrackerCustomizer<SiteInitializerExtension> {

	@Override
	public SiteInitializerExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		List<BundleCapability> bundleCapabilities =
			bundleWiring.getCapabilities("liferay.site.initializer");

		if (ListUtil.isEmpty(bundleCapabilities)) {
			return null;
		}

		SiteInitializerExtension siteInitializerExtension =
			new SiteInitializerExtension(bundle, _bundleContext);

		siteInitializerExtension.start();

		return siteInitializerExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		SiteInitializerExtension siteInitializerExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		SiteInitializerExtension siteInitializerExtension) {

		siteInitializerExtension.destroy();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, ~Bundle.INSTALLED & ~Bundle.UNINSTALLED, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

}