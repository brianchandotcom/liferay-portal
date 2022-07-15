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

package com.liferay.frontend.icons.web.internal.portal.settings.configuration.admin.display;

import com.liferay.frontend.icons.web.internal.display.context.FrontendIconsConfigurationDisplayContext;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class FrontendIconsPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "frontend-icons";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/frontend_icons.jsp";
	}

	@Override
	public String getKey() {
		return "frontend-icons";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(
			locale, "frontend-icons-instance-configuration-name");
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
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute(
			FrontendIconsConfigurationDisplayContext.class.getName(),
			new FrontendIconsConfigurationDisplayContext(
				_frontendIconsResourcePackRepository, httpServletRequest,
				(RenderResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE)));
	}

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.icons.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}