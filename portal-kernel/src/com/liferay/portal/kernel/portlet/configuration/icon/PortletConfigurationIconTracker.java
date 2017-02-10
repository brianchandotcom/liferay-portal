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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletConfigurationIconTracker {

	public static List<PortletConfigurationIcon> getPortletConfigurationIcons(
		String portletId, PortletRequest portletRequest) {

		return _getPortletConfigurationIcons(portletId, portletRequest, false);
	}

	public static List<PortletConfigurationIcon> getPortletConfigurationIcons(
		String portletId, final PortletRequest portletRequest,
		Comparator<?> comparator) {

		List<PortletConfigurationIcon> portletConfigurationIcons =
			_getPortletConfigurationIcons(portletId, portletRequest, true);

		Collections.sort(
			portletConfigurationIcons,
			(Comparator<PortletConfigurationIcon>)comparator);

		return portletConfigurationIcons;
	}

	protected static String getKey(String portletId, String path) {
		return portletId.concat(StringPool.COLON).concat(path);
	}

	protected static Set<String> getPaths(
		String portletId, PortletRequest portletRequest) {

		Set<String> paths = _defaultPaths;

		for (PortletConfigurationIconLocator portletConfigurationIconLocator :
				_serviceTrackerList) {

			String path = portletConfigurationIconLocator.getPath(
				portletRequest);

			if (!path.isEmpty() && !path.equals(StringPool.DASH)) {
				if (paths == _defaultPaths) {
					paths = new HashSet<>();
				}

				paths.add(path);

				List<String> defaultViews =
					portletConfigurationIconLocator.getDefaultViews(portletId);

				if (defaultViews.contains(path)) {
					paths.add(StringPool.DASH);
				}
			}
		}

		return paths;
	}

	private static List<PortletConfigurationIcon> _getPortletConfigurationIcons(
		String portletId, PortletRequest portletRequest, boolean filter) {

		List<PortletConfigurationIcon> portletConfigurationIcons =
			new ArrayList<>();

		for (String path : getPaths(portletId, portletRequest)) {
			List<PortletConfigurationIcon> portletPortletConfigurationIcons =
				_serviceTrackerMap.getService(getKey(StringPool.STAR, path));

			if (portletPortletConfigurationIcons != null) {
				for (PortletConfigurationIcon portletConfigurationIcon :
						portletPortletConfigurationIcons) {

					if (!filter ||
						portletConfigurationIcon.isShow(portletRequest)) {

						portletConfigurationIcons.add(portletConfigurationIcon);
					}
				}
			}

			portletPortletConfigurationIcons = _serviceTrackerMap.getService(
				getKey(portletId, path));

			if (portletPortletConfigurationIcons == null) {
				continue;
			}

			for (PortletConfigurationIcon portletConfigurationIcon :
					portletPortletConfigurationIcons) {

				if (!portletConfigurationIcons.contains(
						portletConfigurationIcon) &&
					(!filter ||
					 portletConfigurationIcon.isShow(portletRequest))) {

					portletConfigurationIcons.add(portletConfigurationIcon);
				}
			}
		}

		return portletConfigurationIcons;
	}

	private static final Set<String> _defaultPaths = Collections.singleton(
		StringPool.DASH);
	private static final ServiceTrackerList<PortletConfigurationIconLocator>
		_serviceTrackerList = ServiceTrackerCollections.openList(
			PortletConfigurationIconLocator.class);
	private static final ServiceTrackerMap
		<String, List<PortletConfigurationIcon>>
			_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
				PortletConfigurationIcon.class, null,
				new PortletConfigurationIconServiceReferenceMapper());

}