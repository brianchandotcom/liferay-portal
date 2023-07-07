/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeJavaSearchVocabulariesMethodCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		String newContent = content;

		boolean failedValidation = false;
		boolean replaced = false;

		Matcher matcher = _searchVocabulariesPattern.matcher(content);

		while (matcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, matcher.start());

			if (!hasClassOrVariableName(
					"AssetVocabularyService", content, content, methodCall) &&
				!hasClassOrVariableName(
					"AssetVocabularyLocalService", content, content,
					methodCall)) {

				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			if (parameterList.size() != 5) {
				continue;
			}

			String[] parameterTypes = {"long", "long", "String", "int", "int"};

			if (!hasParameterTypes(
					content, content, ArrayUtil.toStringArray(parameterList),
					parameterTypes)) {

				failedValidation = true;

				continue;
			}

			String newMethod = _addOrReplaceParameters(
				matcher.group(0), methodCall, parameterList);

			newContent = StringUtil.replace(newContent, methodCall, newMethod);

			replaced = true;
		}

		if (replaced) {
			newContent = JavaSourceUtil.addImports(
				newContent, "com.liferay.portal.kernel.search.Sort");
		}

		if (failedValidation) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Could not resolve types of searchVocabularies method. ",
					"The method signature has changed to searchVocabularies(",
					"long companyId, long[] groupIds, String title, int[] ",
					"visibilityTypes, int start, int end, Sort sort). Fill ",
					"the new parameters manually."));
		}

		return newContent;
	}

	private String _addOrReplaceParameters(
		String group, String methodCall, List<String> parameterList) {

		parameterList.add(3, "new int[]{}");
		parameterList.add(6, "new Sort()");
		parameterList.set(
			1,
			StringBundler.concat(
				"new long[]{", parameterList.get(1),
				StringPool.CLOSE_CURLY_BRACE));

		StringBundler sb = new StringBundler(3);

		sb.append(group);
		sb.append(StringUtil.merge(parameterList, StringPool.COMMA_AND_SPACE));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return StringUtil.replace(methodCall, methodCall, sb.toString());
	}

	private static final Pattern _searchVocabulariesPattern = Pattern.compile(
		"\\w+\\.searchVocabularies\\(");

}