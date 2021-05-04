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

package com.liferay.site.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ConfigurationScreen.class)
public class SiteTemplateSiteSettingsConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "other";
	}

	@Override
	public String getKey() {
		return "site-configuration-site-template";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "site-template");
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.GROUP.getValue();
	}

	@Override
	public boolean isVisible() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		Group siteGroup = themeDisplay.getSiteGroup();

		if ((siteGroup == null) || siteGroup.isCompany()) {
			return false;
		}

		LayoutSet privateLayoutSet = null;
		LayoutSet publicLayoutSet = null;

		try {
			privateLayoutSet = _layoutSetLocalService.getLayoutSet(
				siteGroup.getGroupId(), true);
			publicLayoutSet = _layoutSetLocalService.getLayoutSet(
				siteGroup.getGroupId(), false);
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return false;
		}

		if ((privateLayoutSet == null) && (publicLayoutSet == null)) {
			return false;
		}

		LayoutSetPrototype privateLayoutSetPrototype = null;

		if (Validator.isNotNull(privateLayoutSet.getLayoutSetPrototypeUuid())) {
			privateLayoutSetPrototype =
				_layoutSetPrototypeLocalService.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						privateLayoutSet.getLayoutSetPrototypeUuid(),
						themeDisplay.getCompanyId());
		}

		LayoutSetPrototype publicLayoutSetPrototype = null;

		if (Validator.isNotNull(publicLayoutSet.getLayoutSetPrototypeUuid())) {
			publicLayoutSetPrototype =
				_layoutSetPrototypeLocalService.
					fetchLayoutSetPrototypeByUuidAndCompanyId(
						publicLayoutSet.getLayoutSetPrototypeUuid(),
						themeDisplay.getCompanyId());
		}

		if ((publicLayoutSetPrototype == null) &&
			(privateLayoutSetPrototype == null)) {

			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/site_settings/site_template.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render site_template.jsp", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteTemplateSiteSettingsConfigurationScreen.class);

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

}