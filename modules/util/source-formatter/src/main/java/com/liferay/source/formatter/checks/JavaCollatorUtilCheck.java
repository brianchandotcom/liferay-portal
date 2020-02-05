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

import com.liferay.portal.tools.ToolsUtil;

/**
 * @author Preston Crary
 */
public class JavaCollatorUtilCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("CollatorUtil.java") &&
			!fileName.endsWith("CollatorUtilTest.java")) {

			_checkCollatorGetInstance(fileName, content);
		}

		return content;
	}

	private void _checkCollatorGetInstance(String fileName, String content) {
		int index = content.indexOf("Collator.getInstance(");

		while (index != -1) {
			if (!ToolsUtil.isInsideQuotes(content, index)) {
				addMessage(
					fileName, "Use CollatorUtil.getInstance(Locale)",
					getLineNumber(content, index));
			}

			index = content.indexOf("Collator.getInstance(", index + 1);
		}
	}

}