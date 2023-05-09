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

import com.liferay.message.boards.internal.util.MBUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class MBMessageNotificationTemplateHelper {

	public MBMessageNotificationTemplateHelper(
		boolean htmlFormat, ServiceContext serviceContext) {

		_htmlFormat = htmlFormat;
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

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageNotificationTemplateHelper.class);

	private final boolean _htmlFormat;
	private final ServiceContext _serviceContext;

}