/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Alan Huang
 */
public class CSPTagMissingAttributesCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith(".ftl") && !fileName.endsWith(".vm")) {
			return content;
		}

		String lowerCaseContent = StringUtil.toLowerCase(content);

		List<String> nonceAttributeTagNames = getAttributeValues(
			_NONCE_ATTRIBUTE_TAG_NAMES_KEY, absolutePath);

		for (String nonceAttributeTagName : nonceAttributeTagNames) {
			int x = -1;

			while (true) {
				x = lowerCaseContent.indexOf(
					"<" + nonceAttributeTagName, x + 1);

				if (x == -1) {
					break;
				}

				String tagString = getTag(content, x);

				if (Validator.isNull(tagString) ||
					(nonceAttributeTagName.equals("link") &&
					 !tagString.contains("rel=\"stylesheet\""))) {

					continue;
				}

				int lineNumber = getLineNumber(content, x);

				if (fileName.endsWith(".ftl")) {
					_checkMissingAttribute(
						fileName, nonceAttributeTagName, "${nonceAttribute}",
						tagString, lineNumber);
				}
				else if (fileName.endsWith(".vm")) {
					_checkMissingAttribute(
						fileName, nonceAttributeTagName, "$nonceAttribute",
						tagString, lineNumber);
				}
			}
		}

		return content;
	}

	private void _checkMissingAttribute(
		String fileName, String tagName, String attribute, String tagString,
		int lineNumber) {

		if (tagString.contains(attribute)) {
			return;
		}

		addMessage(
			fileName,
			StringBundler.concat(
				"Missing attribute \"", attribute, "\" in <", tagName,
				"> tag, see LPD-18227"),
			lineNumber);
	}

	private static final String _NONCE_ATTRIBUTE_TAG_NAMES_KEY =
		"nonceAttributeTagNames";

}