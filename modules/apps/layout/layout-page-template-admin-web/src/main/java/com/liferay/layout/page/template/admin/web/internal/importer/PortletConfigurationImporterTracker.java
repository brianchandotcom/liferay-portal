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

package com.liferay.layout.page.template.admin.web.internal.importer;

import com.liferay.layout.page.template.importer.PortletConfigurationImporter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletConfigurationImporterTracker.class)
public class PortletConfigurationImporterTracker {

	public PortletConfigurationImporter getPortletConfigurationImporter(
		String portletName) {

		return _portletConfigurationImporters.get(portletName);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setPortletConfigurationImporter(
		PortletConfigurationImporter portletConfigurationImporter) {

		_portletConfigurationImporters.put(
			portletConfigurationImporter.getPortletName(),
			portletConfigurationImporter);
	}

	protected void unsetPortletConfigurationImporter(
		PortletConfigurationImporter portletConfigurationImporter) {

		_portletConfigurationImporters.remove(portletConfigurationImporter);
	}

	private final Map<String, PortletConfigurationImporter>
		_portletConfigurationImporters = new ConcurrentHashMap<>();

}