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

package com.liferay.portlet.configuration.css.web.internal.portal.settings.configuration.admin.display;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;
import com.liferay.portlet.configuration.css.web.internal.configuration.DefaultPortletDecoratorConfiguration;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author João Torres
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class DefaultPortletDecoratorPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "default-portlet-decorator";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/default_portlet_decorator_configuration.jsp";
	}

	@Override
	public String getKey() {
		return "default-portlet-decorator-configuration-name";
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(
			resourceBundle, "default-portlet-decorator-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/portal_settings/save_default_portlet_decorator";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		DefaultPortletDecoratorConfiguration
			defaultPortletDecoratorConfiguration = null;

		try {
			defaultPortletDecoratorConfiguration =
				_configurationProvider.getCompanyConfiguration(
					DefaultPortletDecoratorConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		httpServletRequest.setAttribute(
			DefaultPortletDecoratorConfiguration.class.getName(),
			defaultPortletDecoratorConfiguration);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portlet.configuration.css.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}