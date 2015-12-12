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

package com.liferay.portal.security.sso.ntlm.auto.login;

import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.BaseAutoLogin;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;
import com.liferay.portal.security.sso.ntlm.constants.NtlmWebKeys;
import com.liferay.portal.security.sso.ntlm.module.configuration.NtlmConfiguration;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Participates in every unauthenticated HTTP request to Liferay Portal.
 * 
 * <p>
 * This class looks for the NTLM_REMOTE_USER request attribute which contains
 * the user's screen name if authentication took place via NTLM. If found, the
 * user is imported from LDAP and logged in.
 * </p>
 * 
 * @author Bruno Farache
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.ntlm.module.configuration.NtlmConfiguration",
	immediate = true, service = AutoLogin.class
)
public class NtlmAutoLogin extends BaseAutoLogin {

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		NtlmConfiguration ntlmConfiguration =
			_configurationFactory.getConfiguration(
				NtlmConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, NtlmConstants.SERVICE_NAME));

		if (!ntlmConfiguration.enabled()) {
			return null;
		}

		String screenName = (String)request.getAttribute(
			NtlmWebKeys.NTLM_REMOTE_USER);

		if (screenName == null) {
			return null;
		}

		request.removeAttribute(NtlmWebKeys.NTLM_REMOTE_USER);

		User user = UserImporterUtil.importUserByScreenName(
			companyId, screenName);

		if (user == null) {
			return null;
		}

		addRedirect(request);

		String[] credentials = new String[3];

		credentials[0] = String.valueOf(user.getUserId());
		credentials[1] = user.getPassword();
		credentials[2] = Boolean.TRUE.toString();

		return credentials;
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private volatile ConfigurationFactory _configurationFactory;

}