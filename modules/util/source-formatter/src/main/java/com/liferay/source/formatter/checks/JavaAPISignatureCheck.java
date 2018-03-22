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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaAPISignatureCheck extends BaseJavaTermCheck {

	@Override
	public void init() throws Exception {
		String content = getPortalContent(
			"source-formatter-api-signature-check-exceptions.txt");

		_apiSignatureExceptions = StringUtil.splitLines(content);
	}

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	public void setIllegalAPIParameterTypes(String illegalAPIParameterTypes) {
		Collections.addAll(
			_illegalAPIParameterTypes,
			StringUtil.split(illegalAPIParameterTypes));
	}

	public void setIllegalAPIServiceParameterTypes(
		String illegalAPIServiceParameterTypes) {

		Collections.addAll(
			_illegalAPIServiceParameterTypes,
			StringUtil.split(illegalAPIServiceParameterTypes));
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (isSubrepository() || isReadOnly(absolutePath)) {
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

		String methodName = javaTerm.getName();

		List<JavaParameter> parameters = signature.getParameters();

		for (JavaParameter parameter : parameters) {
			String parameterType = parameter.getParameterType();

			if (methodName.equals("set" + parameterType)) {
				continue;
			}

			if (_illegalAPIServiceParameterTypes.contains(parameterType) &&
				!absolutePath.contains("-service/") &&
				!_matches(_SERVICE_PACKAGE_NAME_WHITELIST, packageName)) {

				addMessage(
					fileName,
					"Do not use type '" + parameterType +
						"' in API method signature",
					"api_method_signatures.markdown");
			}

			if (_illegalAPIParameterTypes.contains(parameterType) &&
				!_matches(_CLASS_NAME_WHITELIST, className) &&
				!_matches(_METHOD_NAME_WHITELIST, javaTerm.getName()) &&
				!_matches(_PACKAGE_NAME_WHITELIST, packageName)) {

				addMessage(
					fileName,
					"Do not use type '" + parameterType +
						"' in API method signature",
					"api_method_signatures.markdown");
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private boolean _isException(
		String packageName, String className, String methodName,
		JavaSignature javaSignature) {

		StringBundler sb = new StringBundler(6);

		sb.append(packageName);
		sb.append(CharPool.PERIOD);
		sb.append(className);
		sb.append(CharPool.POUND);
		sb.append(methodName);
		sb.append(javaSignature.toString());

		if (ArrayUtil.contains(_apiSignatureExceptions, sb.toString())) {
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

	private static final String[] _SERVICE_PACKAGE_NAME_WHITELIST =
		{".*\\.service(\\..*)?", ".*\\.test(\\..*)?"};

	private String[] _apiSignatureExceptions;
	private final List<String> _illegalAPIParameterTypes = new ArrayList<>();
	private final List<String> _illegalAPIServiceParameterTypes =
		new ArrayList<>();

}