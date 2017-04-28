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

import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaModuleIllegalImportsCheck extends BaseFileCheck {

	public JavaModuleIllegalImportsCheck(boolean checkRegistryInTestClasses) {
		_checkRegistryInTestClasses = checkRegistryInTestClasses;
	}

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("JavaModuleIllegalImportsCheck.java")) {
			return content;
		}

		String packagePath = JavaSourceUtil.getPackagePath(content);

		if (!packagePath.startsWith("com.liferay")) {
			return content;
		}

		// LPS-62989

		if (!absolutePath.contains("/modules/core/jaxws-osgi-bridge") &&
			!absolutePath.contains("/modules/core/portal-bootstrap") &&
			!absolutePath.contains("/modules/core/registry-") &&
			!absolutePath.contains("/modules/core/slim-runtime") &&
			(_checkRegistryInTestClasses ||
			 (!absolutePath.contains("/test/") &&
			  !absolutePath.contains("/testIntegration/")))) {

			Matcher matcher = _registryImportPattern.matcher(content);

			if (matcher.find()) {
				addMessage(
					fileName,
					"Do not use com.liferay.registry classes in modules, see " +
						"LPS-62989");
			}
		}

		// LPS-64238

		if (content.contains("import com.liferay.util.dao.orm.CustomSQLUtil")) {
			addMessage(
				fileName,
				"Do not use com.liferay.util.dao.orm.CustomSQLUtil in " +
					"modules, see LPS-64238");
		}

		// LPS-64335

		if (content.contains("import com.liferay.util.ContentUtil")) {
			addMessage(
				fileName,
				"Do not use com.liferay.util.ContentUtil in modules, see " +
					"LPS-64335");
		}

		return content;
	}

	private final boolean _checkRegistryInTestClasses;
	private final Pattern _registryImportPattern = Pattern.compile(
		"\nimport (com\\.liferay\\.registry\\..+);");

}