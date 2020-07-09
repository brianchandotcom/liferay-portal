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

package com.liferay.frontend.theme.token.definition.internal;

import com.liferay.frontend.theme.token.definition.ThemeTokenDefinitions;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ThemeTokenDefinitions.class)
public class ThemeTokenDefinitionsImpl implements ThemeTokenDefinitions {

	@Override
	public String getTokenDefinitionString(String themeId) {
		AtomicReference<String> atomicReference = _serviceTrackerMap.getService(
			themeId);

		if (atomicReference == null) {
			return null;
		}

		return atomicReference.get();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ServletContext.class, "osgi.web.symbolicname",
			new ServiceTrackerCustomizer
				<ServletContext, AtomicReference<String>>() {

				@Override
				public AtomicReference<String> addingService(
					ServiceReference<ServletContext> serviceReference) {

					AtomicReference<String> atomicReference =
						new AtomicReference<>();

					_reloadTokens(serviceReference, atomicReference);

					return atomicReference;
				}

				@Override
				public void modifiedService(
					ServiceReference<ServletContext> serviceReference,
					AtomicReference<String> atomicReference) {

					_reloadTokens(serviceReference, atomicReference);
				}

				@Override
				public void removedService(
					ServiceReference<ServletContext> serviceReference,
					AtomicReference<String> atomicReference) {

					atomicReference.set(null);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _reloadTokens(
		ServiceReference<ServletContext> serviceReference,
		AtomicReference<String> atomicReference) {

		Bundle bundle = serviceReference.getBundle();

		if (!ThemeBundleInspector.isTheme(bundle)) {
			atomicReference.set(null);

			return;
		}

		ThemeBundleInspector themeBundleInspector = new ThemeBundleInspector(
			bundle);

		atomicReference.set(themeBundleInspector.getTokenDefinition());
	}

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, AtomicReference<String>>
		_serviceTrackerMap;

}