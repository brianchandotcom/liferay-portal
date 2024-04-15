/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.web.internal.configuration.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.configuration.admin.display.ConfigurationScreenWrapper;
import com.liferay.portal.defaultpermissions.resource.PortalDefaultPermissionsModelResourceRegistry;
import com.liferay.portal.defaultpermissions.web.internal.display.context.PortalDefaultPermissionsCompanyConfigurationDisplayContext;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenFactory;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ConfigurationScreen.class)
public class PortalDefaultPermissionsCompanyConfigurationScreenWrapper
	extends ConfigurationScreenWrapper {

	@Override
	protected ConfigurationScreen getConfigurationScreen() {
		return _portalSettingsConfigurationScreenFactory.create(
			new DefaultPermissionsPortalConfigurationScreenContributor());
	}

	@Reference
	private Language _language;

	@Reference
	private PortalDefaultPermissionsModelResourceRegistry
		_portalDefaultPermissionsModelResourceRegistry;

	@Reference
	private PortalSettingsConfigurationScreenFactory
		_portalSettingsConfigurationScreenFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.defaultpermissions.web)"
	)
	private ServletContext _servletContext;

	private class DefaultPermissionsPortalConfigurationScreenContributor
		implements PortalSettingsConfigurationScreenContributor {

		@Override
		public String getCategoryKey() {
			return "default-permissions";
		}

		@Override
		public String getJspPath() {
			return "/configuration" +
				"/portal_default_permissions_company_configuration.jsp";
		}

		@Override
		public String getKey() {
			return "default-permissions-company-configuration";
		}

		@Override
		public String getName(Locale locale) {
			return _language.get(locale, "default-permissions");
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
			if (FeatureFlagManagerUtil.isEnabled("LPD-21265")) {
				return true;
			}

			return false;
		}

		@Override
		public void setAttributes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			httpServletRequest.setAttribute(
				PortalDefaultPermissionsCompanyConfigurationDisplayContext.
					class.getName(),
				new PortalDefaultPermissionsCompanyConfigurationDisplayContext(
					httpServletRequest, _language,
					_portalDefaultPermissionsModelResourceRegistry));
		}

	}

}