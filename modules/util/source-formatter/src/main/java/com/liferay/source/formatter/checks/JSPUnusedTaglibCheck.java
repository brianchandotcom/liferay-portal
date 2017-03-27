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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPUnusedTaglibCheck extends JSPUnusedTermCheck {

	public JSPUnusedTaglibCheck(Map<String, String> contentsMap) {
		_contentsMap = contentsMap;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<String> checkedFileNames = new HashSet<>();
		Set<String> includeFileNames = new HashSet<>();

		content = _removeUnusedTaglibs(
			fileName, content, checkedFileNames, includeFileNames);

		return new Tuple(content, Collections.emptySet());
	}

	private String _removeUnusedTaglibs(
		String fileName, String content, Set<String> checkedFileNames,
		Set<String> includeFileNames) {

		Matcher matcher = _taglibURIPattern.matcher(content);

		while (matcher.find()) {
			String regex =
				StringPool.LESS_THAN + matcher.group(1) + StringPool.COLON;

			if (hasUnusedJSPTerm(
					fileName, regex, "taglib", checkedFileNames,
					includeFileNames, _contentsMap)) {

				return StringUtil.removeSubstring(content, matcher.group());
			}
		}

		return content;
	}

	private final Map<String, String> _contentsMap;
	private final Pattern _taglibURIPattern = Pattern.compile(
		"<%@\\s+taglib uri=.* prefix=\"(.*?)\" %>");

}