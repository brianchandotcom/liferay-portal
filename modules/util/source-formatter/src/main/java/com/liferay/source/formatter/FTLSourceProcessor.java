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

import com.liferay.source.formatter.checks.FTLEmptyLinesCheck;
import com.liferay.source.formatter.checks.FTLIfStatementCheck;
import com.liferay.source.formatter.checks.FTLImportsCheck;
import com.liferay.source.formatter.checks.FTLLiferayVariableOrderCheck;
import com.liferay.source.formatter.checks.FTLStringRelationalOperatorCheck;
import com.liferay.source.formatter.checks.FTLTagCheck;
import com.liferay.source.formatter.checks.FTLWhitespaceCheck;
import com.liferay.source.formatter.checks.SourceCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class FTLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {
			"**/journal/dependencies/template.ftl",
			"**/service/builder/dependencies/props.ftl"
		};

		return getFileNames(excludes, getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() {
		List<SourceCheck> sourceChecks = new ArrayList<>();

		sourceChecks.add(new FTLWhitespaceCheck());

		sourceChecks.add(new FTLEmptyLinesCheck());
		sourceChecks.add(new FTLIfStatementCheck());
		sourceChecks.add(new FTLImportsCheck());
		sourceChecks.add(new FTLLiferayVariableOrderCheck());
		sourceChecks.add(new FTLStringRelationalOperatorCheck());
		sourceChecks.add(new FTLTagCheck());

		return sourceChecks;
	}

	private static final String[] _INCLUDES = new String[] {"**/*.ftl"};

}