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

package com.liferay.site.navigation.internal.type;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = SiteNavigationMenuItemTypeRegistry.class)
public class SiteNavigationMenuItemTypeRegistryImpl
	implements SiteNavigationMenuItemTypeRegistry {

	@Override
	public SiteNavigationMenuItemType getSiteNavigationMenuItemType(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());
	}

	@Override
	public SiteNavigationMenuItemType getSiteNavigationMenuItemType(
		String type) {

		return _serviceTrackerMap.getService(type);
	}

	@Override
	public List<SiteNavigationMenuItemType> getSiteNavigationMenuItemTypes() {
		List<SiteNavigationMenuItemType> siteNavigationMenuItemTypes =
			new ArrayList<>();

		for (SiteNavigationMenuItemType siteNavigationMenuItemType :
				_serviceTrackerList) {

			siteNavigationMenuItemTypes.add(siteNavigationMenuItemType);
		}

		return siteNavigationMenuItemTypes;
	}

	@Override
	public String[] getTypes() {
		List<String> types = new ArrayList<>();

		for (SiteNavigationMenuItemType siteNavigationMenuItemType :
				_serviceTrackerList) {

			types.add(siteNavigationMenuItemType.getType());
		}

		return types.toArray(new String[0]);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SiteNavigationMenuItemType.class,
			new PropertyServiceReferenceComparator<>("service.ranking"));
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SiteNavigationMenuItemType.class, null,
			new PropertyServiceReferenceMapper<>(
				"site.navigation.menu.item.type"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
		_serviceTrackerMap.close();
	}

	private ServiceTrackerList<SiteNavigationMenuItemType> _serviceTrackerList;
	private volatile ServiceTrackerMap<String, SiteNavigationMenuItemType>
		_serviceTrackerMap;

}