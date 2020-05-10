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

package com.liferay.layout.page.template.admin.web.internal.exporter;

import com.liferay.layout.page.template.exporter.PortletPreferencesPortletConfigurationExporter;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletPreferencesPortletConfigurationExporterImpl.class)
public class PortletPreferencesPortletConfigurationExporterImpl
	implements PortletPreferencesPortletConfigurationExporter {

	@Override
	public Map<String, Object> getPortletConfiguration(
		long plid, String portletId) {

		return null;
	}

}