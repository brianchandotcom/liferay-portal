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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = AssetPublisherCustomizerRegistry.class)
public class DefaultAssetPublisherCustomizerRegistry
	implements AssetPublisherCustomizerRegistry {

	@Override
	public AssetPublisherCustomizer getAssetPublisherCustomizer(
		String portletId) {

		AssetPublisherCustomizer assetPublisherCustomizer =
			_assetPublisherCustomizers.get(portletId);

		if (assetPublisherCustomizer == null) {
			assetPublisherCustomizer = _assetPublisherCustomizers.get(
				AssetPublisherPortletKeys.ASSET_PUBLISHER);
		}

		return assetPublisherCustomizer;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	public void registerAssetPublisherCustomizer(
		AssetPublisherCustomizer assetPublisherCustomizer) {

		_assetPublisherCustomizers.put(
			assetPublisherCustomizer.getPortletId(), assetPublisherCustomizer);
	}

	public void unregisterAssetPublisherCustomizer(
		AssetPublisherCustomizer assetPublisherCustomizer) {

		_assetPublisherCustomizers.remove(
			assetPublisherCustomizer.getPortletId());
	}

	private final Map<String, AssetPublisherCustomizer>
		_assetPublisherCustomizers = new ConcurrentHashMap<>();

}