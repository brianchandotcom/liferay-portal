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
public class DescriptionPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new DescriptionPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new DescriptionPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		String message = getQuotedContent(readableSyntax);

		addAttribute("message", message);
	}

	protected DescriptionPoshiElement() {
	}

	protected DescriptionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected DescriptionPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "description";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if ((parentPoshiElement instanceof CommandPoshiElement) &&
			readableSyntax.startsWith("@description")) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "description";

}