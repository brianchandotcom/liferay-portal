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

import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijswer
 */
public class UpgradeSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

		File upgradeInputDataDirectory = SourceFormatterUtil.getFile(
			sourceFormatterArgs.getBaseDirName(),
			SourceFormatterUtil.UPGRADE_INPUT_DATA_DIRECTORY_NAME,
			sourceFormatterArgs.getMaxDirLevel());

		if (upgradeInputDataDirectory == null) {
			return Collections.emptyList();
		}

		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	private static final String[] _INCLUDES = {"**/*.java"};

}