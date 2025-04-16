/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class CSPTagIllegalAttributesCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		List<String> ignoredTagPrefixes = new ArrayList<>();
		String liferayUiCSPTagClose = StringPool.BLANK;
		String liferayUiCSPTagOpen = StringPool.BLANK;

		if (fileName.endsWith(".ftl")) {
			ignoredTagPrefixes = getAttributeValues(
				_IGNORED_FTL_TAG_PREFIXES_KEY, absolutePath);
			liferayUiCSPTagClose = "</@liferay_ui.csp>";
			liferayUiCSPTagOpen = "<@liferay_ui.csp>";
		}
		else if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf") ||
				 fileName.endsWith(".jspx")) {

			ignoredTagPrefixes = getAttributeValues(
				_IGNORED_JSP_TAG_PREFIXES_KEY, absolutePath);
			liferayUiCSPTagClose = "</liferay-ui:csp>";
			liferayUiCSPTagOpen = "<liferay-ui:csp>";
		}

		String lowerCaseContent = StringUtil.toLowerCase(content);

		List<String> illegalAttributeNames = getAttributeValues(
			_ILLEGAL_ATTRIBUTE_NAMES_KEY, absolutePath);

		for (String illegalAttributeName : illegalAttributeNames) {
			int x = -1;

			outerLoop:
			while (true) {
				x = lowerCaseContent.indexOf(
					illegalAttributeName + StringPool.EQUAL, x + 1);

				if (x == -1) {
					break;
				}

				if ((x == 0) ||
					!Character.isWhitespace(content.charAt(x - 1)) ||
					isJavaSource(content, x)) {

					continue;
				}

				int tagStartPosition = _getTagStartPosition(content, x);

				if (tagStartPosition == -1) {
					continue;
				}

				String tagString = getTag(content, tagStartPosition);

				if (Validator.isNull(tagString)) {
					continue;
				}

				if (illegalAttributeName.equals("style")) {
					Tag tag = parseTag(tagString, false);

					if (tag == null) {
						continue;
					}

					String tagName = tag.getName();

					if (!tagName.matches("\\w+")) {
						continue;
					}
				}

				for (String ignoredTagPrefix : ignoredTagPrefixes) {
					if (tagString.startsWith(ignoredTagPrefix)) {
						continue outerLoop;
					}
				}

				String previousPart = content.substring(0, tagStartPosition);

				int y = previousPart.lastIndexOf(liferayUiCSPTagClose);
				int z = previousPart.lastIndexOf(liferayUiCSPTagOpen);

				if (z > y) {
					continue;
				}

				addMessage(
					fileName,
					"Tag attribute \"" + illegalAttributeName +
						"\" is not allowed, see LPD-18227",
					getLineNumber(content, x));
			}
		}

		return content;
	}

	private int _getTagStartPosition(String content, int x) {
		while (x >= 0) {
			char c = content.charAt(x);

			if ((c == CharPool.LESS_THAN) &&
				(content.charAt(x + 1) != CharPool.EQUAL) &&
				(content.charAt(x + 1) != CharPool.PERCENT) &&
				!isJavaSource(content, x, true)) {

				return x;
			}

			x = x - 1;
		}

		return -1;
	}

	private static final String _IGNORED_FTL_TAG_PREFIXES_KEY =
		"ignoredFTLTagPrefixes";

	private static final String _IGNORED_JSP_TAG_PREFIXES_KEY =
		"ignoredJSPTagPrefixes";

	private static final String _ILLEGAL_ATTRIBUTE_NAMES_KEY =
		"illegalAttributeNames";

}