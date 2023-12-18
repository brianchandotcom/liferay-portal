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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

		List<String> fileNames = new ArrayList<>();

		for (int i = 0; i < getMaxDirLevel(); i++) {
			File file = new File(getBaseDirName() + moduleRootDirLocation);

			if (file.exists()) {
				fileNames = SourceFormatterUtil.matchFileContents(
					Arrays.asList("--untracked", "-E", "-l", "@Component"),
					file.getCanonicalPath(), new String[] {"**/*.java"});

				break;
			}

			moduleRootDirLocation = "../" + moduleRootDirLocation;
		}

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			if (fileName.contains("/src/test/java/") ||
				fileName.contains("/test/unit/")) {

				continue;
			}

			File file = new File(fileName);

			if (!file.exists()) {
				continue;
			}

			BNDSettings bndSettings = getBNDSettings(fileName);

			if (bndSettings == null) {
				continue;
			}

			String bndSettingsContent = bndSettings.getContent();

			if (!bndSettingsContent.contains(
					"-dsannotations-options: inherit")) {

				continue;
			}

			String superClassName = _getSuperClassFullyQualifiedName(
				FileUtil.read(file));

			if (superClassName != null) {
				List<String> subclassNames = _componentJavaFileMap.get(
					superClassName);

				if (subclassNames == null) {
					subclassNames = new ArrayList<>();
				}

				subclassNames.add(JavaSourceUtil.getClassName(fileName));

				_componentJavaFileMap.put(superClassName, subclassNames);
			}
		}

		return _componentJavaFileMap;
	}

	private String _getSuperClassFullyQualifiedName(String content) {
		String superClassName = JavaSourceUtil.getSuperClassName(content);

		if (superClassName == null) {
			return null;
		}

		List<String> importNames = JavaSourceUtil.getImportNames(content);

		for (String importName : importNames) {
			if (importName.endsWith("." + superClassName)) {
				return importName;
			}
		}

		return JavaSourceUtil.getPackageName(content) + "." + superClassName;
	}

	private Map<String, List<String>> _componentJavaFileMap;

}