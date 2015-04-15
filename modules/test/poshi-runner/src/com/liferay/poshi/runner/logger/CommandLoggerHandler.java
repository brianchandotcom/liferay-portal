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

	public static void failCommandLine(Element element) {
		if (!_isCurrentCommandLine(element)) {
			return;
		}

		_commandLineElement = null;
	}

	public static void passCommandLine(Element element) {
		if (!_isCurrentCommandLine(element)) {
			return;
		}

		_commandLineElement = null;
	}

	public static void startCommandLine(Element element) {
		if (!_isCommandLine(element)) {
			return;
		}

		_commandLineElement = element;
	}

	private static boolean _isCommandLine(Element element) {
		if (!Validator.equals(element.getName(), "execute")) {
			return false;
		}

		if (Validator.isNull(element.attributeValue("function"))) {
			return false;
		}

		if (_commandLineElement != null) {
			return false;
		}

		return true;
	}

	private static boolean _isCurrentCommandLine(Element element) {
		if (element == _commandLineElement) {
			return true;
		}

		return false;
	}

	private static Element _commandLineElement = null;
	private static final LoggerElement _commandLogLoggerElement =
		new LoggerElement("commandLog");

}