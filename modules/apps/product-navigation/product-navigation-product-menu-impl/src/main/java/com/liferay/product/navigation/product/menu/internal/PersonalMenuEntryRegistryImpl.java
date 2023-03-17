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

package com.liferay.product.navigation.product.menu.internal;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntryRegistry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Jiaxu Wei
 */
@Component(service = PersonalMenuEntryRegistry.class)
public class PersonalMenuEntryRegistryImpl
	implements PersonalMenuEntryRegistry {

	@Override
	public List<PersonalMenuEntry> getPersonalMenuEntries() {
		return _personalMenuEntryServiceTrackerList.toList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Comparator<ServiceReference<PersonalMenuEntry>> groupComparator =
			new PropertyServiceReferenceComparator<>(
				"product.navigation.personal.menu.group");

		Comparator<ServiceReference<PersonalMenuEntry>> entryOrderComparator =
			new PropertyServiceReferenceComparator<>(
				"product.navigation.personal.menu.entry.order");

		_personalMenuEntryServiceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, PersonalMenuEntry.class,
			Collections.reverseOrder(
				groupComparator.thenComparing(entryOrderComparator)));
	}

	@Deactivate
	protected void deactivate() {
		_personalMenuEntryServiceTrackerList.close();
	}

	private ServiceTrackerList<PersonalMenuEntry>
		_personalMenuEntryServiceTrackerList;

}