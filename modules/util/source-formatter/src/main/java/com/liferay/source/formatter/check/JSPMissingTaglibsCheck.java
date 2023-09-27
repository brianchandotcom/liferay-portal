/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPMissingTaglibsCheck extends BaseJSPTermsCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.contains("/custom_jsps/") ||
			absolutePath.contains("-fragment/")) {

			return content;
		}

		Set<String> taglibPrefixes = _getTaglibPrefixes(content);

		if (taglibPrefixes.isEmpty()) {
			return content;
		}

		populateContentsMap(fileName, content);

		Set<String> missingTaglibPrefixes = getMissingTaglibPrefixes(
			fileName, taglibPrefixes);

		for (String prefix : missingTaglibPrefixes) {
			addMessage(
				fileName,
				"Missing taglib for tag with prefix '" + prefix + "'");
		}

		return content;
	}

	private Set<String> _getTaglibPrefixes(String content) {
		Set<String> taglibPrefixes = new HashSet<>();

		Matcher matcher = _tagPattern.matcher(content);

		while (matcher.find()) {
			taglibPrefixes.add(matcher.group(1));
		}

		return taglibPrefixes;
	}

	private static final Pattern _tagPattern = Pattern.compile(
		"<(aui|c|chart|clay|display|liferay(-[\\w-]+)|portlet|soy):");

}