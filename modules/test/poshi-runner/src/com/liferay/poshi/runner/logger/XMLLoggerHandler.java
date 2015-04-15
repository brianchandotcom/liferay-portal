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

package com.liferay.poshi.runner.logger;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class XMLLoggerHandler {

	public static void generateXMLLog(String classCommandName) {
		LoggerElement xmlLoggerElement = new LoggerElement();

		xmlLoggerElement.setClassName("header");
		xmlLoggerElement.setName("li");

		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		LoggerElement btnLoggerElement = new LoggerElement();

		btnLoggerElement.setClassName("btn btn-collapse");
		btnLoggerElement.setName("button");

		btnContainerLoggerElement.addChildLoggerElement(btnLoggerElement);

		xmlLoggerElement.addChildLoggerElement(btnContainerLoggerElement);

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		LoggerElement lineLoggerElement = new LoggerElement();

		lineLoggerElement.setClassName("test-case-command");
		lineLoggerElement.setName("h3");
		lineLoggerElement.setText(classCommandName);

		lineContainerLoggerElement.addChildLoggerElement(lineLoggerElement);

		xmlLoggerElement.addChildLoggerElement(lineContainerLoggerElement);
	}

	private static LoggerElement _getBtnContainerLoggerElement(
		Element element) {

		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		LoggerElement lineNumberLoggerElement = new LoggerElement();

		lineNumberLoggerElement.setClassName("line-number");
		lineNumberLoggerElement.setName("div");
		lineNumberLoggerElement.setText(element.attributeValue("line-number"));

		btnContainerLoggerElement.addChildLoggerElement(
			lineNumberLoggerElement);

		return btnContainerLoggerElement;
	}

	private static LoggerElement _getChildContainerLoggerElement() {
		LoggerElement childContainerLoggerElement = new LoggerElement();

		childContainerLoggerElement.setClassName(
			"child-container collapse collapsible");
		childContainerLoggerElement.setName("ul");

		return childContainerLoggerElement;
	}

	private static LoggerElement _getLineContainerLoggerElement(
		Element element) {

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		lineContainerLoggerElement.addChildLoggerElement(
			_getLineLoggerElement("misc", "&lt;"));

		lineContainerLoggerElement.addChildLoggerElement(
			_getLineLoggerElement("action-type", element.getName()));

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.equals("line-number")) {
				continue;
			}

			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("tag-type", attributeName));

			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("misc", "="));

			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("misc quote", "\""));

			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("name", attribute.getValue()));

			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("misc quote", "\""));
		}

		List<Element> elements = element.elements();

		if (elements.isEmpty()) {
			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("misc", "/&gt;"));
		}
		else {
			lineContainerLoggerElement.addChildLoggerElement(
				_getLineLoggerElement("misc", "&gt;"));
		}

		return lineContainerLoggerElement;
	}

	private static LoggerElement _getLineLoggerElement(
		String className, String text) {

		LoggerElement lineLoggerElement = new LoggerElement();

		lineLoggerElement.setClassName(className);
		lineLoggerElement.setName("span");
		lineLoggerElement.setText(text);

		return lineLoggerElement;
	}

}