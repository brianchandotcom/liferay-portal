/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.internal.security.sso;

import com.liferay.portal.kernel.security.sso.SSO;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(immediate = true, service = SSO.class)
public class SSOImpl implements SSO {

	@Override
	public String getSessionExpirationRedirectUrl(long companyId) {
		return null;
	}

	@Override
	public String getSignInURL(long companyId, String defaultSigninURL) {
		return null;
	}

	@Override
	public boolean isLoginRedirectRequired(long companyId) {
		return _samlProviderConfigurationHelper.isEnabled();
	}

	@Override
	public boolean isRedirectRequired(long companyId) {
		return false;
	}

	@Override
	public boolean isSessionRedirectOnExpire(long companyId) {
		return false;
	}

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}