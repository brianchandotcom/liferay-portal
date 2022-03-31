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

package com.liferay.frontend.icons.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.frontend.icons.web.internal.display.context.FrontendIconsConfigurationDisplayContext;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(service = ConfigurationScreen.class)
public class FrontendIconsConfigurationScreen implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "frontend-icons";
	}

	@Override
	public String getKey() {
		return "frontend-icons";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, getClass()),
			"frontend-icons-configuration-name");
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.COMPANY.getValue();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			FrontendIconsConfigurationDisplayContext.class.getName(),
			new FrontendIconsConfigurationDisplayContext(
				_frontendIconsResourcePackRepository, httpServletRequest,
				(RenderResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE)));

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/portal_settings/frontend_icons.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render frontend_icons.jsp", exception);
		}
	}

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.icons.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}