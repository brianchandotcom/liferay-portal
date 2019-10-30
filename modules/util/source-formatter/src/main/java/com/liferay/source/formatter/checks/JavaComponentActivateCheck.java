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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaComponentActivateCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		List<String> importNames = getImportNames(javaTerm);

		if (importNames.isEmpty()) {
			return javaTerm.getContent();
		}

		return _formatModifiers(
			fileName, javaTerm, importNames, "Activate", "Deactivate");
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatModifiers(
		String fileName, JavaTerm javaTerm, List<String> importNames,
		String... annotationNames) {

		String content = javaTerm.getContent();

		for (String annotationName : annotationNames) {
			if (!javaTerm.hasAnnotation(annotationName) ||
				!importNames.contains(
					"org.osgi.service.component.annotations." +
						annotationName)) {

				continue;
			}

			String methodName = javaTerm.getName();

			if (!javaTerm.hasAnnotation("Override")) {
				String expectedMethodName = StringUtil.toLowerCase(
					annotationName);

				if (!methodName.equals(expectedMethodName)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Method with annotation '", annotationName,
							"' should have name '", expectedMethodName, "'"));
				}
			}

			if (javaTerm.getAccessModifier() ==
					JavaTerm.ACCESS_MODIFIER_PROTECTED) {

				continue;
			}

			Pattern pattern = Pattern.compile(
				StringBundler.concat(
					"(\\s)", javaTerm.getAccessModifier(), "( (.* )?void\\s*",
					javaTerm.getName(), ")"));

			Matcher matcher = pattern.matcher(content);

			if (matcher.find()) {
				return StringUtil.replaceFirst(
					content, javaTerm.getAccessModifier(),
					JavaTerm.ACCESS_MODIFIER_PROTECTED, matcher.start());
			}

			addMessage(
				fileName,
				"Method '" + javaTerm.getName() + "' should be protected",
				javaTerm.getLineNumber(matcher.start()));
		}

		return content;
	}

}