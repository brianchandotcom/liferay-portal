/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JavaInjectAnnotationsCheck extends JavaAnnotationsCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String formatAnnotation(
			String fileName, String absolutePath, JavaClass javaClass,
			String fileContent, String annotation, String indent)
		throws Exception {

		if (!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/")) {

			return annotation;
		}

		String trimmedAnnotation = StringUtil.trim(annotation);

		if (!trimmedAnnotation.equals("@Inject") &&
			!trimmedAnnotation.startsWith("@Inject(")) {

			return annotation;
		}

		List<String> importNames = javaClass.getImportNames();

		if (!importNames.contains("com.liferay.portal.test.rule.Inject")) {
			return annotation;
		}

		_checkFilterAttribute(
			fileName, absolutePath, javaClass, annotation, "filter");

		return annotation;
	}

	private void _checkFilterAttribute(
			String fileName, String absolutePath, JavaClass javaClass,
			String annotation, String filterAttribute)
		throws Exception {

		String filterAttributeValue = getAnnotationAttributeValue(
			annotation, filterAttribute);

		if (filterAttributeValue == null) {
			return;
		}

		checkComponentName(
			fileName, absolutePath, javaClass, filterAttributeValue, false);
	}

}