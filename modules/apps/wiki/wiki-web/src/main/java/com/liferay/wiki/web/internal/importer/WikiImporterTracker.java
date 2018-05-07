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

package com.liferay.wiki.web.internal.importer;

import com.liferay.wiki.importer.WikiImporter;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = WikiImporterTracker.class)
public class WikiImporterTracker {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		service = WikiImporter.class, unbind = "removedService",
		updated = "modifiedService"
	)
	public void addingService(ServiceReference<WikiImporter> serviceReference) {
		String format = (String)serviceReference.getProperty("importer");

		_serviceReferences.put(format, serviceReference);
	}

	public Collection<String> getImporters() {
		return _serviceReferences.keySet();
	}

	public String getProperty(String importer, String key) {
		ServiceReference<WikiImporter> serviceReference =
			_serviceReferences.get(importer);

		return (String)serviceReference.getProperty(key);
	}

	public void modifiedService(
		ServiceReference<WikiImporter> serviceReference) {

		removedService(serviceReference);

		addingService(serviceReference);
	}

	public void removedService(
		ServiceReference<WikiImporter> serviceReference) {

		String importer = (String)serviceReference.getProperty("importer");

		_serviceReferences.remove(importer);
	}

	private final ConcurrentMap<String, ServiceReference<WikiImporter>>
		_serviceReferences = new ConcurrentSkipListMap<>();

}