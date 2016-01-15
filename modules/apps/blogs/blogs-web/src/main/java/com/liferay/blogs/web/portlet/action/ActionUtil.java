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

package com.liferay.blogs.web.portlet.action;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.exception.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getEntry(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(request, "entryId");

		String urlTitle = ParamUtil.getString(request, "urlTitle");

		BlogsEntry entry = null;

		if (entryId > 0) {
			entry = BlogsEntryServiceUtil.getEntry(entryId);
		}
		else if (Validator.isNotNull(urlTitle)) {
			try {
				entry = BlogsEntryServiceUtil.getEntry(
					themeDisplay.getScopeGroupId(), urlTitle);
			}
			catch (NoSuchEntryException nsee) {
				if (urlTitle.indexOf(CharPool.UNDERLINE) != -1) {

					// Check another URL title for backwards compatibility. See
					// LEP-5733.

					urlTitle = StringUtil.replace(
						urlTitle, CharPool.UNDERLINE, CharPool.DASH);

					entry = BlogsEntryServiceUtil.getEntry(
						themeDisplay.getScopeGroupId(), urlTitle);
				}
				else {
					throw nsee;
				}
			}
		}

		if ((entry != null) && entry.isInTrash()) {
			throw new NoSuchEntryException("{entryId=" + entryId + "}");
		}

		request.setAttribute(WebKeys.BLOGS_ENTRY, entry);
	}

	public static void getEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getEntry(request);
	}

}