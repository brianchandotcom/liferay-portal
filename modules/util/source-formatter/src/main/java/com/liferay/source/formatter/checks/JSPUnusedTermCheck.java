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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class JSPUnusedTermCheck extends BaseFileCheck {

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	protected Map<String, String> getContentsMap() throws Exception {
		String[] excludes = new String[] {"**/null.jsp", "**/tools/**"};

		if (getExcludes() != null) {
			excludes = ArrayUtil.append(excludes, getExcludes());
		}

		List<String> allJSPFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, excludes, new String[] {"**/*.jsp", "**/*.jspf"});

		return JSPSourceUtil.getContentsMap(allJSPFileNames);
	}

	protected boolean hasUnusedJSPTerm(
		String fileName, String regex, String type,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames,
		Map<String, String> contentsMap) {

		includeFileNames.add(fileName);

		Set<String> checkedForUnusedJSPTerm = new HashSet<>();

		return !isJSPTermRequired(
			fileName, regex, type, checkedForUnusedJSPTerm,
			checkedForIncludesFileNames, includeFileNames, contentsMap);
	}

	protected boolean isJSPTermRequired(
		String fileName, String regex, String type,
		Set<String> checkedForUnusedJSPTerm,
		Set<String> checkedForIncludesFileNames, Set<String> includeFileNames,
		Map<String, String> contentsMap) {

		if (checkedForUnusedJSPTerm.contains(fileName)) {
			return false;
		}

		checkedForUnusedJSPTerm.add(fileName);

		String content = contentsMap.get(fileName);

		if (Validator.isNull(content)) {
			return false;
		}

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find() &&
			(!type.equals("variable") || (checkedForUnusedJSPTerm.size() > 1) ||
			 matcher.find())) {

			return true;
		}

		if (!checkedForIncludesFileNames.contains(fileName)) {
			includeFileNames.addAll(
				JSPSourceUtil.getJSPIncludeFileNames(
					fileName, includeFileNames, contentsMap));
			includeFileNames.addAll(
				JSPSourceUtil.getJSPReferenceFileNames(
					fileName, includeFileNames, contentsMap));
		}

		checkedForIncludesFileNames.add(fileName);

		String[] includeFileNamesArray = includeFileNames.toArray(
			new String[includeFileNames.size()]);

		for (String includeFileName : includeFileNamesArray) {
			if (!checkedForUnusedJSPTerm.contains(includeFileName) &&
				isJSPTermRequired(
					includeFileName, regex, type, checkedForUnusedJSPTerm,
					checkedForIncludesFileNames, includeFileNames,
					contentsMap)) {

				return true;
			}
		}

		return false;
	}

	private List<String> _allFileNames;

}