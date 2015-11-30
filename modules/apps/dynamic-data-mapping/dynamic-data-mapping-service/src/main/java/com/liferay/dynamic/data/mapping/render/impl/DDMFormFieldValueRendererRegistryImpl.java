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

import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistry;
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
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValueRendererRegistryImpl
	implements DDMFormFieldValueRendererRegistry {

	public DDMFormFieldValueRendererRegistryImpl() {
		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		_bundleContext = bundle.getBundleContext();

		try {
			_serviceTracker = ServiceTrackerFactory.open(
				_bundleContext,
				"(&(objectClass=" + DDMFormFieldValueRenderer.class.getName() +
					")(!(objectClass=" + clazz.getName() + ")))",
				new DDMFormFieldValueRendererServiceTrackerCustomizer());
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}
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

		for (DDMFormFieldValueRenderer ddmFormFieldValueRenderer :
				ddmFormFieldValueRenderers) {

			_bundleContext.registerService(
				DDMFormFieldValueRenderer.class, ddmFormFieldValueRenderer,
				null);
		}
	}

	private final BundleContext _bundleContext;
	private final Map<String, List<DDMFormFieldValueRenderer>>
		_ddmFormFieldValueRenderersMap = new ConcurrentHashMap<>();
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

			DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
				_bundleContext.getService(serviceReference);

			String supportedDDMFormFieldType =
				ddmFormFieldValueRenderer.getSupportedDDMFormFieldType();

			List<DDMFormFieldValueRenderer> ddmFormFieldValueRenderers =
				_ddmFormFieldValueRenderersMap.get(supportedDDMFormFieldType);

			if (ddmFormFieldValueRenderers == null) {
				ddmFormFieldValueRenderers = new ArrayList<>();

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

			_bundleContext.ungetService(serviceReference);

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