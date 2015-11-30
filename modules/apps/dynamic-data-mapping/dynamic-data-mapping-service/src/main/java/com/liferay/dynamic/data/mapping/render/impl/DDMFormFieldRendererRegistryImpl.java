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

package com.liferay.dynamic.data.mapping.render.impl;

import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRendererRegistry;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldRendererRegistryImpl
	implements DDMFormFieldRendererRegistry {

	public DDMFormFieldRendererRegistryImpl() {
		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		_bundleContext = bundle.getBundleContext();

		try {
			_serviceTracker = ServiceTrackerFactory.open(
				_bundleContext,
				"(&(objectClass=" + DDMFormFieldRenderer.class.getName() +
					")(!(objectClass=" + clazz.getName() + ")))",
				new DDMFormFieldRendererServiceTrackerCustomizer());
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}
	}

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer(
		String ddmFormFieldType) {

		List<DDMFormFieldRenderer> ddmFormFieldRenders =
			_ddmFormFieldRenderersMap.get(ddmFormFieldType);

		if ((ddmFormFieldRenders == null) || ddmFormFieldRenders.isEmpty()) {
			return null;
		}

		return ddmFormFieldRenders.get(ddmFormFieldRenders.size() - 1);
	}

	public void setDefaultDDMFormFieldRenderer(
		DDMFormFieldRenderer ddmFormFieldRenderer) {

		ServiceRegistration<DDMFormFieldRenderer> serviceRegistration =
			_bundleContext.registerService(
				DDMFormFieldRenderer.class, ddmFormFieldRenderer, null);

		_serviceRegistrations.put(ddmFormFieldRenderer, serviceRegistration);
	}

	private final BundleContext _bundleContext;
	private final Map<String, List<DDMFormFieldRenderer>>
		_ddmFormFieldRenderersMap = new ConcurrentHashMap<>();
	private final
		Map<DDMFormFieldRenderer, ServiceRegistration<DDMFormFieldRenderer>>
			_serviceRegistrations = new ConcurrentHashMap<>();
	private final ServiceTracker<DDMFormFieldRenderer, DDMFormFieldRenderer>
		_serviceTracker;

	private class DDMFormFieldRendererServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<DDMFormFieldRenderer, DDMFormFieldRenderer> {

		@Override
		public DDMFormFieldRenderer addingService(
			ServiceReference<DDMFormFieldRenderer> serviceReference) {

			DDMFormFieldRenderer ddmFormFieldRenderer =
				_bundleContext.getService(serviceReference);

			for (String supportedDDMFormFieldType :
					ddmFormFieldRenderer.getSupportedDDMFormFieldTypes()) {

				List<DDMFormFieldRenderer> ddmFormFieldRenderers =
					_ddmFormFieldRenderersMap.get(supportedDDMFormFieldType);

				if (ddmFormFieldRenderers == null) {
					ddmFormFieldRenderers = new ArrayList<>();

					_ddmFormFieldRenderersMap.put(
						supportedDDMFormFieldType, ddmFormFieldRenderers);
				}

				ddmFormFieldRenderers.add(ddmFormFieldRenderer);
			}

			return ddmFormFieldRenderer;
		}

		@Override
		public void modifiedService(
			ServiceReference<DDMFormFieldRenderer> serviceReference,
			DDMFormFieldRenderer ddmFormFieldRenderer) {
		}

		@Override
		public void removedService(
			ServiceReference<DDMFormFieldRenderer> serviceReference,
			DDMFormFieldRenderer ddmFormFieldRenderer) {

			_bundleContext.ungetService(serviceReference);

			for (String supportedDDMFormFieldType :
					ddmFormFieldRenderer.getSupportedDDMFormFieldTypes()) {

				List<DDMFormFieldRenderer> ddmFormFieldRenderers =
					_ddmFormFieldRenderersMap.get(supportedDDMFormFieldType);

				if (ddmFormFieldRenderers == null) {
					return;
				}

				ddmFormFieldRenderers.remove(ddmFormFieldRenderer);
			}
		}

	}

}