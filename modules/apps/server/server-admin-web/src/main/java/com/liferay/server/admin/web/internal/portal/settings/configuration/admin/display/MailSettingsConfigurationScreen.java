/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.language.Language;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Erick Monteiro
 */
@Component(service = ConfigurationScreen.class)
public class MailSettingsConfigurationScreen implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "email";
	}

	@Override
	public String getKey() {
		return "mail-settings";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "mail-settings");
	}

	@Override
	public String getScope() {
		return "company";
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/company_mail.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render company_mail.jsp", exception);
		}
	}

	protected String getJspPath() {
		return "/company_mail.jsp";
	}

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.server.admin.web)")
	private ServletContext _servletContext;

}