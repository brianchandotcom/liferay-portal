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

package com.liferay.google.places.web.internal.site.settings.configuration.admin.display;

import com.liferay.google.places.constants.GooglePlacesWebKeys;
import com.liferay.google.places.util.GooglePlacesUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Paulino
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class GooglePlacesSiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "maps";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/google_places.jsp";
	}

	@Override
	public String getKey() {
		return "google-places-site-settings";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "google-places");
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		SiteSettingsConfigurationScreenContributor.super.setAttributes(
			httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getSiteGroup();

		httpServletRequest.setAttribute(
			GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY,
			GooglePlacesUtil.getGooglePlacesAPIKey(
				themeDisplay.getCompanyId(), group.getGroupId(),
				_groupLocalService));
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.google.places.web)")
	private ServletContext _servletContext;

}