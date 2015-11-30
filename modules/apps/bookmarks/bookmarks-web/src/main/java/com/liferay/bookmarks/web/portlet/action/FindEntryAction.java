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

package com.liferay.bookmarks.web.portlet.action;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.struts.FindStrutsAction;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 */
@Component(
	property = "path=/bookmarks/find_entry", service = StrutsAction.class
)
public class FindEntryAction extends FindStrutsAction {

	@Override
	protected long getGroupId(long primaryKey) throws Exception {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(primaryKey);

		return entry.getGroupId();
	}

	@Override
	protected String getPrimaryKeyParameterName() {
		return "entryId";
	}

	@Override
	protected String getStrutsAction(
		HttpServletRequest request, String portletId) {

		return "/bookmarks/view_entry";
	}

	@Override
	protected String[] initPortletIds() {
		return new String[] {BookmarksPortletKeys.BOOKMARKS};
	}

	@Reference(unbind = "-")
	protected void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	private volatile BookmarksEntryLocalService _bookmarksEntryLocalService;

}