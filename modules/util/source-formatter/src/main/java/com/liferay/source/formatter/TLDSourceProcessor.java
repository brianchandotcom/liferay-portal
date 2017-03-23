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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.TLDElementOrderCheck;
import com.liferay.source.formatter.checks.TLDTypeCheck;
import com.liferay.source.formatter.checks.WhitespaceCheck;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class TLDSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return StringUtil.replace(content, "\n\n\n", "\n\n");
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {"**/WEB-INF/tld/**", "**/test_*.tld"};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	@Override
	protected void populateFileChecks() {
		_fileChecks.add(new WhitespaceCheck());

		_fileChecks.add(new TLDElementOrderCheck());
		_fileChecks.add(new TLDTypeCheck());
	}

	private static final String[] _INCLUDES = new String[] {"**/*.tld"};

	private final List<FileCheck> _fileChecks = new ArrayList<>();

}