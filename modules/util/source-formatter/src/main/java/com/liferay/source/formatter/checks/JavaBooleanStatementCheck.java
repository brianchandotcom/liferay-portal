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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaBooleanStatementCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		return _formatBooleanStatements(javaTerm);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _formatBooleanStatement(
		String javaTermContent, String booleanStatement, String tabs,
		String variableName, String ifCondition, String trueValue,
		String falseValue) {

		StringBundler sb = new StringBundler(19);

		sb.append("\n\n");
		sb.append(tabs);
		sb.append("boolean ");
		sb.append(variableName);
		sb.append(" = ");
		sb.append(falseValue);
		sb.append(";\n\n");
		sb.append(tabs);
		sb.append("if (");
		sb.append(ifCondition);
		sb.append(") {\n\n");
		sb.append(tabs);
		sb.append("\t");
		sb.append(variableName);
		sb.append(" = ");
		sb.append(trueValue);
		sb.append(";\n");
		sb.append(tabs);
		sb.append("}\n");

		return StringUtil.replace(
			javaTermContent, booleanStatement, sb.toString());
	}

	private String _formatBooleanStatements(JavaTerm javaTerm) {
		String javaTermContent = javaTerm.getContent();

		Matcher matcher1 = _booleanPattern.matcher(javaTermContent);

		while (matcher1.find()) {
			String booleanStatement = matcher1.group();

			if (booleanStatement.contains("\t//") ||
				booleanStatement.contains(" {\n")) {

				continue;
			}

			String criteria = matcher1.group(3);

			String[] ternaryOperatorParts = getTernaryOperatorParts(criteria);

			if (ternaryOperatorParts != null) {
				String falseValue = ternaryOperatorParts[2];
				String ifCondition = ternaryOperatorParts[0];
				String trueValue = ternaryOperatorParts[1];

				return _formatBooleanStatement(
					javaTermContent, booleanStatement, matcher1.group(1),
					matcher1.group(2), ifCondition, trueValue, falseValue);
			}

			String strippedCriteria = _stripQuotesAndMethodParameters(criteria);

			if ((getLevel(strippedCriteria) == 0) &&
				(strippedCriteria.contains("|") ||
				 strippedCriteria.contains("&") ||
				 strippedCriteria.contains("^"))) {

				return _formatBooleanStatement(
					javaTermContent, booleanStatement, matcher1.group(1),
					matcher1.group(2), criteria, "true", "false");
			}

			Matcher matcher2 = _relationalOperatorPattern.matcher(
				strippedCriteria);

			if (matcher2.find()) {
				return _formatBooleanStatement(
					javaTermContent, booleanStatement, matcher1.group(1),
					matcher1.group(2), criteria, "true", "false");
			}
		}

		return javaTermContent;
	}

	private String _stripQuotesAndMethodParameters(String s) {
		s = stripQuotes(s);

		outerLoop:
		while (true) {
			int start = -1;

			for (int i = 1; i < s.length(); i++) {
				char c1 = s.charAt(i);

				if (start == -1) {
					if (c1 == CharPool.OPEN_PARENTHESIS) {
						char c2 = s.charAt(i - 1);

						if (Character.isLetterOrDigit(c2)) {
							start = i;
						}
					}

					continue;
				}

				if (c1 != CharPool.CLOSE_PARENTHESIS) {
					continue;
				}

				String part = s.substring(start, i + 1);

				if (getLevel(part) == 0) {
					s = StringUtil.replace(s, part, StringPool.BLANK, start);

					continue outerLoop;
				}
			}

			break;
		}

		return s;
	}

	private final Pattern _booleanPattern = Pattern.compile(
		"\n(\t+)boolean (\\w+) =(.*?);\n", Pattern.DOTALL);
	private final Pattern _relationalOperatorPattern = Pattern.compile(
		".* (==|!=|<|>|>=|<=)[ \n].*");

}