/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Julio Camarero
 * @author Samuel Kong
 */
public class FriendlyURLNormalizerUtil {

	public static FriendlyURLNormalizer getFriendlyURLNormalizer() {
		PortalRuntimePermission.checkGetBeanProperty(
			FriendlyURLNormalizerUtil.class);

		return _friendlyURLNormalizer;
	}

	public static String normalize(String friendlyURL) {
		return getFriendlyURLNormalizer().normalize(friendlyURL);
	}

	public static String normalize(String friendlyURL, char[] replaceChars) {
		return getFriendlyURLNormalizer().normalize(friendlyURL, replaceChars);
	}

	public void setFriendlyURLNormalizer(
		FriendlyURLNormalizer friendlyURLNormalizer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_friendlyURLNormalizer = friendlyURLNormalizer;
	}

	private static FriendlyURLNormalizer _friendlyURLNormalizer;

}