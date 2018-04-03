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

package com.liferay.user.associated.data.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.aggregator.UADAggregator;

/**
 * @author Noah Sherrill
 */
public class UADEntityChunkedCommandUtil {

	public static <T> void executeChunkedCommand(
			long userId, UADAggregator<T> uadAggregator,
			UnsafeConsumer<T, PortalException> unsafeConsumer)
		throws PortalException {

		int count = (int)uadAggregator.count(userId);
		int end = _CHUNK_SIZE;
		int start = 0;

		while (start < count) {
			if (end > count) {
				end = count;
			}

			for (T t : uadAggregator.getRange(userId, start, end)) {
				unsafeConsumer.accept(t);
			}

			start = end;

			end += _CHUNK_SIZE;
		}
	}

	private static final int _CHUNK_SIZE = 100;

}