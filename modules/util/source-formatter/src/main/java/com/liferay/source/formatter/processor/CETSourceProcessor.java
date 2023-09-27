/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.util.CETUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Peter Shin
 */
public class CETSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected void postFormat() throws Exception {
		if (!isPortalSource()) {
			return;
		}

		List<String> fileNames = doGetFileNames();

		if (fileNames.isEmpty()) {
			return;
		}

		String shortFileName = "client-extension-types.json";

		File jsonFile = new File(
			getPortalDir(),
			"modules/apps/client-extension/client-extension-type-api/src/main" +
				"/resources/com/liferay/client/extension/type/dependencies/" +
					shortFileName);

		String oldContent = FileUtil.read(jsonFile);

		List<String> cetFileNames = SourceFormatterUtil.scanForFileNames(
			getPortalDir() + _CET_DIR_LOCATION, new String[0],
			new String[] {"**/*CET.java"}, new SourceFormatterExcludes(), true);

		String newContent = CETUtil.getJSONContent(cetFileNames);

		if (!oldContent.equals(newContent)) {
			FileUtil.write(jsonFile, newContent);

			System.out.println("Updated '" + shortFileName + "'");
		}
	}

	private static final String _CET_DIR_LOCATION =
		"/modules/apps/client-extension/client-extension-type-api/src/main" +
			"/java/com/liferay/client/extension/type";

	private static final String[] _INCLUDES = {
		"**/client-extension-type-api/src/main/java/com/liferay/client" +
			"/extension/type/*.java"
	};

}