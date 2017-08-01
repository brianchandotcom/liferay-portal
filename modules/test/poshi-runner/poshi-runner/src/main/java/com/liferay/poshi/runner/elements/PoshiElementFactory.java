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

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.FileUtil;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactory {

	public static PoshiElement newPoshiElement(Element element) {
		PoshiElement[] poshiElements = {
			new CommandElement(element), new ConditionElement(element),
			new DefinitionElement(element), new DescriptionElement(element),
			new ElseElement(element), new EqualsElement(element),
			new ExecuteElement(element), new ForElement(element),
			new IfElement(element), new IssetElement(element),
			new PropertyElement(element), new ReturnElement(element),
			new SetUpElement(element), new TearDownElement(element),
			new ThenElement(element), new VarElement(element),
			new WhileElement(element)
		};

		for (PoshiElement poshiElement : poshiElements) {
			String poshiElementName = poshiElement.getName();

			if (!poshiElementName.equals("unsupported")) {
				return poshiElement;
			}
		}

		return new UnsupportedElement(element);
	}

	public static PoshiElement newPoshiElement(String readableSyntax) {
		PoshiElement[] poshiElements = {
			new CommandElement(readableSyntax),
			new DefinitionElement(readableSyntax),
			new ExecuteElement(readableSyntax), new ForElement(readableSyntax),
			new IfElement(readableSyntax), new PropertyElement(readableSyntax),
			new SetUpElement(readableSyntax),
			new TearDownElement(readableSyntax), new VarElement(readableSyntax),
			new WhileElement(readableSyntax)
		};

		for (PoshiElement poshiElement : poshiElements) {
			String poshiElementName = poshiElement.getName();

			if (!poshiElementName.equals("unsupported")) {
				return poshiElement;
			}
		}

		return new UnsupportedElement(readableSyntax);
	}

	public static PoshiElement newPoshiElementFromFile(String filePath) {
		File file = new File(filePath);

		try {
			String fileContent = FileUtil.read(file);

			if (fileContent.contains("<definition")) {
				Document document = Dom4JUtil.parse(fileContent);

				Element rootElement = document.getRootElement();

				return newPoshiElement(rootElement);
			}

			return newPoshiElement(fileContent);
		}
		catch (Exception e) {
			System.out.println("Unable to generate the Poshi element");

			e.printStackTrace();
		}

		return null;
	}

}