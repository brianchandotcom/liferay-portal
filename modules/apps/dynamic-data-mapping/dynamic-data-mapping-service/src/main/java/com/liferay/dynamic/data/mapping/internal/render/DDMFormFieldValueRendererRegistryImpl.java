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

import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistry;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistryUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormFieldValueRendererRegistry.class)
public class DDMFormFieldValueRendererRegistryImpl
	implements DDMFormFieldValueRendererRegistry {

	@Activate
	public void activate(BundleContext bundleContext) {
		for (int i = 0; i < _defaultDDMFormFieldValueRenderers.length; i++) {
			_serviceRegistrations[i] = bundleContext.registerService(
				DDMFormFieldValueRenderer.class,
				_defaultDDMFormFieldValueRenderers[i], null);
		}

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMFormFieldValueRenderer.class, null,
			(serviceReference, emitter) -> {
				DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
					bundleContext.getService(serviceReference);

				try {
					emitter.emit(
						ddmFormFieldValueRenderer.
							getSupportedDDMFormFieldType());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});

		_ddmFormFieldValueRendererRegistryUtil.
			setDDMFormFieldValueRendererRegistry(this);
	}

	@Deactivate
	public void deactivate() {
		_ddmFormFieldValueRendererRegistryUtil.
			setDDMFormFieldValueRendererRegistry(null);

		_serviceTrackerMap.close();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	public DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(
		String ddmFormFieldType) {

		return _serviceTrackerMap.getService(ddmFormFieldType);
	}

	private final DDMFormFieldValueRendererRegistryUtil
		_ddmFormFieldValueRendererRegistryUtil =
			new DDMFormFieldValueRendererRegistryUtil();
	private final DDMFormFieldValueRenderer[]
		_defaultDDMFormFieldValueRenderers = {
			new CheckboxDDMFormFieldValueRenderer(),
			new ColorDDMFormFieldValueRenderer(),
			new DateDDMFormFieldValueRenderer(),
			new DecimalDDMFormFieldValueRenderer(),
			new DocumentLibraryDDMFormFieldValueRenderer(),
			new GeolocationDDMFormFieldValueRenderer(),
			new IntegerDDMFormFieldValueRenderer(),
			new JournalArticleDDMFormFieldValueRenderer(),
			new LinkToPageDDMFormFieldValueRenderer(),
			new NumberDDMFormFieldValueRenderer(),
			new RadioDDMFormFieldValueRenderer(),
			new SelectDDMFormFieldValueRenderer(),
			new TextAreaDDMFormFieldValueRenderer(),
			new TextDDMFormFieldValueRenderer(),
			new TextHTMLDDMFormFieldValueRenderer()
		};
	private final ServiceRegistration<?>[] _serviceRegistrations =
		new ServiceRegistration[_defaultDDMFormFieldValueRenderers.length];
	private ServiceTrackerMap<String, DDMFormFieldValueRenderer>
		_serviceTrackerMap;

}