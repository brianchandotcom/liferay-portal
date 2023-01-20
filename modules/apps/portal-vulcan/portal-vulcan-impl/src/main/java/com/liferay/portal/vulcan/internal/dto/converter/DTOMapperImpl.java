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

package com.liferay.portal.vulcan.internal.dto.converter;

import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Javier de Arcos
 */
@Component(service = DTOMapper.class)
public class DTOMapperImpl implements DTOMapper {

	@Override
	public String toInternalDTOClassName(String externalDTOClassName) {
		return _externalInternalDTOClassNameMap.get(externalDTOClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			(Class<DTOConverter<?, ?>>)(Class<?>)DTOConverter.class,
			new DTOConverterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private BundleContext _bundleContext;
	private final Map<String, String> _externalInternalDTOClassNameMap =
		new HashMap<>();
	private ServiceTracker<DTOConverter<?, ?>, String> _serviceTracker;

	private class DTOConverterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<DTOConverter<?, ?>, String> {

		@Override
		public String addingService(
			ServiceReference<DTOConverter<?, ?>> serviceReference) {

			DTOConverter<?, ?> dtoConverter = _bundleContext.getService(
				serviceReference);

			String internalDTOClassName = (String)serviceReference.getProperty(
				"dto.class.name");

			if (internalDTOClassName == null) {
				internalDTOClassName = _getDTOConverterGenericType(
					dtoConverter, 0);
			}

			String externalDTOClassName = (String)serviceReference.getProperty(
				"external.dto.class.name");

			if (externalDTOClassName == null) {
				externalDTOClassName = _getDTOConverterGenericType(
					dtoConverter, 1);
			}

			if ((externalDTOClassName == null) &&
				(internalDTOClassName == null)) {

				_bundleContext.ungetService(serviceReference);

				return null;
			}

			_externalInternalDTOClassNameMap.put(
				externalDTOClassName, internalDTOClassName);

			_bundleContext.ungetService(serviceReference);

			return externalDTOClassName;
		}

		@Override
		public void modifiedService(
			ServiceReference<DTOConverter<?, ?>> serviceReference,
			String externalDTOClassName) {
		}

		@Override
		public void removedService(
			ServiceReference<DTOConverter<?, ?>> serviceReference,
			String externalDTOClassName) {

			_externalInternalDTOClassNameMap.remove(externalDTOClassName);
		}

		private String _getDTOConverterGenericType(
			DTOConverter<?, ?> dtoConverter, int genericTypeIndex) {

			Class<?> dtoConverterClass = dtoConverter.getClass();

			Type[] genericInterfaceTypes =
				dtoConverterClass.getGenericInterfaces();

			for (Type genericInterfaceType : genericInterfaceTypes) {
				if (genericInterfaceType instanceof ParameterizedType) {
					ParameterizedType parameterizedType =
						(ParameterizedType)genericInterfaceType;

					if (parameterizedType.getRawType() != DTOConverter.class) {
						continue;
					}

					Type[] genericTypes =
						parameterizedType.getActualTypeArguments();

					Class<DTOConverter<?, ?>> resourceGenericType =
						(Class<DTOConverter<?, ?>>)
							genericTypes[genericTypeIndex];

					return resourceGenericType.getName();
				}
			}

			return null;
		}

	}

}