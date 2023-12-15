/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Seiphon Wang
 */
public class JavaAccessModifierCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String packageName = JavaSourceUtil.getPackageName(content);

		if (!packageName.startsWith("com.liferay")) {
			return content;
		}

		Map<String, List<String>> componentJavaFileMap =
			_getCommponentJavaFileMap();

		Set<String> superClassNames = componentJavaFileMap.keySet();

		if (!superClassNames.contains(
				packageName + "." + JavaSourceUtil.getClassName(fileName))) {

			return content;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (childJavaTerm instanceof JavaVariable) {
				JavaVariable javaVariable = (JavaVariable)childJavaTerm;

				String accessModifier = javaVariable.getAccessModifier();
				String javaTermContent = javaVariable.getContent();

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

	private Map<String, List<String>> _getCommponentJavaFileMap()
		throws Exception {

		if (_componentJavaFileMap != null) {
			return _componentJavaFileMap;
		}

		_componentJavaFileMap = new ConcurrentHashMap<>();

		String moduleRootDirLocation = "modules/";

		List<String> lines = new ArrayList<>();

		for (int i = 0; i < getMaxDirLevel(); i++) {
			File file = new File(getBaseDirName() + moduleRootDirLocation);

			if (file.exists()) {
				lines = SourceFormatterUtil.matchFileContents(
					Arrays.asList("--untracked", "-E", "-l", "@Component"),
					getBaseDirName(), new String[] {"**/*.java"});
			}

			moduleRootDirLocation = "../" + moduleRootDirLocation;
		}

		for (String line : lines) {
			if (line.contains("/src/test/java/") ||
				line.contains("/test/unit/")) {

				continue;
			}

			Path baseDirPath = Paths.get(getBaseDirName());

			Path filePath = baseDirPath.resolve(line);

			if (Files.exists(filePath)) {
				String fileName = filePath.toString();

				fileName = StringUtil.replace(
					fileName, CharPool.BACK_SLASH, CharPool.SLASH);

				BNDSettings bndSettings = getBNDSettings(fileName);

				if (bndSettings == null) {
					continue;
				}

				String bndSettingsContent = bndSettings.getContent();

				if (!bndSettingsContent.contains(
						"-dsannotations-options: inherit")) {

					continue;
				}

				String content = FileUtil.read(filePath.toFile());

				String superClassName = _getSuperClassNameWithPackageName(
					content);

				if (superClassName != null) {
					String className = JavaSourceUtil.getClassName(fileName);

					List<String> subclassList = _componentJavaFileMap.get(
						superClassName);

					if (subclassList == null) {
						subclassList = new ArrayList<>();
					}

					subclassList.add(className);

					_componentJavaFileMap.put(superClassName, subclassList);
				}
			}
		}

		return _componentJavaFileMap;
	}

	private String _getSuperClassNameWithPackageName(String content) {
		String superClassName = JavaSourceUtil.getSuperClassName(content);

		if (superClassName == null) {
			return null;
		}

		Pattern superClassImportPattern = Pattern.compile(
			"import\\s+([\\w.]+" + superClassName + ")\\s*;", Pattern.DOTALL);

		Matcher matcher = superClassImportPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return JavaSourceUtil.getPackageName(content) + "." + superClassName;
	}

	private Map<String, List<String>> _componentJavaFileMap;

}