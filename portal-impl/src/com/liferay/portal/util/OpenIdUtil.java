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