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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassType;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import org.dom4j.Document;
import org.dom4j.Element;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import java.nio.file.Path;

/**
 * @author Igor Beslic
 */
public class JavaServiceImplErcUsageCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = javaTerm.getParentJavaClass();

		String className = javaClass.getName();

		if (className.endsWith("ServiceImpl")) {
			List<String> entities = _getErcEnabledEntityNodeNames(_getServiceXmlDocument(absolutePath));

			if (!entities.contains(javaTerm.getName())) {
				return javaTerm.getContent();
			}

			return _formatServiceImpl(javaTerm);
		}

		return javaTerm.getContent();
	}

	private List<String> _getErcEnabledEntityNodeNames(Document document) {
		if (document == null) {
			return Collections.emptyList();
		}

		Element serviceXMLElement = document.getRootElement();

		Iterator<Element> iterator = serviceXMLElement.elementIterator("entity");

		List<String> entities = new ArrayList<>();

		while (iterator.hasNext()) {
			Element element = iterator.next();

			if (element.attributeValue("external-reference-code") != null) {
				entities.add(element.attributeValue("external-reference-code"));
			}
		}

		return entities;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private Document _getServiceXmlDocument(String absolutePath)  {
		Path serviceXmlPath = Paths.get(absolutePath, "service.xml");

		try {
			return SourceUtil.readXML(FileUtil.read(serviceXmlPath.toFile()));
		}
		catch (Exception exception) {
			addMessage("service.xml", exception.getMessage());
		}

		return null;
	}

	private String _formatServiceImpl(JavaTerm javaTerm) {
		String javaTermContent = javaTerm.getContent();
		String javaTermName = javaTerm.getName();

		JavaSignature javaSignature = javaTerm.getSignature();

		String entityReturnType = javaSignature.getReturnType();

		System.out.println(
			StringBundler.concat(
				"Checking ", javaTermName, ", returns ", entityReturnType));

		if (!javaTermName.equals("add") &&
			!javaTermName.equals("add" + entityReturnType)) {

			System.out.println("Skipping");

			return javaTermContent;
		}

		String entityVariableName = StringUtil.lowerCaseFirstLetter(
			entityReturnType);

		List<JavaParameter> parameters = javaSignature.getParameters();

		String setExternalReferenceCodeCall = "setExternalReferenceCode";

		System.out.println(
			StringBundler.concat(
				"Checking ", javaTermName, ", returns ", entityReturnType,
				", and uses ", entityVariableName));

		if (parameters.contains(_externalReferenceCodeJavaParameter) ||
			javaTermContent.contains(setExternalReferenceCodeCall)) {

			return javaTermContent;
		}

		String indent = SourceUtil.getIndent(javaTermContent) + "\t";

		return StringUtil.replaceFirst(
			javaTermContent, entityVariableName + ".set",
			StringBundler.concat(
				entityVariableName,
				".setExternalReferenceCode(externalReferenceCode);\n", indent,
				entityVariableName, ".set"));
	}

	private static final JavaParameter _externalReferenceCodeJavaParameter =
		new JavaParameter(
			"externalReferenceCode",
			new JavaClassType("String", "java.lang", Collections.emptyList()),
			Collections.emptySet(), false);

}