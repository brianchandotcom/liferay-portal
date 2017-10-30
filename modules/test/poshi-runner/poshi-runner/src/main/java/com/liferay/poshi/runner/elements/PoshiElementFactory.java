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

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class PoshiElementFactory {

	public static PoshiElement newPoshiElement(Element element) {
		for (PoshiElement poshiElement : _poshiElements) {
			PoshiElement newPoshiElement = poshiElement.clone(element);

			if (newPoshiElement != null) {
				return newPoshiElement;
			}
		}

		String xml = null;

		try {
			xml = Dom4JUtil.format(element);
		}
		catch (IOException ioe) {
			xml = element.toString();
		}

		throw new RuntimeException("Unknown element\n" + xml);
	}

	public static PoshiElement newPoshiElement(
		PoshiElement parentPoshiElement, String readableSyntax) {

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

	public static PoshiElement newPoshiElementFromFile(String filePath) {
		File file = new File(filePath);

		try {
			String content = FileUtil.read(file);

			if (content.contains("<definition")) {
				Document document = Dom4JUtil.parse(content);

				Element rootElement = document.getRootElement();

				return newPoshiElement(rootElement);
			}

			return newPoshiElement(null, content);
		}
		catch (Exception e) {
			System.out.println("Unable to generate the Poshi element");

			e.printStackTrace();
		}

		return null;
	}

	private static final List<PoshiElement> _poshiElements = new ArrayList<>();

	static {
		try {
			URL url = BasePoshiElement.class.getResource(
				BasePoshiElement.class.getSimpleName() + ".class");

			File basePoshiElementClassFile = new File(url.toURI());

			File dir = basePoshiElementClassFile.getParentFile();

			List<File> dirFiles = Arrays.asList(dir.listFiles());

			Collections.sort(dirFiles);

			for (File file : dirFiles) {
				String fileName = file.getName();

				if (fileName.endsWith(
						BasePoshiElement.class.getSimpleName() + ".class")) {

					continue;
				}

				if (!fileName.endsWith("PoshiElement.class")) {
					continue;
				}

				Package pkg = BasePoshiElement.class.getPackage();

				int index = fileName.indexOf(".");

				String className = fileName.substring(0, index);

				Class<?> clazz = Class.forName(pkg.getName() + "." + className);

				if (BasePoshiElement.class.isAssignableFrom(clazz)) {
					PoshiElement poshiElement =
						(PoshiElement)clazz.newInstance();

					_poshiElements.add(poshiElement);
				}
			}
		}
		catch (ClassNotFoundException | IllegalAccessException |
			   InstantiationException | URISyntaxException e) {

			throw new RuntimeException(e);
		}
	}

}