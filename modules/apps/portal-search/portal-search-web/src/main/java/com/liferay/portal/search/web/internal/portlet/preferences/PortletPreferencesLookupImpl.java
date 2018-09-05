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

package com.liferay.portal.search.web.internal.portlet.preferences;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Optional;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = PortletPreferencesLookup.class)
public class PortletPreferencesLookupImpl implements PortletPreferencesLookup {

	@Override
	public Optional<PortletPreferences> fetchPreferences(
		Portlet portlet, ThemeDisplay themeDisplay) {

		if (portlet.isStatic()) {
			return Optional.ofNullable(
				portletPreferencesLocalService.fetchPreferences(
					themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId(),
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
					PortletKeys.PREFS_PLID_SHARED, portlet.getPortletId()));
		}

		return Optional.ofNullable(
			portletPreferencesLocalService.fetchPreferences(
				themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid(),
				portlet.getPortletId()));
	}

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

}