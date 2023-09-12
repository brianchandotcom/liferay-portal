/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.click.to.chat.web.internal.configuration;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author José Abelenda
 */
public class ClickToChatConfigurationUtil {

	public static ClickToChatConfiguration getClickToChatConfiguration(
		long companyId, long groupId) {

		try {
			ClickToChatConfiguration companyClickToChatConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					ClickToChatConfiguration.class, companyId);

			if ((groupId == 0) ||
				Objects.equals(
					companyClickToChatConfiguration.siteSettingsStrategy(),
					"always-inherit")) {

				return companyClickToChatConfiguration;
			}

			ClickToChatConfiguration groupClickToChatConfiguration =
				ConfigurationProviderUtil.getGroupConfiguration(
					ClickToChatConfiguration.class, groupId);

			if (Objects.equals(
					companyClickToChatConfiguration.siteSettingsStrategy(),
					"always-override")) {

				return groupClickToChatConfiguration;
			}

			if (Objects.equals(
					companyClickToChatConfiguration.siteSettingsStrategy(),
					"inherit-or-override")) {

				if (Validator.isNotNull(
						groupClickToChatConfiguration.
							chatProviderAccountId()) &&
					Validator.isNotNull(
						groupClickToChatConfiguration.chatProviderId())) {

					return groupClickToChatConfiguration;
				}

				return companyClickToChatConfiguration;
			}

			return companyClickToChatConfiguration;
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

}