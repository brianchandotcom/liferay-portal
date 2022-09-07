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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaUpgradeVersionCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		JavaClass javaClass = (JavaClass)javaTerm;

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		String content = javaClass.getContent();

		if (!implementedClassNames.contains("UpgradeStepRegistrator")) {
			return content;
		}

		BNDSettings bndSettings = getBNDSettings(fileName);

		boolean liferayService = false;

		if ((bndSettings != null) &&
			GetterUtil.getBoolean(
				BNDSourceUtil.getDefinitionValue(
					bndSettings.getContent(), "Liferay-Service"))) {

			liferayService = true;
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			String name = childJavaTerm.getName();

			if (!name.equals("register")) {
				continue;
			}

			if (liferayService) {
				_checkServiceUpgradeStepVersion(fileName, childJavaTerm);
			}
			else {
				content = _replaceDummyUpgradeStepForInitialize(
					content, childJavaTerm, fileName);
			}
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkServiceUpgradeStepVersion(
		String fileName, JavaTerm javaTerm) {

		String methodContent = javaTerm.getContent();

		int x = 0;

		while (true) {
			x = methodContent.indexOf("registry.register(", x + 1);

			if (x == -1) {
				return;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodContent.substring(x));

			String fromVersion = StringUtil.removeChar(
				parameterList.get(0), CharPool.QUOTE);

			if (fromVersion.equals("0.0.0")) {
				addMessage(
					fileName,
					"Upgrades from version 0.0.0 for service builder modules " +
						"are not allowed",
					javaTerm.getLineNumber(x));
			}
		}
	}

	private String _replaceDummyUpgradeStepForInitialize(
		String content, JavaTerm javaTerm, String fileName) {

		String methodContent = javaTerm.getContent();

		if (methodContent.contains(
				"registry.registerInitialDeploymentUpgradeSteps") ||
			methodContent.contains("registry.registerInitialization()")) {

			return content;
		}

		int x = methodContent.indexOf("registry.register(");

		if (x == -1) {
			return content;
		}

		int y = methodContent.indexOf(");", x) + 2;

		String methodCall = methodContent.substring(x, y);

		while (getLevel(methodCall) != 0) {
			y = methodContent.indexOf(");", y) + 2;

			methodCall = methodContent.substring(x, y);
		}

		List<String> parameterList = JavaSourceUtil.getParameterList(
			methodCall);

		if (!Objects.equals(parameterList.get(0), "\"0.0.0\"")) {
			String version = StringUtil.removeChar(
				parameterList.get(0), CharPool.QUOTE);

			if (!version.matches("\\d+\\.\\d+\\.\\d+")) {
				return content;
			}

			int index = methodContent.lastIndexOf(StringPool.NEW_LINE, x);

			StringBundler leadTabSB = new StringBundler();

			leadTabSB.append(StringPool.NEW_LINE);

			for (int i = 1; i < (x - index); i++) {
				leadTabSB.append(StringPool.TAB);
			}

			String newMethodContent = StringUtil.insert(
				methodContent,
				leadTabSB + "registry.registerInitialization();\n", index);

			return StringUtil.replaceFirst(
				content, methodContent, newMethodContent);
		}

		if (parameterList.size() != 3) {
			return content;
		}

		if (Objects.equals(parameterList.get(2), "new DummyUpgradeStep()") ||
			Objects.equals(parameterList.get(2), "new DummyUpgradeProcess()")) {

			String newMethodContent = StringUtil.replaceFirst(
				methodContent, methodCall, "registry.registerInitialization();",
				x);

			return StringUtil.replaceFirst(
				content, methodContent, newMethodContent);
		}

		addMessage(
			fileName,
			"Registered starting with '0.0.0' should be replace for " +
				"registry.registerInitialization();");

		return content;
	}

}