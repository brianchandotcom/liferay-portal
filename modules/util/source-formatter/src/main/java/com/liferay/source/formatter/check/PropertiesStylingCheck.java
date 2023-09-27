/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class PropertiesStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = content.replaceAll(
			"(\n\n)((( *#+)( [^#\n]+)\n)+( *#))", "$1$4\n$2");

		content = content.replaceAll(
			"(\n\n)( *#+)(\n(\\2( [^#\n]+)\n)+)(?! *#)", "$1$2$3$2\n");

		content = content.replaceAll(
			"(\\A|(?<!\\\\)\n)( *[\\w.-]+)(( +=)|(= +))(.*)(\\Z|\n)",
			"$1$2=$6$7");

		content = content.replaceAll("(?m)^(.*,) +(\\\\)$", "$1$2");

		if (fileName.endsWith("test.properties")) {
			return _sortTestProperties(
				fileName, content, StringPool.BLANK,
				StringPool.POUND + StringPool.POUND);
		}

		return content;
	}

	private String _sortTestProperties(
		String fileName, String content, String indent, String pounds) {

		String indentWithPounds = indent + pounds;

		CommentComparator comparator = new CommentComparator();

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"((?<=\\A|\n\n)", indentWithPounds, "\n", indentWithPounds,
				"( .+)\n", indentWithPounds, "\n\n[\\s\\S]*?)(?=(\n\n",
				indentWithPounds, "\n|\\Z))"));

		Matcher matcher = pattern.matcher(content);

		String previousProperties = null;
		String previousPropertiesComment = null;
		int previousPropertiesStartPosition = -1;

		while (matcher.find()) {
			String properties = matcher.group(1);
			String propertiesComment = matcher.group(2);
			int propertiesStartPosition = matcher.start();

			if (pounds.length() == 2) {
				String newProperties = _sortTestProperties(
					fileName, properties, indent + StringPool.FOUR_SPACES,
					StringPool.POUND);

				if (!newProperties.equals(properties)) {
					return StringUtil.replaceFirst(
						content, properties, newProperties,
						propertiesStartPosition);
				}
			}

			if (Validator.isNull(previousProperties)) {
				previousProperties = properties;
				previousPropertiesComment = propertiesComment;
				previousPropertiesStartPosition = propertiesStartPosition;

				continue;
			}

			int value = comparator.compare(
				previousPropertiesComment, propertiesComment);

			if (value > 0) {
				content = StringUtil.replaceFirst(
					content, properties, previousProperties,
					propertiesStartPosition);
				content = StringUtil.replaceFirst(
					content, previousProperties, properties,
					previousPropertiesStartPosition);

				return content;
			}

			previousProperties = properties;
			previousPropertiesComment = propertiesComment;
			previousPropertiesStartPosition = propertiesStartPosition;
		}

		return content;
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