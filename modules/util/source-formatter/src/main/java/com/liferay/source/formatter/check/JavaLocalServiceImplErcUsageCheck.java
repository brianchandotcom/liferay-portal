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
public class JavaLocalServiceImplErcUsageCheck extends BaseServiceImplCheck {

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

		if (!className.endsWith("LocalServiceImpl")) {
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

		String entityVariableName = StringUtil.lowerCaseFirstLetter(
			entityReturnType);

		if (javaTermContent.contains(
				entityVariableName + ".setExternalReferenceCode(")) {

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

		x = -1;

		while (true) {
			x = javaTermContent.indexOf(entityVariableName + ".set", x + 1);

			if (x == -1) {
				break;
			}

			if (!isInsideComment(javaTermContent, x)) {
				javaTermContent = StringUtil.insert(
					javaTermContent,
					entityVariableName +
						".setExternalReferenceCode(externalReferenceCode);\n",
					x);

				break;
			}
		}

		return javaTermContent;
	}

}