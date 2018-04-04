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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class JavaJSPDynamicIncludeCheck extends BaseJavaTermCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		String className = javaTerm.getName();

		if (!className.endsWith("DynamicInclude")) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (extendedClassNames.contains("BaseJSPDynamicInclude") &&
			!className.endsWith("JSPDynamicInclude")) {

			addMessage(
				fileName,
				"Class '" + className +
					"' should end with 'JSPDynamicInclude'");
		}

		if (!className.endsWith("JSPDynamicInclude")) {
			return javaTerm.getContent();
		}

		String jspPath = _getJspPath(javaClass);

		if (jspPath == null) {
			return javaTerm.getContent();
		}

		String bundleSymbolicName = _getBundleSymbolicName(fileName);

		if (jspPath.contains(bundleSymbolicName)) {
			String message = StringBundler.concat(
				"The JSP path should not contain '", bundleSymbolicName,
				"'. This is only needed when hooking into another module.");

			addMessage(fileName, message);
		}

		if (!jspPath.startsWith("/dynamic_include/")) {
			addMessage(
				fileName, "The JSP path should start with '/dynamic_include/'");
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private BNDSettings _getBNDSettings(String fileName) throws Exception {
		String bndFileLocation = fileName;

		while (true) {
			int pos = bndFileLocation.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return null;
			}

			bndFileLocation = bndFileLocation.substring(0, pos + 1);

			File file = new File(bndFileLocation + "bnd.bnd");

			if (file.exists()) {
				return new BNDSettings(
					bndFileLocation + "bnd.bnd", FileUtil.read(file));
			}

			bndFileLocation = StringUtil.replaceLast(
				bndFileLocation, CharPool.SLASH, StringPool.BLANK);
		}
	}

	private String _getBundleSymbolicName(String fileName) throws Exception {
		BNDSettings bndSettings = _getBNDSettings(fileName);

		if (bndSettings == null) {
			return null;
		}

		return BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");
	}

	private String _getJspPath(JavaClass javaClass) {
		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			String name = javaMethod.getName();

			if (!name.equals("getJspPath")) {
				continue;
			}

			JavaSignature javaSignature = javaMethod.getSignature();

			String returnType = javaSignature.getReturnType();

			if (!Objects.equals(returnType, "String")) {
				continue;
			}

			String content = javaMethod.getContent();

			content = content.replaceAll("\"\\s+\\+\\s+\"", "");

			Matcher matcher = _jspPathPattern.matcher(content);

			if (matcher.matches()) {
				return matcher.group(1);
			}
		}

		return null;
	}

	private final Pattern _jspPathPattern = Pattern.compile(
		".*\\s+return\\s+\"(\\S+)\";.*", Pattern.DOTALL);

}