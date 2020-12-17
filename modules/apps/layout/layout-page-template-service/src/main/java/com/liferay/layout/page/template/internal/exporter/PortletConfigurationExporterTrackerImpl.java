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

package com.liferay.layout.page.template.internal.exporter;

import com.liferay.layout.page.template.exporter.PortletConfigurationExporter;
import com.liferay.layout.page.template.exporter.PortletConfigurationExporterTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletConfigurationExporterTracker.class)
public class PortletConfigurationExporterTrackerImpl
	implements PortletConfigurationExporterTracker {

	@Override
	public PortletConfigurationExporter getPortletConfigurationExporter(
		String portletName) {

		return _portletConfigurationExporters.get(portletName);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setPortletConfigurationExporter(
		PortletConfigurationExporter portletConfigurationExporter) {

		_portletConfigurationExporters.put(
			portletConfigurationExporter.getPortletName(),
			portletConfigurationExporter);
	}

	protected void unsetPortletConfigurationExporter(
		PortletConfigurationExporter portletConfigurationExporter) {

		_portletConfigurationExporters.remove(portletConfigurationExporter);
	}

	private final Map<String, PortletConfigurationExporter>
		_portletConfigurationExporters = new ConcurrentHashMap<>();

}