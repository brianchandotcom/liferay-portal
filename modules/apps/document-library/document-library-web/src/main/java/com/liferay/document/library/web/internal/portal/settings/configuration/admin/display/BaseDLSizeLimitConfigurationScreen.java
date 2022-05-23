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

package com.liferay.document.library.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDLSizeLimitConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "documents-and-media";
	}

	@Override
	public String getKey() {
		return "dl-size-limit-configuration-" + getScope();
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "dl-size-limit-configuration-name");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(
					"/document_library_settings/size_limit.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException("Unable to render size_limit.jsp", exception);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.web)"
	)
	protected ServletContext servletContext;

}