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

package com.liferay.product.navigation.personal.menu.web.internal;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PersonalMenuEntryRegistry.class)
public class PersonalMenuEntryRegistry {

	public List<List<PersonalMenuEntry>> getGroupedPersonalMenuEntries() {
		SortedSet<String> personalMenuGroups = new TreeSet<>(
			_serviceTrackerMap.keySet());

		List<List<PersonalMenuEntry>> groupedPersonalMenuEntries =
			new ArrayList<>(personalMenuGroups.size());

		for (String group : personalMenuGroups) {
			groupedPersonalMenuEntries.add(
				_serviceTrackerMap.getService(group));
		}

		return groupedPersonalMenuEntries;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, PersonalMenuEntry.class,
			"(product.navigation.personal.menu.group=*)",
			new PersonalMenuEntryServiceReferenceMapper(),
			new PersonalMenuEntryOrderComparator(
				"product.navigation.personal.menu.entry.order"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PersonalMenuEntryRegistry.class);

	private ServiceTrackerMap<String, List<PersonalMenuEntry>>
		_serviceTrackerMap;

	private class PersonalMenuEntryOrderComparator
		extends PropertyServiceReferenceComparator<PersonalMenuEntry> {

		public PersonalMenuEntryOrderComparator(String propertyKey) {
			super(propertyKey);
		}

		@Override
		public int compare(
			ServiceReference<PersonalMenuEntry> serviceReference1,
			ServiceReference<PersonalMenuEntry> serviceReference2) {

			return -super.compare(serviceReference1, serviceReference2);
		}

	}

	private class PersonalMenuEntryServiceReferenceMapper
		implements ServiceReferenceMapper<String, PersonalMenuEntry> {

		@Override
		public void map(
			ServiceReference<PersonalMenuEntry> serviceReference,
			Emitter<String> emitter) {

			Integer personalMenuGroup = (Integer)serviceReference.getProperty(
				"product.navigation.personal.menu.group");

			if (personalMenuGroup == null) {
				_log.error(
					"Unable to register personal menu entry because of " +
						"missing service property " +
							"\"product.navigation.personal.menu.group\"");
			}
			else {
				emitter.emit(String.valueOf(personalMenuGroup));
			}
		}

	}

}