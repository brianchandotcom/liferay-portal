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

package com.liferay.portlet.asset.provider;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Eudaldo Alonso
 */
public class PortletProviderUtil {

	public static String getPortletId(String className, String action) {
		PortletProvider portletProvider = null;

		if (action.equals(PortletProvider.ACTION_ADD)) {
			portletProvider = _addServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _addServiceTrackerMap.getService(
					PortletProvider.DEFAULT);
			}
		}
		else if (action.equals(PortletProvider.ACTION_BROWSE)) {
			portletProvider = _browseServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _browseServiceTrackerMap.getService(
					PortletProvider.DEFAULT);
			}
		}
		else if (action.equals(PortletProvider.ACTION_VIEW)) {
			portletProvider = _viewServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _viewServiceTrackerMap.getService(
					PortletProvider.DEFAULT);
			}
		}

		if (portletProvider != null) {
			return portletProvider.getPortletId();
		}

		return StringPool.BLANK;
	}

	private static final ServiceTrackerMap<String, AddPortletProvider>
		_addServiceTrackerMap = ServiceTrackerCollections.singleValueMap(
			AddPortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, BrowsePortletProvider>
		_browseServiceTrackerMap = ServiceTrackerCollections.singleValueMap(
			BrowsePortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, ViewPortletProvider>
		_viewServiceTrackerMap = ServiceTrackerCollections.singleValueMap(
			ViewPortletProvider.class, "model.class.name");

	static {
		_addServiceTrackerMap.open();
		_browseServiceTrackerMap.open();
		_viewServiceTrackerMap.open();
	}

}