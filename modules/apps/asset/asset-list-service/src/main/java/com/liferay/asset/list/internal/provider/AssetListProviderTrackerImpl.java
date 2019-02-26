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

package com.liferay.asset.list.internal.provider;

import com.liferay.asset.list.provider.AssetListProvider;
import com.liferay.asset.list.provider.AssetListProviderTracker;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = AssetListProviderTracker.class)
public class AssetListProviderTrackerImpl implements AssetListProviderTracker {

	@Override
	public AssetListProvider getAssetListProvider(String className) {
		if (Validator.isNull(className)) {
			return null;
		}

		return _assetListProviders.get(className);
	}

	@Override
	public List<AssetListProvider> getAssetListProviders() {
		return new ArrayList(_assetListProviders.values());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setAssetListProvider(AssetListProvider assetListProvider) {
		Class<?> clazz = assetListProvider.getClass();

		_assetListProviders.put(clazz.getName(), assetListProvider);
	}

	protected void unsetAssetListProvider(AssetListProvider assetListProvider) {
		Class<?> clazz = assetListProvider.getClass();

		_assetListProviders.remove(clazz.getName());
	}

	private final Map<String, AssetListProvider> _assetListProviders =
		new ConcurrentHashMap<>();

}