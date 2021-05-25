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
			DigitalSignatureConfiguration digitalSignatureConfigurationCompany =
				ConfigurationProviderUtil.getCompanyConfiguration(
					DigitalSignatureConfiguration.class, companyId);

			if (groupId == 0) {
				return digitalSignatureConfigurationCompany;
			}

			if (Objects.equals(
					digitalSignatureConfigurationCompany.siteSettingsStrategy(),
					"always-inherit")) {

				return digitalSignatureConfigurationCompany;
			}

			DigitalSignatureConfiguration digitalSignatureConfigurationGroup =
				ConfigurationProviderUtil.getGroupConfiguration(
					DigitalSignatureConfiguration.class, groupId);

			if (Objects.equals(
					digitalSignatureConfigurationCompany.siteSettingsStrategy(),
					"always-override")) {

				return digitalSignatureConfigurationGroup;
			}

			if (Objects.equals(
					digitalSignatureConfigurationCompany.siteSettingsStrategy(),
					"inherit-or-override")) {

				if (Validator.isNotNull(
						digitalSignatureConfigurationGroup.apiUsername()) &&
					Validator.isNotNull(
						digitalSignatureConfigurationGroup.apiAccountId()) &&
					Validator.isNotNull(
						digitalSignatureConfigurationGroup.accountBaseURI()) &&
					Validator.isNotNull(
						digitalSignatureConfigurationGroup.integrationKey()) &&
					Validator.isNotNull(
						digitalSignatureConfigurationGroup.rsaPrivateKey())) {

					return digitalSignatureConfigurationGroup;
				}

				return digitalSignatureConfigurationCompany;
			}

			return digitalSignatureConfigurationCompany;
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

}