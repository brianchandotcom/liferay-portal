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

package com.liferay.portal.kernel.dao.search;

/**
 * @author Roberto Díaz
 */
public class SearchPaginationUtil {

	public static int[] calculateStartAndEnd(int cur, int delta) {
		int start = 0;

		if (cur > 0) {
			start = (cur - 1) * delta;
		}

		int end = start + delta;

		return new int[] {start, end};
	}

	public static int[] calculateStartAndEnd(int start, int end, int total) {
		if (total <= 0) {
			return new int[] {0, 0};
		}

		int[] startAndEnd = {start, end};

		int delta = end - start;

		if (delta < 0) {
			return new int[] {0, 0};
		}

		while ((start > 0) && (start >= total)) {
			int cur = start / delta;

			startAndEnd = calculateStartAndEnd(cur, delta);

			start = startAndEnd[0];
		}

		end = startAndEnd[1];

		if (end > total) {
			startAndEnd[1] = total;
		}

		return startAndEnd;
	}

}