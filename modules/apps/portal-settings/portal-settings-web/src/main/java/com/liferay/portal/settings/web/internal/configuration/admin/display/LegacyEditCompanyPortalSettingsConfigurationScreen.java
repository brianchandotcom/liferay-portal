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

package com.liferay.portal.settings.web.internal.configuration.admin.display;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class LegacyEditCompanyPortalSettingsConfigurationScreen
	extends BaseEditCompanyPortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "instance-configuration";
	}

	@Override
	public String getJspPath() {
		return "/general.jsp";
	}

	@Override
	public String getKey() {
		return "general";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "general");
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.settings.web)"
	)
	private ServletContext _servletContext;

}