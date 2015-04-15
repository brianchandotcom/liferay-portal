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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Eudaldo Alonso
 */
public class PortletProviderUtil {

	public static String getPortletId(
		String className, PortletProvider.Action action) {

		PortletProvider portletProvider = null;

		if (action.equals(PortletProvider.Action.ADD)) {
			portletProvider = _addServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _addServiceTrackerMap.getService(
					PortletProvider.CLASS_NAME_ANY);
			}
		}
		else if (action.equals(PortletProvider.Action.BROWSE)) {
			portletProvider = _browseServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _browseServiceTrackerMap.getService(
					PortletProvider.CLASS_NAME_ANY);
			}
		}
		else if (action.equals(PortletProvider.Action.EDIT)) {
			portletProvider = _editServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _editServiceTrackerMap.getService(
					PortletProvider.CLASS_NAME_ANY);
			}
		}
		else if (action.equals(PortletProvider.Action.VIEW)) {
			portletProvider = _viewServiceTrackerMap.getService(className);

			if (portletProvider == null) {
				portletProvider = _viewServiceTrackerMap.getService(
					PortletProvider.CLASS_NAME_ANY);
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
	private static final ServiceTrackerMap<String, EditPortletProvider>
		_editServiceTrackerMap = ServiceTrackerCollections.singleValueMap(
			EditPortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, ViewPortletProvider>
		_viewServiceTrackerMap = ServiceTrackerCollections.singleValueMap(
			ViewPortletProvider.class, "model.class.name");

	static {
		_addServiceTrackerMap.open();
		_browseServiceTrackerMap.open();
		_editServiceTrackerMap.open();
		_viewServiceTrackerMap.open();
	}

}