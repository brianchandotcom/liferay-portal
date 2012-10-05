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

package com.liferay.portal.util;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Julio Camarero
 */
public class HexImpl implements com.liferay.portal.kernel.util.Hex {

	public byte[] decodeHex(char[] data) throws Exception {
		return Hex.decodeHex(data);
	}

	public char[] encodeHex(byte[] data) {
		return Hex.encodeHex(data);
	}

	public char[] encodeHex(byte[] data, boolean toLowerCase) {
		return Hex.encodeHex(data, toLowerCase);
	}

	public String encodeHexString(byte[] data) {
		return Hex.encodeHexString(data);
	}

}