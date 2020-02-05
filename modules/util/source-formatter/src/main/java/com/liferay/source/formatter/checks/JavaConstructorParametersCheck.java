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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaConstructorParametersCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaSignature signature = javaTerm.getSignature();

		List<JavaParameter> parameters = signature.getParameters();

		if (!parameters.isEmpty()) {
			_checkConstructorParameterOrder(fileName, javaTerm, parameters);
		}

		if (parameters.size() > 1) {
			return _fixIncorrectEmptyLines(javaTerm.getContent(), parameters);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR};
	}

	private void _checkConstructorParameterOrder(
		String fileName, JavaTerm javaTerm, List<JavaParameter> parameters) {

		String previousParameterName = null;
		int previousPos = -1;

		for (JavaParameter parameter : parameters) {
			String parameterName = parameter.getParameterName();

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\\{\n([\\s\\S]*?)(_", parameterName, " =[ \t\n]+",
					parameterName, ";)"));

			Matcher matcher = pattern.matcher(javaTerm.getContent());

			if (!matcher.find()) {
				continue;
			}

			String beforeParameter = matcher.group(1);

			if (beforeParameter.contains(parameterName + " =")) {
				continue;
			}

			int pos = matcher.start(2);

			if (previousPos < pos) {
				previousParameterName = parameterName;
				previousPos = pos;

				continue;
			}

			StringBundler sb = new StringBundler(9);

			sb.append("'_");
			sb.append(previousParameterName);
			sb.append(" = ");
			sb.append(previousParameterName);
			sb.append(";' should come before '_");
			sb.append(parameterName);
			sb.append(" = ");
			sb.append(parameterName);
			sb.append(";' to match order of constructor parameters");

			addMessage(fileName, sb.toString());

			return;
		}
	}

	private String _fixIncorrectEmptyLines(
		String content, List<JavaParameter> parameters) {

		for (int i = 1; i < parameters.size(); i++) {
			JavaParameter parameter = parameters.get(i);
			JavaParameter previousParameter = parameters.get(i - 1);

			String name = parameter.getParameterName();
			String previousName = previousParameter.getParameterName();

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"\t_", previousName, " =[ \t\n]+", previousName,
					";(\n\n)\t+_", name, " =[ \t\n]+", name, ";\n"));

			Matcher matcher = pattern.matcher(content);

			if (!matcher.find()) {
				continue;
			}

			if ((_getOccurenceCount(content, name) == 2) &&
				(_getOccurenceCount(content, "_" + name) == 1) &&
				(_getOccurenceCount(content, previousName) == 2) &&
				(_getOccurenceCount(content, "_" + previousName) == 1)) {

				return StringUtil.replaceFirst(
					content, StringPool.NEW_LINE, StringPool.BLANK,
					matcher.start(1));
			}
		}

		return content;
	}

	private int _getOccurenceCount(String content, String name) {
		int count = 0;

		Pattern pattern = Pattern.compile("\\W" + name + "\\W");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			count++;
		}

		return count;
	}

}