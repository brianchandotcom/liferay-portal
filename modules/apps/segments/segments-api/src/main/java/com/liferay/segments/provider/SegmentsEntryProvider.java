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
 * Provides methods for obtaining {@link
 * com.liferay.segments.model.SegmentsEntry SegmentsEntry}s and their related
 * entities.
 *
 * @author Eduardo García
 */
@ProviderType
public interface SegmentsEntryProvider {

	/**
	 * Returns the primary keys of the entities related to the segment.
	 *
	 * @param  segmentsEntryId the segment's ID
	 * @param  start the lower bound of the range of primary keys
	 * @param  end the upper bound of the range of primary keys (not inclusive)
	 * @return the primary keys of the entities related to the segment
	 * @throws PortalException if a portal exception occurred
	 */
	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException;

	/**
	 * Returns the number of entities related to the segment.
	 *
	 * @param  segmentsEntryId the segment's ID
	 * @return the number of entities related to the segment
	 * @throws PortalException if a portal exception occurred
	 */
	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException;

	/**
	 * Returns IDs of the group's active segments entries that are related to
	 * the entity.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @return the IDs of the active segments entries related to the entity
	 * @throws PortalException if a portal exception occurred
	 */
	public default long[] getSegmentsEntryIds(
			long groupId, String className, long classPK)
		throws PortalException {

		return getSegmentsEntryIds(groupId, className, classPK, null);
	}

	/**
	 * Returns IDs of the group's active segments entries that are related to
	 * the entity under the given context.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @param  context the context
	 * @return the IDs of the active segments entries related to the entity
	 * @throws PortalException if a portal exception occurred
	 */
	public long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context)
		throws PortalException;

	/**
	 * Returns IDs of the group's active segments entries that are related to
	 * the entity under the given context.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @param  context the context
	 * @param  segmentsEntryIds the IDs of the group's active segments entries
	 *         that are currently related to the entity under the given context
	 * @return the IDs of the active segments entries related to the entity
	 * @throws PortalException if a portal exception occurred
	 * @review
	 */
	public default long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context,
			long[] segmentsEntryIds)
		throws PortalException {

		return getSegmentsEntryIds(groupId, className, classPK, context);
	}

}