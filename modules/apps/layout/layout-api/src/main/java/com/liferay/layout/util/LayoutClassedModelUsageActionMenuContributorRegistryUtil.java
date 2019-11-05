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

package com.liferay.layout.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Pavel Savinov
 */
public class LayoutClassedModelUsageActionMenuContributorRegistryUtil {

	public static LayoutClassedModelUsageActionMenuContributor
		getLayoutClassedModelUsageActionMenuContributor(String className) {

		return _layoutClassedModelUsageActionMenuContributorRegistryUtil.
			_getLayoutClassedModelUsageActionMenuContributor(className);
	}

	private LayoutClassedModelUsageActionMenuContributorRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutClassedModelUsageActionMenuContributorRegistryUtil.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(),
			LayoutClassedModelUsageActionMenuContributor.class,
			"model.class.name");
	}

	private LayoutClassedModelUsageActionMenuContributor
		_getLayoutClassedModelUsageActionMenuContributor(String className) {

		return _serviceTrackerMap.getService(className);
	}

	private static final
		LayoutClassedModelUsageActionMenuContributorRegistryUtil
			_layoutClassedModelUsageActionMenuContributorRegistryUtil =
				new LayoutClassedModelUsageActionMenuContributorRegistryUtil();

	private final ServiceTrackerMap
		<String, LayoutClassedModelUsageActionMenuContributor>
			_serviceTrackerMap;

}