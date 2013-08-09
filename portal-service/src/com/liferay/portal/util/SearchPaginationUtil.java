/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * @author Roberto Díaz
 */
public class SearchPaginationUtil {

	public static int[] calculateStartAndEnd(int start, int end, int total) {
		if ((total > 0) && (start >= total)) {
			int delta = end - start;

			int cur = start / delta;

			start = 0;

			if (cur > 0) {
				start = (cur - 1) * delta;
			}

			end = start + delta;
		}

		return new int[] {start, end};
	}

}