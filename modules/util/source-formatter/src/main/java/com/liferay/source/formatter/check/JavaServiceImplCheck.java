/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Hugo Huijser
 */
public class JavaServiceImplCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = javaTerm.getParentJavaClass();

		String className = javaClass.getName();

		if (className.endsWith("ServiceImpl")) {
			return _formatServiceImpl(javaTerm);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatServiceImpl(JavaTerm javaTerm) {
		String javaTermContent = javaTerm.getContent();
		String javaTermName = javaTerm.getName();

		if ((!javaTermName.equals("afterPropertiesSet") &&
			 !javaTermName.equals("destroy")) ||
			!javaTerm.hasAnnotation("Override")) {

			return javaTermContent;
		}

		String superMethodCall = "super." + javaTermName + "();";

		if (javaTermContent.contains(superMethodCall)) {
			return javaTermContent;
		}

		String indent = SourceUtil.getIndent(javaTermContent) + "\t";

		return StringUtil.replaceFirst(
			javaTermContent, "{\n",
			StringBundler.concat("{\n", indent, superMethodCall, "\n\n"));
	}

}