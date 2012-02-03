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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class WordPressImporter {

	public static void importData(PortletDataContext context)
		throws PortalException, SystemException {

		Map<String, Long> userMap = getWordPressUserMap(context);

		String path = getWordPressPath(context, _EXPORT_FILE);

		String fileData = context.getZipEntryAsString(path);

		if (Validator.isNull(fileData)) {
			return;
		}

		Document wordPressDoc = null;

		try {
			wordPressDoc = SAXReaderUtil.read(fileData);
		}
		catch (DocumentException de) {
			_log.error("Reading " + path, de);

			return;
		}

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			context.getCompanyId());

		Element root = wordPressDoc.getRootElement();

		List<Element> entryEls = root.element("channel").elements("item");

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_DATE_FORMAT);

		dateFormat.setTimeZone(TimeZoneUtil.getTimeZone(StringPool.UTC));

		for (Element entryEl : entryEls) {
			importEntry(context, defaultUser, userMap, dateFormat, entryEl);
		}
	}

	protected static String getWordPressPath(
		PortletDataContext context, String fileName) {

		return context.getSourcePortletPath(PortletKeys.BLOGS).concat(
			StringPool.SLASH).concat(fileName);
	}

	protected static Map<String, Long> getWordPressUserMap(
		PortletDataContext context) {

		Map<String, Long> userMap = new HashMap<String, Long>();

		String path = getWordPressPath(context, _USER_MAP_FILE);

		String fileData = context.getZipEntryAsString(path);

		if (Validator.isNull(fileData)) {
			return userMap;
		}

		Document doc = null;

		try {
			doc = SAXReaderUtil.read(fileData);
		}
		catch (DocumentException de) {
			_log.error(de.getMessage(), de);

			return userMap;
		}

		Element root = doc.getRootElement();

		List<Element> userEls = root.elements("wordpress-user");

		for (Element userEl : userEls) {
			try {
				User user = UserUtil.findByC_EA(
					context.getCompanyId(),
					userEl.attributeValue("email-address"));

				userMap.put(userEl.getTextTrim(), user.getUserId());
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"User for {" + context.getCompanyId() + ", " +
							userEl.attributeValue("email-address") + "}", e);
				}
			}
		}

		return userMap;
	}

	protected static void importComment(
			PortletDataContext context, User defaultUser,
			MBMessageDisplay messageDisplay, Map<Long, Long> messageIdMap,
			BlogsEntry entry, Element commentEl)
		throws PortalException, SystemException {

		MBThread thread = messageDisplay.getThread();

		long commentId = GetterUtil.getLong(
			commentEl.elementTextTrim(
				SAXReaderUtil.createQName("comment_id", _NS_WP)));

		String commentContent = commentEl.elementTextTrim(
			SAXReaderUtil.createQName("comment_content", _NS_WP));

		if (Validator.isNull(commentContent)) {
			return;
		}

		String commentAuthor = commentEl.elementTextTrim(
			SAXReaderUtil.createQName("comment_author", _NS_WP));

		commentAuthor = commentAuthor.substring(
			0, Math.min(75, commentAuthor.length()));

		long commentParentId = GetterUtil.getLong(
			commentEl.elementTextTrim(
				SAXReaderUtil.createQName("comment_parent", _NS_WP)));

		if (commentParentId == 0) {
			commentParentId = messageDisplay.getMessage().getMessageId();
		}
		else {
			commentParentId = messageIdMap.get(commentParentId);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		MBMessage message = MBMessageLocalServiceUtil.addDiscussionMessage(
			defaultUser.getUserId(), commentAuthor, context.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			thread.getThreadId(), commentParentId, null, commentContent,
			serviceContext);

		messageIdMap.put(commentId, message.getMessageId());
	}

	protected static void importEntry(
			PortletDataContext context, User defaultUser,
			Map<String, Long> userMap, DateFormat dateFormat, Element entryEl)
		throws PortalException, SystemException {

		String creator = entryEl.elementText(
			SAXReaderUtil.createQName("creator", _NS_DC));

		Long userId = userMap.get(creator);

		if (userId == null) {
			userId = context.getUserId(null);
		}

		String title = entryEl.elementTextTrim("title");

		if (Validator.isNull(title)) {
			title = entryEl.elementTextTrim(
				SAXReaderUtil.createQName("post_name", _NS_WP));
		}

		String content = entryEl.elementText(
			SAXReaderUtil.createQName("encoded", _NS_CONTENT));

		content = content.replaceAll("\\n", "\n<br />");

		// LPS-1425

		if (Validator.isNull(content)) {
			content = "<br />";
		}

		String dateText = entryEl.elementTextTrim(
			SAXReaderUtil.createQName("post_date_gmt", _NS_WP));

		Date postDate = new Date();

		try {
			postDate = dateFormat.parse(dateText);
		}
		catch (ParseException pe) {
			_log.warn("Parse " + dateText, pe);
		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(postDate);

		int displayDateMonth = cal.get(Calendar.MONTH);
		int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = cal.get(Calendar.YEAR);
		int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = cal.get(Calendar.MINUTE);

		String pingStatusText = entryEl.elementTextTrim(
			SAXReaderUtil.createQName("ping_status", _NS_WP));

		boolean allowPingbacks = pingStatusText.equalsIgnoreCase("open");
		boolean allowTrackbacks = allowPingbacks;

		String statusText = entryEl.elementTextTrim(
			SAXReaderUtil.createQName("status", _NS_WP));

		int workflowAction = WorkflowConstants.ACTION_PUBLISH;

		if (statusText.equalsIgnoreCase("draft")) {
			workflowAction = WorkflowConstants.ACTION_SAVE_DRAFT;
		}

		String[] assetTagNames = null;

		String categoryText = entryEl.elementTextTrim("category");

		if (Validator.isNotNull(categoryText)) {
			assetTagNames = new String[] {categoryText};
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setScopeGroupId(context.getGroupId());
		serviceContext.setWorkflowAction(workflowAction);

		BlogsEntry entry = null;

		try {
			entry = BlogsEntryLocalServiceUtil.addEntry(
				userId, title, StringPool.BLANK, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, null, false,
				null, null, null, serviceContext);
		}
		catch (Exception e) {
			_log.error("Add entry " + title, e);

			return;
		}

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, context.getGroupId(), BlogsEntry.class.getName(),
				entry.getEntryId(), WorkflowConstants.STATUS_APPROVED);

		Map<Long, Long> messageIdMap = new HashMap<Long, Long>();

		List<Node> commentNodes = entryEl.selectNodes(
			"wp:comment", "wp:comment_parent/text()");

		for (Node commentNode : commentNodes) {
			Element commentEl = (Element)commentNode;

			importComment(
				context, defaultUser, messageDisplay, messageIdMap, entry,
				commentEl);
		}
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final String _EXPORT_FILE = "wordpress.xml";

	private static final Namespace _NS_CONTENT = SAXReaderUtil.createNamespace(
		"content", "http://purl.org/rss/1.0/modules/content/");

	private static final Namespace _NS_DC = SAXReaderUtil.createNamespace(
		"dc", "http://purl.org/dc/elements/1.1/");

	private static final Namespace _NS_WP = SAXReaderUtil.createNamespace(
		"wp", "http://wordpress.org/export/1.0/");

	private static final String _USER_MAP_FILE = "wordpress-user-map.xml";

	private static Log _log = LogFactoryUtil.getLog(WordPressImporter.class);

}