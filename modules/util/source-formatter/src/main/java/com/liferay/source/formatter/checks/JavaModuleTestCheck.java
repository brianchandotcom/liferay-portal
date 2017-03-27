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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class JavaModuleTestCheck extends BaseFileCheck {

	public JavaModuleTestCheck(boolean subrepository) {
		_subrepository = subrepository;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("Test.java") ||
			!isModulesFile(absolutePath, _subrepository)) {

			return new Tuple(content, Collections.emptySet());
		}

		String packagePath = JavaSourceUtil.getPackagePath(content);

		if (!packagePath.startsWith("com.liferay")) {
			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkTestPackage(
			sourceFormatterMessages, fileName, absolutePath, content,
			packagePath);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkTestPackage(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String absolutePath, String content, String packagePath) {

		if (absolutePath.contains("/src/testIntegration/java/") ||
			absolutePath.contains("/test/integration/")) {

			if (content.contains("@RunWith(Arquillian.class)") &&
				content.contains("import org.powermock.")) {

				addMessage(
					sourceFormatterMessages, fileName,
					"Do not use PowerMock inside Arquillian tests, see " +
						"LPS-56706");
			}

			if (!packagePath.endsWith(".test")) {
				addMessage(
					sourceFormatterMessages, fileName,
					"Module integration test must be under a test " +
						"subpackage, see LPS-57722");
			}
		}
		else if ((absolutePath.contains("/test/unit/") ||
				  absolutePath.contains("/src/test/java/")) &&
				 packagePath.endsWith(".test")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Module unit test should not be under a test subpackage, see " +
					"LPS-57722");
		}
	}

	private final boolean _subrepository;

}