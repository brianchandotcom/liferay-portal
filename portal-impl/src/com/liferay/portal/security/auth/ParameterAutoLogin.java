/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.verifier.AuthVerifier;
import com.liferay.portal.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Minhchau Dang
 * @author Tomas Polesovsky
 */
public class ParameterAutoLogin implements AutoLogin, AuthVerifier {

	public String[] login(
			HttpServletRequest request, HttpServletResponse response)
		throws AutoLoginException {

		try {
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
				userId = UserLocalServiceUtil.getUserIdByEmailAddress(
					company.getCompanyId(), login);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				userId = UserLocalServiceUtil.getUserIdByScreenName(
					company.getCompanyId(), login);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				userId = GetterUtil.getLong(login);
			}
			else {
				return null;
			}

			if (userId > 0) {
				User user = UserLocalServiceUtil.getUserById(userId);

				String userPassword = user.getPassword();

				if (!user.isPasswordEncrypted()) {
					userPassword = PwdEncryptor.encrypt(userPassword);
				}

				String encPassword = PwdEncryptor.encrypt(password);

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
		catch (Exception e) {
			throw new AutoLoginException(e);
		}
	}

	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties configuration)
		throws AuthException {

		AuthVerifierResult result = new AuthVerifierResult();

		try {
			String[] loginResult = login(
				accessControlContext.getHttpServletRequest(),
				accessControlContext.getHttpServletResponse());

			if (loginResult != null) {
				result.setState(AuthVerifierResult.State.SUCCESS);
				result.setUserId(Long.valueOf(loginResult[0]));
				result.setPassword(loginResult[1]);
			}
		}
		catch (AutoLoginException e) {
			throw new AuthException(e);
		}

		return result;
	}

	protected String getLoginParam() {
		return _LOGIN_PARAM;
	}

	protected String getPasswordParam() {
		return _PASSWORD_PARAM;
	}

	private static final String _LOGIN_PARAM = "parameterAutoLoginLogin";

	private static final String _PASSWORD_PARAM = "parameterAutoLoginPassword";

}