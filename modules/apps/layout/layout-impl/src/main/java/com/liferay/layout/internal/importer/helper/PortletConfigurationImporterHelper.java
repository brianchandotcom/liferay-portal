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

package com.liferay.layout.internal.importer.helper;

import com.liferay.layout.importer.PortletPreferencesPortletConfigurationImporter;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PortletConfigurationImporterHelper.class)
public class PortletConfigurationImporterHelper {

	public void importPortletConfiguration(
			long plid, String portletId, Map<String, Object> widgetConfig)
		throws Exception {

		if (widgetConfig == null) {
			return;
		}

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return;
		}

		String portletName = PortletIdCodec.decodePortletName(portletId);

		Portlet portlet = _portletLocalService.getPortletById(portletName);

		if (portlet == null) {
			return;
		}

		_portletPreferencesPortletConfigurationImporter.
			importPortletConfiguration(
				layout.getPlid(), portletId, widgetConfig);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesPortletConfigurationImporter
		_portletPreferencesPortletConfigurationImporter;

}