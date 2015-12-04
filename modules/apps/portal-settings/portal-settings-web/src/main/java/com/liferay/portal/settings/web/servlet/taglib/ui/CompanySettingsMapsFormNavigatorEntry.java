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

package com.liferay.portal.settings.web.servlet.taglib.ui;

import com.liferay.frontend.map.api.constants.MapProviderWebKeys;
import com.liferay.frontend.map.api.util.MapAPIHelperUtil;
import com.liferay.frontend.map.api.util.MapProviderTracker;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 * @author Philip Jones
 */
@Component(
	immediate = true, property = {"service.ranking:Integer=20"},
	service = FormNavigatorEntry.class
)
public class CompanySettingsMapsFormNavigatorEntry
	extends BaseCompanySettingsFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return
			FormNavigatorConstants.CATEGORY_KEY_COMPANY_SETTINGS_MISCELLANEOUS;
	}

	@Override
	public String getKey() {
		return "maps";
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			MapProviderWebKeys.GROUP_MAPS_API_PROVIDER,
			_mapAPIHelperUtil.getGroupMapsAPIProvider(request));

		request.setAttribute(
			MapProviderWebKeys.MAP_PROVIDER_TRACKER, _mapProviderTracker);

		super.include(request, response);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.settings.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/maps.jsp";
	}

	@Reference(unbind = "-")
	protected void setMapAPIHelperUtil(MapAPIHelperUtil mapAPIHelperUtil) {
		_mapAPIHelperUtil = mapAPIHelperUtil;
	}

	@Reference(unbind = "-")
	protected void setMapProviderTracker(
		MapProviderTracker mapProviderTracker) {

		_mapProviderTracker = mapProviderTracker;
	}

	private volatile MapAPIHelperUtil _mapAPIHelperUtil;
	private volatile MapProviderTracker _mapProviderTracker;

}