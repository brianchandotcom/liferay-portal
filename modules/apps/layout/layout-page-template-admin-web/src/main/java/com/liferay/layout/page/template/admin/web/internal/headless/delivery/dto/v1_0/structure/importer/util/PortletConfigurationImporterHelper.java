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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletPreferences;

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

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				layout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId, portlet.getDefaultPreferences());

		for (Map.Entry<String, Object> entrySet : widgetConfig.entrySet()) {
			portletPreferences.setValue(
				entrySet.getKey(), (String)entrySet.getValue());
		}

		String portletPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portletPreferences);

		_portletPreferencesLocalService.addPortletPreferences(
			layout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(), portletId,
			null, portletPreferencesXML);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}