/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class CSPComplianceCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String lowerCaseContent = StringUtil.toLowerCase(content);

		_checkIllegalAttributes(
			fileName, absolutePath, content, lowerCaseContent);
		_checkIllegalTags(fileName, absolutePath, content, lowerCaseContent);

		return content;
	}

	protected String getEndingTag(String s, int fromIndex) {
		int x = fromIndex;

		while (true) {
			x = s.indexOf(">", x + 1);

			if (x == -1) {
				return null;
			}

			String part = s.substring(fromIndex, x + 1);

			if (getLevel(part, "</", ">") == 0) {
				return part;
			}
		}
	}

	private void _checkIllegalAttributes(
		String fileName, String absolutePath, String content,
		String lowerCaseContent) {

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

		if (ListUtil.isEmpty(ignoredTagPrefixes)) {
			return;
		}

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
	}

	private void _checkIllegalTags(
		String fileName, String absolutePath, String content,
		String lowerCaseContent) {

		if (!fileName.endsWith(".ftl") && !fileName.endsWith(".jsp") &&
			!fileName.endsWith(".jspf") && !fileName.endsWith(".jspx") &&
			!fileName.endsWith(".vm")) {

			return;
		}

		List<String> illegalTagNamesData = getAttributeValues(
			_ILLEGAL_TAG_NAMES_DATA_KEY, absolutePath);

		for (String illegalTagNameData : illegalTagNamesData) {
			String[] parts = StringUtil.split(
				illegalTagNameData, StringPool.COLON);

			String tagName = parts[0];

			String requiredAttribute = null;

			if (parts.length == 2) {
				requiredAttribute = parts[1];
			}

			int x = -1;

			if ((fileName.endsWith(".ftl") || fileName.endsWith(".vm")) &&
				_illegalTagAuiReplacements.contains(tagName)) {

				while (true) {
					x = lowerCaseContent.indexOf("<" + tagName, x + 1);

					if (x == -1) {
						break;
					}

					String tagString = getTag(content, x);

					if (Validator.isNull(tagString) ||
						((requiredAttribute != null) &&
						 !tagString.contains(requiredAttribute))) {

						continue;
					}

					int lineNumber = getLineNumber(content, x);

					if (fileName.endsWith(".ftl")) {
						_checkMissingAttribute(
							fileName, tagName, "${nonceAttribute}", tagString,
							lineNumber);
					}

					if (fileName.endsWith(".vm")) {
						_checkMissingAttribute(
							fileName, tagName, "$nonceAttribute", tagString,
							lineNumber);
					}
				}
			}

			x = -1;

			while (true) {
				x = lowerCaseContent.indexOf("</" + tagName, x + 1);

				if (x == -1) {
					break;
				}

				if (Validator.isNull(getEndingTag(content, x))) {
					continue;
				}

				int lineNumber = getLineNumber(content, x);

				if (_illegalTagAuiReplacements.contains(tagName)) {
					if (!fileName.endsWith(".jsp") &&
						!fileName.endsWith(".jspf") &&
						!fileName.endsWith(".jspx")) {

						continue;
					}

					addMessage(
						fileName,
						StringBundler.concat(
							"Use <aui:", tagName, "> tag instead of <", tagName,
							">, see LPD-18227"),
						lineNumber);
				}

				addMessage(
					fileName,
					StringBundler.concat(
						"Remove usage of </", tagName, "> tag, see LPD-47204"),
					lineNumber);
			}
		}
	}

	private void _checkMissingAttribute(
		String fileName, String tagName, String attribute, String tagString,
		int lineNumber) {

		if (!tagString.contains(attribute)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Missing attribute \"", attribute, "\" in <", tagName,
					"> tag, see LPD-18227"),
				lineNumber);
		}
	}

	private int _getTagStartPosition(String content, int x) {
		while (x >= 0) {
			char c = content.charAt(x);

			if ((c == CharPool.LESS_THAN) &&
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

	private static final String _ILLEGAL_TAG_NAMES_DATA_KEY =
		"illegalTagNamesData";

	private static final List<String> _illegalTagAuiReplacements =
		Arrays.asList("link:rel=\"stylesheet\"", "script", "style");

}