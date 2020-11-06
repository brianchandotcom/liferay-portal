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

package com.liferay.frontend.taglib.form.navigator.internal;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.internal.servlet.taglib.ui.WrapperFormNavigatorEntry;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Eudaldo Alonso
 */
public class FormNavigatorEntryServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<?>,
		 ServiceRegistration<FormNavigatorEntry<?>>> {

	public FormNavigatorEntryServiceTrackerCustomizer(
		BundleContext bundleContext) {

		_bundleContext = bundleContext;
	}

	@Override
	public ServiceRegistration<FormNavigatorEntry<?>> addingService(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<?>>
				serviceReference) {

		return _bundleContext.registerService(
			(Class<FormNavigatorEntry<?>>)(Class<?>)FormNavigatorEntry.class,
			new WrapperFormNavigatorEntry(
				_bundleContext.getService(serviceReference)),
			_buildProperties(serviceReference));
	}

	@Override
	public void modifiedService(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<?>>
				serviceReference,
		ServiceRegistration<FormNavigatorEntry<?>> serviceRegistration) {

		serviceRegistration.setProperties(_buildProperties(serviceReference));
	}

	@Override
	public void removedService(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<?>>
				serviceReference,
		ServiceRegistration<FormNavigatorEntry<?>> serviceRegistration) {

		serviceRegistration.unregister();
	}

	private Dictionary<String, Object> _buildProperties(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<?>>
				serviceReference) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"form.navigator.entry.order",
			serviceReference.getProperty("form.navigator.entry.order"));

		return properties;
	}

	private final BundleContext _bundleContext;

}