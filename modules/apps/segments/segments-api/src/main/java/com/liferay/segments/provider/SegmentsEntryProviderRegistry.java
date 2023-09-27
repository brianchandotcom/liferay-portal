/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getSegmentsEntryIds(long, String, long, Context, long[])}
	 */
	@Deprecated
	public default long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context)
		throws PortalException {

		return getSegmentsEntryIds(
			groupId, className, classPK, context, new long[0]);
	}

	public long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context,
			long[] segmentEntryIds)
		throws PortalException;

	public SegmentsEntryProvider getSegmentsEntryProvider(String source);

}