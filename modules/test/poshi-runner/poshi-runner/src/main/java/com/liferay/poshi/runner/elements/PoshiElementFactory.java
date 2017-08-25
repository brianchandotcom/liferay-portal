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
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactory {

	public static PoshiElement newPoshiElement(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		for (PoshiElement poshiElement : _poshiElements) {
			PoshiElement newPoshiElement = poshiElement.clone(
				parentPoshiElement, readableSyntax);

			if (newPoshiElement != null) {
				return newPoshiElement;
			}
		}

		throw new RuntimeException(
			"Unknown readable syntax\n" + readableSyntax);
	}

	public static PoshiElement newPoshiElement(Element element) {
		for (PoshiElement poshiElement : _poshiElements) {
			PoshiElement newPoshiElement = poshiElement.clone(element);

			if (newPoshiElement != null) {
				return newPoshiElement;
			}
		}

		String formattedElement = null;

		try {
			formattedElement = Dom4JUtil.format(element);
		}
		catch (IOException ioe) {
			formattedElement = element.toString();
		}

		throw new RuntimeException("Unknown element\n" + formattedElement);
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

			return newPoshiElement(null, fileContent);
		}
		catch (Exception e) {
			System.out.println("Unable to generate the Poshi element");

			e.printStackTrace();
		}

		return null;
	}

	private static final List<PoshiElement> _poshiElements = new ArrayList<>();

	static {
		String path = "com.liferay.poshi.runner.elements";

		try {
			Class<?> basePoshiElementClass = Class.forName(
				path + ".BasePoshiElement");

			File directory = new File(
				basePoshiElementClass.getResource("").getPath());

			for (File file : directory.listFiles()) {
				String fileName = file.getName();

				if (fileName.contains("PoshiElement.class") &&
					!fileName.contains("Base")) {

					int index = fileName.indexOf(".");

					String className = fileName.substring(0, index);

					Class<?> clazz = Class.forName(path + "." + className);

					if (basePoshiElementClass.isAssignableFrom(clazz)) {
						PoshiElement poshiElement =
							(PoshiElement)clazz.newInstance();

						_poshiElements.add(poshiElement);
					}
				}
			}
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
		catch (InstantiationException ie) {
			throw new RuntimeException(ie);
		}
		catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
	}

}