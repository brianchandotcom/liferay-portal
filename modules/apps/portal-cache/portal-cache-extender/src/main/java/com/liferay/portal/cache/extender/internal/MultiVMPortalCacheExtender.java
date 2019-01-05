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

package com.liferay.portal.cache.extender.internal;

import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.util.PropsKeys;

import java.io.Serializable;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
public class MultiVMPortalCacheExtender {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = PortalCacheExtenderUtil.createBundleTracker(
			bundleContext, _portalCacheManager,
			PropsKeys.EHCACHE_MULTI_VM_CONFIG_LOCATION,
			"/META-INF/module-multi-vm-clustered.xml");

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private BundleTracker<Void> _bundleTracker;

	@Reference(
		target = "(portal.cache.manager.name=" + PortalCacheManagerNames.MULTI_VM + ")"
	)
	private PortalCacheManager<? extends Serializable, ? extends Serializable>
		_portalCacheManager;

}