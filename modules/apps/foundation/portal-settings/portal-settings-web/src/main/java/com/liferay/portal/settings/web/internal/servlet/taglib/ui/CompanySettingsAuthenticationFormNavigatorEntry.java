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

package com.liferay.portal.settings.web.internal.servlet.taglib.ui;

import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.settings.web.internal.constants.PortalSettingsWebKeys;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 * @author Philip Jones
 * @author Tomas Polesovsky
 */
@Component(
	immediate = true, property = {"form.navigator.entry.order:Integer=70"},
	service = FormNavigatorEntry.class
)
public class CompanySettingsAuthenticationFormNavigatorEntry
	extends BaseCompanySettingsFormNavigatorEntry {

	@Override
	public String getCategoryKey() {
		return
			FormNavigatorConstants.CATEGORY_KEY_COMPANY_SETTINGS_CONFIGURATION;
	}

	@Override
	public String getKey() {
		return "authentication";
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			PortalSettingsWebKeys.AUTHENTICATION_DYNAMIC_INCLUDES,
			_dynamicIncludes.values());

		String tabsNames = StringUtil.merge(_dynamicIncludes.keySet());

		request.setAttribute(
			PortalSettingsWebKeys.AUTHENTICATION_TABS_NAMES, tabsNames);

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

	@Deactivate
	protected void deactivated() {
		_dynamicIncludes.clear();
	}

	@Override
	protected String getJspPath() {
		return "/authentication.jsp";
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(portal.settings.authentication.tabs.name=*)"
	)
	protected void setDynamicInclude(
		DynamicInclude dynamicInclude, Map<String, Object> properties) {

		String tabsName = MapUtil.getString(
			properties, "portal.settings.authentication.tabs.name");

		_dynamicIncludes.put(tabsName, dynamicInclude);
	}

	protected void unsetDynamicInclude(
		DynamicInclude dynamicInclude, Map<String, Object> properties) {

		String tabsName = MapUtil.getString(
			properties, "portal.settings.authentication.tabs.name");

		_dynamicIncludes.remove(tabsName);
	}

	private final Map<String, DynamicInclude> _dynamicIncludes =
		new ConcurrentHashMap<>();

}