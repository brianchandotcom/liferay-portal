/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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

		if (fileName.endsWith(EXTENSION_VELOCITY)) {
			FileUtil.write(
				_getFreeMarkerMigratedFile(fileName),
				migrateContent(
					_getFreeMarkerMigratedContent(content, fileName)));
		}

		return content;
	}

	protected abstract String migrateContent(String content);

	protected static final String EXTENSION_FREEMARKER = ".ftl";

	protected static final String EXTENSION_VELOCITY = ".vm";

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
				EXTENSION_FREEMARKER));
	}

}