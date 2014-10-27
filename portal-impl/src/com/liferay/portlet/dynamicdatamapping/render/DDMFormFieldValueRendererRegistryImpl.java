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
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValueRendererRegistryImpl
	implements DDMFormFieldValueRendererRegistry {

	public DDMFormFieldValueRendererRegistryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		Class<?> clazz = getClass();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + DDMFormFieldValueRenderer.class.getName() +
				")(!(objectClass=" + clazz.getName() + ")))");

		_serviceTracker = registry.trackServices(
			filter, new DDMFormFieldValueRendererServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Override
	public DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(
		String ddmFormFieldType) {

		List<DDMFormFieldValueRenderer> ddmFormFieldValueRenderers =
			_ddmFormFieldValueRenderersMap.get(ddmFormFieldType);

		if ((ddmFormFieldValueRenderers == null) ||
			ddmFormFieldValueRenderers.isEmpty()) {

			return null;
		}

		return ddmFormFieldValueRenderers.get(
			ddmFormFieldValueRenderers.size() - 1);
	}

	public void setDefaultDDMFormFieldValueRenderers(
		List<DDMFormFieldValueRenderer> ddmFormFieldValueRenderers) {

		Registry registry = RegistryUtil.getRegistry();

		for (DDMFormFieldValueRenderer ddmFormFieldValueRenderer :
				ddmFormFieldValueRenderers) {

			registry.registerService(
				DDMFormFieldValueRenderer.class, ddmFormFieldValueRenderer);
		}
	}

	private final Map<String, List<DDMFormFieldValueRenderer>>
		_ddmFormFieldValueRenderersMap =
			new HashMap<String, List<DDMFormFieldValueRenderer>>();
	private final
		ServiceTracker<DDMFormFieldValueRenderer, DDMFormFieldValueRenderer>
			_serviceTracker;

	private class DDMFormFieldValueRendererServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<DDMFormFieldValueRenderer, DDMFormFieldValueRenderer> {

		@Override
		public DDMFormFieldValueRenderer addingService(
			ServiceReference<DDMFormFieldValueRenderer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
				registry.getService(serviceReference);

			String supportedDDMFormFieldType =
				ddmFormFieldValueRenderer.getSupportedDDMFormFieldType();

			List<DDMFormFieldValueRenderer> ddmFormFieldValueRenderers =
				_ddmFormFieldValueRenderersMap.get(supportedDDMFormFieldType);

			if (ddmFormFieldValueRenderers == null) {
				ddmFormFieldValueRenderers =
					new ArrayList<DDMFormFieldValueRenderer>();

				_ddmFormFieldValueRenderersMap.put(
					supportedDDMFormFieldType, ddmFormFieldValueRenderers);
			}

			ddmFormFieldValueRenderers.add(ddmFormFieldValueRenderer);

			return ddmFormFieldValueRenderer;
		}

		@Override
		public void modifiedService(
			ServiceReference<DDMFormFieldValueRenderer> serviceReference,
			DDMFormFieldValueRenderer ddmFormFieldValueRenderer) {
		}

		@Override
		public void removedService(
			ServiceReference<DDMFormFieldValueRenderer> serviceReference,
			DDMFormFieldValueRenderer ddmFormFieldValueRenderer) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String supportedDDMFormFieldType =
				ddmFormFieldValueRenderer.getSupportedDDMFormFieldType();

			List<DDMFormFieldValueRenderer> ddmFormFieldValueRenderers =
				_ddmFormFieldValueRenderersMap.get(supportedDDMFormFieldType);

			if (ddmFormFieldValueRenderers == null) {
				return;
			}

			ddmFormFieldValueRenderers.remove(ddmFormFieldValueRenderer);
		}

	}

}