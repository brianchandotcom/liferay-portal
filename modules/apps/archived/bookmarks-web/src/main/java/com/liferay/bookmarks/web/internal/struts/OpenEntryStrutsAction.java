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

package com.liferay.bookmarks.web.internal.struts;

import com.liferay.bookmarks.exception.NoSuchEntryException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
public class OpenEntryStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			long entryId = ParamUtil.getLong(httpServletRequest, "entryId");

			BookmarksEntry entry = _bookmarksEntryService.getEntry(entryId);

			if (entry.isInTrash()) {
				int status = ParamUtil.getInteger(
					httpServletRequest, "status",
					WorkflowConstants.STATUS_APPROVED);

				if (status != WorkflowConstants.STATUS_IN_TRASH) {
					throw new NoSuchEntryException("{entryId=" + entryId + "}");
				}
			}

			entry = _bookmarksEntryService.openEntry(entry);

			httpServletResponse.sendRedirect(entry.getUrl());

			return null;
		}
		catch (Exception e) {
			_portal.sendError(e, httpServletRequest, httpServletResponse);

			return null;
		}
	}

	@Reference
	private BookmarksEntryService _bookmarksEntryService;

	@Reference
	private Portal _portal;

}