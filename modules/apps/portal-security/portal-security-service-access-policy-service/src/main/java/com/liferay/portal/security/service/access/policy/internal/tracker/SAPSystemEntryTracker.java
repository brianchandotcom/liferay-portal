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

package com.liferay.portal.security.service.access.policy.internal.tracker;

import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.security.service.access.policy.util.SAPSystemEntry;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Marta Medio
 */
@Component(immediate = true, service = {})
public class SAPSystemEntryTracker {

	public static boolean isSystemEntry(String name) {
		Collection<Set<String>> sapSystemValues = _sapSystemMap.values();

		for (Set<String> values : sapSystemValues) {
			if (values.contains(name)) {
				return true;
			}
		}

		return false;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, updated = "updateSAPSystemEntry"
	)
	public void addSAPSystemEntry(
		ServiceReference<SAPSystemEntry> serviceReference) {

		_sapSystemMap.put(
			serviceReference,
			new HashSet<>(
				StringPlus.asList(
					serviceReference.getProperty("sap.system.entry"))));
	}

	public void removeSAPSystemEntry(
		ServiceReference<SAPSystemEntry> serviceReference) {

		_sapSystemMap.remove(serviceReference);
	}

	public void updateSAPSystemEntry(
		ServiceReference<SAPSystemEntry> serviceReference) {

		removeSAPSystemEntry(serviceReference);

		addSAPSystemEntry(serviceReference);
	}

	private static final Map<ServiceReference<SAPSystemEntry>, Set<String>>
		_sapSystemMap = new ConcurrentHashMap<>();

}