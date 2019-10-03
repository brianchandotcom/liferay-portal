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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class TextReplacer {

	public static void main(String[] args) {
		String dirs = System.getProperty("text.replacer.dirs");
		String includes = System.getProperty("text.replacer.includes");
		String excludes = System.getProperty("text.replacer.excludes");
		String tokenFile = System.getProperty("text.replacer.token.file");
		String valueFile = System.getProperty("text.replacer.value.file");

		new TextReplacer(dirs, includes, excludes, tokenFile, valueFile);
	}

	public TextReplacer(
		String dirs, String includes, String excludes, String tokenFile,
		String valueFile) {

		try {
			String token = _fileImpl.read(tokenFile);

			if (token == null) {
				System.out.println(tokenFile + " does not exist");

				return;
			}

			String value = _fileImpl.read(valueFile);

			if (value == null) {
				System.out.println(valueFile + " does not exist");

				return;
			}

			for (String dir : StringUtil.split(dirs)) {
				if (!dir.endsWith("/")) {
					dir += "/";
				}

				if (!_fileImpl.exists(dir)) {
					System.out.println(dir + " does not exist");

					continue;
				}

				DirectoryScanner directoryScanner = new DirectoryScanner();

				directoryScanner.setBasedir(dir);
				directoryScanner.setIncludes(StringUtil.split(includes));
				directoryScanner.setExcludes(StringUtil.split(excludes));

				directoryScanner.scan();

				for (String fileName : directoryScanner.getIncludedFiles()) {
					File file = new File(dir + fileName);

					String content = _fileImpl.read(file);

					String newContent = StringUtil.replace(
						content, token, value);

					if (!content.equals(newContent)) {
						_fileImpl.write(file, newContent);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final FileImpl _fileImpl = FileImpl.getInstance();

}