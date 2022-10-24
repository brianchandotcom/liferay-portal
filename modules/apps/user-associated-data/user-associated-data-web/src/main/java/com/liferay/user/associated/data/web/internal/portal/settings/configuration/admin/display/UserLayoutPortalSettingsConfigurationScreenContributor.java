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

package com.liferay.user.associated.data.web.internal.portal.settings.configuration.admin.display;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;
import com.liferay.portal.util.PropsUtil;
import com.liferay.user.associated.data.web.internal.configuration.UserLayoutConfiguration;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fernando Vilela
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class UserLayoutPortalSettingsConfigurationScreenContributor
	implements PortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "users";
	}

	@Override
	public String getJspPath() {
		return "/portal_settings/user_layout_configuration.jsp";
	}

	@Override
	public String getKey() {
		return "user-layout-configuration";
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, "user-layout-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/portal_settings/save_user_layout_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		UserLayoutConfiguration userLayoutConfiguration = null;

		try {
			userLayoutConfiguration =
				_configurationProvider.getCompanyConfiguration(
					UserLayoutConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		if (Validator.isNotNull(userLayoutConfiguration.userPrivateLayout())) {
			PropsUtil.set(
				PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED,
				userLayoutConfiguration.userPrivateLayout());
		}

		if (Validator.isNotNull(
				userLayoutConfiguration.userPrivateLayoutAutoCreate())) {

			PropsUtil.set(
				PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE,
				userLayoutConfiguration.userPrivateLayoutAutoCreate());
		}

		if (Validator.isNotNull(userLayoutConfiguration.userPublicLayout())) {
			PropsUtil.set(
				PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED,
				userLayoutConfiguration.userPublicLayout());
		}

		if (Validator.isNotNull(
				userLayoutConfiguration.userPublicLayoutAutoCreate())) {

			PropsUtil.set(
				PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE,
				userLayoutConfiguration.userPublicLayoutAutoCreate());
		}

		httpServletRequest.setAttribute(
			UserLayoutConfiguration.class.getName(), userLayoutConfiguration);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.user.associated.data.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}