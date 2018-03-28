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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaModuleComponentCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	public void setAllowedClassNames(String allowedClassNames) {
		Collections.addAll(
			_allowedClassNames, StringUtil.split(allowedClassNames));
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (isSubrepository() || isReadOnly(absolutePath)) {
			return javaTerm.getContent();
		}

		if (javaTerm.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		String packageName = JavaSourceUtil.getPackageName(fileContent);

		if (_allowedClassNames.contains(
				packageName + "." + javaTerm.getName())) {

			return javaTerm.getContent();
		}

		if (javaTerm.hasAnnotation("Component")) {
			if (absolutePath.contains("-api/") ||
				absolutePath.contains("-spi/")) {

				addMessage(
					fileName,
					"Do not use @Component in '-api' or '-spi' module",
					"component.markdown");
			}
		}
		else {
			JavaClass javaClass = (JavaClass)javaTerm;

			for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
				if (childJavaTerm.hasAnnotation("Reference")) {
					addMessage(
						fileName,
						"@Reference should not be used in a class without " +
							"@Component");

					break;
				}
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private final List<String> _allowedClassNames = new ArrayList<>();

}