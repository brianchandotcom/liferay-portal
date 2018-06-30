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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Hugo Huijser
 */
public class SlantedQuotesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		int x = content.length() + 1;

		while (true) {
			x = Math.max(
				content.lastIndexOf('\u201c', x - 1),
				content.lastIndexOf('\u201d', x - 1));

			if (x == -1) {
				return content;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			content = StringBundler.concat(
				content.substring(0, x), StringPool.QUOTE,
				content.substring(x + 1));
		}
	}

}