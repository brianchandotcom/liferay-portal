/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.ContentUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	protected void doFormat() throws Exception {
		formatPortalProperties();
	}

	protected void formatPortalProperties() throws Exception {
		String basedir = "./";

		String portalPortalProperties = null;

		if (isPortalSource()) {
			File portalPortalPropertiesFile = new File(
				basedir + "portal-impl/src/portal.properties");

			portalPortalProperties = getFileUtil().read(
				portalPortalPropertiesFile);
		}
		else {
			portalPortalProperties = ContentUtil.get("portal.properties");
		}

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(basedir);

		String[] excludes = getExcludes();

		if (isPortalSource()) {
			excludes = ArrayUtil.append(
				excludes, new String[] {"**\\classes\\**", "**\\bin\\**"});

			directoryScanner.setIncludes(
				new String[] {
					"**\\portal-ext.properties",
					"**\\portal-legacy-*.properties",
				});
		}
		else {
			directoryScanner.setIncludes(
				new String[] {
					"**\\portal.properties", "**\\portal-ext.properties"
				});
		}

		directoryScanner.setExcludes(excludes);

		List<String> fileNames = getSourceFormatterHelper().scanForFiles(
			directoryScanner);

		for (String fileName : fileNames) {
			File file = new File(basedir + fileName);

			fileName = StringUtil.replace(
				fileName, StringPool.BACK_SLASH, StringPool.SLASH);

			String content = getFileUtil().read(file);

			formatPortalProperties(fileName, content, portalPortalProperties);
		}
	}

	private void formatPortalProperties(
			String fileName, String content, String portalPortalProperties)
		throws IOException {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		int lineCount = 0;

		String line = null;

		int previousPos = -1;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			int pos = line.indexOf(StringPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			String property = line.substring(0, pos + 1);

			property = property.trim();

			pos = portalPortalProperties.indexOf(
				StringPool.FOUR_SPACES + property);

			if (pos == -1) {
				continue;
			}

			if (pos < previousPos) {
				processErrorMessage(
					fileName, "sort " + fileName + " " + lineCount);
			}

			previousPos = pos;
		}
	}

}