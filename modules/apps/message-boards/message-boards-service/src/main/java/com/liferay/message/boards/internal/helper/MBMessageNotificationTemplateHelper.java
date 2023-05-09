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

package com.liferay.message.boards.internal.helper;

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.internal.util.MBUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class MBMessageNotificationTemplateHelper {

	public MBMessageNotificationTemplateHelper(
		boolean htmlFormat, MBMessageLocalService mbMessageLocalService,
		ServiceContext serviceContext) {

		_htmlFormat = htmlFormat;
		_mbMessageLocalService = mbMessageLocalService;
		_serviceContext = serviceContext;
	}

	public String getMessageBody(MBMessage message, String quoteMark) {
		if (!_htmlFormat) {
			return _getQuotedMessage(true, message.getBody(), quoteMark);
		}

		if (!message.isFormatBBCode()) {
			return message.getBody();
		}

		try {
			String messageBody = BBCodeTranslatorUtil.getHTML(
				message.getBody());

			HttpServletRequest httpServletRequest =
				_serviceContext.getRequest();

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
					"Unable to parse message ", message.getMessageId(), ": ",
					exception.getMessage()));
		}

		return message.getBody();
	}

	public String renderRootMessage(MBMessage message) throws PortalException {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-182020") ||
			(message.getParentMessageId() ==
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID)) {

			return StringPool.BLANK;
		}

		MBMessage rootMessage = _mbMessageLocalService.getMessage(
			message.getRootMessageId());

		return StringBundler.concat(
			_getMarkupElement(MarkupElement.START_ROOT),
			_getMarkupElement(MarkupElement.START_USER_ROOT),
			_getUserName(rootMessage, _getQuoteMark()),
			_getMarkupElement(MarkupElement.END),
			_getMarkupElement(MarkupElement.START_BODY_ROOT),
			getMessageBody(rootMessage, _getQuoteMark()),
			_getMarkupElement(MarkupElement.END),
			_getMarkupElement(MarkupElement.END));
	}

	private String _getMarkupElement(MarkupElement element) {
		if (!_htmlFormat) {
			return StringPool.BLANK;
		}

		return _markupElements.getOrDefault(element, StringPool.BLANK);
	}

	private String _getQuotedMessage(
		boolean lastPosition, String messageBody, String quoteMark) {

		if (Validator.isBlank(quoteMark)) {
			return messageBody;
		}

		StringBundler sb = new StringBundler();

		for (String line : messageBody.split(StringPool.NEW_LINE)) {
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

	private String _getQuoteMark() {
		if (_htmlFormat) {
			return StringPool.BLANK;
		}

		return _QUOTE_MARK + StringPool.SPACE;
	}

	private String _getUserName(MBMessage message, String quoteMark) {
		if (!_htmlFormat) {
			return _getQuotedMessage(false, message.getUserName(), quoteMark);
		}

		return message.getUserName() + "<br />";
	}

	private static final String _QUOTE_MARK = StringPool.GREATER_THAN;

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageNotificationTemplateHelper.class);

	private final boolean _htmlFormat;
	private final Map<MarkupElement, String> _markupElements =
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
	private final MBMessageLocalService _mbMessageLocalService;
	private final ServiceContext _serviceContext;

	private enum MarkupElement {

		END, END_ELEMENT, END_MESSAGE, START_BODY_ROOT, START_BODY_SIBLING,
		START_MESSAGE, START_MESSAGE_BODY, START_MESSAGE_SIBLING,
		START_MESSAGE_THREAD, START_ROOT, START_SIBLING, START_USER_MESSAGE,
		START_USER_ROOT, START_USER_SIBLING,

	}

}