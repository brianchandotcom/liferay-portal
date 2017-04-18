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

import com.liferay.source.formatter.checks.BNDBundleNameCheck;
import com.liferay.source.formatter.checks.BNDCapabilityCheck;
import com.liferay.source.formatter.checks.BNDDefinitionKeysCheck;
import com.liferay.source.formatter.checks.BNDDirectoryNameCheck;
import com.liferay.source.formatter.checks.BNDExportsCheck;
import com.liferay.source.formatter.checks.BNDImportsCheck;
import com.liferay.source.formatter.checks.BNDIncludeResourceCheck;
import com.liferay.source.formatter.checks.BNDLineBreaksCheck;
import com.liferay.source.formatter.checks.BNDRangeCheck;
import com.liferay.source.formatter.checks.BNDSchemaVersionCheck;
import com.liferay.source.formatter.checks.BNDStylingCheck;
import com.liferay.source.formatter.checks.BNDWebContextPathCheck;
import com.liferay.source.formatter.checks.BNDWhitespaceCheck;
import com.liferay.source.formatter.checks.SourceCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class BNDSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<SourceCheck> getModuleSourceChecks() {
		return _moduleSourceChecks;
	}

	@Override
	protected List<SourceCheck> getSourceChecks() {
		return _sourceChecks;
	}

	@Override
	protected void populateModuleSourceChecks() throws Exception {
		_moduleSourceChecks.add(new BNDBundleNameCheck());
		_moduleSourceChecks.add(new BNDDirectoryNameCheck());
		_moduleSourceChecks.add(new BNDExportsCheck());
		_moduleSourceChecks.add(new BNDIncludeResourceCheck());
		_moduleSourceChecks.add(new BNDWebContextPathCheck());
	}

	@Override
	protected void populateSourceChecks() {
		_sourceChecks.add(new BNDWhitespaceCheck());

		_sourceChecks.add(new BNDCapabilityCheck());
		_sourceChecks.add(new BNDDefinitionKeysCheck());
		_sourceChecks.add(new BNDImportsCheck());
		_sourceChecks.add(new BNDLineBreaksCheck());
		_sourceChecks.add(new BNDRangeCheck());
		_sourceChecks.add(new BNDSchemaVersionCheck());
		_sourceChecks.add(new BNDStylingCheck());
	}

	private static final String[] _INCLUDES = new String[] {"**/*.bnd"};

	private final List<SourceCheck> _moduleSourceChecks = new ArrayList<>();
	private final List<SourceCheck> _sourceChecks = new ArrayList<>();

}