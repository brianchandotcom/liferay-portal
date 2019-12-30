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

package com.liferay.segments.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.context.Context;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eduardo García
 */
@ProviderType
public interface SegmentsEntryProviderRegistry {

	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException;

	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException;

	public default long[] getSegmentsEntryIds(
			long groupId, String className, long classPK)
		throws PortalException {

		return getSegmentsEntryIds(groupId, className, classPK, null);
	}

	public long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context)
		throws PortalException;

	public SegmentsEntryProvider getSegmentsEntryProvider(String source);

}