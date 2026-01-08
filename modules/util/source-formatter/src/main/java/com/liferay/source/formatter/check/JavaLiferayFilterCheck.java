/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

/**
 * @author Alan Huang
 */
public class JavaLiferayFilterCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (fileName.endsWith("/PortalInstancesFilter.java") ||
			!fileName.endsWith("Filter.java") ||
			!_isLiferayFilter(absolutePath, fileContent)) {

			return javaTerm.getContent();
		}

		_checkMethodCall(fileName, fileContent, "PortalInstances.getCompany");
		_checkMethodCall(fileName, fileContent, "PortalInstances.getCompanyId");
		_checkMethodCall(fileName, fileContent, "PortalUtil.getCompany");
		_checkMethodCall(fileName, fileContent, "PortalUtil.getCompanyId");

		JavaClass javaClass = (JavaClass)javaTerm;

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaVariable()) {
				continue;
			}

			JavaVariable javaVariable = (JavaVariable)childJavaTerm;

			String variableName = javaVariable.getName();

			String variableTypeName = getVariableTypeName(
				javaVariable.getContent(), childJavaTerm, fileContent, fileName,
				variableName, true, false);

			if (!variableTypeName.equals("Portal")) {
				continue;
			}

			_checkMethodCall(
				fileName, fileContent, variableName + ".getCompany");
			_checkMethodCall(
				fileName, fileContent, variableName + ".getCompanyId");
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkMethodCall(
		String fileName, String content, String methodCall) {

		StringBundler sb = new StringBundler(5);

		sb.append("Use \"CompanyThreadLocal.getCompanyId\" ");

		if (methodCall.endsWith("getCompany")) {
			sb.append("and \"CompanyLocalServiceUtil.getCompany\" ");
		}

		sb.append("instead of \"");
		sb.append(methodCall);
		sb.append("\", see LPD-69643");

		int x = -1;

		while (true) {
			x = content.indexOf(methodCall + "(", x + 1);

			if (x == -1) {
				return;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			addMessage(fileName, sb.toString(), getLineNumber(content, x));
		}
	}

	private boolean _isLiferayFilter(String absolutePath, String content) {
		return isDerivedFrom(
			absolutePath, content,
			"com.liferay.portal.kernel.servlet.BaseFilter");
	}

}