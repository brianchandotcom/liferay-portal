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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Nícolas Moura
 */
public abstract class BaseUpgradeVelocityMigrationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (fileName.endsWith(VELOCITY_EXTENSION)) {
			FileUtil.write(
				_getFreeMarkerMigratedFile(fileName),
				migrateContent(
					_getFreeMarkerMigratedContent(content, fileName)));
		}

		return content;
	}

	protected abstract String migrateContent(String content);

	protected static final String FREEMARKER_EXTENSION = ".ftl";

	protected static final String VELOCITY_EXTENSION = ".vm";

	private String _getFreeMarkerMigratedContent(
			String content, String velocityFileName)
		throws IOException {

		File file = _getFreeMarkerMigratedFile(velocityFileName);

		if (file.length() != 0) {
			content = FileUtil.read(file);
		}

		return content;
	}

	private File _getFreeMarkerMigratedFile(String fileName) {
		int periodIndex = fileName.lastIndexOf(StringPool.PERIOD);
		int slashIndex = fileName.lastIndexOf(File.separator);

		return new File(
			StringBundler.concat(
				fileName.substring(0, slashIndex), File.separator, "migrated",
				File.separator, fileName.substring(slashIndex + 1, periodIndex),
				FREEMARKER_EXTENSION));
	}

}