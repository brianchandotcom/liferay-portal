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

package com.liferay.portal.security.sso.token.internal.auto.login;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.exportimport.UserImporter;
import com.liferay.portal.security.sso.token.internal.configuration.TokenConfiguration;
import com.liferay.portal.security.sso.token.internal.constants.TokenConstants;
import com.liferay.portal.security.sso.token.security.auth.TokenRetriever;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Participates in every unauthenticated HTTP request to Liferay Portal.
 *
 * <p>
 * If this class finds an authentication token in the HTTP request and
 * successfully identifies a Liferay Portal user using it, then this user is
 * logged in without any further challenge.
 * </p>
 *
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.token.internal.configuration.TokenConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	service = AutoLogin.class
)
public class TokenAutoLogin extends BaseAutoLogin {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_tokenRetrievers = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, TokenRetriever.class, "token.location");
	}

	@Deactivate
	protected void deactivate() {
		_tokenRetrievers.close();
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long companyId = _portal.getCompanyId(httpServletRequest);

		TokenConfiguration tokenCompanyServiceSettings =
			_configurationProvider.getConfiguration(
				TokenConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, TokenConstants.SERVICE_NAME));

		if (!tokenCompanyServiceSettings.enabled()) {
			return null;
		}

		String userTokenName = tokenCompanyServiceSettings.userTokenName();

		String tokenLocation = tokenCompanyServiceSettings.tokenLocation();

		TokenRetriever tokenRetriever = _tokenRetrievers.getService(
			tokenLocation);

		if (tokenRetriever == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No token retriever found for " + tokenLocation);
			}

			return null;
		}

		String login = tokenRetriever.getLoginToken(
			httpServletRequest, userTokenName);

		if (Validator.isNull(login)) {
			if (_log.isInfoEnabled()) {
				_log.info("No login found for " + tokenLocation);
			}

			return null;
		}

		User user = getUser(companyId, login, tokenCompanyServiceSettings);

		addRedirect(httpServletRequest);

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	protected User getUser(
			long companyId, String login,
			TokenConfiguration tokenCompanyServiceSettings)
		throws PortalException {

		User user = null;

		String authType = PrefsPropsUtil.getString(
			companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
			PropsValues.COMPANY_SECURITY_AUTH_TYPE);

		if (tokenCompanyServiceSettings.importFromLDAP()) {
			try {
				if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					user = _userImporter.importUser(
						companyId, StringPool.BLANK, login);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					user = _userImporter.importUser(
						companyId, login, StringPool.BLANK);
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(7);

						sb.append("The property \"");
						sb.append(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
						sb.append("\" must be set to either \"");
						sb.append(CompanyConstants.AUTH_TYPE_EA);
						sb.append("\" or \"");
						sb.append(CompanyConstants.AUTH_TYPE_SN);
						sb.append("\"");

						_log.warn(sb.toString());
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to import from LDAP", e);
				}
			}
		}

		if (user != null) {
			return user;
		}

		if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			user = _userLocalService.getUserByScreenName(companyId, login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			user = _userLocalService.getUserByEmailAddress(companyId, login);
		}
		else {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(6);

				sb.append("Incompatible setting for: ");
				sb.append(PropsKeys.COMPANY_SECURITY_AUTH_TYPE);
				sb.append(". Please configure to either: ");
				sb.append(CompanyConstants.AUTH_TYPE_EA);
				sb.append(" or ");
				sb.append(CompanyConstants.AUTH_TYPE_SN);

				_log.warn(sb.toString());
			}
		}

		return user;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setUserImporter(UserImporter userImporter) {
		_userImporter = userImporter;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(TokenAutoLogin.class);

	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, TokenRetriever> _tokenRetrievers;
	private UserImporter _userImporter;
	private UserLocalService _userLocalService;

}