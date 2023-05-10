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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(service = DTOConverterRegistry.class)
public class DTOConverterRegistryImpl implements DTOConverterRegistry {

	@Override
	public Set<String> getDTOClassNames() {
		return _serviceTrackerMap.keySet();
	}

	@Override
	public DTOConverter<?, ?> getDTOConverter(String dtoClassName) {
		return _serviceTrackerMap.getService(dtoClassName);
	}

	@Override
	public DTOConverter<?, ?> getDTOConverter(
		String applicationName, String dtoClassName, String version) {

		return _serviceTrackerMap.getService(
			_getKey(applicationName, dtoClassName, version));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<DTOConverter<?, ?>>)(Class<?>)DTOConverter.class,
			"(dto.class.name=*)",
			(serviceReference, emitter) -> {
				String dtoClassName = (String)serviceReference.getProperty(
					"dto.class.name");

				emitter.emit(dtoClassName);

				String applicationName = (String)serviceReference.getProperty(
					"application.name");
				String version = (String)serviceReference.getProperty(
					"version");

				if (!Validator.isBlank(applicationName) &&
					!Validator.isBlank(version)) {

					emitter.emit(
						_getKey(applicationName, dtoClassName, version));
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getKey(
		String applicationName, String dtoClassName, String version) {

		return StringBundler.concat(
			applicationName, StringPool.POUND, dtoClassName, StringPool.POUND,
			version);
	}

	private ServiceTrackerMap<String, DTOConverter<?, ?>> _serviceTrackerMap;

}