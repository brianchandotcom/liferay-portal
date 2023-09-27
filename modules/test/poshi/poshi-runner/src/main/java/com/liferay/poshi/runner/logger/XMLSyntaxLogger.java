/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.logger;

import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.util.HtmlUtil;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class XMLSyntaxLogger extends SyntaxLogger {

	public XMLSyntaxLogger(String namespacedClassCommandName) throws Exception {
		super(namespacedClassCommandName);
	}

	@Override
	public void updateStatus(Element element, String status) {
		updateElementStatus(element, status);

		String elementName = element.getName();

		if (elementName.equals("else") || elementName.equals("elseif")) {
			Element ifElement = element.getParent();

			updateElementStatus(ifElement, status);
		}

		poshiStackTrace.setCurrentElement(element);
	}

	@Override
	protected LoggerElement getBtnContainerLoggerElement(Element element) {
		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setClassName("btn-container");
		btnContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(
			getLineNumberItemText(PoshiGetterUtil.getLineNumber(element)));

		if (isExecuteChildElementLogged(element)) {
			sb.append(getBtnItemText("btn-collapse"));
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty() && isExecuting(element)) {
			sb.append(getBtnItemText("btn-var"));
		}

		btnContainerLoggerElement.setText(sb.toString());

		return btnContainerLoggerElement;
	}

	@Override
	protected LoggerElement getIfLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(
			"conditional", element);

		loggerElement.addChildLoggerElement(
			_getIfChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

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

		StringBuilder sb = new StringBuilder();

		sb.append(getLineItemText("misc", "&lt;"));
		sb.append(getLineItemText("action-type", element.getName()));

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.equals("line-number")) {
				continue;
			}

			sb.append(getLineItemText("tag-type", attributeName));
			sb.append(getLineItemText("misc", "="));
			sb.append(getLineItemText("misc quote", "\""));
			sb.append(getLineItemText("name", attribute.getValue()));
			sb.append(getLineItemText("misc quote", "\""));
		}

		List<Element> elements = element.elements();

		String innerText = element.getText();

		innerText = innerText.trim();

		if (elements.isEmpty() && Validator.isNull(innerText)) {
			sb.append(getLineItemText("misc", "/&gt;"));
		}
		else {
			sb.append(getLineItemText("misc", "&gt;"));
		}

		if (Validator.isNotNull(innerText)) {
			sb.append(getLineItemText("name", HtmlUtil.escape(innerText)));
			sb.append(getLineItemText("misc", "&lt;/"));
			sb.append(getLineItemText("action-type", element.getName()));
			sb.append(getLineItemText("misc", "&gt;"));
		}

		lineContainerLoggerElement.setText(sb.toString());

		String elementName = element.getName();

		if (elementName.equals("execute") && !elements.isEmpty()) {
			lineContainerLoggerElement.addChildLoggerElement(
				_getParameterContainerLoggerElement(element));
		}

		return lineContainerLoggerElement;
	}

	@Override
	protected LoggerElement getLoggerElementFromElement(Element element)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			getChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	@Override
	protected LoggerElement getWhileLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			_getIfChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private LoggerElement _getClosingLineContainerLoggerElement(
		Element element) {

		LoggerElement closingLineContainerLoggerElement = new LoggerElement();

		closingLineContainerLoggerElement.setClassName("line-container");
		closingLineContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(getLineItemText("misc", "&lt;/"));
		sb.append(getLineItemText("action-type", element.getName()));
		sb.append(getLineItemText("misc", "&gt;"));

		closingLineContainerLoggerElement.setText(sb.toString());

		return closingLineContainerLoggerElement;
	}

	private LoggerElement _getConditionalLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = null;

		if (isExecutingFunction(element)) {
			loggerElement = getLineGroupLoggerElement(
				"conditional-function", element);
		}
		else {
			loggerElement = getLineGroupLoggerElement("conditional", element);
		}

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			LoggerElement childContainerLoggerElement =
				getChildContainerLoggerElement();

			for (Element childElement : childElements) {
				childContainerLoggerElement.addChildLoggerElement(
					_getConditionalLoggerElement(childElement));
			}

			loggerElement.addChildLoggerElement(childContainerLoggerElement);
			loggerElement.addChildLoggerElement(
				_getClosingLineContainerLoggerElement(element));
		}

		return loggerElement;
	}

	private LoggerElement _getIfChildContainerLoggerElement(Element element)
		throws Exception {

		LoggerElement loggerElement = getChildContainerLoggerElement();

		List<Element> childElements = element.elements();

		Element conditionElement = childElements.get(0);

		loggerElement.addChildLoggerElement(
			_getConditionalLoggerElement(conditionElement));

		Element thenElement = element.element("then");

		loggerElement.addChildLoggerElement(
			getLoggerElementFromElement(thenElement));

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

	private LoggerElement _getParameterContainerLoggerElement(Element element) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setAttribute(
			"data-btnlinkid", "var-" + getBtnLinkVarId());
		loggerElement.setClassName(
			"child-container collapse parameter-container");
		loggerElement.setID(null);
		loggerElement.setName("div");

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			loggerElement.addChildLoggerElement(
				getLineNumberItem(PoshiGetterUtil.getLineNumber(childElement)));
			loggerElement.addChildLoggerElement(
				getLineContainerLoggerElement(childElement));
		}

		return loggerElement;
	}

}