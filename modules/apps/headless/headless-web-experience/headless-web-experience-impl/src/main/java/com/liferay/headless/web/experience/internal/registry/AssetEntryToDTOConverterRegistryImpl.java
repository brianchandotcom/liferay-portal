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

package com.liferay.headless.web.experience.internal.registry;

import com.liferay.headless.common.spi.osgi.AssetEntryToDTOConverter;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
@Component(service = AssetEntryToDTOConverterRegistry.class)
public class AssetEntryToDTOConverterRegistryImpl
	implements AssetEntryToDTOConverterRegistry {

	@Override
	public Optional<AssetEntryToDTOConverter> getAssetEntryToDTOConverter(
		String className) {

		return Optional.ofNullable(_serviceTrackerMap.getService(className));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AssetEntryToDTOConverter.class,
			"(asset.entry.to.dto.converter.class.name=*)",
			new AssetEntryToDTOConverterServiceReferenceMapper());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, AssetEntryToDTOConverter>
		_serviceTrackerMap;

	private static class AssetEntryToDTOConverterServiceReferenceMapper
		implements ServiceReferenceMapper<String, AssetEntryToDTOConverter> {

		@Override
		public void map(
			ServiceReference<AssetEntryToDTOConverter> serviceReference,
			Emitter<String> emitter) {

			String className = (String)serviceReference.getProperty(
				"asset.entry.to.dto.converter.class.name");

			emitter.emit(className);
		}

	}

}