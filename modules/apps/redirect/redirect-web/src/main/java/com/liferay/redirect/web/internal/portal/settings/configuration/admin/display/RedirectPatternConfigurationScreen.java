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

package com.liferay.redirect.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;
import com.liferay.redirect.web.internal.display.context.RedirectPatternConfigurationDisplayContext;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.PortletResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(immediate = true, service = ConfigurationScreen.class)
public class RedirectPatternConfigurationScreen implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "pages";
	}

	@Override
	public String getKey() {
		return "redirect-pattern-configuration-" + getScope();
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "redirect-pattern-configuration-name");
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.GROUP.getValue();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			httpServletRequest.setAttribute(
				RedirectPatternConfigurationDisplayContext.class.getName(),
				new RedirectPatternConfigurationDisplayContext(
					httpServletRequest,
					_portal.getLiferayPortletResponse(
						(PortletResponse)httpServletRequest.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE)),
					_redirectPatternConfigurationProvider,
					themeDisplay.getScopeGroupId()));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/redirect_settings/redirect_pattern.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render redirect_pattern.jsp", exception);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private RedirectPatternConfigurationProvider
		_redirectPatternConfigurationProvider;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.redirect.web)")
	private ServletContext _servletContext;

}