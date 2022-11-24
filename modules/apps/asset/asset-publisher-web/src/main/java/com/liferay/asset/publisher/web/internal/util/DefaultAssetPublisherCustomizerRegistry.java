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

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Pavel Savinov
 */
@Component(service = AssetPublisherCustomizerRegistry.class)
public class DefaultAssetPublisherCustomizerRegistry
	implements AssetPublisherCustomizerRegistry {

	@Override
	public AssetPublisherCustomizer getAssetPublisherCustomizer(
		String portletId) {

		AssetPublisherCustomizer assetPublisherCustomizer =
			_serviceTrackerMap.getService(portletId);

		if (assetPublisherCustomizer == null) {
			assetPublisherCustomizer = _serviceTrackerMap.getService(
				AssetPublisherPortletKeys.ASSET_PUBLISHER);
		}

		return assetPublisherCustomizer;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, AssetPublisherCustomizer.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(assetPublisherCustomizer, emitter) -> emitter.emit(
					assetPublisherCustomizer.getPortletId())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, AssetPublisherCustomizer>
		_serviceTrackerMap;

}