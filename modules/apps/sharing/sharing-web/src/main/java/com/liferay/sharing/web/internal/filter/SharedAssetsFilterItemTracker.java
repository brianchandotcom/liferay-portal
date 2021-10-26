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

package com.liferay.sharing.web.internal.filter;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharing.filter.SharedAssetsFilterItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = SharedAssetsFilterItemTracker.class)
public class SharedAssetsFilterItemTracker {

	public SharedAssetsFilterItem getSharedAssetsFilterItem(String className) {
		for (SharedAssetsFilterItem sharedAssetsFilterItem :
				_serviceTrackerList) {

			if (StringUtil.equals(
					className, sharedAssetsFilterItem.getClassName())) {

				return sharedAssetsFilterItem;
			}
		}

		return null;
	}

	public List<SharedAssetsFilterItem> getSharedAssetsFilterItems() {
		List<SharedAssetsFilterItem> sharedAssetsFilterItems =
			new ArrayList<>();

		_serviceTrackerList.forEach(sharedAssetsFilterItems::add);

		return sharedAssetsFilterItems;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SharedAssetsFilterItem.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"navigation.item.order")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<SharedAssetsFilterItem> _serviceTrackerList;

}