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

package com.liferay.saml.persistence.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelperUtil;

import java.util.Date;

/**
 * @author Mika Koivisto
 */
public class SamlIdpSsoSessionImpl extends SamlIdpSsoSessionBaseImpl {

	public SamlIdpSsoSessionImpl() {
	}

	@Override
	public boolean isExpired() {
		SamlProviderConfiguration samlProviderConfiguration =
			SamlProviderConfigurationHelperUtil.getSamlProviderConfiguration();

		if (samlProviderConfiguration == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get SAML provider configuration");
			}

			return true;
		}

		long samlIdpSessionMaximumAge =
			samlProviderConfiguration.sessionMaximumAge();
		long samlIdpSessionTimeout = samlProviderConfiguration.sessionTimeout();

		if (samlIdpSessionMaximumAge > 0) {
			Date createDate = getCreateDate();

			long expirationTime =
				createDate.getTime() + samlIdpSessionMaximumAge * Time.SECOND;

			if (System.currentTimeMillis() > expirationTime) {
				return true;
			}
		}

		if (samlIdpSessionTimeout <= 0) {
			return false;
		}

		Date modifiedDate = getModifiedDate();

		long expirationTime =
			modifiedDate.getTime() + samlIdpSessionTimeout * Time.SECOND;

		if (System.currentTimeMillis() > expirationTime) {
			return true;
		}
		else {
			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlIdpSsoSessionImpl.class);

}