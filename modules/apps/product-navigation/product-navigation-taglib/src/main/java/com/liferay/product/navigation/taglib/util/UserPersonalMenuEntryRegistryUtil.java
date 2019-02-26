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

package com.liferay.product.navigation.taglib.util;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.product.navigation.user.personal.menu.UserPersonalMenuEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Pei-Jung Lan
 */
public class UserPersonalMenuEntryRegistryUtil {

	public static List<List<UserPersonalMenuEntry>>
		getGroupedUserPersonalMenuEntries() {

		SortedSet<String> personalMenuGroups = new TreeSet<>(
			_serviceTrackerMap.keySet());

		List<List<UserPersonalMenuEntry>> groupedUserPersonalMenuEntries =
			new ArrayList<>(personalMenuGroups.size());

		for (String group : personalMenuGroups) {
			groupedUserPersonalMenuEntries.add(
				_serviceTrackerMap.getService(group));
		}

		return groupedUserPersonalMenuEntries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPersonalMenuEntryRegistryUtil.class);

	private static final ServiceTrackerMap<String, List<UserPersonalMenuEntry>>
		_serviceTrackerMap;

	private static class UserPersonalMenuEntryOrderComparator
		extends PropertyServiceReferenceComparator {

		public UserPersonalMenuEntryOrderComparator(String propertyKey) {
			super(propertyKey);
		}

		@Override
		public int compare(
			ServiceReference serviceReference1,
			ServiceReference serviceReference2) {

			return -super.compare(serviceReference1, serviceReference2);
		}

	}

	private static class UserPersonalMenuEntryServiceReferenceMapper
		implements ServiceReferenceMapper<String, UserPersonalMenuEntry> {

		@Override
		public void map(
			ServiceReference<UserPersonalMenuEntry> serviceReference,
			Emitter<String> emitter) {

			Integer personalMenuGroup = (Integer)serviceReference.getProperty(
				"product.navigation.user.personal.menu.group");

			if (personalMenuGroup == null) {
				_log.error(
					"Unable to register user personal menu entry because of " +
						"missing service property " +
							"\"product.navigation.user.personal.menu.group\"");
			}
			else {
				emitter.emit(String.valueOf(personalMenuGroup));
			}
		}

	}

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			UserPersonalMenuEntryRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, UserPersonalMenuEntry.class,
			"(product.navigation.user.personal.menu.group=*)",
			new UserPersonalMenuEntryServiceReferenceMapper(),
			new UserPersonalMenuEntryOrderComparator(
				"product.navigation.user.personal.menu.entry.order"));
	}

}