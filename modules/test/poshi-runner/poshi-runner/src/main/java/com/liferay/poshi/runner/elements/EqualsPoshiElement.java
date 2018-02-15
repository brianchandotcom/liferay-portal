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
public class EqualsPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new EqualsPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new EqualsPoshiElement(readableSyntax);
		}

		return null;
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

	protected EqualsPoshiElement() {
	}

	protected EqualsPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected EqualsPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "equals";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (!isConditionValidInParent(parentPoshiElement)) {
			return false;
		}

		if (readableSyntax.contains(" && ") ||
			readableSyntax.contains(" || ") ||
			readableSyntax.startsWith("!(") ||
			readableSyntax.startsWith("else if (")) {

			return false;
		}

		if (readableSyntax.contains("==")) {
			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "equals";

}