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
public class DescriptionElement extends PoshiElement {

	public static final String ELEMENT_NAME = "description";

	static {
		PoshiElementFactory poshiElementFactory = new PoshiElementFactory() {

				@Override
				public PoshiElement newPoshiElement(Element element) {
					if (isElementType(ELEMENT_NAME, element)) {
						return new DescriptionElement(element);
					}

					return null;
				}

				@Override
				public PoshiElement newPoshiElement(
					PoshiElement parentPoshiElement, String readableSyntax) {

					if (isElementType(parentPoshiElement, readableSyntax)) {
						return new DescriptionElement(readableSyntax);
					}

					return null;
				}

			};

		PoshiElement.addPoshiElementFactory(poshiElementFactory);
	}

	public static boolean isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (parentPoshiElement instanceof CommandElement &&
			readableSyntax.startsWith("@description")) {

			return true;
		}

		return false;
	}

	@Override
	public String getBlockName() {
		return "description";
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		String message = getQuotedContent(readableSyntax);

		addAttribute("message", message);
	}

	protected DescriptionElement(Element element) {
		super(ELEMENT_NAME, element);
	}

	protected DescriptionElement(String readableSyntax) {
		super(ELEMENT_NAME, readableSyntax);
	}

}