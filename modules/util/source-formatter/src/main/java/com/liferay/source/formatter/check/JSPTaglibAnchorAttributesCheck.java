/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Qi Zhang
 */
public class JSPTaglibAnchorAttributesCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		String expectedValue = null;

		if (isModulesFile(absolutePath)) {
			String bundleSymbolicName = _getBundleSymbolicName(fileName);

			if (bundleSymbolicName == null) {
				return content;
			}

			int x = fileName.lastIndexOf("/resources/");

			if (x == -1) {
				return content;
			}

			expectedValue = StringBundler.concat(
				bundleSymbolicName, "#", fileName.substring(x + 10));
		}
		else if (absolutePath.contains("/portal-web/docroot")) {
			int x = absolutePath.indexOf("/portal-web/docroot") + 19;

			expectedValue = absolutePath.substring(x);
		}

		List<String> taglibAnchorAttributes = getAttributeValues(
			_TAGLIB_ANCHOR_ATTRIBUTES_KEY, absolutePath);

		for (String taglibAnchorAttribute : taglibAnchorAttributes) {
			String[] attributeParts = StringUtil.split(
				taglibAnchorAttribute, "->");

			if (attributeParts.length != 2) {
				continue;
			}

			String taglibName = "<" + attributeParts[0];

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

				for (Map.Entry<String, String> entry :
						attributesMap.entrySet()) {

					String attributeName = entry.getKey();

					if (!StringUtil.equals(attributeName, attributeParts[1])) {
						continue;
					}

					String attributeValue = attributesMap.get(attributeName);

					if (!attributeValue.contains("<%") &&
						!attributeValue.startsWith(expectedValue)) {

						addMessage(
							fileName,
							StringBundler.concat(
								"Tag '", tag.getName(), "' attribute '",
								attributeName, "' value '", attributeValue,
								"' should start with '", expectedValue, "'"),
							getLineNumber(content, x));
					}
				}
			}
		}

		return content;
	}

	private String _getBundleSymbolicName(String fileName) throws IOException {
		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return null;
		}

		return BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");
	}

	private static final String _TAGLIB_ANCHOR_ATTRIBUTES_KEY =
		"taglibAnchorAttributes";

}