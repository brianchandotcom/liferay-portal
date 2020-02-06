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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class MarkdownSourceFormatterDocumentationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains(
				"/modules/util/source-formatter/src/main/resources" +
					"/documentation/checks/")) {

			return content;
		}

		int x = absolutePath.lastIndexOf(CharPool.SLASH);
		int y = absolutePath.lastIndexOf(CharPool.PERIOD);

		String expectedHeaderName = StringUtil.removeChar(
			absolutePath.substring(x + 1, y), CharPool.UNDERLINE);

		if (!StringUtil.startsWith(
				content, "## " + expectedHeaderName + "\n")) {

			addMessage(
				fileName,
				"There should be a header name corresponding with the name " +
					"of the documented check");
		}

		return content;
	}

}