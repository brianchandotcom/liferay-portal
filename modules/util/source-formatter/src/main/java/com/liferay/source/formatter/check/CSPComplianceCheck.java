/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
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

		List<String> illegalTagNamesData = getAttributeValues(
			_ILLEGAL_TAG_NAMES_DATA_KEY, absolutePath);

		content = _checkIllegalTags(fileName, content, illegalTagNamesData);

		List<String> illegalAttributeNames = getAttributeValues(
			_ILLEGAL_ATTRIBUTE_NAMES_KEY, absolutePath);

		return _checkIllegalAttributes(
			fileName, content, illegalAttributeNames);
	}

	protected String getEnclosingTagStart(String content, int x) {
		int startIndex = x;

		for (startIndex = x; startIndex >= 0; startIndex--) {
			char c = content.charAt(startIndex);

			if ((c != CharPool.LESS_THAN) ||
				(content.charAt(startIndex + 1) == CharPool.PERCENT) ||
				isJavaSource(content, startIndex)) {

				continue;
			}

			if (StringUtil.equals(
					content.substring(startIndex, startIndex + 18),
					"<portlet:namespace")) {

				break;
			}

			return content.substring(startIndex, x);
		}

		return null;
	}

	private String _checkIllegalAttributes(
		String fileName, String content, List<String> illegalAttributeNames) {

		String lowerCaseContent = StringUtil.toLowerCase(content);
		String lowerCaseFileName = StringUtil.toLowerCase(fileName);

		for (String illegalAttributeName : illegalAttributeNames) {
			int x = -1;

			while (true) {
				x = lowerCaseContent.indexOf(
					illegalAttributeName + StringPool.EQUAL, x + 1);

				if (x == -1) {
					break;
				}

				if (!Character.isWhitespace(content.charAt(x - 1)) ||
					isJavaSource(content, x)) {

					continue;
				}

				String tagString = getEnclosingTagStart(content, x);

				if (tagString == null) {
					continue;
				}

				List<String> ignoredTagPrefixes = Collections.emptyList();

				if (lowerCaseFileName.endsWith(".ftl")) {
					ignoredTagPrefixes = _ignoredFTLTagPrefixes;
				}
				else if (lowerCaseFileName.endsWith(".jsp") ||
						 lowerCaseFileName.endsWith(".jspf") ||
						 lowerCaseFileName.endsWith(".jspx")) {

					ignoredTagPrefixes = _ignoredJSPTagPrefixes;
				}

				boolean skip = false;

				for (String ignoredTagPrefix : ignoredTagPrefixes) {
					if (tagString.startsWith(ignoredTagPrefix)) {
						skip = true;

						break;
					}
				}

				if (skip) {
					continue;
				}

				addMessage(
					fileName,
					"Tag attribute '" + illegalAttributeName +
						"' is not allowed, see LPD-18227",
					getLineNumber(content, x));
			}
		}

		return content;
	}

	private String _checkIllegalTags(
		String fileName, String content, List<String> illegalTagNamesData) {

		String lowerCaseContent = StringUtil.toLowerCase(content);
		String lowerCaseFileName = StringUtil.toLowerCase(fileName);

		for (String illegalTagNameData : illegalTagNamesData) {
			String[] parts = StringUtil.split(
				illegalTagNameData, StringPool.COLON);

			String tagName = parts[0];

			String requiredAttribute = null;

			if (parts.length == 2) {
				requiredAttribute = parts[1];
			}

			int x = -1;

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

				if (lowerCaseFileName.endsWith(".jsp") ||
					lowerCaseFileName.endsWith(".jspf") ||
					lowerCaseFileName.endsWith(".jspx")) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Use <aui:", tagName, "> tag instead of <", tagName,
							">, see LPD-18227"),
						lineNumber);
				}
				else if (lowerCaseFileName.endsWith(".ftl")) {
					_checkMissingAttribute(
						fileName, tagName, "${nonceAttribute}", tagString,
						lineNumber);
				}
				else if (lowerCaseFileName.endsWith(".vm")) {
					_checkMissingAttribute(
						fileName, tagName, "$nonceAttribute", tagString,
						lineNumber);
				}
			}
		}

		return content;
	}

	private void _checkMissingAttribute(
		String fileName, String tagName, String attribute, String tagString,
		int lineNumber) {

		if (!tagString.contains(attribute)) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Missing attribute '", attribute, "' in <", tagName,
					"> tag, see LPD-18227"),
				lineNumber);
		}
	}

	private static final String _ILLEGAL_ATTRIBUTE_NAMES_KEY =
		"illegalAttributeNames";

	private static final String _ILLEGAL_TAG_NAMES_DATA_KEY =
		"illegalTagNamesData";

	private static final List<String> _ignoredFTLTagPrefixes = Arrays.asList(
		"<@clay.", "<@clay[", "<@liferay_aui.", "<@liferay_aui[",
		"<@liferay_frontend.", "<@liferay_frontend[", "<@liferay_ui.",
		"<@liferay_ui[");
	private static final List<String> _ignoredJSPTagPrefixes = Arrays.asList(
		"<aui:", "<clay:", "<liferay-frontend:", "<liferay-ui:");

}