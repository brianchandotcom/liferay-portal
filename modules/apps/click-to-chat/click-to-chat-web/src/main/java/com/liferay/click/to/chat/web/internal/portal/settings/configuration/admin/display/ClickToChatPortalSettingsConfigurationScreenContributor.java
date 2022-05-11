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

package com.liferay.click.to.chat.web.internal.portal.settings.configuration.admin.display;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class ClickToChatPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "click-to-chat";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/click_to_chat.jsp";
	}

	@Override
	public String getKey() {
		return "click-to-chat";
	}

	@Override
	public String getName(Locale locale) {
		return _language.get(locale, "click-to-chat-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/click_to_chat/save_company_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ClickToChatConfiguration clickToChatConfiguration = null;

		try {
			clickToChatConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ClickToChatConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		httpServletRequest.setAttribute(
			ClickToChatConfiguration.class.getName(), clickToChatConfiguration);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.click.to.chat.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}