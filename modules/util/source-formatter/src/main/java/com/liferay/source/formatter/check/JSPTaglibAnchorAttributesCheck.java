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

		List<String> taglibAnchorAttributes = getAttributeValues(
			_TAGLIB_ANCHOR_ATTRIBUTES_KEY, absolutePath);

		outerLoop:
		for (String taglibAnchorAttribute : taglibAnchorAttributes) {
			String[] attributeParts = StringUtil.split(
				taglibAnchorAttribute, "->");

			if (attributeParts.length != 2) {
				continue;
			}

			String taglibName = "<" + attributeParts[0];

			int pos = content.indexOf(taglibName);

			while (true) {
				if (pos == -1) {
					continue outerLoop;
				}

				Tag tag = parseTag(getTag(content, pos), false);

				if (tag == null) {
					pos = content.indexOf(taglibName, pos + 1);

					continue;
				}

				Map<String, String> attributesMap = tag.getAttributesMap();

				for (Map.Entry<String, String> entry :
						attributesMap.entrySet()) {

					String attribute = entry.getKey();

					if (!StringUtil.equals(attribute, attributeParts[1])) {
						continue;
					}

					String expectedValue = null;

					if (fileName.contains("/portal-web/docroot")) {
						int index =
							fileName.indexOf("/portal-web/docroot") + 19;

						expectedValue = fileName.substring(index);
					}
					else {
						String symbolicName = _getSymbolicName(fileName);

						if (symbolicName == null) {
							continue outerLoop;
						}

						int index = fileName.lastIndexOf("/resources/");

						if (index == -1) {
							continue outerLoop;
						}

						expectedValue = StringBundler.concat(
							symbolicName, "#", fileName.substring(index + 10));
					}

					String value = attributesMap.get(attribute);

					if (!value.contains("<%") &&
						!value.startsWith(expectedValue)) {

						addMessage(
							fileName,
							StringBundler.concat(
								"Tag '", tag.getName(), "' attribute '",
								attribute, "' value '", value,
								"' should start with '", expectedValue, "'"),
							getLineNumber(content, pos));
					}
				}

				pos = content.indexOf("tagName", pos + 1);
			}
		}

		return content;
	}

	private String _getSymbolicName(String fileName) throws IOException {
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