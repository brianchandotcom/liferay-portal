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

package com.liferay.digital.signature.configuration;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author José Abelenda
 */
public class DigitalSignatureConfigurationUtil {

	public static DigitalSignatureConfiguration
		getDigitalSignatureConfiguration(long companyId, long groupId) {

		try {
			DigitalSignatureConfiguration companyDigitalSignatureConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					DigitalSignatureConfiguration.class, companyId);

			if ((groupId == 0) ||
				Objects.equals(
					companyDigitalSignatureConfiguration.siteSettingsStrategy(),
					"always-inherit")) {

				return companyDigitalSignatureConfiguration;
			}

			DigitalSignatureConfiguration groupDigitalSignatureConfiguration =
				ConfigurationProviderUtil.getGroupConfiguration(
					DigitalSignatureConfiguration.class, groupId);

			if (Objects.equals(
					companyDigitalSignatureConfiguration.siteSettingsStrategy(),
					"always-override")) {

				return groupDigitalSignatureConfiguration;
			}

			if (Objects.equals(
					companyDigitalSignatureConfiguration.siteSettingsStrategy(),
					"inherit-or-override")) {

				if (Validator.isNotNull(
						groupDigitalSignatureConfiguration.apiUsername()) &&
					Validator.isNotNull(
						groupDigitalSignatureConfiguration.apiAccountId()) &&
					Validator.isNotNull(
						groupDigitalSignatureConfiguration.accountBaseURI()) &&
					Validator.isNotNull(
						groupDigitalSignatureConfiguration.integrationKey()) &&
					Validator.isNotNull(
						groupDigitalSignatureConfiguration.rsaPrivateKey())) {

					return groupDigitalSignatureConfiguration;
				}

				return companyDigitalSignatureConfiguration;
			}

			return companyDigitalSignatureConfiguration;
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

}