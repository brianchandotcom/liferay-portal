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

package com.liferay.map.util;

import com.liferay.map.MapProvider;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = MapProviderTracker.class)
public class MapProviderTracker {

	public MapProvider getMapProvider(String mapProviderKey) {
		return _mapProviders.get(mapProviderKey);
	}

	public Collection<MapProvider> getMapProviders() {
		return _mapProviders.values();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unregisterMapProvider"
	)
	protected synchronized void registerMapProvider(MapProvider mapProvider) {
		_mapProviders.put(mapProvider.getKey(), mapProvider);
	}

	protected synchronized void unregisterMapProvider(MapProvider mapProvider) {
		_mapProviders.remove(mapProvider.getKey());
	}

	private final Map<String, MapProvider> _mapProviders =
		new ConcurrentHashMap<>();

}