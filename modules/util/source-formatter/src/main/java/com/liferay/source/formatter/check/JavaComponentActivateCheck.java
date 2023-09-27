/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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

			if (javaTerm.isProtected()) {
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