/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Seiphon Wang
 */
public class JavaAccessModifierCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!content.contains("@Component")) {
			return content;
		}

		String packageName = JavaSourceUtil.getPackageName(content);

		if (!packageName.startsWith("com.liferay")) {
			return content;
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return content;
		}

		String bndSettingsContent = bndSettings.getContent();

		if (!bndSettingsContent.contains("-dsannotations-options: inherit")) {
			return content;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		if (!_hasSubclasses(javaClass)) {
			return content;
		}

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (childJavaTerm instanceof JavaVariable) {
				JavaVariable javaVariable = (JavaVariable)childJavaTerm;

				String javaTermContent = javaVariable.getContent();

				String accessModifier = javaVariable.getAccessModifier();

				if (javaTermContent.contains("@Reference") &&
					accessModifier.equals("private")) {

					addMessage(
						fileName,
						"The access modifier of variable '" +
							javaVariable.getName() +
								"' should be 'protected'.");
				}
			}
		}

		return content;
	}

	private boolean _hasSubclasses(JavaClass javaClass) {
		String className = javaClass.getName();

		String packageName = javaClass.getPackageName();

		String moduleRootDirLocation = "modules/";

		List<String> lines = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			File file = new File(getBaseDirName() + moduleRootDirLocation);

			if (file.exists()) {
				lines = SourceFormatterUtil.matchFileContents(
					Arrays.asList(
						"-E", "-l",
						StringBundler.concat(
							"(extends ", className, ")|(extends ", packageName,
							".", className, ")")),
					getBaseDirName(), new String[0], new String[] {"**/*.java"},
					getSourceFormatterExcludes(), false);
			}

			moduleRootDirLocation = "../" + moduleRootDirLocation;
		}

		if (!lines.isEmpty()) {
			for (String line : lines) {
				if (line.contains("/src/test/java/") ||
					line.contains("/test/unit/")) {

					continue;
				}

				Path baseDir = Paths.get(getBaseDirName());

				Path filePath = baseDir.resolve(line);

				if (Files.exists(filePath)) {
					try {
						String content = FileUtil.read(filePath.toFile());

						if (!content.contains("@Component")) {
							return false;
						}

						if (content.contains(
								StringBundler.concat(
									"package ", packageName, ";")) ||
							content.contains(
								StringBundler.concat(
									"import ", packageName, ".", className))) {

							return true;
						}
					}
					catch (IOException ioException) {
					}
				}
			}
		}

		return false;
	}

}