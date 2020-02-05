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

import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaModuleExposureCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return javaTerm.getContent();
		}

		String bundleSymbolicName = BNDSourceUtil.getDefinitionValue(
			bndSettings.getContent(), "Bundle-SymbolicName");

		if (!bundleSymbolicName.endsWith(".api")) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = javaTerm.getParentJavaClass();

		while (true) {
			if (javaClass.getParentJavaClass() == null) {
				break;
			}

			javaClass = javaClass.getParentJavaClass();
		}

		if (!_hasExportPackage(javaClass.getPackageName(), bndSettings)) {
			return javaTerm.getContent();
		}

		String exposedSPIType = _getExposedSPIType(
			javaTerm, javaClass.getImports());

		if (exposedSPIType != null) {
			addMessage(
				fileName,
				"Do not expose '" + exposedSPIType + "' in 'api' module");
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _getExposedSPIType(
		JavaTerm javaTerm, List<String> importNames) {

		JavaSignature signature = javaTerm.getSignature();

		if (signature == null) {
			return null;
		}

		for (String importName : importNames) {
			if (!importName.contains(".spi.")) {
				continue;
			}

			if (importName.equals(signature.getReturnType()) ||
				importName.endsWith("." + signature.getReturnType())) {

				return signature.getReturnType();
			}

			for (JavaParameter javaParameter : signature.getParameters()) {
				if (importName.equals(javaParameter.getParameterType()) ||
					importName.endsWith(
						"." + javaParameter.getParameterType())) {

					return javaParameter.getParameterType();
				}
			}
		}

		return null;
	}

	private boolean _hasExportPackage(
		String packageName, BNDSettings bndSettings) {

		List<String> exportPackages = BNDSourceUtil.getDefinitionValues(
			bndSettings.getContent(), "Export-Package");

		if (exportPackages.contains(packageName)) {
			return true;
		}

		for (String exportPackage : exportPackages) {
			if (exportPackage.endsWith(".*") &&
				packageName.startsWith(
					exportPackage.substring(0, exportPackage.length() - 2))) {

				return true;
			}
		}

		return false;
	}

}