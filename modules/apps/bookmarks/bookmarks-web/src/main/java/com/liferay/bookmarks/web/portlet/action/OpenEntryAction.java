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

import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryService;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "path=/bookmarks/open_entry", service = StrutsAction.class
)
public class OpenEntryAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			long entryId = ParamUtil.getLong(request, "entryId");

			BookmarksEntry entry = _bookmarksEntryService.getEntry(entryId);

			if (entry.isInTrash()) {
				int status = ParamUtil.getInteger(
					request, "status", WorkflowConstants.STATUS_APPROVED);

				if (status != WorkflowConstants.STATUS_IN_TRASH) {
					throw new NoSuchEntryException("{entryId=" + entryId + "}");
				}
			}

			entry = _bookmarksEntryService.openEntry(entry);

			response.sendRedirect(entry.getUrl());

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	@Reference(unbind = "-")
	protected void setBookmarksEntryService(
		BookmarksEntryService bookmarksEntryService) {

		_bookmarksEntryService = bookmarksEntryService;
	}

	private volatile BookmarksEntryService _bookmarksEntryService;

}