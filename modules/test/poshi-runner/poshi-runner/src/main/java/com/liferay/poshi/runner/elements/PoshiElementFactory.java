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

	public static PoshiElement newPoshiElement(String readableSyntax) {
		try (BufferedReader bufferedReader = new BufferedReader(
				new StringReader(readableSyntax))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.length() == 0) {
					continue;
				}

				if (line.endsWith(");")) {
					return new ExecuteElement(readableSyntax);
				}

				if (line.startsWith("@")) {
					continue;
				}

				if (line.startsWith("definition {")) {
					return new DefinitionElement(readableSyntax);
				}

				if (line.startsWith("property ")) {
					return new PropertyElement(readableSyntax);
				}

				if (line.startsWith("setUp {")) {
					return new SetUpElement(readableSyntax);
				}

				if (line.startsWith("tearDown {")) {
					return new TearDownElement(readableSyntax);
				}

				if (line.startsWith("test") && line.endsWith(" {")) {
					return new CommandElement(readableSyntax);
				}

				if (line.startsWith("var ")) {
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