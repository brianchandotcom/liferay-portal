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

package com.liferay.item.selector.taglib.internal.util;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Cristina González
 */
public class GroupItemSelectorProviderRegistryUtil {

	public static GroupItemSelectorProvider getGroupItemSelectorProvider(
		String groupType) {

		GroupItemSelectorProvider groupItemSelectorProvider =
			_serviceTrackerMap.getService(groupType);

		if ((groupItemSelectorProvider != null) &&
			groupItemSelectorProvider.isEnabled()) {

			return groupItemSelectorProvider;
		}

		return null;
	}

	public static Set<String> getGroupItemSelectorProviderTypes() {
		Set<String> types = new HashSet<>();

		for (GroupItemSelectorProvider groupItemSelectorProvider :
				_serviceTrackerMap.values()) {

			if (groupItemSelectorProvider.isEnabled()) {
				types.add(groupItemSelectorProvider.getGroupType());
			}
		}

		return types;
	}

	private static final ServiceTrackerMap<String, GroupItemSelectorProvider>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			GroupItemSelectorProviderRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, GroupItemSelectorProvider.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(service, emitter) -> emitter.emit(service.getGroupType())));
	}

}