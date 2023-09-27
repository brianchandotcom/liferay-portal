/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (samlIdpSessionMaximumAge > 0) {
			Date createDate = getCreateDate();

			long expirationTime =
				createDate.getTime() + (samlIdpSessionMaximumAge * Time.SECOND);

			if (System.currentTimeMillis() > expirationTime) {
				return true;
			}
		}

		long samlIdpSessionTimeout = samlProviderConfiguration.sessionTimeout();

		if (samlIdpSessionTimeout <= 0) {
			return false;
		}

		Date modifiedDate = getModifiedDate();

		long expirationTime =
			modifiedDate.getTime() + (samlIdpSessionTimeout * Time.SECOND);

		if (System.currentTimeMillis() > expirationTime) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlIdpSsoSessionImpl.class);

}