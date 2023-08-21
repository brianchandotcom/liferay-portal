/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portal.settings.configuration.admin.display;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class LockedLayoutsPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "pages";
	}

	@Override
	public String getJspPath() {
		return "/configuration/locked_layouts.jsp";
	}

	@Override
	public String getKey() {
		return "locked-layouts-portal-settings";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "locked-pages");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public boolean isVisible() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328")) {
			return false;
		}

		return true;
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.content.page.editor.web)"
	)
	private ServletContext _servletContext;

}