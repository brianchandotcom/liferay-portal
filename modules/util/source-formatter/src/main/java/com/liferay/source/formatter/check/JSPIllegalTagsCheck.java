/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.poshi.core.util.StringUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JSPIllegalTagsCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String lowerCaseFileName = StringUtil.lowerCase(fileName);

		List<String> illegalTagNames = getAttributeValues(
			_ILLEGAL_TAG_NAMES_KEY, absolutePath);

		if (lowerCaseFileName.endsWith(".jsp") ||
			lowerCaseFileName.endsWith(".jspf") ||
			lowerCaseFileName.endsWith(".jspx")) {

			for (String illegalTagName : illegalTagNames) {
				int searchIndex = 0;

				while (true) {
					TagOccurrence tagOccurrence = _getNextTagOccurrence(
						illegalTagName, content, searchIndex);

					if (tagOccurrence == null) {
						break;
					}

					addMessage(
						fileName,
						StringBundler.concat(
							"Do not use <", illegalTagName, "> tag (use ",
							"<aui:", illegalTagName, "> instead), see ",
							"LPD-18227"),
						getLineNumber(content, tagOccurrence.searchIndex));

					String tag = tagOccurrence.tag;

					searchIndex += tagOccurrence.searchIndex + tag.length();
				}
			}
		}
		else if (lowerCaseFileName.endsWith(".ftl")) {
			_checkAttribute(
				"${nonceAttribute}", content, fileName, illegalTagNames);
		}
		else if (lowerCaseFileName.endsWith(".vm")) {
			_checkAttribute(
				"$nonceAttribute", content, fileName, illegalTagNames);
		}

		return content;
	}

	private void _checkAttribute(
		String attribute, String content, String fileName,
		List<String> illegalTagNames) {

		for (String illegalTagName : illegalTagNames) {
			int searchIndex = 0;

			while (true) {
				TagOccurrence tagOccurrence = _getNextTagOccurrence(
					illegalTagName, content, searchIndex);

				if (tagOccurrence == null) {
					break;
				}

				String tag = tagOccurrence.tag;

				if (!tag.contains(attribute)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Tag <", illegalTagName, "> is missing attribute '",
							attribute, "', see LPD-18227"),
						getLineNumber(content, tagOccurrence.searchIndex));
				}

				searchIndex += tagOccurrence.searchIndex + tag.length();
			}
		}
	}

	private TagOccurrence _getNextTagOccurrence(
		String illegalTagName, String content, int searchIndex) {

		String requiredAttribute = null;

		int openBracketIndex = illegalTagName.indexOf(StringPool.OPEN_BRACKET);

		if (openBracketIndex != -1) {
			requiredAttribute = illegalTagName.substring(
				openBracketIndex + 1,
				illegalTagName.indexOf(StringPool.CLOSE_BRACKET));

			illegalTagName = illegalTagName.substring(0, openBracketIndex);
		}

		while (true) {
			searchIndex = content.indexOf(
				CharPool.LESS_THAN + illegalTagName, searchIndex + 1);

			if (searchIndex == -1) {
				return null;
			}

			int greaterThanIndex = searchIndex + 1;

			// Instead of correctly parsing the content, which would require
			// treating escaping correctly and would take way more time, we will
			// detect exceptions for > characters inside the tag we are
			// extracting.

			do {
				greaterThanIndex = content.indexOf(
					CharPool.GREATER_THAN, greaterThanIndex + 1);

				if (greaterThanIndex == -1) {
					return null;
				}
			}
			while (content.charAt(greaterThanIndex - 1) == CharPool.PERCENT);

			String tag = content.substring(searchIndex, greaterThanIndex + 1);

			if ((requiredAttribute != null) &&
				!tag.contains(requiredAttribute)) {

				continue;
			}

			return new TagOccurrence(searchIndex, tag);
		}
	}

	private static final String _ILLEGAL_TAG_NAMES_KEY = "illegalTagNames";

	private static class TagOccurrence {

		public TagOccurrence(int searchIndex, String tag) {
			this.searchIndex = searchIndex;
			this.tag = tag;
		}

		public final int searchIndex;
		public final String tag;

	}

}