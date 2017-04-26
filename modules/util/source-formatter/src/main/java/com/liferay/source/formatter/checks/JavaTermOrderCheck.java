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
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaStaticBlock;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.comparator.JavaTermComparator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTermOrderCheck extends BaseJavaTermCheck {

	public JavaTermOrderCheck() throws Exception {
		_portalCustomSQLContent = _getPortalCustomSQLContent();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		String javaTermContent = javaTerm.getContent();

		if (javaTermContent.contains("@Meta.OCD")) {
			return javaTermContent;
		}

		String className = javaTerm.getName();

		String customSQLContent = null;

		if (absolutePath.contains("/persistence/") &&
			className.endsWith("FinderImpl")) {

			customSQLContent = _getCustomSQLContent(fileName, absolutePath);
		}

		return _sortJavaTerms(
			fileName, absolutePath, (JavaClass)javaTerm, customSQLContent);
	}

	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _getPortalCustomSQLContent() throws Exception {
		if (!isPortalSource()) {
			return null;
		}

		File portalCustomSQLFile = getFile(
			"portal-impl/src/custom-sql/default.xml",
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		String portalCustomSQLContent = FileUtil.read(portalCustomSQLFile);

		Matcher matcher = _customSQLFilePattern.matcher(portalCustomSQLContent);

		while (matcher.find()) {
			File customSQLFile = getFile(
				"portal-impl/src/" + matcher.group(1),
				ToolsUtil.PORTAL_MAX_DIR_LEVEL);

			if (customSQLFile != null) {
				portalCustomSQLContent += FileUtil.read(customSQLFile);
			}
		}

		return portalCustomSQLContent;
	}

	private String _getCustomSQLContent(String fileName, String absolutePath)
		throws Exception {

		if (isPortalSource() && !isModulesFile(absolutePath)) {
			return _portalCustomSQLContent;
		}

		int i = fileName.lastIndexOf("/src/");

		if (i == -1) {
			return null;
		}

		File customSQLFile = new File(
			fileName.substring(0, i) + "/src/custom-sql/default.xml");

		if (!customSQLFile.exists()) {
			customSQLFile = new File(
				fileName.substring(0, i) +
					"/src/main/resources/META-INF/custom-sql/default.xml");
		}

		if (!customSQLFile.exists()) {
			customSQLFile = new File(
				fileName.substring(0, i) +
					"/src/main/resources/custom-sql/default.xml");
		}

		if (!customSQLFile.exists()) {
			return null;
		}

		return FileUtil.read(customSQLFile);
	}

	private String _sortJavaTerms(
		String fileName, String absolutePath, JavaClass javaClass,
		String customSQLContent) {

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		if (childJavaTerms.size() < 2) {
			return javaClass.getContent();
		}

		JavaTermComparator javaTermComparator = new JavaTermComparator(
			customSQLContent);

		JavaTerm previousJavaTerm = null;

		for (JavaTerm javaTerm : childJavaTerms) {
			if (javaTerm instanceof JavaStaticBlock) {
				continue;
			}

			if (previousJavaTerm == null) {
				previousJavaTerm = javaTerm;

				continue;
			}

			int compare = javaTermComparator.compare(
				previousJavaTerm, javaTerm);

			if (compare == 0) {
				addMessage(fileName, "Duplicate " + javaTerm.getName());
			}
			else if (!isExcludedPath(
						JAVATERM_SORT_EXCLUDES, absolutePath,
						previousJavaTerm.getName()) &&
					 !isExcludedPath(
						 JAVATERM_SORT_EXCLUDES, absolutePath,
						 javaTerm.getName()) &&
					 (compare > 0)) {

				String classContent = javaClass.getContent();

				String newClassContent = StringUtil.replaceFirst(
					classContent, "\n" + previousJavaTerm.getContent(),
					"\n" + javaTerm.getContent());

				newClassContent = StringUtil.replaceLast(
					newClassContent, "\n" + javaTerm.getContent(),
					"\n" + previousJavaTerm.getContent());

				return newClassContent;
			}

			previousJavaTerm = javaTerm;
		}

		return javaClass.getContent();
	}

	private final Pattern _customSQLFilePattern = Pattern.compile(
		"<sql file=\"(.*)\" \\/>");
	private final String _portalCustomSQLContent;

}