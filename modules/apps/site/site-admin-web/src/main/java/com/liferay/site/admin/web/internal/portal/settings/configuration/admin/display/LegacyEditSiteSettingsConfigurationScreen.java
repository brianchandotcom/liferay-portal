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
import com.liferay.portal.kernel.language.LanguageUtil;

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
public class LegacyEditSiteSettingsConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "site-configuration";
	}

	@Override
	public String getKey() {
		return "site-configuration-general";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "general");
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

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/edit_site.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException("Unable to render edit_site.jsp", exception);
		}
	}

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

}