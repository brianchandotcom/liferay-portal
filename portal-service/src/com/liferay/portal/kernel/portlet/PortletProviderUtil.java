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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletProviderUtil {

	public static String getPortletId(
		String className, PortletProvider.Action action) {

		PortletProvider portletProvider = getPortletProvider(className, action);

		if (portletProvider != null) {
			return portletProvider.getPortletId();
		}

		return StringPool.BLANK;
	}

	public static PortletURL getPortletURL(
			HttpServletRequest request, String className,
			PortletProvider.Action action)
		throws PortalException {

		PortletProvider portletProvider = getPortletProvider(className, action);

		if (portletProvider != null) {
			return portletProvider.getPortletURL(request);
		}

		return null;
	}

	public static PortletURL getPortletURL(
			PortletRequest portletRequest, String className,
			PortletProvider.Action action)
		throws PortalException {

		return getPortletURL(
			PortalUtil.getHttpServletRequest(portletRequest), className,
			action);
	}

	protected static PortletProvider getPortletProvider(
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

		return portletProvider;
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