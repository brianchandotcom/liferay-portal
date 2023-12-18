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
public class JavaAccessModifierCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		if (javaTerm.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		String packageName = javaClass.getPackageName();

		if (!packageName.startsWith("com.liferay")) {
			return javaTerm.getContent();
		}

		Map<String, List<String>> componentJavaFileMap =
			_getCommponentJavaFileMap();

		Set<String> superClassNames = componentJavaFileMap.keySet();

		if (!superClassNames.contains(
				packageName + "." + JavaSourceUtil.getClassName(fileName))) {

			return javaTerm.getContent();
		}

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (childJavaTerm.isJavaVariable()) {
				JavaVariable javaVariable = (JavaVariable)childJavaTerm;

				String accessModifier = javaVariable.getAccessModifier();
				String variableContent = javaVariable.getContent();

				if (variableContent.contains("@Reference") &&
					accessModifier.equals("private")) {

					addMessage(
						fileName,
						"The access modifier of variable '" +
							javaVariable.getName() +
								"' should be 'protected'.");
				}
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
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