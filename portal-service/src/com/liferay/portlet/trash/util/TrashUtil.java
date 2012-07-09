/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.portlet.trash.util.comparator.EntryTypeComparator;
import com.liferay.portlet.trash.util.comparator.EntryUserNameComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 * @author Julio Camarero
 */
public class TrashUtil {

	public static List<TrashEntry> getEntries(Hits hits)
		throws PortalException, SystemException {

		List<TrashEntry> entries = new ArrayList<TrashEntry>();

		for (Document doc : hits.getDocs()) {
			String entryClassName = GetterUtil.getString(
				doc.get(Field.ENTRY_CLASS_NAME));
			long classPK = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			try {
				TrashEntry entry = TrashEntryLocalServiceUtil.getEntry(
					entryClassName, classPK);

				entries.add(entry);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Can't find trash entry with the given classPK: "
						+ classPK + " and className: " + entryClassName);
				}
			}
		}

		return entries;
	}

	public static OrderByComparator getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("removed-by")) {
			orderByComparator = new EntryUserNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("removed-date")) {
			orderByComparator = new EntryCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new EntryTypeComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private static Log _log = LogFactoryUtil.getLog(TrashUtil.class);

}