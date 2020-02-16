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

import com.liferay.source.formatter.checks.util.JSPSourceUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJSPTermsCheck extends BaseFileCheck {

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	protected Map<String, String> getContentsMap() {
		return _contentsMap;
	}

	protected synchronized void populateContentsMap(
			String fileName, String content)
		throws IOException {

		if (_contentsMap != null) {
			return;
		}

		String[] excludes = {"**/null.jsp", "**/tools/**"};

		List<String> allJSPFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, excludes,
			new String[] {"**/*.jsp", "**/*.jspf", "**/*.tag"},
			getSourceFormatterExcludes(), true);

		_contentsMap = JSPSourceUtil.getContentsMap(allJSPFileNames);

		// When running tests, the contentsMap is empty, because the file
		// extension of the test files is *.testjsp

		if (_contentsMap.isEmpty()) {
			_contentsMap.put(fileName, content);
		}
	}

	protected void put(String fileName, String content) {
		_contentsMap.put(fileName, content);
	}

	private List<String> _allFileNames;
	private Map<String, String> _contentsMap;

}