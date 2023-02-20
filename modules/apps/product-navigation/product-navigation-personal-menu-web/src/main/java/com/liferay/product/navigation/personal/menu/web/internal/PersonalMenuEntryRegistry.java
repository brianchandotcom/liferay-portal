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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
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
			(serviceReference, emitter) -> emitter.emit(
				String.valueOf(
					serviceReference.getProperty(
						"product.navigation.personal.menu.group"))),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"product.navigation.personal.menu.entry.order")));
	}

	private ServiceTrackerMap<String, List<PersonalMenuEntry>>
		_serviceTrackerMap;

}