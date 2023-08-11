/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.portal.kernel.language.Language;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class LockedPagesSiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "pages";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/locked_pages.jsp";
	}

	@Override
	public String getKey() {
		return "site-configuration-locked-pages";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "locked-pages");
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.site.admin.web)")
	private ServletContext _servletContext;

}