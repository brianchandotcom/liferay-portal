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

package com.liferay.wiki.translator;

import java.util.Map;

/**
 * @author Roberto Díaz
 * @author Daniel Sanz
 */
public class BaseWikiPageRenameProcessor
	extends BaseTranslator implements WikiPageRenameProcessor {

	@Override
	public String translate(String content, String title, String newTitle) {
		return runRegexps(content, title, newTitle);
	}

	protected String runRegexps(String content, String title, String newTitle) {
		for (Map.Entry<String, String> entry : regexps.entrySet()) {
			String regexp = entry.getKey();
			String replacement = entry.getValue();

			regexp = regexp.replaceAll("@old_title@", title);

			replacement = replacement.replaceAll("@new_title@", newTitle);

			content = runRegexp(content, regexp, replacement);
		}

		return content;
	}

}