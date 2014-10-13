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

package com.liferay.portlet.dynamicdatamapping.render;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldRendererRegistryImpl
	implements DDMFormFieldRendererRegistry {

	public DDMFormFieldRendererRegistryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		Class<?> clazz = getClass();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + DDMFormFieldRenderer.class.getName() +
				")(!(objectClass=" + clazz.getName() + ")))");

		_serviceTracker = registry.trackServices(
			filter, new DDMFormFieldRendererServiceTrackerCustomizer());

		_serviceTracker.open();
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

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<DDMFormFieldRenderer> serviceRegistration =
			registry.registerService(
				DDMFormFieldRenderer.class, ddmFormFieldRenderer);

		_serviceRegistrations.put(ddmFormFieldRenderer, serviceRegistration);
	}

	private final Map<String, List<DDMFormFieldRenderer>>
		_ddmFormFieldRenderersMap =
			new HashMap<String, List<DDMFormFieldRenderer>>();
	private final ServiceRegistrationMap<DDMFormFieldRenderer>
		_serviceRegistrations =
			new ServiceRegistrationMap<DDMFormFieldRenderer>();
	private final ServiceTracker<DDMFormFieldRenderer, DDMFormFieldRenderer>
		_serviceTracker;

	private class DDMFormFieldRendererServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<DDMFormFieldRenderer, DDMFormFieldRenderer> {

		@Override
		public DDMFormFieldRenderer addingService(
			ServiceReference<DDMFormFieldRenderer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			DDMFormFieldRenderer ddmFormFieldRenderer = registry.getService(
				serviceReference);

			for (String supportedDDMFormFieldType :
					ddmFormFieldRenderer.getSupportedDDMFormFieldTypes()) {

				List<DDMFormFieldRenderer> ddmFormFieldRenderers =
					_ddmFormFieldRenderersMap.get(supportedDDMFormFieldType);

				if (ddmFormFieldRenderers == null) {
					ddmFormFieldRenderers =
						new ArrayList<DDMFormFieldRenderer>();

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

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

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