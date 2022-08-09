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

package com.liferay.content.dashboard.web.internal.item.filter;

import com.liferay.content.dashboard.item.filter.provider.ContentDashboardItemFilterProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFilterProviderTracker.class)
public class ContentDashboardItemFilterProviderTracker {

	public List<ContentDashboardItemFilterProvider>
		getContentDashboardItemFilterProviders() {

		Iterator<ContentDashboardItemFilterProvider> iterator =
			_serviceTrackerList.iterator();

		List<ContentDashboardItemFilterProvider>
			contentDashboardItemFilterProviders = new ArrayList<>();

		iterator.forEachRemaining(contentDashboardItemFilterProviders::add);

		return contentDashboardItemFilterProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, ContentDashboardItemFilterProvider.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private ServiceTrackerList<ContentDashboardItemFilterProvider>
		_serviceTrackerList;

}