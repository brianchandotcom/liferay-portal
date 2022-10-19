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

package com.liferay.portal.vulcan.internal.extension;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ExtensionProviderRegistry.class)
public class ExtensionProviderRegistryImpl
	implements ExtensionProviderRegistry {

	@Override
	public List<ExtensionProvider> getExtensionProviders(
		long companyId, String className) {

		return TransformUtil.transformToList(
			_serviceTracker.getServices(new ExtensionProvider[0]),
			extensionProvider -> {
				if (extensionProvider.isApplicableExtension(
						companyId, className)) {

					return extensionProvider;
				}

				return null;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, ExtensionProvider.class, null);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<ExtensionProvider, ExtensionProvider>
		_serviceTracker;

}