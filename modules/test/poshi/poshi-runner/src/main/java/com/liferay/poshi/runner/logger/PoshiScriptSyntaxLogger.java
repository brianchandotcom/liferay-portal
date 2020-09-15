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

import com.liferay.poshi.runner.elements.PoshiElement;
import com.liferay.poshi.runner.util.StringUtil;

import java.util.List;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class PoshiScriptSyntaxLogger extends SyntaxLogger {

	public PoshiScriptSyntaxLogger(String namespacedClassCommandName)
		throws Exception {

		generateSyntaxLog(namespacedClassCommandName);
	}

	@Override
	public void updateStatus(Element element, String status) {
		String elementName = element.getName();

		if (!elementName.equals("then")) {
			updateElementStatus(element, status);
		}
	}

	protected LoggerElement getClosingLineContainerLoggerElement() {
		LoggerElement closingLineContainerLoggerElement = new LoggerElement();

		closingLineContainerLoggerElement.setClassName("line-container");
		closingLineContainerLoggerElement.setName("div");
		closingLineContainerLoggerElement.setText("}");

		return closingLineContainerLoggerElement;
	}

	@Override
	protected LoggerElement getIfLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setName("div");

		LoggerElement ifLoggerElement = getLineGroupLoggerElement(
			"conditional", element);

		ifLoggerElement.addChildLoggerElement(
			getChildContainerLoggerElement(element.element("then")));
		ifLoggerElement.addChildLoggerElement(
			getClosingLineContainerLoggerElement());

		loggerElement.addChildLoggerElement(ifLoggerElement);

		List<Element> elseIfElements = element.elements("elseif");

		for (Element elseIfElement : elseIfElements) {
			loggerElement.addChildLoggerElement(
				getIfLoggerElement(elseIfElement));
		}

		Element elseElement = element.element("else");

		if (elseElement != null) {
			loggerElement.addChildLoggerElement(
				getLoggerElementFromElement(elseElement));
		}

		return loggerElement;
	}

	@Override
	protected LoggerElement getLineContainerLoggerElement(Element element) {
		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		if (element.attributeValue("macro") != null) {
			lineContainerLoggerElement.setAttribute(
				"onmouseout", "macroHover(this, false)");
			lineContainerLoggerElement.setAttribute(
				"onmouseover", "macroHover(this, true)");
		}

		PoshiElement poshiElement = (PoshiElement)element;

		String logStatement = StringUtil.trim(
			poshiElement.getPoshiLogDescriptor());

		String name = element.getName();

		List<PoshiElement> elements = poshiElement.toPoshiElements(
			element.elements());

		if (!name.equals("execute") && !elements.isEmpty()) {
			logStatement += " {";
		}

		lineContainerLoggerElement.setText(
			getLineItemText("name", logStatement));

		return lineContainerLoggerElement;
	}

	@Override
	protected LoggerElement getLoggerElementFromElement(Element element)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			getChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			getClosingLineContainerLoggerElement());

		return loggerElement;
	}

	@Override
	protected LoggerElement getWhileLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			getChildContainerLoggerElement(element.element("then")));
		loggerElement.addChildLoggerElement(
			getClosingLineContainerLoggerElement());

		return loggerElement;
	}

}