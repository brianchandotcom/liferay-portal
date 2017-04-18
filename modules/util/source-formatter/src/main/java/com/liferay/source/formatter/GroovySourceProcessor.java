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

import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.WhitespaceCheck;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class GroovySourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		return content;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		if (!portalSource && !subrepository) {
			return new ArrayList<>();
		}

		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected void populateSourceChecks() {
		_sourceChecks.add(new WhitespaceCheck());
	}

	@Override
	protected void postFormat() throws Exception {
	}

	private static final String[] _INCLUDES = new String[] {"**/*.groovy"};

	private final List<SourceCheck> _sourceChecks = new ArrayList<>();

}