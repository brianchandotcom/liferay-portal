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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaReturnStatementCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		return _formatReturnStatements(javaTerm);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _formatReturnStatement(
		String javaTermContent, String returnStatement, String tabs,
		String ifCondition, String trueValue, String falseValue) {

		StringBundler sb = new StringBundler(15);

		sb.append("\n");
		sb.append(tabs);
		sb.append("if (");
		sb.append(ifCondition);
		sb.append(") {\n\n");
		sb.append(tabs);
		sb.append("\treturn ");
		sb.append(trueValue);
		sb.append(";\n");
		sb.append(tabs);
		sb.append("}\n\n");
		sb.append(tabs);
		sb.append("return ");
		sb.append(falseValue);
		sb.append(";\n");

		return StringUtil.replace(
			javaTermContent, returnStatement, sb.toString());
	}

	private String _formatReturnStatements(JavaTerm javaTerm) {
		String javaTermContent = javaTerm.getContent();

		JavaSignature signature = javaTerm.getSignature();

		String returnType = signature.getReturnType();

		Matcher matcher1 = _returnPattern.matcher(javaTermContent);

		while (matcher1.find()) {
			String returnStatement = matcher1.group();

			if (returnStatement.contains("\t//") ||
				returnStatement.contains(" {\n")) {

				continue;
			}

			String[] ternaryOperatorParts = getTernaryOperatorParts(
				matcher1.group(2));

			if (ternaryOperatorParts != null) {
				String falseValue = ternaryOperatorParts[2];
				String ifCondition = ternaryOperatorParts[0];
				String trueValue = ternaryOperatorParts[1];

				return _formatReturnStatement(
					javaTermContent, returnStatement, matcher1.group(1),
					ifCondition, trueValue, falseValue);
			}

			if ((returnType == null) || !returnType.equals("boolean")) {
				continue;
			}

			String strippedReturnStatement = stripQuotes(returnStatement);

			if (strippedReturnStatement.contains("|") ||
				strippedReturnStatement.contains("&") ||
				strippedReturnStatement.contains("^")) {

				return _formatReturnStatement(
					javaTermContent, returnStatement, matcher1.group(1),
					matcher1.group(2), "true", "false");
			}

			Matcher matcher2 = _relationalOperatorPattern.matcher(
				returnStatement);

			if (matcher2.find() &&
				!ToolsUtil.isInsideQuotes(returnStatement, matcher2.start(1))) {

				return _formatReturnStatement(
					javaTermContent, returnStatement, matcher1.group(1),
					matcher1.group(2), "true", "false");
			}
		}

		return javaTermContent;
	}

	private final Pattern _relationalOperatorPattern = Pattern.compile(
		".* (==|!=|<|>|>=|<=)[ \n].*");
	private final Pattern _returnPattern = Pattern.compile(
		"\n(\t+)return (.*?);\n", Pattern.DOTALL);

}