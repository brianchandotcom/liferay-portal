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

package com.liferay.object.resource;

import com.liferay.object.dto.ObjectEntry;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

/**
 * @author Javier de Arcos
 */
public interface ObjectEntryResource {

	public void deleteObjectEntry(long objectEntryId) throws Exception;

	public Page<ObjectEntry> getObjectEntriesPage(
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts, String search)
		throws Exception;

	public ObjectEntry getObjectEntry(long objectEntryId) throws Exception;

	public ObjectEntry postObjectEntry(Long siteId, ObjectEntry objectEntry)
		throws Exception;

	public ObjectEntry putObjectEntry(
			long objectEntryId, ObjectEntry objectEntry)
		throws Exception;

}