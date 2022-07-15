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

package com.liferay.google.places.web.internal.portal.settings.configuration.admin.display;

import com.liferay.google.places.constants.GooglePlacesWebKeys;
import com.liferay.google.places.util.GooglePlacesUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class GooglePlacesPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "maps";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/google_places.jsp";
	}

	@Override
	public String getKey() {
		return "google-places-portal-settings";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "google-places");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/portal_settings/edit_company";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		PortalSettingsConfigurationScreenContributor.super.setAttributes(
			httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY,
			GooglePlacesUtil.getGooglePlacesAPIKey(
				themeDisplay.getCompanyId()));
	}

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.google.places.web)")
	private ServletContext _servletContext;

}