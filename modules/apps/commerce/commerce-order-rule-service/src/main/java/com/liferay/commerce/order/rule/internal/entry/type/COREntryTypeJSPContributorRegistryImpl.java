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

package com.liferay.commerce.order.rule.internal.entry.type;

import com.liferay.commerce.order.rule.entry.type.COREntryTypeJSPContributor;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeJSPContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = COREntryTypeJSPContributorRegistry.class
)
public class COREntryTypeJSPContributorRegistryImpl
	implements COREntryTypeJSPContributorRegistry {

	@Override
	public COREntryTypeJSPContributor getCOREntryTypeJSPContributor(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<COREntryTypeJSPContributor> getCOREntryTypeJSPContributors() {
		List<COREntryTypeJSPContributor> corEntryTypeJSPContributors =
			new ArrayList<>();

		for (String key : _serviceTrackerMap.keySet()) {
			corEntryTypeJSPContributors.add(_serviceTrackerMap.getService(key));
		}

		return Collections.unmodifiableList(corEntryTypeJSPContributors);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, COREntryTypeJSPContributor.class,
			"commerce.order.rule.entry.type.jsp.contributor.key");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, COREntryTypeJSPContributor>
		_serviceTrackerMap;

}