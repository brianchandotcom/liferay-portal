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
public class IsSetPoshiElement extends BasePoshiElement {

	public static boolean isElementType(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		if (parentPoshiElement instanceof IfPoshiElement &&
			readableSyntax.startsWith("isSet(")) {

			return true;
		}

		return false;
	}

	public IsSetPoshiElement() {
	}

	@Override
	public PoshiElement clone(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		if (isElementType(parentPoshiElement, readableSyntax)) {
			return new IsSetPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new IsSetPoshiElement(element);
		}

		return null;
	}

	@Override
	public String getBlockName() {
		return "isSet";
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		String issetContent = getParentheticalContent(readableSyntax);

		addAttribute("var", issetContent);
	}

	@Override
	public String toReadableSyntax() {
		return "isSet(" + attributeValue("var") + ")";
	}

	protected IsSetPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected IsSetPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	private static final String _ELEMENT_NAME = "isset";

}