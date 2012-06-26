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
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portlet.trash.NoSuchEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.impl.TrashEntryImpl;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.portlet.trash.util.comparator.EntryTypeComparator;
import com.liferay.portlet.trash.util.comparator.EntryUserNameComparator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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

			TrashEntry entry = TrashEntryLocalServiceUtil.fetchEntry(
				entryClassName, classPK);

			if (entry == null) {
				String userName = GetterUtil.getString(
					doc.get(Field.REMOVED_BY));

				Date removedDate = GetterUtil.get(
					doc.get(Field.REMOVED_DATE), _dateFormat, new Date());

				entry = new TrashEntryImpl();

				entry.setClassName(entryClassName);
				entry.setClassPK(classPK);

				entry.setUserName(userName);
				entry.setCreateDate(removedDate);
			}

			entries.add(entry);
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

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static DateFormat _dateFormat =
		(DateFormat)FastDateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

	private static Log _log = LogFactoryUtil.getLog(TrashUtil.class);

}