/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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