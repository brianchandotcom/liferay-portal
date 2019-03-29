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

package com.liferay.headless.web.experience.internal.dto.v1_0.converter;

import com.liferay.headless.common.spi.dto.converter.DTOConverter;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(service = DTOConverterRegistry.class)
public class DTOConverterRegistry {

	public DTOConverter getDTOConverter(String className, String version) {
		List<DTOConverterWrapper> dtoConverterWrappers =
			_serviceTrackerMap.getService(className);

		if (ListUtil.isEmpty(dtoConverterWrappers)) {
			return null;
		}

		return dtoConverterWrappers.stream(
		).filter(
			wrapper -> version.equals(wrapper.getVersion())
		).findFirst(
		).map(
			DTOConverterWrapper::getDtoConverter
		).orElse(
			null
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, DTOConverter.class, "(dto.converter.class.name=*)",
			new DTOConverterServiceReferenceMapper(),
			new ServiceTrackerCustomizer<DTOConverter, DTOConverterWrapper>() {

				@Override
				public DTOConverterWrapper addingService(
					ServiceReference<DTOConverter> serviceReference) {

					String version = (String)serviceReference.getProperty(
						"dto.converter.api.version");

					DTOConverter dtoConverter = bundleContext.getService(
						serviceReference);

					return new DTOConverterWrapper(dtoConverter, version);
				}

				@Override
				public void modifiedService(
					ServiceReference<DTOConverter> serviceReference,
					DTOConverterWrapper dtoConverterWrapper) {
				}

				@Override
				public void removedService(
					ServiceReference<DTOConverter> serviceReference,
					DTOConverterWrapper dtoConverterWrapper) {

					bundleContext.ungetService(serviceReference);
				}

			},
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator(
					"dto.converter.api.version")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<DTOConverterWrapper>>
		_serviceTrackerMap;

	private static class DTOConverterServiceReferenceMapper
		implements ServiceReferenceMapper<String, DTOConverter> {

		@Override
		public void map(
			ServiceReference<DTOConverter> serviceReference,
			Emitter<String> emitter) {

			String className = (String)serviceReference.getProperty(
				"dto.converter.class.name");

			emitter.emit(className);
		}

	}

	private static class DTOConverterWrapper {

		public DTOConverterWrapper(DTOConverter dtoConverter, String version) {
			_dtoConverter = dtoConverter;
			_version = version;
		}

		public DTOConverter getDtoConverter() {
			return _dtoConverter;
		}

		public String getVersion() {
			return _version;
		}

		private final DTOConverter _dtoConverter;
		private final String _version;

	}

}