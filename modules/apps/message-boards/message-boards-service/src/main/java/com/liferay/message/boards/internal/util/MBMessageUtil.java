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

package com.liferay.message.boards.internal.util;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.persistence.MBMessageFinder;
import com.liferay.message.boards.service.persistence.MBMessagePersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author Preston Crary
 */
public class MBMessageUtil {

	public static String getMessageBody(
		boolean htmlFormat, MBMessage mbMessage, String quoteMark,
		ServiceContext serviceContext) {

		if (!htmlFormat) {
			return _getQuotedMessage(true, mbMessage.getBody(), quoteMark);
		}

		if (!mbMessage.isFormatBBCode()) {
			return mbMessage.getBody();
		}

		try {
			String messageBody = BBCodeTranslatorUtil.getHTML(
				mbMessage.getBody());

			HttpServletRequest httpServletRequest = serviceContext.getRequest();

			if (httpServletRequest == null) {
				return messageBody;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return MBUtil.replaceMessageBodyPaths(themeDisplay, messageBody);
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to parse mbMessage ", mbMessage.getMessageId(),
					": ", exception.getMessage()));
		}

		return mbMessage.getBody();
	}

	public static List<MBMessage> getThreadMessages(
		MBMessagePersistence mbMessagePersistence,
		MBMessageFinder mbMessageFinder, long userId, long threadId, int status,
		int start, int end, Comparator<MBMessage> comparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			OrderByComparator<MBMessage> orderByComparator = null;

			if (comparator instanceof OrderByComparator) {
				orderByComparator = (OrderByComparator<MBMessage>)comparator;
			}

			List<MBMessage> messages = mbMessagePersistence.findByT_NotS(
				threadId, WorkflowConstants.STATUS_IN_TRASH, start, end,
				orderByComparator);

			if (!(comparator instanceof OrderByComparator)) {
				messages = ListUtil.sort(messages, comparator);
			}

			return messages;
		}

		QueryDefinition<MBMessage> queryDefinition = new QueryDefinition<>(
			status, userId, true, start, end, null);

		if (comparator instanceof OrderByComparator) {
			queryDefinition.setOrderByComparator(
				(OrderByComparator<MBMessage>)comparator);
		}

		List<MBMessage> messages = mbMessageFinder.findByThreadId(
			threadId, queryDefinition);

		if (!(comparator instanceof OrderByComparator)) {
			messages = ListUtil.sort(messages, comparator);
		}

		return messages;
	}

	public static String renderRootMessage(
		boolean htmlFormat, MBMessage mbMessage,
		ServiceContext serviceContext) {

		return StringBundler.concat(
			_getMarkupElement(MarkupElement.START_ROOT, htmlFormat),
			_getMarkupElement(MarkupElement.START_USER_ROOT, htmlFormat),
			_getUserName(htmlFormat, mbMessage, _getQuoteMark(htmlFormat)),
			_getMarkupElement(MarkupElement.END, htmlFormat),
			_getMarkupElement(MarkupElement.START_BODY_ROOT, htmlFormat),
			getMessageBody(
				htmlFormat, mbMessage, _getQuoteMark(htmlFormat),
				serviceContext),
			_getMarkupElement(MarkupElement.END, htmlFormat),
			_getMarkupElement(MarkupElement.END, htmlFormat));
	}

	public static MBMessage updateAnswer(
		MBMessagePersistence mbMessagePersistence, MBMessage message,
		boolean answer, boolean cascade) {

		if (message.isAnswer() != answer) {
			message.setAnswer(answer);

			message = mbMessagePersistence.update(message);
		}

		if (cascade) {
			List<MBMessage> messages = mbMessagePersistence.findByT_P(
				message.getThreadId(), message.getMessageId());

			for (MBMessage curMessage : messages) {
				updateAnswer(mbMessagePersistence, curMessage, answer, cascade);
			}
		}

		return message;
	}

	private static String _getMarkupElement(
		MarkupElement element, boolean htmlFormat) {

		if (!htmlFormat) {
			return StringPool.BLANK;
		}

		return _markupElements.getOrDefault(element, StringPool.BLANK);
	}

	private static String _getQuotedMessage(
		boolean lastPosition, String message, String quoteMark) {

		if (Validator.isBlank(quoteMark)) {
			return message;
		}

		StringBundler sb = new StringBundler();

		for (String line : message.split(StringPool.NEW_LINE)) {
			sb.append(StringPool.NEW_LINE);
			sb.append(quoteMark);
			sb.append(line);
		}

		sb.append(StringPool.NEW_LINE);

		if (!lastPosition) {
			sb.append(quoteMark);
		}

		return sb.toString();
	}

	private static String _getQuoteMark(boolean htmlFormat) {
		if (htmlFormat) {
			return StringPool.BLANK;
		}

		return _QUOTE_MARK + StringPool.SPACE;
	}

	private static String _getUserName(
		boolean htmlFormat, MBMessage mbMessage, String quoteMark) {

		if (!htmlFormat) {
			return _getQuotedMessage(false, mbMessage.getUserName(), quoteMark);
		}

		return mbMessage.getUserName() + "<br />";
	}

	private MBMessageUtil() {
	}

	private static final String _QUOTE_MARK = StringPool.GREATER_THAN;

	private static final Log _log = LogFactoryUtil.getLog(MBMessageUtil.class);

	private static final Map<MarkupElement, String> _markupElements =
		HashMapBuilder.put(
			MarkupElement.END, "</div>"
		).put(
			MarkupElement.END_ELEMENT, "</ul>"
		).put(
			MarkupElement.END_MESSAGE, "</li>"
		).put(
			MarkupElement.START_BODY_ROOT,
			"<div class=\"mb-root-message-body\">"
		).put(
			MarkupElement.START_BODY_SIBLING,
			"<div class=\"mb-sibling-message-body\">"
		).put(
			MarkupElement.START_MESSAGE, "<ul><li class=\"mb-parent-message\">"
		).put(
			MarkupElement.START_MESSAGE_BODY,
			"<div class=\"mb-parent-message-body\">"
		).put(
			MarkupElement.START_MESSAGE_SIBLING,
			"<div class=\"mb-sibling-message\">"
		).put(
			MarkupElement.START_MESSAGE_THREAD,
			"<div class=\"mb-parent-message-thread\">"
		).put(
			MarkupElement.START_ROOT, "<div class=\"mb-root-message\">"
		).put(
			MarkupElement.START_SIBLING,
			"<div class=\"mb-sibling-message-thread\">"
		).put(
			MarkupElement.START_USER_MESSAGE,
			"<div class=\"mb-parent-message-user\">"
		).put(
			MarkupElement.START_USER_ROOT,
			"<div class=\"mb-root-message-user\">"
		).put(
			MarkupElement.START_USER_SIBLING,
			"<div class=\"mb-sibling-message-user\">"
		).build();

	private static enum MarkupElement {

		END, END_ELEMENT, END_MESSAGE, START_BODY_ROOT, START_BODY_SIBLING,
		START_MESSAGE, START_MESSAGE_BODY, START_MESSAGE_SIBLING,
		START_MESSAGE_THREAD, START_ROOT, START_SIBLING, START_USER_MESSAGE,
		START_USER_ROOT, START_USER_SIBLING,

	}

}