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

import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

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

		return _siteNavigationMenuItemTypeControllers.get(type);
	}

	public String[] getTypes() {
		Set<String> types = _siteNavigationMenuItemTypeControllers.keySet();

		return types.toArray(new String[types.size()]);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "unregisterSiteNavigationMenuItemTypeController"
	)
	public void registerSiteNavigationMenuItemTypeController(
		SiteNavigationMenuItemTypeController
			siteNavigationMenuItemTypeController) {

		_siteNavigationMenuItemTypeControllers.put(
			siteNavigationMenuItemTypeController.getType(),
			siteNavigationMenuItemTypeController);
	}

	public void unregisterSiteNavigationMenuItemTypeController(
		SiteNavigationMenuItemTypeController
			siteNavigationMenuItemTypeController) {

		_siteNavigationMenuItemTypeControllers.remove(
			siteNavigationMenuItemTypeController.getType());
	}

	private final Map<String, SiteNavigationMenuItemTypeController>
		_siteNavigationMenuItemTypeControllers = new ConcurrentHashMap<>();

}