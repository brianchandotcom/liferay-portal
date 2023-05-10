/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

/**
 * @author Nícolas Moura
 */
public class UpgradeBNDIncludeResourceCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!absolutePath.endsWith("/bnd.bnd")) {
			return content;
		}

		List<String> includeResourceValues = _getIncludeResourceValues(
			fileName);

		if (ListUtil.isEmpty(includeResourceValues)) {
			return content;
		}

		int initialSize = includeResourceValues.size();

		Iterator<String> iterator = includeResourceValues.iterator();

		while (iterator.hasNext()) {
			String includeResourceValue = iterator.next();

			if (!includeResourceValue.endsWith(".jar")) {
				iterator.remove();
			}
		}

		if (includeResourceValues.isEmpty()) {
			return content;
		}

		if (includeResourceValues.size() == initialSize) {
			String definition = BNDSourceUtil.getDefinition(
				content, "-includeresource");

			if (definition == null) {
				definition = BNDSourceUtil.getDefinition(
					content, "Include-Resource");
			}

			return StringUtil.removeSubstring(content, definition);
		}

		for (String includeResourceValue : includeResourceValues) {
			if (content.contains(
					includeResourceValue.concat(StringPool.COMMA))) {

				includeResourceValue = StringBundler.concat(
					StringPool.BACK_SLASH, StringPool.NEW_LINE, StringPool.TAB,
					includeResourceValue, StringPool.COMMA);
			}
			else {
				includeResourceValue = StringBundler.concat(
					StringPool.COMMA, StringPool.BACK_SLASH,
					StringPool.NEW_LINE, StringPool.TAB, includeResourceValue);
			}

			content = StringUtil.removeSubstring(content, includeResourceValue);
		}

		return content;
	}

	private List<String> _getIncludeResourceValues(String fileName)
		throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return null;
		}

		String bndSettingsContent = bndSettings.getContent();

		List<String> includeResourceValues = BNDSourceUtil.getDefinitionValues(
			bndSettingsContent, "-includeresource");

		includeResourceValues.addAll(
			BNDSourceUtil.getDefinitionValues(
				bndSettingsContent, "Include-Resource"));

		return includeResourceValues;
	}

}