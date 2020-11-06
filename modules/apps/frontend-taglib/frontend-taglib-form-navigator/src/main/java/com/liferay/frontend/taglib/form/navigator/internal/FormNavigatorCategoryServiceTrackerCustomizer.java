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

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.frontend.taglib.form.navigator.internal.servlet.taglib.ui.WrapperFormNavigatorCategory;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Eudaldo Alonso
 */
public class FormNavigatorCategoryServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory,
		 ServiceRegistration<FormNavigatorCategory>> {

	public FormNavigatorCategoryServiceTrackerCustomizer(
		BundleContext bundleContext) {

		_bundleContext = bundleContext;
	}

	@Override
	public ServiceRegistration<FormNavigatorCategory> addingService(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory>
				serviceReference) {

		return _bundleContext.registerService(
			FormNavigatorCategory.class,
			new WrapperFormNavigatorCategory(
				_bundleContext.getService(serviceReference)),
			_buildProperties(serviceReference));
	}

	@Override
	public void modifiedService(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory>
				serviceReference,
		ServiceRegistration<FormNavigatorCategory> serviceRegistration) {

		serviceRegistration.setProperties(_buildProperties(serviceReference));
	}

	@Override
	public void removedService(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory>
				serviceReference,
		ServiceRegistration<FormNavigatorCategory> serviceRegistration) {

		serviceRegistration.unregister();
	}

	private Dictionary<String, Object> _buildProperties(
		ServiceReference
			<com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory>
				serviceReference) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"form.navigator.category.order",
			serviceReference.getProperty("form.navigator.category.order"));

		return properties;
	}

	private final BundleContext _bundleContext;

}