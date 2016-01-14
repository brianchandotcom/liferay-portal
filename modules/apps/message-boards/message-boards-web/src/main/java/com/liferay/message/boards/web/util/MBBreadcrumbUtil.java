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

package com.liferay.message.boards.web.util;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class MBBreadcrumbUtil {

	public static void addPortletBreadcrumbEntries(
			long categoryId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		MBCategory category = null;

		if ((categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(categoryId != MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			category = MBCategoryLocalServiceUtil.getCategory(categoryId);
		}

		addPortletBreadcrumbEntries(category, request, renderResponse);
	}

	public static void addPortletBreadcrumbEntries(
			MBCategory category, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String mvcRenderCommandName = ParamUtil.getString(
			request, "mvcRenderCommandName");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (mvcRenderCommandName.equals("/message_boards/select_category")) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/select_category");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("categories"),
				portletURL.toString());
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view");
		}

		PortalUtil.addPortletBreadcrumbEntry(
			request, themeDisplay.translate("home"), portletURL.toString());

		if (category == null) {
			return;
		}

		if (!mvcRenderCommandName.equals("/message_boards/select_category")) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view_category");
		}

		List<MBCategory> ancestorCategories = category.getAncestors();

		Collections.reverse(ancestorCategories);

		for (MBCategory curCategory : ancestorCategories) {
			portletURL.setParameter(
				"mbCategoryId", String.valueOf(curCategory.getCategoryId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, curCategory.getName(), portletURL.toString());
		}

		portletURL.setParameter(
			"mbCategoryId", String.valueOf(category.getCategoryId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, category.getName(), portletURL.toString());
	}

	public static void addPortletBreadcrumbEntries(
			MBMessage message, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		if (message.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			return;
		}

		MBCategory category = null;

		if (message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			category = message.getCategory();
		}

		addPortletBreadcrumbEntries(category, request, renderResponse);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, message.getSubject(), portletURL.toString());
	}

}