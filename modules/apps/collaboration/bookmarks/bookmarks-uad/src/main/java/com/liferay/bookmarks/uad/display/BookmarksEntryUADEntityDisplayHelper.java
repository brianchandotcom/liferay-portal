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

package com.liferay.bookmarks.uad.display;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true, service = BookmarksEntryUADEntityDisplayHelper.class
)
public class BookmarksEntryUADEntityDisplayHelper {

	public String getBookmarksEntryEditURL(
			BookmarksEntry bookmarksEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return StringPool.BLANK;
	}

	public String[] getDisplayFieldNames() {
		return new String[] {"name", "description", "url"};
	}

	public Map<String, Object> getNonanonymizableFieldValues(
		BookmarksEntry bookmarksEntry) {

		Map<String, Object> uadEntityNonanonymizableFieldValues =
			new HashMap<>();

		uadEntityNonanonymizableFieldValues.put(
			"description", bookmarksEntry.getDescription());
		uadEntityNonanonymizableFieldValues.put(
			"name", bookmarksEntry.getName());
		uadEntityNonanonymizableFieldValues.put("url", bookmarksEntry.getUrl());

		return uadEntityNonanonymizableFieldValues;
	}

}