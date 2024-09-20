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

		if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf") ||
			fileName.endsWith(".jspx")) {

			for (String illegalTagNameData : illegalTagNamesData) {
				String[] tagNameParts = StringUtil.split(
					illegalTagNameData, StringPool.COLON);

				illegalTagNameData = tagNameParts[0];

				String requiredAttribute = null;

				if (tagNameParts.length == 2) {
					requiredAttribute = tagNameParts[1];
				}

				int x = -1;

				while (true) {
					x = content.indexOf("<" + illegalTagNameData, x + 1);

					if (x == -1) {
						break;
					}

					String tagString = getTag(content, x);

					if (Validator.isNull(tagString) ||
						((requiredAttribute != null) &&
						 !tagString.contains(requiredAttribute))) {

						continue;
					}

					addMessage(
						fileName,
						StringBundler.concat(
							"Do not use <", illegalTagNameData, "> tag (use ",
							"<aui:", illegalTagNameData, "> instead), see ",
							"LPD-18227"),
						getLineNumber(content, x));
				}
			}
		}
		else if (fileName.endsWith(".ftl")) {
			_checkMissingAttribute(
				"${nonceAttribute}", content, fileName, illegalTagNamesData);
		}
		else if (fileName.endsWith(".vm")) {
			_checkMissingAttribute(
				"$nonceAttribute", content, fileName, illegalTagNamesData);
		}

		return content;
	}

	private void _checkMissingAttribute(
		String attribute, String content, String fileName,
		List<String> illegalTagNamesData) {

		for (String illegalTagNameData : illegalTagNamesData) {
			String[] parts = StringUtil.split(
				illegalTagNameData, StringPool.COLON);

			String illegalTagName = parts[0];

			String requiredAttribute = null;

			if (parts.length == 2) {
				requiredAttribute = parts[1];
			}

			int x = -1;

			while (true) {
				x = content.indexOf("<" + illegalTagName, x + 1);

				if (x == -1) {
					break;
				}

				String tagString = getTag(content, x);

				if (Validator.isNull(tagString) ||
					((requiredAttribute != null) &&
					 !tagString.contains(requiredAttribute))) {

					continue;
				}

				if (!tagString.contains(attribute)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Tag <", illegalTagName, "> is missing attribute '",
							attribute, "', see LPD-18227"),
						getLineNumber(content, x));
				}
			}
		}
	}

	private static final String _ILLEGAL_TAG_NAMES_DATA_KEY =
		"illegalTagNamesData";

}