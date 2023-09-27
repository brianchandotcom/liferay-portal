/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRuleImpl extends CommerceDiscountRuleBaseImpl {

	@Override
	public UnicodeProperties getSettingsProperties() {
		if (_unicodeProperties == null) {
			_unicodeProperties = new UnicodeProperties(true);

			try {
				_unicodeProperties.load(super.getTypeSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}

		return _unicodeProperties;
	}

	@Override
	public String getSettingsProperty(String key) {
		UnicodeProperties unicodeProperties = getSettingsProperties();

		return unicodeProperties.getProperty(key);
	}

	@Override
	public void setSettingsProperties(UnicodeProperties unicodeProperties) {
		_unicodeProperties = unicodeProperties;

		super.setTypeSettings(unicodeProperties.toString());
	}

	@Override
	public void setTypeSettings(String settings) {
		_unicodeProperties = null;

		super.setTypeSettings(settings);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountRuleImpl.class);

	private UnicodeProperties _unicodeProperties;

}