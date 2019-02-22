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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UserPersonalMenuEntryRegistry.class)
public class UserPersonalMenuEntryRegistry {

	public List<List<UserPersonalMenuEntry>>
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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, UserPersonalMenuEntry.class,
			"(product.navigation.user.personal.menu.group=*)",
			new UserPersonalMenuEntryServiceReferenceMapper(),
			new UserPersonalMenuEntryOrderComparator(
				"product.navigation.user.personal.menu.entry.order"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserPersonalMenuEntryRegistry.class);

	private ServiceTrackerMap<String, List<UserPersonalMenuEntry>>
		_serviceTrackerMap;

	private class UserPersonalMenuEntryOrderComparator
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

	private class UserPersonalMenuEntryServiceReferenceMapper
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

}