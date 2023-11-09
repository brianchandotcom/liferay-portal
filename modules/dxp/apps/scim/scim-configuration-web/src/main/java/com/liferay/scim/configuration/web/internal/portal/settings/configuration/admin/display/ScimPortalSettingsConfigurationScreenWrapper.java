/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.configuration.admin.display.ConfigurationScreenWrapper;
import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenFactory;
import com.liferay.scim.rest.util.ScimClientUtil;

import java.util.Dictionary;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	configurationPid = "com.liferay.scim.rest.internal.configuration.ScimClientOAuth2ApplicationConfiguration",
	service = ConfigurationScreen.class
)
public class ScimPortalSettingsConfigurationScreenWrapper
	extends ConfigurationScreenWrapper {

	@Override
	protected ConfigurationScreen getConfigurationScreen() {
		return _portalSettingsConfigurationScreenFactory.create(
			new ScimPortalSettingsConfigurationScreenContributor());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScimPortalSettingsConfigurationScreenWrapper.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Language _language;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	@Reference
	private PortalSettingsConfigurationScreenFactory
		_portalSettingsConfigurationScreenFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.scim.configuration.web)"
	)
	private ServletContext _servletContext;

	private class ScimPortalSettingsConfigurationScreenContributor
		implements PortalSettingsConfigurationScreenContributor {

		@Override
		public String getCategoryKey() {
			return "scim-name";
		}

		@Override
		public String getJspPath() {
			return "/portal_settings/scim_configuration.jsp";
		}

		@Override
		public String getKey() {
			return "scim-name";
		}

		@Override
		public String getName(Locale locale) {
			return _language.get(locale, "scim-configuration-name");
		}

		@Override
		public String getSaveMVCActionCommandName() {
			return "/scim_configuration/save_scim_configuration";
		}

		@Override
		public ServletContext getServletContext() {
			return _servletContext;
		}

		@Override
		public boolean isVisible() {
			if (!FeatureFlagManagerUtil.isEnabled("LPS-96845")) {
				return false;
			}

			return true;
		}

		@Override
		public void setAttributes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Dictionary<String, Object> properties = null;

			try {
				Configuration[] configurations =
					_configurationAdmin.listConfigurations(
						StringBundler.concat(
							"(&(", ConfigurationAdmin.SERVICE_FACTORYPID,
							"=com.liferay.scim.rest.internal.configuration.",
							"ScimClientOAuth2ApplicationConfiguration)(",
							"companyId=", themeDisplay.getCompanyId(), "))"));

				if (configurations == null) {
					return;
				}

				Configuration configuration = configurations[0];

				properties = configuration.getProperties();
			}
			catch (Exception exception) {
				ReflectionUtil.throwException(exception);
			}

			String oAuth2AplicationName = (String)properties.get("oAuth2ApplicationName");

			OAuth2Application oAuth2Application = null;

			try {
				oAuth2Application =
					_oAuth2ApplicationLocalService.getOAuth2Application(
						themeDisplay.getCompanyId(),
						ScimClientUtil.generateScimClientId(oAuth2ApplicationName));
			}
			catch (NoSuchOAuth2ApplicationException
						noSuchOAuth2ApplicationException) {

				if (_log.isInfoEnabled()) {
					_log.info(noSuchOAuth2ApplicationException);
				}

				return;
			}

			List<OAuth2Authorization> oAuth2Authorizations =
				_oAuth2AuthorizationLocalService.getOAuth2Authorizations(
					oAuth2Application.getOAuth2ApplicationId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					OrderByComparatorFactoryUtil.create(
						"OAuth2Authorization", "accessTokenExpirationDate",
						"desc"));

			if (!oAuth2Authorizations.isEmpty()) {
				OAuth2Authorization oAuth2Authorization =
					oAuth2Authorizations.get(0);

				httpServletRequest.setAttribute(
					"token", oAuth2Authorization.getAccessTokenContent());
			}

			String matcherField = (String)properties.get("matcherField");

			httpServletRequest.setAttribute("oAuth2ApplicationName", oAuth2ApplicationName);
			httpServletRequest.setAttribute("matcherField", matcherField);
		}

	}

}