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
public class TearDownElement extends CommandElement {

	public static final String ELEMENT_NAME = "tear-down";

	static {
		PoshiElementFactory tearDownElementFactory = new PoshiElementFactory() {

			@Override
			public PoshiElement newPoshiElement(Element element) {
				if (isElementType(ELEMENT_NAME, element)) {
					return new TearDownElement(element);
				}

				return null;
			}

			@Override
			public PoshiElement newPoshiElement(
				PoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new TearDownElement(readableSyntax);
				}

				return null;
			}

		};

		PoshiElement.addPoshiElementFactory(tearDownElementFactory);
	}

	public static boolean isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		for (String line : readableSyntax.split("\n")) {
			line = line.trim();

			if (line.startsWith("@")) {
				continue;
			}

			if (!line.equals("tearDown {")) {
				return false;
			}

			break;
		}

		return true;
	}

	protected TearDownElement(Element element) {
		super(ELEMENT_NAME, element);
	}

	protected TearDownElement(String readableSyntax) {
		super(ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getReadableCommandTitle() {
		return "tearDown";
	}

}