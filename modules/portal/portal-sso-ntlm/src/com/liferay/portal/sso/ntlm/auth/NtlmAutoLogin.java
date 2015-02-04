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

package com.liferay.portal.sso.ntlm.auth;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.BaseAutoLogin;
import com.liferay.portal.security.exportimport.UserImporterUtil;
import com.liferay.portal.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.sso.ntlm.constants.NtlmWebKeys;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bruno Farache
 */
@Component(immediate = true, service = AutoLogin.class)
public class NtlmAutoLogin extends BaseAutoLogin {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ntlmConfiguration = Configurable.createConfigurable(
			NtlmConfiguration.class, properties);
	}

	@Override
	protected String[] doLogin(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.NTLM_AUTH_ENABLED,
			_ntlmConfiguration.enabled())) {

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

	private volatile NtlmConfiguration _ntlmConfiguration;

}