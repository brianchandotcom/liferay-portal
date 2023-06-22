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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Torres
 */
public class UpgradeJavaOnAfterUpdateParameterCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		JavaClass javaClass = (JavaClass)javaTerm;

		if (!_extendsFromBaseModelListener(javaClass)) {
			return fileContent;
		}

		String newContent = javaClass.getContent();

		for (JavaTerm childJavaTerms : javaClass.getChildJavaTerms()) {
			String content = childJavaTerms.getContent();

			Matcher matcher = _onAfterUpdatePattern.matcher(content);

			while (matcher.find()) {
				newContent = _format(
					childJavaTerms,
					JavaSourceUtil.getMethodCall(content, matcher.start()),
					newContent);
			}
		}

		return newContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _cloneAndRenameFirstParameter(JavaParameter parameter) {
		StringBundler sb = new StringBundler(7);

		sb.append(parameter.getParameterType());
		sb.append(" original");
		sb.append(
			StringUtil.upperCaseFirstLetter(parameter.getParameterName()));
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(parameter.getParameterType());
		sb.append(StringPool.SPACE);
		sb.append(parameter.getParameterName());

		return sb.toString();
	}

	private boolean _extendsFromBaseModelListener(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (extendedClassNames.contains("BaseModelListener")) {
			return true;
		}

		return false;
	}

	private String _format(
		JavaTerm childJavaTerms, String methodCall, String newContent) {

		JavaSignature javaSignature = childJavaTerms.getSignature();

		List<JavaParameter> parameterList = javaSignature.getParameters();

		if (parameterList.size() != 1) {
			return newContent;
		}

		String method = _formatMethod(childJavaTerms, parameterList.get(0));

		String newParameters = _cloneAndRenameFirstParameter(
			parameterList.get(0));

		return StringUtil.replace(
			newContent, methodCall,
			StringUtil.replace(
				methodCall, method,
				childJavaTerms.getName() + StringPool.OPEN_PARENTHESIS +
					newParameters));
	}

	private String _formatMethod(
		JavaTerm childJavaTerms, JavaParameter parameter) {

		StringBundler sb = new StringBundler(5);

		sb.append(childJavaTerms.getName());
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(parameter.getParameterType());
		sb.append(StringPool.SPACE);
		sb.append(parameter.getParameterName());

		return sb.toString();
	}

	private static final Pattern _onAfterUpdatePattern = Pattern.compile(
		" void\\s*onAfterUpdate\\(");

}