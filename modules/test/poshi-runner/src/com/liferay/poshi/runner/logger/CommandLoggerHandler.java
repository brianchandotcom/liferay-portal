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

import com.liferay.poshi.runner.util.Validator;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public final class CommandLoggerHandler {

	public static void failCommand(Element element) {
		if (!_isCurrentCommand(element)) {
			return;
		}

		_commandElement = null;
	}

	public static void passCommand(Element element) {
		if (!_isCurrentCommand(element)) {
			return;
		}

		_commandElement = null;
	}

	public static void startCommand(Element element) {
		if (!_isCommand(element)) {
			return;
		}

		_commandElement = element;

		_commandLoggerElement = _getCommandLoggerElement(element);

		_commandLogLoggerElement.addChildLoggerElement(_commandLoggerElement);
	}

	private static LoggerElement _getCommandLoggerElement(Element element) {
		LoggerElement commandLoggerElement = new LoggerElement();

		commandLoggerElement.setClassName("line-group linkable");
		commandLoggerElement.setName("li");

		commandLoggerElement.addChildLoggerElement(
			_getLineContainerLoggerElement(element));

		return commandLoggerElement;
	}

	private static LoggerElement _getLineContainerLoggerElement(
		Element element) {

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setClassName("line-container");

		LoggerElement commandNameLoggerElement = new LoggerElement();

		commandNameLoggerElement.setClassName("command-name");
		commandNameLoggerElement.setName("span");
		commandNameLoggerElement.setText(element.attributeValue("function"));

		lineContainerLoggerElement.addChildLoggerElement(
			commandNameLoggerElement);

		LoggerElement miscLoggerElement = new LoggerElement();

		miscLoggerElement.setClassName("misc");
		miscLoggerElement.setName("span");

		lineContainerLoggerElement.addChildLoggerElement(miscLoggerElement);

		return lineContainerLoggerElement;
	}

	private static boolean _isCommand(Element element) {
		if (!Validator.equals(element.getName(), "execute")) {
			return false;
		}

		if (Validator.isNull(element.attributeValue("function"))) {
			return false;
		}

		if (_commandElement != null) {
			return false;
		}

		return true;
	}

	private static boolean _isCurrentCommand(Element element) {
		return element.equals(_commandElement);
	}

	private static Element _commandElement;
	private static LoggerElement _commandLoggerElement;
	private static final LoggerElement _commandLogLoggerElement =
		new LoggerElement("commandLog");

}