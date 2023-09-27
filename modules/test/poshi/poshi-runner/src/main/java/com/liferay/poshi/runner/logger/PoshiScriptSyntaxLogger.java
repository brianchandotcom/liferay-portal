/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.logger;

import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.util.StringUtil;

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