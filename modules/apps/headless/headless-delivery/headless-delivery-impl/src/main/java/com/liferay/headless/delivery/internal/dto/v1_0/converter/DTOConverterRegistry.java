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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.converter.DTOConverter;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public class DTOConverterRegistry {

	public static DTOConverter getDTOConverter(String assetEntryClassName) {
		return _serviceTrackerMap.getService(assetEntryClassName);
	}

	private static final ServiceTrackerMap<String, DTOConverter>
		_serviceTrackerMap;

	private static class DTOConverterServiceReferenceMapper
		implements ServiceReferenceMapper<String, DTOConverter> {

		@Override
		public void map(
			ServiceReference<DTOConverter> serviceReference,
			Emitter<String> emitter) {

			String assetEntryClassName = (String)serviceReference.getProperty(
				"asset.entry.class.name");

			emitter.emit(assetEntryClassName);
		}

	}

	static {
		Bundle bundle = FrameworkUtil.getBundle(DTOConverterRegistry.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DTOConverter.class, "(asset.entry.class.name=*)",
			new DTOConverterServiceReferenceMapper());
	}

}