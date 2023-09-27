/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistry;
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
@Component(service = DDMFormFieldValueRendererRegistry.class)
public class DDMFormFieldValueRendererRegistryImpl
	implements DDMFormFieldValueRendererRegistry {

	@Override
	public DDMFormFieldValueRenderer getDDMFormFieldValueRenderer(
		String ddmFormFieldType) {

		return _serviceTrackerMap.getService(ddmFormFieldType);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
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
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private final DDMFormFieldValueRenderer[]
		_defaultDDMFormFieldValueRenderers = {
			new CheckboxDDMFormFieldValueRenderer(),
			new ColorDDMFormFieldValueRenderer(),
			new DateDDMFormFieldValueRenderer(),
			new DecimalDDMFormFieldValueRenderer(),
			new DocumentLibraryDDMFormFieldValueRenderer(),
			new GeolocationDDMFormFieldValueRenderer(),
			new ImageDDMFormFieldValueRenderer(),
			new IntegerDDMFormFieldValueRenderer(),
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