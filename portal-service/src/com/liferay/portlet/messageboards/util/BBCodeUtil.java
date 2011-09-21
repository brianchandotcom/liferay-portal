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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portlet.messageboards.model.MBMessage;

/**
 * @author Alexander Chow
 */
public class BBCodeUtil {

	static String[][] EMOTICONS = BBCodeTranslatorUtil.getEmoticons();

	static String[] EMOTICONS_DESCRIPTIONS =
		BBCodeTranslatorUtil.getEmoticonDescriptions();

	static String[] EMOTICONS_FILES =
		BBCodeTranslatorUtil.getEmoticonFiles();

	static String[] EMOTICONS_SYMBOLS =
		BBCodeTranslatorUtil.getEmoticonSymbols();

	public static final String NEW_THREAD_URL = "${newThreadURL}";

	public static String getHTML(MBMessage message) {
		String body = message.getBody();

		try {
			body = getHTML(body);
		}
		catch (Exception e) {
			_log.error(
				"Could not parse message " + message.getMessageId() + " " +
					e.getMessage());
		}

		return body;
	}

	public static String getHTML(String bbcode) {
		try {
			bbcode = BBCodeTranslatorUtil.parse(bbcode);
		}
		catch (Exception e) {
			_log.error(
				"Could not parse bbcode: " + bbcode + " " + e.getMessage());

			bbcode = HtmlUtil.escape(bbcode);
		}

		return bbcode;
	}

	private static Log _log = LogFactoryUtil.getLog(BBCodeUtil.class);

}