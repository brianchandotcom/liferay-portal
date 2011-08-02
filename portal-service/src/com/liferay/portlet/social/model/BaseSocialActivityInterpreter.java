/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public abstract class BaseSocialActivityInterpreter
	implements SocialActivityInterpreter {

	public SocialActivityFeedEntry interpret(
		SocialActivity activity, ThemeDisplay themeDisplay) {

		try {
			return doInterpret(activity, themeDisplay);
		}
		catch (Exception e) {
			_log.error("Unable to interpret activity", e);
		}

		return null;
	}

	protected String cleanContent(String content) {
		return StringUtil.shorten(HtmlUtil.extractText(content), 200);
	}

	protected abstract SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception;

	protected String getGroupName(long groupId, ThemeDisplay themeDisplay) {
		try {
			if (groupId <= 0) {
				return StringPool.BLANK;
			}

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			String groupName = group.getDescriptiveName();

			if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
				return HtmlUtil.escape(groupName);
			}

			String groupDisplayURL =
				themeDisplay.getPortalURL() + themeDisplay.getPathMain() +
					"/my_sites/view?groupId=" +  group.getGroupId();

			if (group.hasPublicLayouts()) {
				groupDisplayURL = groupDisplayURL + "&privateLayout=0";
			}
			else if (group.hasPrivateLayouts()) {
				groupDisplayURL = groupDisplayURL + "&privateLayout=1";
			}
			else {
				return HtmlUtil.escape(groupName);
			}

			groupName =
				"<a class=\"group\" href=\"" + groupDisplayURL + "\">" +
					HtmlUtil.escape(groupName) + "</a>";

			return groupName;
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	protected String getUserName(long userId, ThemeDisplay themeDisplay) {
		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = UserLocalServiceUtil.getUserById(userId);

			if (user.getUserId() == themeDisplay.getUserId()) {
				return HtmlUtil.escape(user.getFirstName());
			}

			String userName = user.getFullName();

			Group group = user.getGroup();

			if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
				return HtmlUtil.escape(userName);
			}

			String userDisplayURL = user.getDisplayURL(themeDisplay);

			userName =
				"<a class=\"user\" href=\"" + userDisplayURL + "\">" +
					HtmlUtil.escape(userName) + "</a>";

			return userName;
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	protected String wrapLink(String link, String text) {
		StringBuilder sb = new StringBuilder();

		sb.append("<a href=\"");
		sb.append(link);
		sb.append("\">");
		sb.append(text);
		sb.append("</a>");

		return sb.toString();
	}

	protected String wrapLink(
		String link, String key, ThemeDisplay themeDisplay) {

		return wrapLink(link, themeDisplay.translate(key));
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseSocialActivityInterpreter.class);

}