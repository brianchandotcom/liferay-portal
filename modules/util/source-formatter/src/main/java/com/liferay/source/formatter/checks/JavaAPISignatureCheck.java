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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaAPISignatureCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (javaTerm.hasAnnotation("Override")) {
			return javaTerm.getContent();
		}

		String accessModifier = javaTerm.getAccessModifier();

		if (!accessModifier.equals(JavaTerm.ACCESS_MODIFIER_PUBLIC)) {
			return javaTerm.getContent();
		}

		String className = JavaSourceUtil.getClassName(fileName);
		String packageName = JavaSourceUtil.getPackageName(fileContent);
		JavaSignature signature = javaTerm.getSignature();

		if (_isException(
				packageName, className, javaTerm.getName(), signature)) {

			return javaTerm.getContent();
		}

		List<String> illegalAPIParameterTypes = getAttributeValues(
			_ILLEGAL_API_PARAMETER_TYPES_KEY, absolutePath);
		List<String> illegalAPIServiceParameterTypes = getAttributeValues(
			_ILLEGAL_API_SERVICE_PARAMETER_TYPES_KEY, absolutePath);

		String methodName = javaTerm.getName();

		List<JavaParameter> parameters = signature.getParameters();

		for (JavaParameter parameter : parameters) {
			String parameterType = parameter.getParameterType();

			if (methodName.equals("set" + parameterType)) {
				continue;
			}

			if (illegalAPIServiceParameterTypes.contains(parameterType) &&
				!absolutePath.contains("-service/") &&
				!_matches(_SERVICE_PACKAGE_NAME_WHITELIST, packageName)) {

				addMessage(
					fileName,
					"Do not use type '" + parameterType +
						"' in API method signature");
			}

			if (illegalAPIParameterTypes.contains(parameterType) &&
				!_matches(_CLASS_NAME_WHITELIST, className) &&
				!_matches(_METHOD_NAME_WHITELIST, javaTerm.getName()) &&
				!_matches(_PACKAGE_NAME_WHITELIST, packageName)) {

				addMessage(
					fileName,
					"Do not use type '" + parameterType +
						"' in API method signature");
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private synchronized String[] _getAPISignatureExceptions()
		throws IOException {

		if (_apiSignatureExceptions != null) {
			return _apiSignatureExceptions;
		}

		_apiSignatureExceptions = new String[0];

		String fileName = "source-formatter-api-signature-check-exceptions.txt";

		List<String> exceptionFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0], new String[] {"**/" + fileName},
			getSourceFormatterExcludes(), true);

		for (String exceptionFileName : exceptionFileNames) {
			exceptionFileName = StringUtil.replace(
				exceptionFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File file = new File(exceptionFileName);

			_apiSignatureExceptions = ArrayUtil.append(
				_apiSignatureExceptions,
				StringUtil.splitLines(FileUtil.read(file)));
		}

		for (int i = 0; i < ToolsUtil.PORTAL_MAX_DIR_LEVEL; i++) {
			File file = new File(getBaseDirName() + fileName);

			if (file.exists()) {
				_apiSignatureExceptions = ArrayUtil.append(
					_apiSignatureExceptions,
					StringUtil.splitLines(FileUtil.read(file)));
			}

			fileName = "../" + fileName;
		}

		return _apiSignatureExceptions;
	}

	private boolean _isException(
			String packageName, String className, String methodName,
			JavaSignature javaSignature)
		throws IOException {

		StringBundler sb = new StringBundler(6);

		sb.append(packageName);
		sb.append(CharPool.PERIOD);
		sb.append(className);
		sb.append(CharPool.POUND);
		sb.append(methodName);
		sb.append(javaSignature.toString());

		if (ArrayUtil.contains(_getAPISignatureExceptions(), sb.toString())) {
			return true;
		}

		return false;
	}

	private boolean _matches(String[] array, String s) {
		for (String arrayItem : array) {
			if (s.matches(arrayItem)) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _CLASS_NAME_WHITELIST = {
		".*Action([A-Z].*)?", ".*Checker([A-Z].*)?", ".*Cookie([A-Z].*)?",
		".*HTTP([A-Z].*)?", ".*Http([A-Z].*)?", ".*JSP([A-Z].*)?",
		".*Language([A-Z].*)?", ".*Param([A-Z].*)?", ".*Portal([A-Z].*)?",
		".*Portlet([A-Z].*)?", ".*Renderer([A-Z].*)?", ".*Request([A-Z].*)?",
		".*Session([A-Z].*)?", ".*Template([A-Z].*)?", ".*Theme([A-Z].*)?",
		".*URL([A-Z].*)?"
	};

	private static final String _ILLEGAL_API_PARAMETER_TYPES_KEY =
		"illegalAPIParameterTypes";

	private static final String _ILLEGAL_API_SERVICE_PARAMETER_TYPES_KEY =
		"illegalAPIServiceParameterTypes";

	private static final String[] _METHOD_NAME_WHITELIST = {
		".*JSP([A-Z].*)?", ".*PortletURL([A-Z].*)?", "include([A-Z].*)?",
		"render([A-Z].*)?"
	};

	private static final String[] _PACKAGE_NAME_WHITELIST = {
		".*\\.alloy\\.mvc(\\..*)?", ".*\\.auth(\\..*)?", ".*\\.axis(\\..*)?",
		".*\\.display\\.context(\\..*)?", ".*\\.http(\\..*)?",
		".*\\.jsp(\\..*)?", ".*\\.layoutconfiguration\\.util(\\..*)?",
		".*\\.portal\\.action(\\..*)?", ".*\\.portal\\.events(\\..*)?",
		".*\\.portlet(\\..*)?", ".*\\.server\\.manager(\\..*)?",
		".*\\.servlet(\\..*)?", ".*\\.spi\\.agent(\\..*)?", ".*\\.sso(\\..*)?",
		".*\\.struts(\\..*)?", ".*\\.template(\\..*)?\\.internal(\\..*)?",
		".*\\.taglib(\\..*)?", ".*\\.web(\\..*)?\\.internal(\\..*)?",
		".*\\.web(\\..*)?\\.util(\\..*)?", ".*\\.webdav(\\..*)?",
		"com\\.liferay\\.frontend(\\..*)?",
		"com\\.liferay\\.portal\\.jsonwebservice(\\..*)?",
		"com\\.liferay\\.portal\\.language(\\..*)?",
		"com\\.liferay\\.portal\\.layoutconfiguration(\\..*)?"
	};

	private static final String[] _SERVICE_PACKAGE_NAME_WHITELIST = {
		".*\\.service(\\..*)?", ".*\\.test(\\..*)?"
	};

	private List<String> _allFileNames;
	private String[] _apiSignatureExceptions;

}