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

import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Set;

/**
 * @author Qi Zhang
 */
public class JSPTaglibAttributesCheck extends BaseTagAttributesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkMissingAttributes(fileName, content);

		return content;
	}

	private void _checkMissingAttributes(String fileName, String content) {
		int x = -1;

		while (true) {
			x = content.indexOf("<clay:dropdown-actions", x + 1);

			if (x == -1) {
				break;
			}

			String tagString = getTag(content, x);

			if (Validator.isNull(tagString)) {
				continue;
			}

			Tag tag = parseTag(tagString, false);

			if (tag == null) {
				continue;
			}

			Map<String, String> attributesMap = tag.getAttributesMap();

			Set<String> attributesSet = attributesMap.keySet();

			if (attributesSet.contains("aria-label") ||
				attributesSet.contains("aria-labelledby") ||
				attributesSet.contains("title")) {

				continue;
			}

			addMessage(
				fileName,
				"When using <clay:dropdown-actions>, always specify one of " +
					"the follwing attributes: 'aria-label', " +
						"'aria-labelledby', 'title'",
				getLineNumber(content, x));
		}
	}

}