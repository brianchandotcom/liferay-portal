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
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRendererFactory;
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
			HttpServletRequest request, Group group, String className,
			PortletProvider.Action action)
		throws PortalException {

		PortletProvider portletProvider = getPortletProvider(className, action);

		if (portletProvider != null) {
			return portletProvider.getPortletURL(request, group);
		}

		return null;
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
			PortletRequest portletRequest, Group group, String className,
			PortletProvider.Action action)
		throws PortalException {

		return getPortletURL(
			PortalUtil.getHttpServletRequest(portletRequest), group, className,
			action);
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
			return getPortletProvider(className, _addServiceTrackerMap);
		}
		else if (action.equals(PortletProvider.Action.BROWSE)) {
			return getPortletProvider(className, _browseServiceTrackerMap);
		}
		else if (action.equals(PortletProvider.Action.EDIT)) {
			return getPortletProvider(className, _editServiceTrackerMap);
		}
		else if (action.equals(PortletProvider.Action.MANAGE)) {
			return getPortletProvider(className, _manageServiceTrackerMap);
		}
		else if (action.equals(PortletProvider.Action.VIEW)) {
			return getPortletProvider(className, _viewServiceTrackerMap);
		}

		return portletProvider;
	}

	protected static PortletProvider getPortletProvider(
		String className,
		ServiceTrackerMap
			<String, ? extends PortletProvider> serviceTrackerMap) {

		PortletProvider portletProvider = serviceTrackerMap.getService(
			className);

		if ((portletProvider == null) && isAssetObject(className)) {
			portletProvider = serviceTrackerMap.getService(
				AssetEntry.class.getName());
		}

		return portletProvider;
	}

	protected static boolean isAssetObject(String className) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			return true;
		}

		return false;
	}

	private static final ServiceTrackerMap<String, AddPortletProvider>
		_addServiceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			AddPortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, BrowsePortletProvider>
		_browseServiceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			BrowsePortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, EditPortletProvider>
		_editServiceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			EditPortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, ManagePortletProvider>
		_manageServiceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			ManagePortletProvider.class, "model.class.name");
	private static final ServiceTrackerMap<String, ViewPortletProvider>
		_viewServiceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			ViewPortletProvider.class, "model.class.name");

}