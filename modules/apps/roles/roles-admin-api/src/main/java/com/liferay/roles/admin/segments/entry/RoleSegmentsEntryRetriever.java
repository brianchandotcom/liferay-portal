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

package com.liferay.roles.admin.segments.entry;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.segments.model.SegmentsEntry;

/**
 * @author Drew Brokke
 */
public interface RoleSegmentsEntryRetriever {

	public BaseModelSearchResult<SegmentsEntry> searchRoleSegmentsEntries(
			long roleId, String keywords, int cur, int delta, String sortField,
			boolean reverse)
		throws PortalException;

}