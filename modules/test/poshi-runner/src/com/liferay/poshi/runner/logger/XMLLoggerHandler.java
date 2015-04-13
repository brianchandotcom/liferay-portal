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
		LoggerElement xmlLogElement = new LoggerElement();

		xmlLogElement.setName("li");
		xmlLogElement.setClassName("header");

		LoggerElement btnContainerElement = new LoggerElement();

		btnContainerElement.setName("div");
		btnContainerElement.setClassName("btn-container");

		LoggerElement btnElement = new LoggerElement();

		btnElement.setName("button");
		btnElement.setClassName("btn btn-collapse");

		btnContainerElement.addChildLoggerElement(btnElement);

		xmlLogElement.addChildLoggerElement(btnContainerElement);

		LoggerElement lineContainerElement = new LoggerElement();

		lineContainerElement.setName("div");
		lineContainerElement.setClassName("line-container");

		LoggerElement lineElement = new LoggerElement();

		lineElement.setName("h3");
		lineElement.setClassName("testCaseCommand");
		lineElement.setText(classCommandName);

		lineContainerElement.addChildLoggerElement(lineElement);

		xmlLogElement.addChildLoggerElement(lineContainerElement);
	}

}