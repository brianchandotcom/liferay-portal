/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 */
public class UpgradeJavaAddFolderParameterCheck
	extends BaseUpgradeMatcherReplacementCheck {

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String methodCall = JavaSourceUtil.getMethodCall(
			content, matcher.start());

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (parameterList.size() >= 7) {
			return newContent;
		}

		String variable = matcher.group(1);

		if (variable.equals("JournalFolderLocalServiceUtil")) {
			return _addParameter(newContent, methodCall);
		}

		String variableTypeName = getVariableTypeName(
			newContent, newContent, variable);

		if (variableTypeName.equals("JournalFolderService") ||
			variableTypeName.equals("JournalFolderLocalService")) {

			newContent = _addParameter(newContent, methodCall);
		}

		return newContent;
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("(\\w+)\\.addFolder\\(");
	}

	private String _addParameter(String content, String methodCall) {
		return StringUtil.replace(
			content, methodCall,
			StringUtil.replace(methodCall, ".addFolder(", ".addFolder(null, "));
	}

}