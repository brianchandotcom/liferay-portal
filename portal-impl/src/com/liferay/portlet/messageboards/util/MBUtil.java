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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.util.ContentUtil;
import com.liferay.util.mail.JavaMailUtil;

import java.io.InputStream;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class MBUtil {

	public static final String BB_CODE_EDITOR_WYSIWYG_IMPL_KEY =
		"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
			"edit_message.bb_code.jsp";

	public static final String MESSAGE_POP_PORTLET_PREFIX = "mb_message.";

	public static void addPortletBreadcrumbEntries(
			long categoryId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		addPortletBreadcrumbEntries(category, request, renderResponse);
	}

	public static void addPortletBreadcrumbEntries(
			MBCategory category, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception {

		String strutsAction = ParamUtil.getString(request, "struts_action");

		PortletURL portletURL = renderResponse.createRenderURL();

		if (strutsAction.equals("/message_boards/select_category") ||
			strutsAction.equals("/message_boards_admin/select_category")) {

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			portletURL.setWindowState(LiferayWindowState.POP_UP);

			portletURL.setParameter(
				"struts_action", "/message_boards/select_category");

			PortalUtil.addPortletBreadcrumbEntry(
				request, themeDisplay.translate("categories"),
				portletURL.toString());
		}
		else {
			portletURL.setParameter("struts_action", "/message_boards/view");
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

		if ((message.getCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(message.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return;
		}

		MBCategory category = message.getCategory();

		addPortletBreadcrumbEntries(category, request, renderResponse);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));

		PortalUtil.addPortletBreadcrumbEntry(
			request, message.getSubject(), portletURL.toString());
	}

	public static void collectMultipartContent(
			MimeMultipart multipart, MBMailMessage collector)
		throws Exception {

		for (int i = 0; i < multipart.getCount(); i++) {
			BodyPart part = multipart.getBodyPart(i);

			collectPartContent(part, collector);
		}
	}

	public static void collectPartContent(
			Part part, MBMailMessage mbMailMessage)
		throws Exception {

		Object partContent = part.getContent();

		String contentType = part.getContentType().toLowerCase();

		if ((part.getDisposition() != null) &&
			part.getDisposition().equalsIgnoreCase(MimeMessage.ATTACHMENT)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Processing attachment");
			}

			byte[] bytes = null;

			if (partContent instanceof String) {
				bytes = ((String)partContent).getBytes();
			}
			else if (partContent instanceof InputStream) {
				bytes = JavaMailUtil.getBytes(part);
			}

			mbMailMessage.addBytes(part.getFileName(), bytes);
		}
		else {
			if (partContent instanceof MimeMultipart) {
				MimeMultipart mimeMultipart = (MimeMultipart)partContent;

				collectMultipartContent(mimeMultipart, mbMailMessage);
			}
			else if (partContent instanceof String) {
				if (contentType.startsWith("text/html")) {
					mbMailMessage.setHtmlBody((String)partContent);
				}
				else {
					mbMailMessage.setPlainBody((String)partContent);
				}
			}
		}
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long mbCategoryId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (mbCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			return themeDisplay.translate("home");
		}

		MBCategory mbCategory = MBCategoryLocalServiceUtil.fetchMBCategory(
			mbCategoryId);

		List<MBCategory> categories = mbCategory.getAncestors();

		StringBundler sb = new StringBundler((categories.size() * 3) + 6);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		for (int i = categories.size() - 1; i >= 0; i--) {
			MBCategory curCategory = categories.get(i);

			sb.append(StringPool.RAQUO);
			sb.append(StringPool.SPACE);
			sb.append(curCategory.getName());
		}

		sb.append(StringPool.RAQUO);
		sb.append(StringPool.SPACE);
		sb.append(mbCategory.getName());

		return sb.toString();
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

	public static String getEmailFromAddress(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromAddress(
			preferences, companyId,
			PropsValues.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
			PortletPreferences preferences, long companyId)
		throws SystemException {

		return PortalUtil.getEmailFromName(
			preferences, companyId, PropsValues.MESSAGE_BOARDS_EMAIL_FROM_NAME);
	}

	public static boolean getEmailHtmlFormat(PortletPreferences preferences) {
		String emailHtmlFormat = preferences.getValue(
			"emailHtmlFormat", StringPool.BLANK);

		if (Validator.isNotNull(emailHtmlFormat)) {
			return GetterUtil.getBoolean(emailHtmlFormat);
		}
		else {
			return PropsValues.MESSAGE_BOARDS_EMAIL_HTML_FORMAT;
		}
	}

	public static String getEmailMessageAddedBody(
		PortletPreferences preferences) {

		String emailMessageAddedBody = preferences.getValue(
			"emailMessageAddedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedBody)) {
			return emailMessageAddedBody;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_BODY);
		}
	}

	public static boolean getEmailMessageAddedEnabled(
		PortletPreferences preferences) {

		String emailMessageAddedEnabled = preferences.getValue(
			"emailMessageAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedEnabled)) {
			return GetterUtil.getBoolean(emailMessageAddedEnabled);
		}
		else {
			return PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED;
		}
	}

	public static String getEmailMessageAddedSignature(
		PortletPreferences preferences) {

		String emailMessageAddedSignature = preferences.getValue(
			"emailMessageAddedSignature", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedSignature)) {
			return emailMessageAddedSignature;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SIGNATURE);
		}
	}

	public static String getEmailMessageAddedSubjectPrefix(
		PortletPreferences preferences) {

		String emailMessageAddedSubjectPrefix = preferences.getValue(
			"emailMessageAddedSubjectPrefix", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageAddedSubjectPrefix)) {
			return emailMessageAddedSubjectPrefix;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_SUBJECT_PREFIX);
		}
	}

	public static String getEmailMessageUpdatedBody(
		PortletPreferences preferences) {

		String emailMessageUpdatedBody = preferences.getValue(
			"emailMessageUpdatedBody", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedBody)) {
			return emailMessageUpdatedBody;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_BODY);
		}
	}

	public static boolean getEmailMessageUpdatedEnabled(
		PortletPreferences preferences) {

		String emailMessageUpdatedEnabled = preferences.getValue(
			"emailMessageUpdatedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedEnabled)) {
			return GetterUtil.getBoolean(emailMessageUpdatedEnabled);
		}
		else {
			return PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED;
		}
	}

	public static String getEmailMessageUpdatedSignature(
		PortletPreferences preferences) {

		String emailMessageUpdatedSignature = preferences.getValue(
			"emailMessageUpdatedSignature", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedSignature)) {
			return emailMessageUpdatedSignature;
		}
		else {
			return ContentUtil.get(
				PropsValues.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SIGNATURE);
		}
	}

	public static String getEmailMessageUpdatedSubjectPrefix(
		PortletPreferences preferences) {

		String emailMessageUpdatedSubject = preferences.getValue(
			"emailMessageUpdatedSubjectPrefix", StringPool.BLANK);

		if (Validator.isNotNull(emailMessageUpdatedSubject)) {
			return emailMessageUpdatedSubject;
		}
		else {
			return ContentUtil.get(
				PropsValues.
					MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_SUBJECT_PREFIX);
		}
	}

	public static String getMailingListAddress(
		long groupId, long categoryId, long messageId, String mx,
		String defaultMailingListAddress) {

		if (PropsValues.POP_SERVER_SUBDOMAIN.length() <= 0) {
			String mailingListAddress = defaultMailingListAddress;

			try {
				MBMailingList mailingList =
					MBMailingListLocalServiceUtil.getCategoryMailingList(
						groupId, categoryId);

				if (mailingList.isActive()) {
					mailingListAddress = mailingList.getEmailAddress();
				}
			}
			catch (Exception e) {
			}

			return mailingListAddress;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(MESSAGE_POP_PORTLET_PREFIX);
		sb.append(categoryId);
		sb.append(StringPool.PERIOD);
		sb.append(messageId);
		sb.append(StringPool.AT);
		sb.append(PropsValues.POP_SERVER_SUBDOMAIN);
		sb.append(StringPool.PERIOD);
		sb.append(mx);

		return sb.toString();
	}

	public static String getMBControlPanelLink(
			PortletRequest portletRequest, long mbCategoryId)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			com.liferay.portal.kernel.util.WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.MESSAGE_BOARDS_ADMIN,
			PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId()),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/message_boards_admin/view");
		portletURL.setParameter("mbCategoryId", String.valueOf(mbCategoryId));

		return portletURL.toString();
	}

	public static String getMessageFormat(PortletPreferences preferences) {
		String messageFormat = preferences.getValue(
			"messageFormat", MBMessageConstants.DEFAULT_FORMAT);

		String editorImpl = PropsUtil.get(BB_CODE_EDITOR_WYSIWYG_IMPL_KEY);

		if (messageFormat.equals("bbcode") &&
			!(editorImpl.equals("bbcode") ||
			  editorImpl.equals("ckeditor_bbcode"))) {

			messageFormat = "html";
		}

		return messageFormat;
	}

	public static long getMessageId(String mailId) {
		int x = mailId.indexOf(CharPool.LESS_THAN) + 1;
		int y = mailId.indexOf(CharPool.AT);

		long messageId = 0;

		if ((x > 0 ) && (y != -1)) {
			String temp = mailId.substring(x, y);

			int z = temp.lastIndexOf(CharPool.PERIOD);

			if (z != -1) {
				messageId = GetterUtil.getLong(temp.substring(z + 1));
			}
		}

		return messageId;
	}

	public static long getParentMessageId(Message message) throws Exception {
		long parentMessageId = -1;

		String parentHeader = getParentMessageIdString(message);

		if (parentHeader != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Parent header " + parentHeader);
			}

			parentMessageId = getMessageId(parentHeader);

			if (_log.isDebugEnabled()) {
				_log.debug("Previous message id " + parentMessageId);
			}
		}

		return parentMessageId;
	}

	public static String getParentMessageIdString(Message message)
		throws Exception {

		// If the previous block failed, try to get the parent message ID from
		// the "References" header as explained in
		// http://cr.yp.to/immhf/thread.html. Some mail clients such as Yahoo!
		// Mail use the "In-Reply-To" header, so we check that as well.

		String parentHeader = null;

		String[] references = message.getHeader("References");

		if ((references != null) && (references.length > 0)) {
			String reference = references[0];

			int x = reference.lastIndexOf("<mb.");

			if (x > -1) {
				int y = reference.indexOf(">", x);

				parentHeader = reference.substring(x, y);
			}
		}

		if (parentHeader == null) {
			String[] inReplyToHeaders = message.getHeader("In-Reply-To");

			if ((inReplyToHeaders != null) && (inReplyToHeaders.length > 0)) {
				parentHeader = inReplyToHeaders[0];
			}
		}

		if (Validator.isNull(parentHeader) ||
			!parentHeader.startsWith(MESSAGE_POP_PORTLET_PREFIX, 1)) {

			parentHeader = _getParentMessageIdFromSubject(message);
		}

		return parentHeader;
	}

	public static String getSubjectWithoutMessageId(Message message)
		throws Exception {

		String subject = message.getSubject();

		String parentMessageId = _getParentMessageIdFromSubject(message);

		if (Validator.isNotNull(parentMessageId)) {
			int pos = subject.indexOf(parentMessageId);

			if (pos != -1) {
				subject = subject.substring(0, pos);
			}
		}

		return subject;
	}

	public static String[] getThreadPriority(
			PortletPreferences preferences, String languageId, double value,
			ThemeDisplay themeDisplay)
		throws Exception {

		String[] priorities = LocalizationUtil.getPreferencesValues(
			preferences, "priorities", languageId);

		String[] priorityPair = _findThreadPriority(
			value, themeDisplay, priorities);

		if (priorityPair == null) {
			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());

			priorities = LocalizationUtil.getPreferencesValues(
				preferences, "priorities", defaultLanguageId);

			priorityPair = _findThreadPriority(value, themeDisplay, priorities);
		}

		return priorityPair;
	}

	public static Date getUnbanDate(MBBan ban, int expireInterval) {
		Date banDate = ban.getCreateDate();

		Calendar cal = Calendar.getInstance();

		cal.setTime(banDate);

		cal.add(Calendar.DATE, expireInterval);

		return cal.getTime();
	}

	public static String getUserRank(
			PortletPreferences preferences, String languageId, int posts)
		throws Exception {

		String rank = StringPool.BLANK;

		String[] ranks = LocalizationUtil.getPreferencesValues(
			preferences, "ranks", languageId);

		for (int i = 0; i < ranks.length; i++) {
			String[] kvp = StringUtil.split(ranks[i], CharPool.EQUAL);

			String kvpName = kvp[0];
			int kvpPosts = GetterUtil.getInteger(kvp[1]);

			if (posts >= kvpPosts) {
				rank = kvpName;
			}
			else {
				break;
			}
		}

		return rank;
	}

	public static String[] getUserRank(
			PortletPreferences preferences, String languageId,
			MBStatsUser statsUser)
		throws Exception {

		String[] rank = {StringPool.BLANK, StringPool.BLANK};

		int maxPosts = 0;

		Group group = GroupLocalServiceUtil.getGroup(statsUser.getGroupId());

		long companyId = group.getCompanyId();

		String[] ranks = LocalizationUtil.getPreferencesValues(
			preferences, "ranks", languageId);

		for (int i = 0; i < ranks.length; i++) {
			String[] kvp = StringUtil.split(ranks[i], CharPool.EQUAL);

			String curRank = kvp[0];
			String curRankValue = kvp[1];

			String[] curRankValueKvp = StringUtil.split(
				curRankValue, CharPool.COLON);

			if (curRankValueKvp.length <= 1) {
				int posts = GetterUtil.getInteger(curRankValue);

				if ((posts <= statsUser.getMessageCount()) &&
					(posts >= maxPosts)) {

					rank[0] = curRank;
					maxPosts = posts;
				}

			}
			else {
				String entityType = curRankValueKvp[0];
				String entityValue = curRankValueKvp[1];

				try {
					if (_isEntityRank(
							companyId, statsUser, entityType, entityValue)) {

						rank[1] = curRank;

						break;
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}
			}
		}

		return rank;
	}

	public static boolean hasMailIdHeader(Message message) throws Exception {
		String[] messageIds = message.getHeader("Message-ID");

		if (messageIds == null) {
			return false;
		}

		for (String messageId : messageIds) {
			if (Validator.isNotNull(PropsValues.POP_SERVER_SUBDOMAIN) &&
				messageId.contains(PropsValues.POP_SERVER_SUBDOMAIN)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isAllowAnonymousPosting(
		PortletPreferences preferences) {

		return GetterUtil.getBoolean(
			preferences.getValue("allowAnonymousPosting", null),
			PropsValues.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED);
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
			!Validator.equals(message.getUserId(), themeDisplay.getUserId()) &&
			!permissionChecker.isGroupAdmin(themeDisplay.getScopeGroupId())) {

			return false;
		}

		return true;
	}

	public static String replaceMessageBodyPaths(
		ThemeDisplay themeDisplay, String messageBody) {

		return StringUtil.replace(
			messageBody,
			new String[] {
				"@theme_images_path@", "href=\"/", "src=\"/"
			},
			new String[] {
				themeDisplay.getPathThemeImages(),
				"href=\"" + themeDisplay.getURLPortal() + "/",
				"src=\"" + themeDisplay.getURLPortal() + "/"
			});
	}

	private static String[] _findThreadPriority(
		double value, ThemeDisplay themeDisplay, String[] priorities) {

		for (int i = 0; i < priorities.length; i++) {
			String[] priority = StringUtil.split(priorities[i]);

			try {
				String priorityName = priority[0];
				String priorityImage = priority[1];
				double priorityValue = GetterUtil.getDouble(priority[2]);

				if (value == priorityValue) {
					if (!priorityImage.startsWith(Http.HTTP)) {
						priorityImage =
							themeDisplay.getPathThemeImages() + priorityImage;
					}

					return new String[] {priorityName, priorityImage};
				}
			}
			catch (Exception e) {
				_log.error("Unable to determine thread priority", e);
			}
		}

		return null;
	}

	private static String _getParentMessageIdFromSubject(Message message)
		throws Exception {

		if (message.getSubject() == null) {
			return null;
		}

		String parentMessageId = null;

		String subject = StringUtil.reverse(message.getSubject());

		int pos = subject.indexOf(CharPool.LESS_THAN);

		if (pos != -1) {
			parentMessageId = StringUtil.reverse(subject.substring(0, pos + 1));
		}

		return parentMessageId;
	}

	private static boolean _isEntityRank(
			long companyId, MBStatsUser statsUser, String entityType,
			String entityValue)
		throws Exception {

		long groupId = statsUser.getGroupId();
		long userId = statsUser.getUserId();

		if (entityType.equals("organization-role") ||
			entityType.equals("site-role")) {

			Role role = RoleLocalServiceUtil.getRole(companyId, entityValue);

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, groupId, role.getRoleId(), true)) {

				return true;
			}
		}
		else if (entityType.equals("organization")) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					companyId, entityValue);

			if (OrganizationLocalServiceUtil.hasUserOrganization(
					userId, organization.getOrganizationId(), false, false)) {

				return true;
			}
		}
		else if (entityType.equals("regular-role")) {
			if (RoleLocalServiceUtil.hasUserRole(
					userId, companyId, entityValue, true)) {

				return true;
			}
		}
		else if (entityType.equals("user-group")) {
			UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
				companyId, entityValue);

			if (UserLocalServiceUtil.hasUserGroupUser(
					userGroup.getUserGroupId(), userId)) {

				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(MBUtil.class);

}