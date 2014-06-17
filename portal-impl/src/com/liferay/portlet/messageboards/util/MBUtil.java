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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.dao.shard.ShardCallable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.ThemeConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.MBSettings;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.util.mail.JavaMailUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

	public static final String EMOTICONS = "/emoticons";

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

			portletURL.setParameter(
				"struts_action", "/message_boards/select_category");
			portletURL.setWindowState(LiferayWindowState.POP_UP);

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

		if (message.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			return;
		}

		if (message.getCategoryId() !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			addPortletBreadcrumbEntries(
				message.getCategory(), request, renderResponse);
		}

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

		String contentType = StringUtil.toLowerCase(part.getContentType());

		if ((part.getDisposition() != null) &&
			StringUtil.equalsIgnoreCase(
				part.getDisposition(), MimeMessage.ATTACHMENT)) {

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
				Map<String, Object> options = new HashMap<String, Object>();

				options.put("emailPartToMBMessageBody", Boolean.TRUE);

				String messageBody = SanitizerUtil.sanitize(
					0, 0, 0, MBMessage.class.getName(), 0, contentType,
					Sanitizer.MODE_ALL, (String)partContent, options);

				if (contentType.startsWith(ContentTypes.TEXT_HTML)) {
					mbMailMessage.setHtmlBody(messageBody);
				}
				else {
					mbMailMessage.setPlainBody(messageBody);
				}
			}
		}
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long mbCategoryId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (mbCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			return themeDisplay.translate("home");
		}

		MBCategory mbCategory = MBCategoryLocalServiceUtil.fetchMBCategory(
			mbCategoryId);

		List<MBCategory> categories = mbCategory.getAncestors();

		Collections.reverse(categories);

		StringBundler sb = new StringBundler((categories.size() * 3) + 5);

		sb.append(themeDisplay.translate("home"));
		sb.append(StringPool.SPACE);

		for (MBCategory curCategory : categories) {
			sb.append(StringPool.RAQUO_CHAR);
			sb.append(StringPool.SPACE);
			sb.append(curCategory.getName());
		}

		sb.append(StringPool.RAQUO_CHAR);
		sb.append(StringPool.SPACE);
		sb.append(mbCategory.getName());

		return sb.toString();
	}

	public static String getBBCodeHTML(String msgBody, String pathThemeImages) {
		return StringUtil.replace(
			BBCodeTranslatorUtil.getHTML(msgBody),
			ThemeConstants.TOKEN_THEME_IMAGES_PATH + EMOTICONS,
			pathThemeImages + EMOTICONS);
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

	public static Set<Long> getCategorySubscriptionClassPKs(long userId) {
		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				userId, MBCategory.class.getName());

		Set<Long> classPKs = new HashSet<Long>(subscriptions.size());

		for (Subscription subscription : subscriptions) {
			classPKs.add(subscription.getClassPK());
		}

		return classPKs;
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$CATEGORY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-category-in-which-the-message-has-been-posted"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-message-board"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			definitionTerms.put(
				"[$MAILING_LIST_ADDRESS$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-email-address-of-the-mailing-list"));
		}

		definitionTerms.put(
			"[$MESSAGE_BODY$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-body"));
		definitionTerms.put(
			"[$MESSAGE_ID$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-id"));
		definitionTerms.put(
			"[$MESSAGE_SUBJECT$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-subject"));
		definitionTerms.put(
			"[$MESSAGE_URL$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-message-url"));
		definitionTerms.put(
			"[$MESSAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-message"));
		definitionTerms.put(
			"[$MESSAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-message"));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(portletRequest));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-message-board"));

		if (!PropsValues.MESSAGE_BOARDS_EMAIL_BULK) {
			definitionTerms.put(
				"[$TO_ADDRESS$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-address-of-the-email-recipient"));
			definitionTerms.put(
				"[$TO_NAME$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-name-of-the-email-recipient"));
		}

		return definitionTerms;
	}

	public static Map<String, String> getEmailFromDefinitionTerms(
		PortletRequest portletRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms =
			new LinkedHashMap<String, String>();

		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-message-board"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-message-board"));

		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			definitionTerms.put(
				"[$MAILING_LIST_ADDRESS$]",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"the-email-address-of-the-mailing-list"));
		}

		definitionTerms.put(
			"[$MESSAGE_USER_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-email-address-of-the-user-who-added-the-message"));
		definitionTerms.put(
			"[$MESSAGE_USER_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-user-who-added-the-message"));
		definitionTerms.put(
			"[$PORTLET_NAME$]", PortalUtil.getPortletTitle(portletRequest));
		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-message-board"));

		return definitionTerms;
	}

	public static List<Object> getEntries(Hits hits) {
		List<Object> entries = new ArrayList<Object>();

		for (Document document : hits.getDocs()) {
			long categoryId = GetterUtil.getLong(
				document.get(Field.CATEGORY_ID));

			try {
				MBCategoryLocalServiceUtil.getCategory(categoryId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Message boards search index is stale and contains " +
							"category " + categoryId);
				}

				continue;
			}

			long threadId = GetterUtil.getLong(document.get("threadId"));

			try {
				MBThreadLocalServiceUtil.getThread(threadId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Message boards search index is stale and contains " +
							"thread " + threadId);
				}

				continue;
			}

			String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			Object obj = null;

			try {
				if (entryClassName.equals(DLFileEntry.class.getName())) {
					long classPK = GetterUtil.getLong(
						document.get(Field.CLASS_PK));

					MBMessageLocalServiceUtil.getMessage(classPK);

					obj = DLFileEntryLocalServiceUtil.getDLFileEntry(
						entryClassPK);
				}
				else if (entryClassName.equals(MBMessage.class.getName())) {
					obj = MBMessageLocalServiceUtil.getMessage(entryClassPK);
				}

				entries.add(obj);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Message boards search index is stale and contains " +
							"entry {className=" + entryClassName + ", " +
								"classPK=" + entryClassPK + "}");
				}

				continue;
			}
		}

		return entries;
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

		if (ArrayUtil.isNotEmpty(references)) {
			String reference = references[0];

			int x = reference.lastIndexOf("<mb.");

			if (x > -1) {
				int y = reference.indexOf(">", x);

				parentHeader = reference.substring(x, y);
			}
		}

		if (parentHeader == null) {
			String[] inReplyToHeaders = message.getHeader("In-Reply-To");

			if (ArrayUtil.isNotEmpty(inReplyToHeaders)) {
				parentHeader = inReplyToHeaders[0];
			}
		}

		if (Validator.isNull(parentHeader) ||
			!parentHeader.startsWith(MESSAGE_POP_PORTLET_PREFIX, 1)) {

			parentHeader = _getParentMessageIdFromSubject(message);
		}

		return parentHeader;
	}

	public static String getReplyToAddress(
		long categoryId, long messageId, String mx,
		String defaultMailingListAddress) {

		if (PropsValues.POP_SERVER_SUBDOMAIN.length() <= 0) {
			return defaultMailingListAddress;
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

	public static String getSubjectForEmail(Message message) throws Exception {
		long parentMessageId = getParentMessageId(message);

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMBMessage(
			parentMessageId);

		String subject = parentMessage.getSubject();

		if (subject.startsWith("RE:")) {
			return subject;
		}
		else {
			return "RE: " + parentMessage.getSubject();
		}
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
			MBSettings mbSettings, String languageId, double value,
			ThemeDisplay themeDisplay)
		throws Exception {

		String[] priorities = mbSettings.getPriorities(languageId);

		String[] priorityPair = _findThreadPriority(
			value, themeDisplay, priorities);

		if (priorityPair == null) {
			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());

			priorities = mbSettings.getPriorities(defaultLanguageId);

			priorityPair = _findThreadPriority(value, themeDisplay, priorities);
		}

		return priorityPair;
	}

	public static Set<Long> getThreadSubscriptionClassPKs(long userId) {
		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				userId, MBThread.class.getName());

		Set<Long> classPKs = new HashSet<Long>(subscriptions.size());

		for (Subscription subscription : subscriptions) {
			classPKs.add(subscription.getClassPK());
		}

		return classPKs;
	}

	public static Date getUnbanDate(MBBan ban, int expireInterval) {
		Date banDate = ban.getCreateDate();

		Calendar cal = Calendar.getInstance();

		cal.setTime(banDate);

		cal.add(Calendar.DATE, expireInterval);

		return cal.getTime();
	}

	public static String getUserRank(
			MBSettings mbSettings, String languageId, int posts)
		throws Exception {

		String rank = StringPool.BLANK;

		String[] ranks = mbSettings.getRanks(languageId);

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
			MBSettings mbSettings, String languageId, MBStatsUser statsUser)
		throws Exception {

		String[] rank = {StringPool.BLANK, StringPool.BLANK};

		int maxPosts = 0;

		Group group = GroupLocalServiceUtil.getGroup(statsUser.getGroupId());

		long companyId = group.getCompanyId();

		String[] ranks = mbSettings.getRanks(languageId);

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

	public static boolean isValidMessageFormat(String messageFormat) {
		String editorImpl = PropsUtil.get(BB_CODE_EDITOR_WYSIWYG_IMPL_KEY);

		if (messageFormat.equals("bbcode") &&
			!(editorImpl.equals("bbcode") ||
			  editorImpl.equals("ckeditor_bbcode"))) {

			return false;
		}

		return true;
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

	public static void propagatePermissions(
			long companyId, long groupId, long parentMessageId,
			ServiceContext serviceContext)
		throws PortalException {

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMBMessage(
			parentMessageId);

		Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(
			groupId);
		Role guestRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		long[] roleIds = {defaultGroupRole.getRoleId(), guestRole.getRoleId()};

		List<String> actionIds = ResourceActionsUtil.getModelResourceActions(
			MBMessage.class.getName());

		Map<Long, Set<String>> roleIdsToActionIds =
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					companyId, MBMessage.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(parentMessage.getMessageId()), roleIds,
					actionIds);

		Set<String> defaultGroupActionIds = roleIdsToActionIds.get(
			defaultGroupRole.getRoleId());

		if (defaultGroupActionIds == null) {
			serviceContext.setGroupPermissions(new String[]{});
		}
		else {
			serviceContext.setGroupPermissions(
				defaultGroupActionIds.toArray(
					new String[defaultGroupActionIds.size()]));
		}

		Set<String> guestActionIds = roleIdsToActionIds.get(
			guestRole.getRoleId());

		if (guestActionIds == null) {
			serviceContext.setGuestPermissions(new String[]{});
		}
		else {
			serviceContext.setGuestPermissions(
				guestActionIds.toArray(new String[guestActionIds.size()]));
		}
	}

	public static String replaceMessageBodyPaths(
		ThemeDisplay themeDisplay, String messageBody) {

		return StringUtil.replace(
			messageBody,
			new String[] {
				ThemeConstants.TOKEN_THEME_IMAGES_PATH, "href=\"/", "src=\"/"
			},
			new String[] {
				themeDisplay.getPathThemeImages(),
				"href=\"" + themeDisplay.getURLPortal() + "/",
				"src=\"" + themeDisplay.getURLPortal() + "/"
			});
	}

	public static void updateCategoryMessageCount(
		long companyId, final long categoryId) {

		Callable<Void> callable = new ShardCallable<Void>(companyId) {

			@Override
			protected Void doCall() throws Exception {
				MBCategory category =
					MBCategoryLocalServiceUtil.fetchMBCategory(categoryId);

				if (category == null) {
					return null;
				}

				int messageCount = _getMessageCount(category);

				category.setMessageCount(messageCount);

				MBCategoryLocalServiceUtil.updateMBCategory(category);

				return null;
			}

		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);
	}

	public static void updateCategoryStatistics(
		long companyId, final long categoryId) {

		Callable<Void> callable = new ShardCallable<Void>(companyId) {

			@Override
			protected Void doCall() throws Exception {
				MBCategory category =
					MBCategoryLocalServiceUtil.fetchMBCategory(categoryId);

				if (category == null) {
					return null;
				}

				int messageCount = _getMessageCount(category);

				int threadCount =
					MBThreadLocalServiceUtil.getCategoryThreadsCount(
						category.getGroupId(), category.getCategoryId(),
						WorkflowConstants.STATUS_APPROVED);

				category.setMessageCount(messageCount);
				category.setThreadCount(threadCount);

				MBCategoryLocalServiceUtil.updateMBCategory(category);

				return null;
			}

		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);
	}

	public static void updateCategoryThreadCount(
		long companyId, final long categoryId) {

		Callable<Void> callable = new ShardCallable<Void>(companyId) {

			@Override
			protected Void doCall() throws Exception {
				MBCategory category =
					MBCategoryLocalServiceUtil.fetchMBCategory(categoryId);

				if (category == null) {
					return null;
				}

				int threadCount =
					MBThreadLocalServiceUtil.getCategoryThreadsCount(
						category.getGroupId(), category.getCategoryId(),
						WorkflowConstants.STATUS_APPROVED);

				category.setThreadCount(threadCount);

				MBCategoryLocalServiceUtil.updateMBCategory(category);

				return null;
			}

		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);
	}

	public static void updateThreadMessageCount(
		long companyId, final long threadId) {

		Callable<Void> callable = new ShardCallable<Void>(companyId) {

			@Override
			protected Void doCall() throws Exception {
				MBThread thread = MBThreadLocalServiceUtil.fetchThread(
					threadId);

				if (thread == null) {
					return null;
				}

				int messageCount =
					MBMessageLocalServiceUtil.getThreadMessagesCount(
						threadId, WorkflowConstants.STATUS_APPROVED);

				thread.setMessageCount(messageCount);

				MBThreadLocalServiceUtil.updateMBThread(thread);

				return null;
			}

		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);
	}

	private static String[] _findThreadPriority(
		double value, ThemeDisplay themeDisplay, String[] priorities) {

		for (int i = 0; i < priorities.length; i++) {
			String[] priority = StringUtil.split(
				priorities[i], StringPool.PIPE);

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

	private static int _getMessageCount(MBCategory category) {
		return MBMessageLocalServiceUtil.getCategoryMessagesCount(
			category.getGroupId(), category.getCategoryId(),
			WorkflowConstants.STATUS_APPROVED);
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