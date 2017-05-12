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

package com.liferay.trash.web.internal.portlet.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

/**
 * Provides a utility method to get Recycle Bin entries by row IDs from the
 * render request to render the Recycle Bin portlet's information panel.
 *
 * @author Jürgen Kappler
 */
public class ActionUtil {

	public static List<TrashEntry> getTrashEntries(ResourceRequest request)
		throws Exception {

		long[] trashEntryIds = ParamUtil.getLongValues(request, "rowIds");

		List<TrashEntry> trashEntries = new ArrayList<>();

		for (long trashEntryId : trashEntryIds) {
			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				trashEntryId);

			trashEntries.add(trashEntry);
		}

		return trashEntries;
	}

}