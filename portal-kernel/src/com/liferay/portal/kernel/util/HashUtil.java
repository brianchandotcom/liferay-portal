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
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.petra.lang.HashUtil}
 */
@Deprecated
public class HashUtil {

	public static int hash(int seed, boolean value) {
		return com.liferay.petra.lang.HashUtil.hash(seed, value);
	}

	public static int hash(int seed, int value) {
		return com.liferay.petra.lang.HashUtil.hash(seed, value);
	}

	public static int hash(int seed, long value) {
		return com.liferay.petra.lang.HashUtil.hash(seed, value);
	}

	public static int hash(int seed, Object value) {
		return com.liferay.petra.lang.HashUtil.hash(seed, value);
	}

}