/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class JavaUpgradeFetchCPDefinitionByCProductExternalReferenceCodeCheck
	extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		List<String> importNames = javaTerm.getImportNames();

		String content = javaTerm.getContent();

		if (!importNames.contains(
				"com.liferay.commerce.product.service." +
					"CPDefinitionLocalService") &&
			!importNames.contains(
				"com.liferay.commerce.product.service.CPDefinitionService")) {

			return content;
		}

		Matcher matcher = _methodCallPattern.matcher(content);

		String newContent = content;

		while (matcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, matcher.start());

			if (_checkMethodCall(content, fileContent, methodCall)) {
				newContent = StringUtil.replace(
					newContent, methodCall,
					_reorderParameters(methodCall, matcher.group(1)));
			}
		}

		return newContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private boolean _checkMethodCall(
		String content, String fileContent, String methodCall) {

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		String secondParameter = parameterList.get(1);

		if (!SourceUtil.isLiteralString(secondParameter) &&
			!Objects.equals(
				getVariableTypeName(content, fileContent, secondParameter),
				"String")) {

			return false;
		}

		if (hasClassOrVariableName(
				"CPDefinitionService", content, fileContent, methodCall) ||
			hasClassOrVariableName(
				"CPDefinitionLocalService", content, fileContent, methodCall)) {

			return true;
		}

		return false;
	}

	private String _reorderParameters(String methodCall, String parameters) {
		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		return StringUtil.replace(
			methodCall, parameters,
			StringBundler.concat(
				parameterList.get(1), StringPool.COMMA_AND_SPACE,
				parameterList.get(0)));
	}

	private static final Pattern _methodCallPattern = Pattern.compile(
		"\\w+\\.\\s*fetchCPDefinitionByCProductExternalReferenceCode" +
			"\\((\\s*.+,\\s*.+)\\s*\\)");

}