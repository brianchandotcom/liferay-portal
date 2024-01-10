/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.util.AssetPublisherHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class AssetPublisherCustomizerRegistry {

	public AssetPublisherCustomizerRegistry(
		AssetPublisherHelper assetPublisherHelper) {

		_register(new DefaultAssetPublisherCustomizer(assetPublisherHelper));
		_register(
			new HighestRatedAssetPublisherCustomizer(assetPublisherHelper));
		_register(new MostViewedAssetPublisherCustomizer(assetPublisherHelper));
		_register(
			new RecentContentAssetPublisherCustomizer(assetPublisherHelper));
		_register(new RelatedAssetPublisherCustomizer(assetPublisherHelper));
	}

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

	private void _register(AssetPublisherCustomizer assetPublisherCustomizer) {
		_assetPublisherCustomizers.put(
			assetPublisherCustomizer.getPortletId(), assetPublisherCustomizer);
	}

	private final Map<String, AssetPublisherCustomizer>
		_assetPublisherCustomizers = new HashMap<>();

}