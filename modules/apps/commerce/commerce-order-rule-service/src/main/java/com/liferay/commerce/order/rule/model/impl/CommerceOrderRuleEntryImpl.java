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

package com.liferay.commerce.order.rule.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

/**
 * @author Luca Pellizzon
 */
public class CommerceOrderRuleEntryImpl extends CommerceOrderRuleEntryBaseImpl {

	@Override
	public UnicodeProperties getSettingsProperties() {
		if (_unicodeProperties == null) {
			_unicodeProperties = new UnicodeProperties(true);

			try {
				_unicodeProperties.load(super.getTypeSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException, ioException);
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
	public void setTypeSettings(String settings) {
		_unicodeProperties = null;

		super.setTypeSettings(settings);
	}

	@Override
	public void setTypeSettingsProperties(UnicodeProperties unicodeProperties) {
		_unicodeProperties = unicodeProperties;

		super.setTypeSettings(unicodeProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderRuleEntryImpl.class);

	private UnicodeProperties _unicodeProperties;

}