/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JSPSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JSPAnchorPointKeyCheck extends BaseTagAttributesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		List<String> anchorPointTags = getAttributeValues(
				_ANCHOR_POINT_TAGS, absolutePath);

		outerLoop:
		for (String anchorPointTag : anchorPointTags) {
			String[] anchorPointTagArray = StringUtil.split(
					anchorPointTag, "->");

			if (anchorPointTagArray.length != 2) {
				continue;
			}

			String tagName = "<" + anchorPointTagArray[0];

			int pos = content.indexOf(tagName);

			while (true) {
				if (pos == -1) {
					continue outerLoop;
				}

				Tag tag = parseTag(getTag(content, pos), false);

				if (tag == null) {
					pos = content.indexOf(tagName, pos + 1);

					continue;
				}

				Map<String, String> attributesMap = tag.getAttributesMap();

				for (String attribute : attributesMap.keySet()) {
					if (!StringUtil.equals(attribute, anchorPointTagArray[1])) {
						continue;
					}

					String expectValue = null;

					if (fileName.contains("/portal-web/docroot")) {
						int index = fileName.indexOf("/portal-web/docroot") + 19;

						expectValue = fileName.substring(index);
					} else {
						String symbolicName = getSymbolicName(fileName);

						if (symbolicName == null) {
							continue outerLoop;
						}

						expectValue = symbolicName + "#" + _getFileName(fileName);
					}

					String value = attributesMap.get(attribute);

					if (!value.contains("<%") && !value.startsWith(expectValue)) {
						addMessage(fileName, "Tag '" + tag.getName() + "' attribute '" + attribute + "' value '" + value + "' should start with '" + expectValue + "'");
					}
				}

				pos = content.indexOf("tagName", pos + 1);
			}
		}

		return content;
	}

	private String getSymbolicName(String fileName) throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return null;
		}

        return BNDSourceUtil.getDefinitionValue(bndSettings.getContent(), "Bundle-SymbolicName");
    }

	private String _getFileName(String fileName) {

		int x = fileName.lastIndexOf(CharPool.SLASH);

		return fileName.substring(x + 1);
	}

	private static final String _ANCHOR_POINT_TAGS =
			"anchorPointTags";

}