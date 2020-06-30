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

package com.liferay.frontend.js.loader.modules.extender.internal.resolution;

import com.liferay.frontend.js.loader.modules.extender.npm.JSBundle;
import com.liferay.frontend.js.loader.modules.extender.npm.JSBundleTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = JSBundleTracker.class)
public class BrowserModuleNameMapperCacheInvalidator
	implements JSBundleTracker {

	@Override
	public void addedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

		BrowserModuleNameMapper browserModuleNameMapper =
			_serviceTracker.getService();

		if (browserModuleNameMapper != null) {
			browserModuleNameMapper.clearCache();
		}
	}

	@Override
	public void removedJSBundle(
		JSBundle jsBundle, Bundle bundle, NPMRegistry npmRegistry) {

		BrowserModuleNameMapper browserModuleNameMapper =
			_serviceTracker.getService();

		if (browserModuleNameMapper != null) {
			browserModuleNameMapper.clearCache();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, BrowserModuleNameMapper.class, null);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<BrowserModuleNameMapper, BrowserModuleNameMapper>
		_serviceTracker;

}