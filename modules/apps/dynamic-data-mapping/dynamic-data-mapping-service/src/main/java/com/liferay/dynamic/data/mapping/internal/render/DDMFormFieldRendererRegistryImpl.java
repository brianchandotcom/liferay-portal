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

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRendererRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(service = DDMFormFieldRendererRegistry.class)
public class DDMFormFieldRendererRegistryImpl
	implements DDMFormFieldRendererRegistry {

	@Override
	public DDMFormFieldRenderer getDDMFormFieldRenderer(
		String ddmFormFieldType) {

		DDMFormFieldRenderer ddmFormFieldRenderer =
			_serviceTrackerMap.getService(ddmFormFieldType);

		if (ddmFormFieldRenderer != null) {
			return ddmFormFieldRenderer;
		}

		Set<String> ddmFormFieldTypeNames =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeNames();

		if (ddmFormFieldTypeNames.contains(ddmFormFieldType)) {
			return _defaultDDMFormFieldRenderer;
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, DDMFormFieldRenderer.class, null,
			(serviceReference, emitter) -> {
				DDMFormFieldRenderer ddmFormFieldRenderer =
					_bundleContext.getService(serviceReference);

				try {
					for (String supportedDDMFormFieldType :
							ddmFormFieldRenderer.
								getSupportedDDMFormFieldTypes()) {

						emitter.emit(supportedDDMFormFieldType);
					}
				}
				finally {
					_bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private BundleContext _bundleContext;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference(target = "(ddm.form.field.renderer.type=freemarker)")
	private DDMFormFieldRenderer _defaultDDMFormFieldRenderer;

	private ServiceTrackerMap<String, DDMFormFieldRenderer> _serviceTrackerMap;

}