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

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public abstract class StylingCheck extends BaseFileCheck {

	protected String formatStyling(String content) {
		content = _formatStyling(
			content, "!Validator.isNotNull(", "Validator.isNull(");
		content = _formatStyling(
			content, "!Validator.isNull(", "Validator.isNotNull(");

		content = _formatStyling(content, "\nfor (;;) {", "\nwhile (true) {");
		content = _formatStyling(content, "\tfor (;;) {", "\twhile (true) {");

		content = _formatStyling(
			content, "String.valueOf(false)", "Boolean.FALSE.toString()");
		content = _formatStyling(
			content, "String.valueOf(true)", "Boolean.TRUE.toString()");

		return content;
	}

	protected boolean isJavaSource(String content, int pos) {
		return true;
	}

	private String _formatStyling(
		String content, String incorrectStyling, String correctStyling) {

		int x = -1;

		while (true) {
			x = content.indexOf(incorrectStyling, x + 1);

			if (x == -1) {
				return content;
			}

			if (isJavaSource(content, x)) {
				return StringUtil.replaceFirst(
					content, incorrectStyling, correctStyling);
			}
		}
	}

}