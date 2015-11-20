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

package com.liferay.portal.security.auto.login.request.parameter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auto.login.request.parameter.constants.RequestParameterAutoLoginConstants;
import com.liferay.portal.security.auto.login.request.parameter.module.configuration.RequestParameterAutoLoginConfiguration;
import com.liferay.portal.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Minhchau Dang
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = "com.liferay.portal.security.auto.login.request.parameter.module.configuration.RequestParameterAutoLoginConfiguration",
	immediate = true, service = AutoLogin.class
)
public class RequestParameterAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (!isEnabled(companyId)) {
			return null;
		}

		String login = ParamUtil.getString(request, getLoginParam());

		if (Validator.isNull(login)) {
			return null;
		}

		String password = ParamUtil.getString(request, getPasswordParam());

		if (Validator.isNull(password)) {
			return null;
		}

		Company company = PortalUtil.getCompany(request);

		String authType = company.getAuthType();

		long userId = 0;

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			userId = _userLocalService.getUserIdByEmailAddress(
				company.getCompanyId(), login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			userId = _userLocalService.getUserIdByScreenName(
				company.getCompanyId(), login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			userId = GetterUtil.getLong(login);
		}
		else {
			return null;
		}

		if (userId > 0) {
			User user = _userLocalService.getUserById(userId);

			String userPassword = user.getPassword();

			if (!user.isPasswordEncrypted()) {
				userPassword = PasswordEncryptorUtil.encrypt(userPassword);
			}

			String encPassword = PasswordEncryptorUtil.encrypt(
				password, userPassword);

			if (!userPassword.equals(password) &&
				!userPassword.equals(encPassword)) {

				return null;
			}
		}

		String[] credentials = new String[] {
			String.valueOf(userId), password, Boolean.FALSE.toString()
		};

		return credentials;
	}

	protected String getLoginParam() {
		return _LOGIN_PARAM;
	}

	protected String getPasswordParam() {
		return _PASSWORD_PARAM;
	}

	protected boolean isEnabled(long companyId) {
		RequestParameterAutoLoginConfiguration
			requestParameterAutoLoginConfiguration =
				_getRequestParameterAutoLoginConfiguration(companyId);

		if (requestParameterAutoLoginConfiguration == null) {
			return false;
		}

		return requestParameterAutoLoginConfiguration.enabled();
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private RequestParameterAutoLoginConfiguration
		_getRequestParameterAutoLoginConfiguration(long companyId) {

		try {
			RequestParameterAutoLoginConfiguration
				requestParameterAutoLoginConfiguration =
					_configurationFactory.getConfiguration(
						RequestParameterAutoLoginConfiguration.class,
						new CompanyServiceSettingsLocator(
							companyId,
							RequestParameterAutoLoginConstants.SERVICE_NAME));

			return requestParameterAutoLoginConfiguration;
		}
		catch (ConfigurationException ce) {
			_log.error(
				"Unable to get request parameter auto login configuration", ce);
		}

		return null;
	}

	private static final String _LOGIN_PARAM = "parameterAutoLoginLogin";

	private static final String _PASSWORD_PARAM = "parameterAutoLoginPassword";

	private static final Log _log = LogFactoryUtil.getLog(
		RequestParameterAutoLogin.class);

	private volatile ConfigurationFactory _configurationFactory;
	private volatile UserLocalService _userLocalService;

}