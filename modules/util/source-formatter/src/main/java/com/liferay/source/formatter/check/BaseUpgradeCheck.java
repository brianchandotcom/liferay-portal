/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;

/**
 * @author Nícolas Moura
 */
public abstract class BaseUpgradeCheck extends BaseFileCheck {

	public boolean hasValidParameters(
		int expectedParametersSize, String fileName, String javaMethodContent,
		String message, List<String> parameterList, String[] parameterTypes) {

		if (parameterList.size() != expectedParametersSize) {
			return false;
		}

		if (!hasParameterTypes(
				javaMethodContent, javaMethodContent,
				ArrayUtil.toStringArray(parameterList), parameterTypes)) {

			addMessage(fileName, message);

			return false;
		}

		return true;
	}

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