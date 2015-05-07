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

import com.liferay.poshi.runner.PoshiRunnerContext;
import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.util.HtmlUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.Validator;

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

		StringBuilder sb = new StringBuilder();

		sb.append(
			_getLineNumberItemText(element.attributeValue("line-number")));

		List<Element> childElements = element.elements();

		boolean executingMacro = _isExecutingMacro(element);

		if (!childElements.isEmpty() || executingMacro) {
			sb.append(_getBtnItemText("btn-collapse"));
		}

		if (!childElements.isEmpty() && executingMacro) {
			sb.append(_getBtnItemText("btn-var"));
		}

		btnContainerLoggerElement.setText(sb.toString());

		return btnContainerLoggerElement;
	}

	private static String _getBtnItemText(String className) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("btn " + className);
		loggerElement.setID(null);
		loggerElement.setName("button");

		return loggerElement.toString();
	}

	private static LoggerElement _getChildContainerLoggerElement() {
		return _getChildContainerLoggerElement(null, null);
	}

	private static LoggerElement _getChildContainerLoggerElement(
		Element element) {

		return _getChildContainerLoggerElement(element, null);
	}

	private static LoggerElement _getChildContainerLoggerElement(
		Element element, Element rootElement) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("child-container collapse collapsible");
		loggerElement.setName("ul");

		if (rootElement != null) {
			List<Element> rootVarElements = rootElement.elements("var");

			for (Element rootVarElement : rootVarElements) {
				loggerElement.addChildLoggerElement(
					_getVarLoggerElement(rootVarElement));
			}
		}

		if (element != null) {
			List<Element> childElements = element.elements();

			for (Element childElement : childElements) {
				String childElementName = childElement.getName();

				if (childElementName.equals("description") ||
					childElementName.equals("echo")) {

					loggerElement.addChildLoggerElement(
						_getEchoLoggerElement(childElement));
				}
				else if (childElementName.equals("execute")) {
					if (childElement.attributeValue("function") != null) {
						loggerElement.addChildLoggerElement(
							_getFunctionExecuteLoggerElement(childElement));
					}
					else if (childElement.attributeValue("macro") != null) {
						loggerElement.addChildLoggerElement(
							_getMacroExecuteLoggerElement(
								childElement, "macro"));
					}
					else if (Validator.isNotNull(
								childElement.attributeValue("macro-desktop")) &&
							 Validator.isNull(
								 PropsValues.MOBILE_DEVICE_TYPE)) {

						loggerElement.addChildLoggerElement(
							_getMacroExecuteLoggerElement(
								childElement, "macro-desktop"));
					}
					else if (Validator.isNotNull(
								childElement.attributeValue("macro-mobile")) &&
							 Validator.isNotNull(
								 PropsValues.MOBILE_DEVICE_TYPE)) {

						loggerElement.addChildLoggerElement(
							_getMacroExecuteLoggerElement(
								childElement, "macro-mobile"));
					}
				}
				else if (childElementName.equals("fail")) {
					loggerElement.addChildLoggerElement(
						_getFailLoggerElement(childElement));
				}
				else if (childElementName.equals("for") ||
						 childElementName.equals("task")) {

					loggerElement.addChildLoggerElement(
						_getForLoggerElement(childElement));
				}
				else if (childElementName.equals("if")) {
					loggerElement.addChildLoggerElement(
						_getIfLoggerElement(childElement));
				}
				else if (childElementName.equals("var")) {
					loggerElement.addChildLoggerElement(
						_getVarLoggerElement(childElement));
				}
				else if (childElementName.equals("while")) {
					loggerElement.addChildLoggerElement(
						_getWhileLoggerElement(childElement));
				}
			}
		}

		return loggerElement;
	}

	private static LoggerElement _getClosingLineContainerLoggerElement(
		Element element) {

		LoggerElement closingLineContainerLoggerElement = new LoggerElement();

		closingLineContainerLoggerElement.setClassName("line-container");
		closingLineContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "&lt;/"));
		sb.append(_getLineItemText("action-type", element.getName()));
		sb.append(_getLineItemText("misc", "&gt;"));

		closingLineContainerLoggerElement.setText(sb.toString());

		return closingLineContainerLoggerElement;
	}

	private static LoggerElement _getConditionalLoggerElement(Element element) {
		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"conditional", element);

		List<Element> childElements = element.elements();

		if (!childElements.isEmpty()) {
			LoggerElement childContainerLoggerElement =
				_getChildContainerLoggerElement();

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

	private static LoggerElement _getEchoLoggerElement(Element element) {
		return _getLineGroupLoggerElement("echo", element);
	}

	private static LoggerElement _getFailLoggerElement(Element element) {
		return _getLineGroupLoggerElement(element);
	}

	private static LoggerElement _getForLoggerElement(Element element) {
		return _getLoggerElementFromElement(element);
	}

	private static LoggerElement _getFunctionExecuteLoggerElement(
		Element element) {

		return _getLineGroupLoggerElement("function", element);
	}

	private static LoggerElement _getIfChildContainerLoggerElement(
		Element element) {

		LoggerElement loggerElement = _getChildContainerLoggerElement();

		List<Element> childElements = element.elements();

		Element conditionElement = childElements.get(0);

		loggerElement.addChildLoggerElement(
			_getConditionalLoggerElement(conditionElement));

		Element thenElement = element.element("then");

		loggerElement.addChildLoggerElement(
			_getLoggerElementFromElement(thenElement));

		List<Element> elseIfElements = element.elements("elseif");

		for (Element elseIfElement : elseIfElements) {
			loggerElement.addChildLoggerElement(
				_getIfLoggerElement(elseIfElement));
		}

		Element elseElement = element.element("else");

		if (elseElement != null) {
			loggerElement.addChildLoggerElement(
				_getLoggerElementFromElement(elseElement));
		}

		return loggerElement;
	}

	private static LoggerElement _getIfLoggerElement(Element element) {
		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"conditional", element);

		loggerElement.addChildLoggerElement(
			_getIfChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getLineContainerLoggerElement(
		Element element) {

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");
		lineContainerLoggerElement.setName("div");

		StringBuilder sb = new StringBuilder();

		sb.append(_getLineItemText("misc", "&lt;"));
		sb.append(_getLineItemText("action-type", element.getName()));

		List<Attribute> attributes = element.attributes();

		for (Attribute attribute : attributes) {
			String attributeName = attribute.getName();

			if (attributeName.equals("line-number")) {
				continue;
			}

			sb.append(_getLineItemText("tag-type", attributeName));
			sb.append(_getLineItemText("misc", "="));
			sb.append(_getLineItemText("misc quote", "\""));
			sb.append(_getLineItemText("name", attribute.getValue()));
			sb.append(_getLineItemText("misc quote", "\""));
		}

		List<Element> elements = element.elements();

		String innerText = element.getText();

		innerText = innerText.trim();

		if (elements.isEmpty() && Validator.isNull(innerText)) {
			sb.append(_getLineItemText("misc", "/&gt;"));
		}
		else {
			sb.append(_getLineItemText("misc", "&gt;"));
		}

		if (Validator.isNotNull(innerText)) {
			sb.append(_getLineItemText("name", HtmlUtil.escape(innerText)));
			sb.append(_getLineItemText("misc", "&lt;/"));
			sb.append(_getLineItemText("action-type", element.getName()));
			sb.append(_getLineItemText("misc", "&gt;"));
		}

		lineContainerLoggerElement.setText(sb.toString());

		return lineContainerLoggerElement;
	}

	private static LoggerElement _getLineGroupLoggerElement(Element element) {
		return _getLineGroupLoggerElement(null, element);
	}

	private static LoggerElement _getLineGroupLoggerElement(
		String className, Element element) {

		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-group");
		loggerElement.setName("li");

		if (Validator.isNotNull(className)) {
			loggerElement.addClassName(className);
		}

		loggerElement.addChildLoggerElement(
			_getBtnContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static String _getLineItemText(String className, String text) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName(className);
		loggerElement.setID(null);
		loggerElement.setName("span");
		loggerElement.setText(text);

		return loggerElement.toString();
	}

	private static String _getLineNumberItemText(String lineNumber) {
		LoggerElement loggerElement = new LoggerElement();

		loggerElement.setClassName("line-number");
		loggerElement.setID(null);
		loggerElement.setName("div");
		loggerElement.setText(lineNumber);

		return loggerElement.toString();
	}

	private static LoggerElement _getLoggerElementFromElement(Element element) {
		LoggerElement loggerElement = _getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			_getChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static LoggerElement _getMacroCommandLoggerElement(
		String classCommandName) {

		Element commandElement = PoshiRunnerContext.getMacroCommandElement(
			classCommandName);

		String className =
			PoshiRunnerGetterUtil.getClassNameFromClassCommandName(
				classCommandName);

		Element rootElement = PoshiRunnerContext.getMacroRootElement(className);

		return _getChildContainerLoggerElement(commandElement, rootElement);
	}

	private static LoggerElement _getMacroExecuteLoggerElement(
		Element executeElement, String macroType) {

		LoggerElement loggerElement = _getLineGroupLoggerElement(
			"macro", executeElement);

		String classCommandName = executeElement.attributeValue(macroType);

		loggerElement.addChildLoggerElement(
			_getMacroCommandLoggerElement(classCommandName));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(executeElement));

		return loggerElement;
	}

	private static LoggerElement _getVarLoggerElement(Element element) {
		return _getLineGroupLoggerElement("var", element);
	}

	private static LoggerElement _getWhileLoggerElement(Element element) {
		LoggerElement loggerElement = _getLineGroupLoggerElement(element);

		loggerElement.addChildLoggerElement(
			_getIfChildContainerLoggerElement(element));
		loggerElement.addChildLoggerElement(
			_getClosingLineContainerLoggerElement(element));

		return loggerElement;
	}

	private static boolean _isExecutingMacro(Element element) {
		if ((element.attributeValue("macro") != null) ||
			(element.attributeValue("macro-desktop") != null) ||
			(element.attributeValue("macro-mobile") != null)) {

			return true;
		}

		return false;
	}

}