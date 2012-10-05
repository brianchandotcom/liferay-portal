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
 */
public class HexUtil {

	public static byte[] decodeHex(char[] data) throws Exception {
		return getHex().decodeHex(data);
	}

	public static char[] encodeHex(byte[] data) {
		return getHex().encodeHex(data);
	}

	public static char[] encodeHex(byte[] data, boolean toLowerCase) {
		return getHex().encodeHex(data, toLowerCase);
	}

	public static String encodeHexString(byte[] data) {
		return getHex().encodeHexString(data);
	}

	public static Hex getHex() {
		PortalRuntimePermission.checkGetBeanProperty(HexUtil.class);

		return _hex;
	}

	public void setHez(Hex hex) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_hex = hex;
	}

	private static Hex _hex;

}