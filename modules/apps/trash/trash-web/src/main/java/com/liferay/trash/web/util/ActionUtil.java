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

package com.liferay.trash.web.util;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

/**
 * @author Jürgen Kappler
 */
public class ActionUtil {

	public static List<TrashEntry> getEntries(ResourceRequest request)
		throws Exception {

		long[] entryIds = ParamUtil.getLongValues(request, "rowIds");

		List<TrashEntry> entries = new ArrayList<>();

		for (long entryId : entryIds) {
			TrashEntry entry = TrashEntryLocalServiceUtil.getEntry(entryId);

			entries.add(entry);
		}

		return entries;
	}

}