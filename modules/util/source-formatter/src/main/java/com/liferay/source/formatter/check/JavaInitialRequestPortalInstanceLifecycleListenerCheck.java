/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Lee
 */
public class JavaInitialRequestPortalInstanceLifecycleListenerCheck
	extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (extendedClassNames.contains(
				"InitialRequestPortalInstanceLifecycleListener")) {

			_checkInitialRequestPortalInstanceLifecycleListener(
				fileName, javaClass);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkInitialRequestPortalInstanceLifecycleListener(
		String fileName, JavaClass javaClass) {

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod() ||
				!Objects.equals(childJavaTerm.getName(), "activate") ||
				!childJavaTerm.hasAnnotation("Activate")) {

				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			JavaSignature javaSignature = javaMethod.getSignature();

			List<JavaParameter> javaParameters = javaSignature.getParameters();

			if (javaParameters.isEmpty()) {
				break;
			}

			JavaParameter javaParameter = javaParameters.get(0);

			if (!Objects.equals(
					javaParameter.getParameterType(), "BundleContext")) {

				break;
			}

			if (!javaMethod.hasAnnotation("Override")) {
				addMessage(
					fileName,
					"The \"activate\" method is missing the \"@Override\" " +
						"annotation",
					javaMethod.getLineNumber());
			}

			Pattern pattern = Pattern.compile(
				"super\\.activate\\(\\s*" + javaParameter.getParameterName());

			Matcher matcher = pattern.matcher(javaMethod.getContent());

			if (!matcher.find()) {
				addMessage(
					fileName,
					StringBundler.concat(
						"The \"activate\" method must call \"super.activate(",
						javaParameter.getParameterName(), ")\""),
					javaMethod.getLineNumber());
			}

			return;
		}

		addMessage(
			fileName,
			StringBundler.concat(
				"Missing \"activate(BundleContext bundleContext)\" method ",
				"with \"@Activate\" annotation that calls ",
				"\"super.activate(bundleContext)\""));
	}

}