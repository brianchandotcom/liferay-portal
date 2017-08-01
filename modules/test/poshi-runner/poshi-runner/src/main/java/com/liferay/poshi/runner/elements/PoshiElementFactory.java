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

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactory {

	public static PoshiElement newPoshiElement(Element element) {
		String elementName = element.getName();

		if (elementName.equals("command")) {
			return new CommandElement(element);
		}

		if (elementName.equals("condition")) {
			return new ConditionElement(element);
		}

		if (elementName.equals("definition")) {
			return new DefinitionElement(element);
		}

		if (elementName.equals("description")) {
			return new DescriptionElement(element);
		}

		if (elementName.equals("else")) {
			return new ElseElement(element);
		}

		if (elementName.equals("equals")) {
			return new EqualsElement(element);
		}

		if (elementName.equals("execute")) {
			return new ExecuteElement(element);
		}

		if (elementName.equals("for")) {
			return new ForElement(element);
		}

		if (elementName.equals("if")) {
			return new IfElement(element);
		}

		if (elementName.equals("isset")) {
			return new IssetElement(element);
		}

		if (elementName.equals("property")) {
			return new PropertyElement(element);
		}

		if (elementName.equals("return")) {
			return new ReturnElement(element);
		}

		if (elementName.equals("set-up")) {
			return new SetUpElement(element);
		}

		if (elementName.equals("tear-down")) {
			return new TearDownElement(element);
		}

		if (elementName.equals("then")) {
			return new ThenElement(element);
		}

		if (elementName.equals("var")) {
			return new VarElement(element);
		}

		if (elementName.equals("while")) {
			return new WhileElement(element);
		}

		return new UnsupportedElement(element);
	}

	public static PoshiElement newPoshiElement(String readableSyntax) {
		try (BufferedReader bufferedReader = new BufferedReader(
				new StringReader(readableSyntax))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.length() == 0) {
					continue;
				}

				if (ExecuteElement.isElementType(readableSyntax)) {
					return new ExecuteElement(readableSyntax);
				}

				if (line.startsWith("@")) {
					continue;
				}

				if (DefinitionElement.isElementType(readableSyntax)) {
					return new DefinitionElement(readableSyntax);
				}

				if (ForElement.isElementType(readableSyntax)) {
					return new ForElement(readableSyntax);
				}

				if (IfElement.isElementType(readableSyntax)) {
					return new IfElement(readableSyntax);
				}

				if (WhileElement.isElementType(readableSyntax)) {
					return new WhileElement(readableSyntax);
				}

				if (PropertyElement.isElementType(readableSyntax)) {
					return new PropertyElement(readableSyntax);
				}

				if (SetUpElement.isElementType(readableSyntax)) {
					return new SetUpElement(readableSyntax);
				}

				if (TearDownElement.isElementType(readableSyntax)) {
					return new TearDownElement(readableSyntax);
				}

				if (CommandElement.isElementType(readableSyntax)) {
					return new CommandElement(readableSyntax);
				}

				if (VarElement.isElementType(readableSyntax)) {
					return new VarElement(readableSyntax);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Unable to generate the Poshi element");

			e.printStackTrace();
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