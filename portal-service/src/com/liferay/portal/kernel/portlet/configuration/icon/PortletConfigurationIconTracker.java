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

package com.liferay.portal.kernel.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.configuration.icon.locator.PortletConfigurationIconLocator;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationIconTracker {

	public static List<PortletConfigurationIconFactory>
		getPortletConfigurationIcons(PortletRequest portletRequest) {

		List<PortletConfigurationIconFactory>
			portletConfigurationIconFactories = new ArrayList<>();

		String portletId = getPortletId(portletRequest);

		for (String path : getPaths(portletRequest)) {
			List<PortletConfigurationIconFactory>
				portletPortletConfigurationIconFactories =
					_serviceTrackerMap.getService(
						getKey(StringPool.STAR, path));

			if (portletPortletConfigurationIconFactories != null) {
				portletConfigurationIconFactories.addAll(
					portletPortletConfigurationIconFactories);
			}

			portletPortletConfigurationIconFactories =
				_serviceTrackerMap.getService(getKey(portletId, path));

			if (portletPortletConfigurationIconFactories != null) {
				portletConfigurationIconFactories.addAll(
					portletPortletConfigurationIconFactories);
			}
		}

		return portletConfigurationIconFactories;
	}

	protected static String getKey(String portletId, String path) {
		return portletId + StringPool.COLON + path;
	}

	protected static Set<String> getPaths(PortletRequest portletRequest) {
		Set<String> paths = new HashSet<>();

		String portletId = getPortletId(portletRequest);

		for (PortletConfigurationIconLocator portletConfigurationIconLocator :
				_serviceTrackerList) {

			String path = portletConfigurationIconLocator.getPath(
				portletRequest);

			List<String> defaultViews =
				portletConfigurationIconLocator.getDefaultViews(portletId);

			String[] defaultViewsArray = ArrayUtil.toStringArray(defaultViews);

			if (Validator.isNotNull(path) &&
				!ArrayUtil.contains(defaultViewsArray, path)) {

				paths.add(path);

				continue;
			}

			paths.addAll(defaultViews);
		}

		return paths;
	}

	protected static String getPortletId(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

	private static final ServiceTrackerList<PortletConfigurationIconLocator>
		_serviceTrackerList = ServiceTrackerCollections.openList(
			PortletConfigurationIconLocator.class);
	private static final ServiceTrackerMap
		<String, List<PortletConfigurationIconFactory>>
			_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
				PortletConfigurationIconFactory.class, null,
				new PortletConfigurationIconServiceReferenceMapper());

}