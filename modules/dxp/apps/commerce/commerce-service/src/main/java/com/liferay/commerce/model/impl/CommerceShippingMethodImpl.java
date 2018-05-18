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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceShippingMethodImpl extends CommerceShippingMethodBaseImpl {

	public CommerceShippingMethodImpl() {
	}

	@Override
	public String getImageURL(ThemeDisplay themeDisplay) {
		if (getImageId() <= 0) {
			return null;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathImage());
		sb.append("/shipping/method?img_id=");
		sb.append(getImageId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(getImageId()));

		return sb.toString();
	}

}