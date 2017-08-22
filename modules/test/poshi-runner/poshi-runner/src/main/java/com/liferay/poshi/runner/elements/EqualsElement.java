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
public class EqualsElement extends PoshiElement {

	public static final String ELEMENT_NAME = "equals";

	static {
		PoshiElementFactory poshiElementFactory = new PoshiElementFactory() {

			@Override
			public PoshiElement newPoshiElement(Element element) {
				if (isElementType(ELEMENT_NAME, element)) {
					return new EqualsElement(element);
				}

				return null;
			}

			@Override
			public PoshiElement newPoshiElement(
				PoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new EqualsElement(readableSyntax);
				}

				return null;
			}

		};

		PoshiElement.addPoshiElementFactory(poshiElementFactory);
	}

	public static boolean isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (parentPoshiElement instanceof IfElement &&
			readableSyntax.contains("==")) {

			return true;
		}

		return false;
	}

	@Override
	public String getBlockName() {
		return "equals";
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		String[] equalsContentArray = readableSyntax.split("==");

		String arg1 = equalsContentArray[0].trim();

		arg1 = getQuotedContent(arg1);

		addAttribute("arg1", arg1);

		String arg2 = equalsContentArray[1].trim();

		arg2 = getQuotedContent(arg2);

		addAttribute("arg2", arg2);
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\"");
		sb.append(attributeValue("arg1"));
		sb.append("\" == \"");
		sb.append(attributeValue("arg2"));
		sb.append("\"");

		return sb.toString();
	}

	protected EqualsElement(Element element) {
		super(ELEMENT_NAME, element);
	}

	protected EqualsElement(String readableSyntax) {
		super(ELEMENT_NAME, readableSyntax);
	}

}