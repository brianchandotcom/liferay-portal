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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.FindAction;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class FindEntryAction extends FindAction {

	@Override
	protected long getGroupId(long primaryKey) throws Exception {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(primaryKey);

		return entry.getGroupId();
	}

	@Override
	protected String getPrimaryKeyParameterName() {
		return "entryId";
	}

	@Override
	protected String getStrutsAction(
		HttpServletRequest request, String portletId) {

		String strutsAction = StringPool.BLANK;

		if (portletId.equals(PortletKeys.BLOGS_ADMIN)) {
			strutsAction = "/blogs_admin";
		}
		else if (portletId.equals(PortletKeys.BLOGS)) {
			strutsAction = "/blogs";
		}
		else {
			strutsAction = "/blogs_aggregator";
		}

		boolean showAllEntries = ParamUtil.getBoolean(
			request, "showAllEntries");

		if (showAllEntries) {
			strutsAction += "/view";
		}
		else {
			strutsAction += "/view_entry";
		}

		return strutsAction;
	}

	@Override
	protected String[] initPortletIds() {

		// Order is important. See LPS-23770.

		return new String[] {
			PortletKeys.BLOGS_ADMIN, PortletKeys.BLOGS,
			PortletKeys.BLOGS_AGGREGATOR
		};
	}

	@Override
	protected void setFindKey(PortletURL portletURL, long primaryKey)
		throws PortalException, SystemException {

		BlogsEntry blogEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			primaryKey);

		portletURL.setParameter("urlTitle", blogEntry.getUrlTitle());
	}

}