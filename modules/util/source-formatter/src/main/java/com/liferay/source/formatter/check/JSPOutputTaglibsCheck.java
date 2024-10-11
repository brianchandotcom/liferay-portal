/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.io.IOException;

import java.util.Map;

/**
 * @author Qi Zhang
 */
public class JSPOutputTaglibsCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		String bundleSymbolicName = _getBundleSymbolicName(fileName);

		if (bundleSymbolicName == null) {
			return content;
		}

		int x = fileName.lastIndexOf("/src/main/resources/META-INF/resources/");

		if (x == -1) {
			return content;
		}

		String expectedValue = StringBundler.concat(
			bundleSymbolicName, "#", fileName.substring(x + 38));

		_checkOutputKey(fileName, content, expectedValue);

		return content;
	}

	private void _checkOutputKey(
		String fileName, String content, String expectedValue) {

		for (String outputTaglibName : _OUTPUT_TAGLIB_NAMES) {
			String taglibName = "<" + outputTaglibName;

			int x = -1;

			while (true) {
				x = content.indexOf(taglibName, x + 1);

				if (x == -1) {
					break;
				}

				Tag tag = parseTag(getTag(content, x), false);

				if (tag == null) {
					continue;
				}

				Map<String, String> attributesMap = tag.getAttributesMap();

				String attributeValue = attributesMap.get("outputKey");

				if ((attributeValue == null) || attributeValue.contains("<%") ||
					attributeValue.startsWith(expectedValue)) {

					continue;
				}

				addMessage(
					fileName,
					StringBundler.concat(
						"The value for \"outputKey\" in <", outputTaglibName,
						"> should start with \"", expectedValue, "\""),
					getLineNumber(content, x));
			}
		}
	}

	private String _getBundleSymbolicName(String fileName) throws IOException {
		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return null;
		}

		return BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");
	}

	private static final String[] _OUTPUT_TAGLIB_NAMES = {
		"liferay-util:body-bottom", "liferay-util:body-top",
		"liferay-util:html-bottom", "liferay-util:html-top"
	};

}