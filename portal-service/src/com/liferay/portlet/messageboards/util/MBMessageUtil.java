/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.messageboards.model.MBMessage;

/**
 * @author Roberto Díaz
 */
public class MBMessageUtil {

	public static final String EMOTICONS = "/emoticons";

	public static final String THEME_IMAGES_PATH = "@theme_images_path@";

	public static String formatMessage(
		MBMessage message, int messageBodyLength, String pathThemeImages) {

		String msgBody = message.getBody();

		if (messageBodyLength > 0) {
			msgBody = StringUtil.shorten(msgBody, messageBodyLength);
		}

		if (message.isFormatBBCode()) {
			return formatMessageBodyToBBCode(
				message.getBody(), pathThemeImages);
		}

		return msgBody;
	}

	public static String formatMessage(
		MBMessage message, String pathThemeImages) {

		return formatMessage(message, 0, pathThemeImages);
	}

	public static String formatMessageBodyToBBCode(
		String msgBody, String pathThemeImages) {

		msgBody = BBCodeTranslatorUtil.getHTML(msgBody);

		return StringUtil.replace(
			msgBody, THEME_IMAGES_PATH + EMOTICONS,
			pathThemeImages + EMOTICONS);
	}

}