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