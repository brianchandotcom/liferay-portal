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

package com.liferay.message.boards.util;

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ThemeConstants;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class MBUtil {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static final String BB_CODE_EDITOR_WYSIWYG_IMPL_KEY =
		"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
			"edit_message.bb_code.jsp";

	public static final String EMOTICONS = "/emoticons";

	public static String getBBCodeHTML(String msgBody, String pathThemeImages) {
		return StringUtil.replace(
			BBCodeTranslatorUtil.getHTML(msgBody),
			ThemeConstants.TOKEN_THEME_IMAGES_PATH + EMOTICONS,
			pathThemeImages + EMOTICONS);
	}

	public static String getSubscriptionClassName(String className) {
		if (className.startsWith(MBDiscussion.class.getName())) {
			return className;
		}

		return StringBundler.concat(
			MBDiscussion.class.getName(), StringPool.UNDERLINE, className);
	}

	public static Date getUnbanDate(MBBan ban, int expireInterval) {
		Date banDate = ban.getCreateDate();

		Calendar cal = Calendar.getInstance();

		cal.setTime(banDate);

		cal.add(Calendar.DATE, expireInterval);

		return cal.getTime();
	}

	public static boolean isValidMessageFormat(String messageFormat) {
		return ArrayUtil.contains(MBMessageConstants.FORMATS, messageFormat);
	}

}