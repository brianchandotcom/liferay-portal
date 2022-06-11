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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesTestFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("test.properties")) {
			return content;
		}

		_checkPropertiesOrder(
			fileName, content, StringPool.BLANK,
			StringPool.POUND + StringPool.POUND);

		return content;
	}

	private void _checkPropertiesOrder(
		String fileName, String content, String indent, String pounds) {

		String indentWithPounds = indent + pounds;

		CommentComparator comparator = new CommentComparator();

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"(?<=\\A|\n\n)", indentWithPounds, "\n", indentWithPounds,
				"( .+)\n", indentWithPounds, "((?=\n\n))"));

		Matcher matcher = pattern.matcher(content);

		String previousBlockComment = null;
		int previousBlockCommentStartPosition = -1;

		while (matcher.find()) {
			String blockComment = matcher.group(1);
			int blockCommentStartPosition = matcher.start();

			if (Validator.isNull(previousBlockComment)) {
				previousBlockComment = blockComment;
				previousBlockCommentStartPosition = blockCommentStartPosition;

				continue;
			}

			if (comparator.compare(previousBlockComment, blockComment) > 0) {
				StringBundler sb = new StringBundler(7);

				sb.append("Incorrect order: Properties block '");
				sb.append(pounds);
				sb.append(previousBlockComment);
				sb.append("' should come after '");
				sb.append(pounds);
				sb.append(blockComment);
				sb.append("'");

				addMessage(fileName, sb.toString());
			}

			if (pounds.length() == 2) {
				_checkPropertiesOrder(
					fileName,
					content.substring(
						previousBlockCommentStartPosition,
						blockCommentStartPosition),
					indent + StringPool.FOUR_SPACES, StringPool.POUND);
			}

			previousBlockComment = blockComment;
			previousBlockCommentStartPosition = blockCommentStartPosition;
		}
	}

	private class CommentComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String comment1, String comment2) {
			if (comment1.equals(" Default")) {
				return -1;
			}

			if (comment2.equals(" Default")) {
				return 1;
			}

			return super.compare(comment1, comment2);
		}

	}

}