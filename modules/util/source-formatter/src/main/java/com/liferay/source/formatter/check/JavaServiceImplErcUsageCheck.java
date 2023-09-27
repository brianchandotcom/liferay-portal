/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;

import org.dom4j.DocumentException;

/**
 * @author Carlos Correa
 * @author Igor Beslic
 */
public class JavaServiceImplErcUsageCheck extends BaseServiceImplCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws DocumentException, IOException {

		JavaClass javaClass = javaTerm.getParentJavaClass();

		if (javaClass.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		String className = javaClass.getName();

		if (className.endsWith("LocalServiceImpl") ||
			!className.endsWith("ServiceImpl")) {

			return javaTerm.getContent();
		}

		List<String> ercEnabledEntityNames = getErcEnabledEntityNames(
			getServiceXmlDocument(absolutePath));

		String entityName = getEntityName(className);

		if (!ercEnabledEntityNames.contains(entityName)) {
			return javaTerm.getContent();
		}

		return _formatServiceImpl(entityName, javaTerm);
	}

	private String _formatServiceImpl(String entityName, JavaTerm javaTerm) {
		String javaTermName = javaTerm.getName();

		JavaSignature javaSignature = javaTerm.getSignature();

		String entityReturnType = javaSignature.getReturnType();

		if (!isApplicableCheck(entityName, entityReturnType, javaTermName)) {
			return javaTerm.getContent();
		}

		String javaTermContent = javaTerm.getContent();

		String entityLocalServiceName =
			StringUtil.lowerCaseFirstLetter(entityReturnType) + "LocalService.";

		if (!javaTermContent.contains(entityLocalServiceName)) {
			return javaTermContent;
		}

		for (JavaParameter parameter : javaSignature.getParameters()) {
			if (StringUtil.equals(
					parameter.getParameterName(), "externalReferenceCode") &&
				StringUtil.equals(parameter.getParameterType(), "String")) {

				return javaTermContent;
			}
		}

		String methodName = javaTermName + StringPool.OPEN_PARENTHESIS;

		int x = -1;

		while (true) {
			x = javaTermContent.indexOf(methodName, x + 1);

			if (x == -1) {
				break;
			}

			if (!isInsideComment(javaTermContent, x)) {
				javaTermContent = StringUtil.insert(
					javaTermContent, "String externalReferenceCode, ",
					methodName.length() + x);

				break;
			}
		}

		String lastMethodNameContent = StringUtil.extractLast(
			javaTermContent, methodName);

		if (lastMethodNameContent.contains("externalReferenceCode,")) {
			return javaTermContent;
		}

		return StringUtil.replaceLast(
			javaTermContent, methodName,
			methodName + "\n\t\texternalReferenceCode, ");
	}

}