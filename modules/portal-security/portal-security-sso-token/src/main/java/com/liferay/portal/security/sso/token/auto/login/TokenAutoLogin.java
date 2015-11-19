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

package com.liferay.portal.security.sso.token.auto.login;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.security.sso.token.internal.constants.TokenConstants;
import com.liferay.portal.security.sso.token.internal.module.configuration.TokenConfiguration;
import com.liferay.portal.security.sso.token.security.auth.TokenLocation;
import com.liferay.portal.security.sso.token.security.auth.TokenRetriever;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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
	configurationPid = "com.liferay.portal.security.sso.token.internal.module.configuration.TokenConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = AutoLogin.class
)
public class TokenAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		TokenConfiguration tokenCompanyServiceSettings =
			_configurationFactory.getConfiguration(
				TokenConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, TokenConstants.SERVICE_NAME));

		if (!tokenCompanyServiceSettings.enabled()) {
			return null;
		}

		String userTokenName = tokenCompanyServiceSettings.userTokenName();

		TokenLocation tokenLocation =
			tokenCompanyServiceSettings.tokenLocation();

		TokenRetriever tokenRetriever = _tokenRetrievers.get(tokenLocation);

		if (tokenRetriever == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No token retriever found for " + tokenLocation);
			}

			return null;
		}

		String login = tokenRetriever.getLoginToken(request, userTokenName);

		User user = getUser(companyId, login, tokenCompanyServiceSettings);

		addRedirect(request);

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
					user = UserImporterUtil.importUser(
						companyId, StringPool.BLANK, login);
				}
				else if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
					user = UserImporterUtil.importUser(
						companyId, login, StringPool.BLANK);
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

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
				_log.warn(
					"Incompatible setting for: " +
						PropsKeys.COMPANY_SECURITY_AUTH_TYPE +
						". Please configure to either: " +
						CompanyConstants.AUTH_TYPE_EA + " or " +
						CompanyConstants.AUTH_TYPE_SN);
			}
		}

		return user;
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setTokenRetriever(TokenRetriever tokenRetriever) {
		_tokenRetrievers.put(tokenRetriever.getTokenLocation(), tokenRetriever);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void unsetTokenRetriever(TokenRetriever tokenRetriever) {
		_tokenRetrievers.remove(tokenRetriever.getTokenLocation());
	}

	private static final Log _log = LogFactoryUtil.getLog(TokenAutoLogin.class);

	private volatile ConfigurationFactory _configurationFactory;
	private final Map<TokenLocation, TokenRetriever> _tokenRetrievers =
		new ConcurrentHashMap<>();
	private volatile UserLocalService _userLocalService;

}