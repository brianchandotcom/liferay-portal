/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class CSPIllegalTagsCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		List<String> illegalTagNames = new ArrayList<>();
		List<String> replacedTagNames = new ArrayList<>();

		if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf") ||
			fileName.endsWith(".jspx")) {

			illegalTagNames = getAttributeValues(
				_JSP_ILLEGAL_TAG_NAMES_KEY, absolutePath);
			replacedTagNames = getAttributeValues(
				_REPLACED_TAG_NAMES_KEY, absolutePath);
		}
		else if (fileName.endsWith(".ftl") || fileName.endsWith(".vm")) {
			illegalTagNames = getAttributeValues(
				_FTL_AND_VM_ILLEGAL_TAG_NAMES_KEY, absolutePath);
		}

		String lowerCaseContent = StringUtil.toLowerCase(content);

		for (String illegalTagName : illegalTagNames) {
			int x = -1;

			while (true) {
				x = lowerCaseContent.indexOf("<" + illegalTagName, x + 1);

				if (x == -1) {
					break;
				}

				if (isJavaSource(content, x)) {
					continue;
				}

				String tagString = getTag(content, x);

				if (Validator.isNull(tagString) ||
					(illegalTagName.equals("link") &&
					 !tagString.contains("rel=\"stylesheet\""))) {

					continue;
				}

				int lineNumber = getLineNumber(content, x);

				if (replacedTagNames.contains(illegalTagName)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Use <aui:", illegalTagName, "> tag instead of <",
							illegalTagName, ">, see LPD-18227"),
						lineNumber);
				}
				else {
					addMessage(
						fileName,
						StringBundler.concat(
							"Do not use <", illegalTagName,
							"> tag, see LPD-47204"),
						lineNumber);
				}
			}
		}

		return content;
	}

	private static final String _FTL_AND_VM_ILLEGAL_TAG_NAMES_KEY =
		"ftlAndVmIllegalTagNames";

	private static final String _JSP_ILLEGAL_TAG_NAMES_KEY =
		"jspIllegalTagNames";

	private static final String _REPLACED_TAG_NAMES_KEY = "replacedTagNames";

}