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

package com.liferay.multi.factor.authentication.checker.email.otp.web.internal.settings;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marta Medio
 */
public class EmailOTPConfigurationLocalizedValuesMap
	extends LocalizedValuesMap {

	public EmailOTPConfigurationLocalizedValuesMap(
		String defaultValue, UnicodeProperties properties) {

		super(defaultValue);

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String key = entry.getKey();

			String[] localeData = key.split("_");

			super.put(
				new Locale(localeData[0], localeData[1]), entry.getValue());
		}
	}

}