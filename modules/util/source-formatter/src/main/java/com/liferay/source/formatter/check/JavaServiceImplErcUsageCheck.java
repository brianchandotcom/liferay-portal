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

import java.util.List;

/**
 * @author Igor Beslic
 */
public class JavaServiceImplErcUsageCheck extends BaseServiceImplCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = javaTerm.getParentJavaClass();

		String className = javaClass.getName();

		if (className.endsWith("LocalServiceImpl") ||
			!className.endsWith("ServiceImpl")) {

			return javaTerm.getContent();
		}

		List<String> entities = getErcEnabledEntityNodeNames(
			getServiceXmlDocument(absolutePath));

		if (!entities.contains(getEntityName(className))) {
			return javaTerm.getContent();
		}

		return _formatServiceImpl(javaTerm);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatServiceImpl(JavaTerm javaTerm) {
		String javaTermName = javaTerm.getName();

		JavaSignature javaSignature = javaTerm.getSignature();

		String entityReturnType = javaSignature.getReturnType();

		if (!javaTermName.equals("add") &&
			!javaTermName.equals("add" + entityReturnType)) {

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

		javaTermContent = StringUtil.replaceFirst(
			javaTermContent, methodName,
			methodName + "String externalReferenceCode, ");

		return StringUtil.replaceLast(
			javaTermContent, methodName,
			methodName + "\n\t\texternalReferenceCode, ");
	}

}