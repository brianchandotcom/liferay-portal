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

package com.liferay.petra.io.unsync;

import com.liferay.petra.string.StringBundler;

/**
 * @author Preston Crary
 */
class BoundaryCheckerUtil {

	public static void check(int count, int offset, int length) {
		int end = offset + length;

		if ((offset < 0) || (offset > count) || (length < 0) || (end > count) ||
			(end < 0)) {

			StringBundler sb = new StringBundler(7);

			sb.append("{count=");
			sb.append(count);
			sb.append(", offset=");
			sb.append(offset);
			sb.append(", length=");
			sb.append(length);
			sb.append("}");

			throw new IndexOutOfBoundsException(sb.toString());
		}
	}

}