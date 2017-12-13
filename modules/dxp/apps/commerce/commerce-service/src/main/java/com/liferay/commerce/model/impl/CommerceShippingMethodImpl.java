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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceShippingMethodImpl extends CommerceShippingMethodBaseImpl {

	public CommerceShippingMethodImpl() {
	}

	@Override
	public String getShippingMethodImageURL(ThemeDisplay themeDisplay) {
		if (getImageId() <= 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/shipping/method?img_id=");
		sb.append(String.valueOf(getImageId()));
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(getImageId()));

		return sb.toString();
	}

}