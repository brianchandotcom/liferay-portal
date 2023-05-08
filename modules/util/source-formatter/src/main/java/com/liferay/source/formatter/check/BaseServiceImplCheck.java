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

import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Igor Beslic
 */
public abstract class BaseServiceImplCheck extends BaseJavaTermCheck {

	protected String getEntityName(String className) {
		if (className.indexOf("LocalServiceImpl") > 0) {
			return className.substring(
				0, className.indexOf("LocalServiceImpl"));
		}

		return className.substring(0, className.indexOf("ServiceImpl"));
	}

	protected List<String> getErcEnabledEntityNames(Document document) {
		if (document == null) {
			return Collections.emptyList();
		}

		Element serviceXMLElement = document.getRootElement();

		Iterator<Element> iterator = serviceXMLElement.elementIterator(
			"entity");

		List<String> ercEnabledEntityNames = new ArrayList<>();

		while (iterator.hasNext()) {
			Element element = iterator.next();

			if (element.attributeValue("external-reference-code") != null) {
				ercEnabledEntityNames.add(element.attributeValue("name"));
			}
		}

		return ercEnabledEntityNames;
	}

	protected Document getServiceXmlDocument(String absolutePath)
		throws DocumentException, IOException {

		Path serviceXmlPath = Paths.get(absolutePath);

		do {
			serviceXmlPath = serviceXmlPath.getParent();
		}
		while (!serviceXmlPath.endsWith("src"));

		serviceXmlPath = serviceXmlPath.getParent();

		serviceXmlPath = serviceXmlPath.resolve("service.xml");

		return SourceUtil.readXML(FileUtil.read(serviceXmlPath.toFile()));
	}

}