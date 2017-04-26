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

import com.liferay.source.formatter.checks.GradleDependenciesCheck;
import com.liferay.source.formatter.checks.GradleVersionCheck;
import com.liferay.source.formatter.checks.SourceCheck;
import com.liferay.source.formatter.checks.WhitespaceCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
public class GradleSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() throws Exception {
		List<SourceCheck> sourceChecks = new ArrayList<>();

		sourceChecks.add(new WhitespaceCheck());

		sourceChecks.add(new GradleDependenciesCheck());
		sourceChecks.add(new GradleVersionCheck());

		return sourceChecks;
	}

	private static final String[] _INCLUDES = new String[] {"**/build.gradle"};

}