/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
				x = content.indexOf("<" + tagName, x + 1);

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

				if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf") ||
					fileName.endsWith(".jspx")) {

					addMessage(
						fileName,
						StringBundler.concat(
							"Use <aui:", tagName, "> tag instead of <", tagName,
							">, see LPD-18227"),
						lineNumber);
				}
				else if (fileName.endsWith(".ftl")) {
					_checkMissingAttribute(
						fileName, tagName, "${nonceAttribute}", tagString,
						lineNumber);
				}
				else if (fileName.endsWith(".vm")) {
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

	private static final String _ILLEGAL_TAG_NAMES_DATA_KEY =
		"illegalTagNamesData";

}