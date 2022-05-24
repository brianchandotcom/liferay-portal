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

package com.liferay.segments;

import com.liferay.segments.context.Context;

/**
 * Provides methods to to obtain the {@link com.liferay.segments.model.SegmentsEntry SegmentsEntry} from a userId.
 *
 * @author Cristina González
 */
public interface SegmentsEntryRetriever {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getSegmentsEntryIds(long, long, Context, long[])}
	 */
	@Deprecated
	public default long[] getSegmentsEntryIds(
		long groupId, long userId, Context context) {

		return getSegmentsEntryIds(groupId, userId, context, new long[0]);
	}

	public long[] getSegmentsEntryIds(
		long groupId, long userId, Context context, long[] segmentEntryIds);

}