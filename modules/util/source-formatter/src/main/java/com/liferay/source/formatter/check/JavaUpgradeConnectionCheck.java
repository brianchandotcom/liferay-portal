/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

/**
 * @author Peter Shin
 */
public class JavaUpgradeConnectionCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (!absolutePath.contains("/upgrade/") ||
			absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/") ||
			!isUpgradeProcess(absolutePath, fileContent)) {

			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String methodName = javaMethod.getName();

			if (methodName.equals("upgrade") &&
				javaMethod.hasAnnotation("Override")) {

				continue;
			}

			String methodContent = javaMethod.getContent();

			int x = methodContent.indexOf("DataAccess.getConnection(");

			if (x == -1) {
				continue;
			}

			addMessage(
				fileName,
				"Use existing connection field instead of calling DataAccess." +
					"getConnection",
				getLineNumber(fileContent, x));
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}