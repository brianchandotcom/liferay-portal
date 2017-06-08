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

/**
 * @author Michael Hashimoto
 */
public final class XMLLoggerHandler {

	public static void generateXMLLog(String classCommandName) {
		LoggerElement xmlLoggerElement = new LoggerElement();

		xmlLoggerElement.setName("li");
		xmlLoggerElement.setClassName("header");

		LoggerElement btnContainerLoggerElement = new LoggerElement();

		btnContainerLoggerElement.setName("div");
		btnContainerLoggerElement.setClassName("btn-container");

		LoggerElement btnLoggerElement = new LoggerElement();

		btnLoggerElement.setName("button");
		btnLoggerElement.setClassName("btn btn-collapse");

		btnContainerLoggerElement.addChildLoggerElement(btnLoggerElement);

		xmlLoggerElement.addChildLoggerElement(btnContainerLoggerElement);

		LoggerElement lineContainerLoggerElement = new LoggerElement();

		lineContainerLoggerElement.setName("div");
		lineContainerLoggerElement.setClassName("line-container");

		LoggerElement lineLoggerElement = new LoggerElement();

		lineLoggerElement.setName("h3");
		lineLoggerElement.setClassName("test-case-command");
		lineLoggerElement.setText(classCommandName);

		lineContainerLoggerElement.addChildLoggerElement(lineLoggerElement);

		xmlLoggerElement.addChildLoggerElement(lineContainerLoggerElement);
	}

}