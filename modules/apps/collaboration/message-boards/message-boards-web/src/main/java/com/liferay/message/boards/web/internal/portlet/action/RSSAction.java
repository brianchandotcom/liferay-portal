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

package com.liferay.message.boards.web.internal.portlet.action;

import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.struts.BaseRSSStrutsAction;
import com.liferay.rss.util.RSSUtil;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "path=/message_boards/rss", service = StrutsAction.class)
public class RSSAction extends BaseRSSStrutsAction {

	@Override
	protected byte[] getRSS(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = themeDisplay.getPlid();
		}

		long companyId = ParamUtil.getLong(request, "companyId");
		long groupId = ParamUtil.getLong(request, "groupId");
		long userId = ParamUtil.getLong(request, "userId");
		long categoryId = ParamUtil.getLong(request, "mbCategoryId");
		long threadId = ParamUtil.getLong(request, "threadId");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		String type = ParamUtil.getString(
			request, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			request, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			request, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		String entryURL = StringBundler.concat(
			themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
			"/message_boards/find_message?p_l_id=", String.valueOf(plid));

		String rss = StringPool.BLANK;

		if (threadId > 0) {
			String feedURL = StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				"/message_boards/find_thread?p_l_id=", String.valueOf(plid),
				"&threadId=", String.valueOf(threadId));

			rss = _mbMessageService.getThreadMessagesRSS(
				threadId, WorkflowConstants.STATUS_APPROVED, max, type, version,
				displayStyle, feedURL, entryURL, themeDisplay);
		}
		else if (categoryId > 0) {
			String feedURL = StringBundler.concat(
				themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
				"/message_boards/find_category?p_l_id=", String.valueOf(plid),
				"&mbCategoryId=", String.valueOf(categoryId));

			rss = _mbMessageService.getCategoryMessagesRSS(
				groupId, categoryId, WorkflowConstants.STATUS_APPROVED, max,
				type, version, displayStyle, feedURL, entryURL, themeDisplay);
		}
		else if (groupId > 0) {
			String mvcRenderCommandName = ParamUtil.getString(
				request, "mvcRenderCommandName");

			String feedURL = null;

			if (mvcRenderCommandName.equals(
					"/message_boards/view_recent_posts")) {

				feedURL = StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					"/message_boards/find_recent_posts?p_l_id=",
					String.valueOf(plid));
			}
			else {
				feedURL = StringBundler.concat(
					themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
					"/message_boards/find_category?p_l_id=",
					String.valueOf(plid), "&mbCategoryId=",
					String.valueOf(categoryId));
			}

			if (userId > 0) {
				rss = _mbMessageService.getGroupMessagesRSS(
					groupId, userId, WorkflowConstants.STATUS_APPROVED, max,
					type, version, displayStyle, feedURL, entryURL,
					themeDisplay);
			}
			else {
				rss = _mbMessageService.getGroupMessagesRSS(
					groupId, WorkflowConstants.STATUS_APPROVED, max, type,
					version, displayStyle, feedURL, entryURL, themeDisplay);
			}
		}
		else if (companyId > 0) {
			String feedURL = StringPool.BLANK;

			rss = _mbMessageService.getCompanyMessagesRSS(
				companyId, WorkflowConstants.STATUS_APPROVED, max, type,
				version, displayStyle, feedURL, entryURL, themeDisplay);
		}

		return rss.getBytes(StringPool.UTF8);
	}

	@Override
	protected boolean isRSSFeedsEnabled(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(themeDisplay.getSiteGroupId());

		return mbGroupServiceSettings.isEnableRSS();
	}

	@Reference(unbind = "-")
	protected void setMBMessageService(MBMessageService mbMessageService) {
		_mbMessageService = mbMessageService;
	}

	private MBMessageService _mbMessageService;

}