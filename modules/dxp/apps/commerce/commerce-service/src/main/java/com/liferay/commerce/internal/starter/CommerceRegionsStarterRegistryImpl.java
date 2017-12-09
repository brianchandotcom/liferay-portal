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

package com.liferay.commerce.internal.starter;

import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.commerce.starter.CommerceRegionsStarterRegistry;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = CommerceRegionsStarterRegistry.class)
public class CommerceRegionsStarterRegistryImpl
	implements CommerceRegionsStarterRegistry {

	@Override
	public List<CommerceRegionsStarter> getCommerceRegionsStarters() {
		List<CommerceRegionsStarter> commerceRegionsStarters =
			new ArrayList<>();

		for (CommerceRegionsStarter commerceRegionsStarter :
				_serviceTrackersList) {

			commerceRegionsStarters.add(commerceRegionsStarter);
		}

		return Collections.unmodifiableList(commerceRegionsStarters);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackersList = ServiceTrackerListFactory.open(
			bundleContext, CommerceRegionsStarter.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackersList.close();
	}

	private ServiceTrackerList<CommerceRegionsStarter, CommerceRegionsStarter>
		_serviceTrackersList;

}