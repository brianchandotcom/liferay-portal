/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Jorge Ferrer
 */
public class OpenIdUtil {

	public static boolean isEnabled(long companyId) {
		return _openId.isEnabled(companyId);
	}

	protected static OpenId getOpenId() {
		return _openId;
	}

	private OpenIdUtil() {
	}

	private static volatile OpenId _openId =
		ServiceProxyFactory.newServiceTrackedInstance(
			OpenId.class, OpenIdUtil.class, "_openId", false, true);

}