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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaAddFolderParameterCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		Matcher matcher1 = _addFolderPattern.matcher(content);

		String newContent = content;

		while (matcher1.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, matcher1.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (parameterList.size() == 7) {
				continue;
			}

			if (methodCall.contains("JournalFolderLocalServiceUtil")) {
				newContent = _addParameter(newContent, methodCall);

				continue;
			}

			String variable = methodCall.substring(
				0, methodCall.indexOf(CharPool.PERIOD));

			Pattern variableDeclarationPattern = Pattern.compile(
				"[A-z]+ " + variable);

			Matcher matcher2 = variableDeclarationPattern.matcher(content);

			if (matcher2.find()) {
				String declaration = matcher2.group();

				if (declaration.contains("JournalFolderService") ||
					declaration.contains("JournalFolderLocalService")) {

					newContent = _addParameter(newContent, methodCall);
				}
			}
		}

		return newContent;
	}

	private String _addParameter(String content, String methodCall) {
		return StringUtil.replace(
			content, methodCall,
			StringUtil.replace(methodCall, ".addFolder(", ".addFolder(null, "));
	}

	private static final Pattern _addFolderPattern = Pattern.compile(
		"[A-z]+\\.addFolder\\(");

}