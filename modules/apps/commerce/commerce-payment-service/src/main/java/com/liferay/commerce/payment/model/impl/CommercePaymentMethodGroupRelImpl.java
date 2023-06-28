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

package com.liferay.commerce.payment.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

/**
 * @author Luca Pellizzon
 */
public class CommercePaymentMethodGroupRelImpl
	extends CommercePaymentMethodGroupRelBaseImpl {

	@Override
	public String getImageURL(ThemeDisplay themeDisplay) {
		if (getImageId() <= 0) {
			return null;
		}

		return StringBundler.concat(
			themeDisplay.getPathImage(), "/payment/method?img_id=",
			getImageId(), "&t=",
			WebServerServletTokenUtil.getToken(getImageId()));
	}

	@Override
	public UnicodeProperties getTypeSettingsUnicodeProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				getTypeSettings()
			).build();
		}

		return _typeSettingsUnicodeProperties;
	}

	private transient UnicodeProperties _typeSettingsUnicodeProperties;

}