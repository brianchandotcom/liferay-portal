/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Connor McKay
 */
public class URLStringEncoder implements StringEncoder {

	@Override
	public String decode(String s) {
		return HttpComponentsUtil.decodeURL(s);
	}

	@Override
	public String encode(String s) {
		return URLCodec.encodeURL(s);
	}

}