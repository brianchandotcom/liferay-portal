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
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaConstructorParameterOrder extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaSignature signature = javaTerm.getSignature();

		List<JavaParameter> parameters = signature.getParameters();

		if (!parameters.isEmpty()) {
			_checkConstructorParameterOrder(fileName, javaTerm, parameters);
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
				"\\{\n([\\s\\S]*?)(_" + parameterName + " =[ \t\n]+" +
					parameterName + ";)");

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

			addMessage(
				fileName, sb.toString(), "constructor_parameters.markdown");

			return;
		}
	}

}