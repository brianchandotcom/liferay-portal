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
public class EqualsPoshiElement extends BasePoshiElement {

	public static boolean isElementType(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		if (parentPoshiElement instanceof IfPoshiElement &&
			readableSyntax.contains("==")) {

			return true;
		}

		return false;
	}

	public EqualsPoshiElement() {
	}

	@Override
	public PoshiElement clone(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		if (isElementType(parentPoshiElement, readableSyntax)) {
			return new EqualsPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new EqualsPoshiElement(element);
		}

		return null;
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

	protected EqualsPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected EqualsPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	private static final String _ELEMENT_NAME = "equals";

	static {
		PoshiElementFactory poshiElementFactory = new PoshiElementFactory() {

			@Override
			public BasePoshiElement newPoshiElement(
				BasePoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new EqualsPoshiElement(readableSyntax);
				}

				return null;
			}

			@Override
			public BasePoshiElement newPoshiElement(Element element) {
				if (isElementType(_ELEMENT_NAME, element)) {
					return new EqualsPoshiElement(element);
				}

				return null;
			}

		};

		BasePoshiElement.addPoshiElementFactory(poshiElementFactory);
	}

}