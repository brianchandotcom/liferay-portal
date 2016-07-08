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

package com.liferay.blogs.web.internal.util;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Roberto Díaz
 */
public class BlogsUtil {

	public static int getReadingTimeMinutes(String content) {
		String strippedContent = HtmlUtil.stripHtml(content);

		String[] words = StringUtil.split(strippedContent, StringPool.SPACE);

		int wordCount = words.length;

		if (wordCount < 250) {
			return 1;
		}

		return Math.round(wordCount / 250F);
	}

}