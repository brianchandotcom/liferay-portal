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

package com.liferay.poshi.runner.elements;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactory {

	public static Element newPoshiElement(Element element) {
		String elementName = element.getName();

		if (elementName.equals("command")) {
			return new CommandElement(element);
		}

		if (elementName.equals("definition")) {
			return new DefinitionElement(element);
		}

		if (elementName.equals("execute")) {
			return new ExecuteElement(element);
		}

		if (elementName.equals("property")) {
			return new PropertyElement(element);
		}

		if (elementName.equals("set-up")) {
			return new SetUpElement(element);
		}

		if (elementName.equals("tear-down")) {
			return new TearDownElement(element);
		}

		if (elementName.equals("var")) {
			return new VarElement(element);
		}

		return new UnsupportedElement(element);
	}

}