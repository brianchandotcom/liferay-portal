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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
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

		Set<PortletConfigurationIcon> portletConfigurationIcons =
			new HashSet<>();

		for (String path : getPaths(portletId, portletRequest)) {
			List<PortletConfigurationIcon> portletPortletConfigurationIcons =
				_serviceTrackerMap.getService(getKey(StringPool.STAR, path));

			if (portletPortletConfigurationIcons != null) {
				portletConfigurationIcons.addAll(
					portletPortletConfigurationIcons);
			}

			portletPortletConfigurationIcons = _serviceTrackerMap.getService(
				getKey(portletId, path));

			if (portletPortletConfigurationIcons != null) {
				portletConfigurationIcons.addAll(
					portletPortletConfigurationIcons);
			}
		}

		return new ArrayList<>(portletConfigurationIcons);
	}

	public static List<PortletConfigurationIcon> getPortletConfigurationIcons(
		String portletId, final PortletRequest portletRequest,
		Comparator<?> comparator) {

		List<PortletConfigurationIcon> portletConfigurationIcons =
			ListUtil.filter(
				getPortletConfigurationIcons(portletId, portletRequest),
				new PredicateFilter<PortletConfigurationIcon>() {

					@Override
					public boolean filter(
						PortletConfigurationIcon portletConfigurationIcon) {

						return portletConfigurationIcon.isShow(portletRequest);
					}

				});

		portletConfigurationIcons = ListUtil.sort(
			portletConfigurationIcons,
			(Comparator<PortletConfigurationIcon>)comparator);

		return portletConfigurationIcons;
	}

	protected static String getKey(String portletId, String path) {
		return portletId + StringPool.COLON + path;
	}

	protected static Set<String> getPaths(
		String portletId, PortletRequest portletRequest) {

		Set<String> paths = new HashSet<>();

		for (PortletConfigurationIconLocator portletConfigurationIconLocator :
				_serviceTrackerList) {

			String path = portletConfigurationIconLocator.getPath(
				portletRequest);

			List<String> defaultViews =
				portletConfigurationIconLocator.getDefaultViews(portletId);

			String[] defaultViewsArray = ArrayUtil.toStringArray(defaultViews);

			if (Validator.isNotNull(path)) {
				paths.add(path);

				if (ArrayUtil.isNotEmpty(defaultViewsArray) &&
					ArrayUtil.contains(defaultViewsArray, path)) {

					paths.add(StringPool.DASH);
				}
			}
		}

		if (SetUtil.isEmpty(paths)) {
			paths.add(StringPool.DASH);
		}

		return paths;
	}

	private static final ServiceTrackerList<PortletConfigurationIconLocator>
		_serviceTrackerList = ServiceTrackerCollections.openList(
			PortletConfigurationIconLocator.class);
	private static final ServiceTrackerMap
		<String, List<PortletConfigurationIcon>>
			_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
				PortletConfigurationIcon.class, null,
				new PortletConfigurationIconServiceReferenceMapper());

}