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

package com.liferay.info.internal.display.url.provider;

import com.liferay.info.display.url.provider.InfoEditURLProvider;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoEditURLProviderTracker.class)
public class InfoEditURLProviderTrackerImpl
	implements InfoEditURLProviderTracker {

	@Override
	public InfoEditURLProvider getInfoEditURLProvider(String className) {
		return _infoEditURLProviders.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoEditURLProviders(
		InfoEditURLProvider infoEditURLProvider,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		_infoEditURLProviders.put(className, infoEditURLProvider);
	}

	protected void unsetInfoEditURLProviders(
		InfoEditURLProvider infoEditURLProvider,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		_infoEditURLProviders.remove(className);
	}

	private final Map<String, InfoEditURLProvider> _infoEditURLProviders =
		new ConcurrentHashMap<>();

}