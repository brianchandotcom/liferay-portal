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

package com.liferay.portal.kernel.util;

/**
 * @author Michael C. Han
 */
public class ByteUtils {

	public static int getNumberBits(int intValue) {
		return (int)Math.ceil(Math.log(intValue) / Math.log(2));
	}

	public static short toUnsigned(byte b) {
		if (b < 0) {
			return (short)(256 + b);
		}
		else {
			return (short)b;
		}
	}

}