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

/**
 * Provides methods for obtaining {@link SegmentsEntry} objects and their
 * related entities.
 *
 * @author Eduardo Garcia
 * @review
 */
public interface SegmentsEntryProvider {

	/**
	 * Clears the cache of all {@link SegmentsEntry} objects.
	 */
	public void clearCache();

	/**
	 * Clears the cache {@link SegmentsEntry} objects related with the entity.
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 */
	public void clearCache(String className, long classPK);

	/**
	 * Returns the primary keys of the entities related with the segment.
	 *
	 * @param  segmentsEntryId the ID of the segment
	 * @return the primary keys of the entities related with the segment
	 * @throws PortalException
	 * @review
	 */
	public long[] getSegmentsEntryClassPKs(long segmentsEntryId)
		throws PortalException;

	/**
	 * Returns ids of the active {@link SegmentsEntry} objects related with the
	 * entity.
	 *
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the ids the of active {@link SegmentsEntry} objects related to
	 *         the entity
	 * @throws PortalException
	 * @review
	 */
	public long[] getSegmentsEntryIds(String className, long classPK)
		throws PortalException;

}