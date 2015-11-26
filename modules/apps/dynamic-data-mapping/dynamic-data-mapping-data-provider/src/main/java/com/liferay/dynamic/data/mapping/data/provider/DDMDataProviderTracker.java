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

package com.liferay.dynamic.data.mapping.data.provider;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMDataProviderTracker.class)
public class DDMDataProviderTracker {

	public DDMDataProvider getDDMDataProvider(String type) {
		return _ddmDataProvidersMap.get(type);
	}

	public Set<String> getDDMDataProviderTypes() {
		return Collections.unmodifiableSet(_ddmDataProvidersMap.keySet());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unregisterDDMDataProvider"
	)
	protected synchronized void registerDDMDataProvider(
		DDMDataProvider ddmDataProvider, Map<String, Object> properties) {

		Object value = properties.get("ddm.data.provider.type");

		_ddmDataProvidersMap.put(value.toString(), ddmDataProvider);
	}

	protected synchronized void unregisterDDMDataProvider(
		DDMDataProvider ddmDataProvider, Map<String, Object> properties) {

		Object value = properties.get("ddm.data.provider.type");

		_ddmDataProvidersMap.remove(value);
	}

	private final Map<String, DDMDataProvider> _ddmDataProvidersMap =
		new ConcurrentHashMap<>();

}