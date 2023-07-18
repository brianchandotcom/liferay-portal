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

import com.liferay.petra.string.CharPool;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

/**
 * @author Nícolas Moura
 */
public abstract class BaseUpgradeCheck extends BaseFileCheck {

	protected String addNewImports(String newContent) {
		String[] newImports = getNewImports();

		if (newImports != null) {
			newContent = JavaSourceUtil.addImports(newContent, newImports);
		}

		return newContent;
	}

	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		return addNewImports(newContent);
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!isValidExtension(fileName)) {
			return content;
		}

		String newContent = format(fileName, absolutePath, content);

		if (!content.equals(newContent)) {
			newContent = afterFormat(
				fileName, absolutePath, content, newContent);
		}

		return newContent;
	}

	protected abstract String format(
			String fileName, String absolutePath, String content)
		throws Exception;

	protected String[] getNewImports() {
		return null;
	}

	protected String[] getValidExtensions() {
		return new String[] {"java"};
	}

	protected boolean isValidExtension(String fileName) {
		for (String extension : getValidExtensions()) {
			if (fileName.endsWith(CharPool.PERIOD + extension)) {
				return true;
			}
		}

		return false;
	}

}