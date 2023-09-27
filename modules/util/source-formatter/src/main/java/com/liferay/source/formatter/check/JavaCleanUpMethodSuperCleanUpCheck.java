/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class JavaCleanUpMethodSuperCleanUpCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (!fileName.endsWith("Tag.java")) {
			return javaTerm.getContent();
		}

		JavaMethod javaMethod = (JavaMethod)javaTerm;

		if (!Objects.equals(javaMethod.getName(), "cleanUp") ||
			!javaMethod.hasAnnotation("Override")) {

			return javaMethod.getContent();
		}

		JavaSignature javaSignature = javaMethod.getSignature();

		if (!Objects.equals(javaSignature.getReturnType(), StringPool.BLANK)) {
			return javaMethod.getContent();
		}

		String javaMethodContent = javaMethod.getContent();

		String regex = StringBundler.concat(
			"(", SourceUtil.getIndent(javaMethodContent),
			javaMethod.getAccessModifier(),
			" .*?[;{]\\s*?\n)((\n*)([^\n]+)\n)?");

		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(javaMethodContent);

		if (!matcher.find()) {
			return javaMethodContent;
		}

		String firstLine = matcher.group(4);

		if (firstLine.contains("super.cleanUp();")) {
			return javaMethodContent;
		}

		String indent = SourceUtil.getIndent(firstLine);

		return StringUtil.replace(
			javaMethodContent, firstLine,
			indent + "super.cleanUp();\n\n" + firstLine);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

}