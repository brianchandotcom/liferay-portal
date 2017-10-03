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

package com.liferay.site.navigation.type.controller;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	service = SiteNavigationMenuItemTypeControllerTracker.class
)
public class SiteNavigationMenuItemTypeControllerTracker {

	public SiteNavigationMenuItemTypeController
		getSiteNavigationMenuItemTypeController(
			SiteNavigationMenuItem siteNavigationMenuItem) {

		return getSiteNavigationMenuItemTypeController(
			siteNavigationMenuItem.getType());
	}

	public SiteNavigationMenuItemTypeController
		getSiteNavigationMenuItemTypeController(String type) {

		SiteNavigationMenuItemTypeController
			siteNavigationMenuItemTypeController =
				_serviceTrackerMap.getService(type);

		return siteNavigationMenuItemTypeController;
	}

	public Map<String, SiteNavigationMenuItemTypeController>
		getSiteNavigationMenuItemTypeControllers() {

		Collection<SiteNavigationMenuItemTypeController>
			siteNavigationMenuItemTypeControllers = _serviceTrackerMap.values();

		Stream<SiteNavigationMenuItemTypeController> stream =
			siteNavigationMenuItemTypeControllers.parallelStream();

		Map siteNavigationMenuItemTypeControllersMap = stream.collect(
			Collectors.toMap(
				SiteNavigationMenuItemTypeController::getType,
				Function.identity()));

		return Collections.unmodifiableMap(
			siteNavigationMenuItemTypeControllersMap);
	}

	public String[] getTypes() {
		Set<String> types = _serviceTrackerMap.keySet();

		return types.toArray(new String[types.size()]);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.singleValueMap(
			bundleContext, SiteNavigationMenuItemTypeController.class,
			"(site.navigation.menu.item.type=*)",
			new PropertyServiceReferenceMapper<String,
				SiteNavigationMenuItemTypeController>(
					"site.navigation.menu.item.type"));

		_serviceTrackerMap.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, SiteNavigationMenuItemTypeController>
		_serviceTrackerMap;

}