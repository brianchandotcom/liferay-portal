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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.message.boards.web.internal.security.permission.MBMessagePermission;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class MBUtil {

	public static String getBBCodeQuoteBody(
		HttpServletRequest request, MBMessage parentMessage) {

		String parentAuthor = null;

		if (parentMessage.isAnonymous()) {
			parentAuthor = LanguageUtil.get(request, "anonymous");
		}
		else {
			parentAuthor = HtmlUtil.escape(
				PortalUtil.getUserName(parentMessage));
		}

		StringBundler sb = new StringBundler(5);

		sb.append("[quote=");
		sb.append(
			StringUtil.replace(
				parentAuthor, new String[] {"[", "]", "(", ")"},
				new String[] {"&#91;", "&#93;", "&#40;", "&#41;"}));
		sb.append("]\n");
		sb.append(parentMessage.getBody(false));
		sb.append("[/quote]\n\n\n");

		return sb.toString();
	}

	public static String getBBCodeSplitThreadBody(HttpServletRequest request) {
		StringBundler sb = new StringBundler(5);

		sb.append("[url=");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append("]");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append("[/url]");

		return LanguageUtil.format(
			request, "the-new-thread-can-be-found-at-x", sb.toString(), false);
	}

	public static long getCategoryId(
		HttpServletRequest request, MBCategory category) {

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (category != null) {
			categoryId = category.getCategoryId();
		}

		categoryId = ParamUtil.getLong(request, "mbCategoryId", categoryId);

		return categoryId;
	}

	public static long getCategoryId(
		HttpServletRequest request, MBMessage message) {

		long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

		if (message != null) {
			categoryId = message.getCategoryId();
		}

		categoryId = ParamUtil.getLong(request, "mbCategoryId", categoryId);

		return categoryId;
	}

	public static String getHtmlQuoteBody(
		HttpServletRequest request, MBMessage parentMessage) {

		String parentAuthor = null;

		if (parentMessage.isAnonymous()) {
			parentAuthor = LanguageUtil.get(request, "anonymous");
		}
		else {
			parentAuthor = HtmlUtil.escape(
				PortalUtil.getUserName(parentMessage));
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<blockquote><div class=\"quote-title\">");
		sb.append(parentAuthor);
		sb.append(": </div><div class=\"quote\"><div class=\"quote-content\">");
		sb.append(parentMessage.getBody(false));
		sb.append("</div></blockquote><br /><br /><br />");

		return sb.toString();
	}

	public static String getHtmlSplitThreadBody(HttpServletRequest request) {
		StringBundler sb = new StringBundler(5);

		sb.append("<a href=");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append(">");
		sb.append(MBThreadConstants.NEW_THREAD_URL);
		sb.append("</a>");

		return LanguageUtil.format(
			request, "the-new-thread-can-be-found-at-x", sb.toString(), false);
	}

	public static String[] getThreadPriority(
			MBGroupServiceSettings mbGroupServiceSettings, String languageId,
			double value)
		throws Exception {

		String[] priorities = mbGroupServiceSettings.getPriorities(languageId);

		String[] priorityPair = _findThreadPriority(value, priorities);

		if (priorityPair == null) {
			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());

			priorities = mbGroupServiceSettings.getPriorities(
				defaultLanguageId);

			priorityPair = _findThreadPriority(value, priorities);
		}

		return priorityPair;
	}

	public static boolean isViewableMessage(
			ThemeDisplay themeDisplay, MBMessage message)
		throws Exception {

		return isViewableMessage(themeDisplay, message, message);
	}

	public static boolean isViewableMessage(
			ThemeDisplay themeDisplay, MBMessage message,
			MBMessage parentMessage)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!MBMessagePermission.contains(
				permissionChecker, parentMessage, ActionKeys.VIEW)) {

			return false;
		}

		if ((message.getMessageId() != parentMessage.getMessageId()) &&
			!MBMessagePermission.contains(
				permissionChecker, message, ActionKeys.VIEW)) {

			return false;
		}

		if (!message.isApproved() &&
			(message.getUserId() != themeDisplay.getUserId()) &&
			!permissionChecker.isContentReviewer(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			return false;
		}

		return true;
	}

	private static String[] _findThreadPriority(
		double value, String[] priorities) {

		for (int i = 0; i < priorities.length; i++) {
			String[] priority = StringUtil.split(
				priorities[i], StringPool.PIPE);

			try {
				String priorityName = priority[0];
				String priorityImage = priority[1];
				double priorityValue = GetterUtil.getDouble(priority[2]);

				if (value == priorityValue) {
					return new String[] {priorityName, priorityImage};
				}
			}
			catch (Exception e) {
				_log.error("Unable to determine thread priority", e);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(MBUtil.class);

}