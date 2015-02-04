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

package com.liferay.portal.sso.ntlm;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.sso.SSO;
import com.liferay.portal.sso.ntlm.configuration.NtlmConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SSO.class)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		return defaultSigninURL;
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.NTLM_AUTH_ENABLED,
				_ntlmConfiguration.enabled())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		return false;
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		return false;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ntlmConfiguration = Configurable.createConfigurable(
			NtlmConfiguration.class, properties);
	}

	private volatile NtlmConfiguration _ntlmConfiguration;

}