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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = {})
public class GroupItemSelectorTrackerUtil {

	public static Optional<GroupItemSelectorProvider>
		getGroupItemSelectorProviderOptional(String groupType) {

		if (_serviceTrackerMap == null) {
			return Optional.empty();
		}

		GroupItemSelectorProvider groupItemSelectorProvider =
			_serviceTrackerMap.getService(groupType);

		if ((groupItemSelectorProvider != null) &&
			groupItemSelectorProvider.isEnabled()) {

			return Optional.of(groupItemSelectorProvider);
		}

		return Optional.empty();
	}

	public static Set<String> getGroupItemSelectorProviderTypes() {
		if (_serviceTrackerMap == null) {
			return Collections.emptySet();
		}

		Collection<GroupItemSelectorProvider> values =
			_serviceTrackerMap.values();

		Stream<GroupItemSelectorProvider> stream = values.stream();

		return stream.filter(
			GroupItemSelectorProvider::isEnabled
		).map(
			GroupItemSelectorProvider::getGroupType
		).collect(
			Collectors.toSet()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, GroupItemSelectorProvider.class, null,
			(serviceReference, emitter) -> {
				GroupItemSelectorProvider groupItemSelectorProvider =
					bundleContext.getService(serviceReference);

				try {
					emitter.emit(groupItemSelectorProvider.getGroupType());
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();
		}
	}

	private static ServiceTrackerMap<String, GroupItemSelectorProvider>
		_serviceTrackerMap;

}