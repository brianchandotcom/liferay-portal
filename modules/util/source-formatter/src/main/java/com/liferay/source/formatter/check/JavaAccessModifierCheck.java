/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.util.SourceFormatterUtil;

/**
 * @author Seiphon Wang
 */
public class JavaAccessModifierCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(String fileName, String absolutePath,
			JavaTerm javaTerm, String fileContent) throws Exception {

		String content = javaTerm.getContent();

		if (!javaTerm.hasAnnotation("Component") ||
				!_hasSubclass(javaTerm) ||
				!_isAnnotationsInherit(absolutePath)) {

			return content;
		}

		if (javaTerm instanceof JavaClass) {
			JavaClass javaClass = (JavaClass)javaTerm;

			List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

			for (JavaTerm childJavaTerm : childJavaTerms) {
				if (childJavaTerm instanceof JavaVariable) {
					JavaVariable javaVariable = (JavaVariable)childJavaTerm;

					String javaTermContent = javaVariable.getContent();

					String accessModifier = javaVariable.getAccessModifier();

					if (javaTermContent.contains("@Reference") &&
							accessModifier.equals("private")) {

						addMessage(fileName, "The access modifier of variable '" +
							javaVariable.getName() + "' should be 'protected'.");
					}
				}
			}
		}

		return content;
	}

	private boolean _isAnnotationsInherit(String filePathString) {
		Path filePath = Paths.get(filePathString);

		Path bndPath = _searchBndFile(filePath.getParent());

		if (bndPath != null) {
			try {
				List<String> lines = Files.readAllLines(bndPath);

				for (String line : lines) {
					if ("-dsannotations-options: inherit".equals(line)) {
						return true;
					}
				}
			} catch (IOException ioException) {
			}
		}

		return false;
	}

	private Path _searchBndFile(Path currentPath) {
		Path result = null;

		while (currentPath != null) {
			Path bndFilePath = currentPath.resolve("bnd.bnd");

			if (Files.exists(bndFilePath)) {
				result = bndFilePath;

				break;
			}

			if (currentPath.equals(Paths.get(getBaseDirName()))) {
				break;
			}

			currentPath = currentPath.getParent();
		}

		return result;
	}

	private boolean _hasSubclass(JavaTerm javaTerm) {
		String className = javaTerm.getName();

		String baseDirName = getBaseDirName();

		List<String> lines = SourceFormatterUtil.matchFileContents(
			baseDirName, "extends " + className, Arrays.asList("--", "*.java"));

		return lines.size() != 0;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}
