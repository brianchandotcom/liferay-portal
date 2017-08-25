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
public class ElsePoshiElement extends ThenPoshiElement {

	public static boolean isElementType(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		if (parentPoshiElement instanceof IfPoshiElement &&
			readableSyntax.startsWith("else {")) {

			return true;
		}

		return false;
	}

	public ElsePoshiElement() {
	}

	@Override
	public PoshiElement clone(
		BasePoshiElement parentPoshiElement, String readableSyntax) {

		if (isElementType(parentPoshiElement, readableSyntax)) {
			return new ElsePoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ElsePoshiElement(element);
		}

		return null;
	}

	@Override
	public String getBlockName() {
		return "else";
	}

	@Override
	public String toReadableSyntax() {
		String readableSyntax = super.toReadableSyntax();

		return createReadableBlock(readableSyntax);
	}

	protected ElsePoshiElement(Element element) {
		super("else", element);
	}

	protected ElsePoshiElement(String readableSyntax) {
		super("else", readableSyntax);
	}

	private static final String _ELEMENT_NAME = "else";

	static {
		PoshiElementFactory poshiElementFactory = new PoshiElementFactory() {

			@Override
			public BasePoshiElement newPoshiElement(
				BasePoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new ElsePoshiElement(readableSyntax);
				}

				return null;
			}

			@Override
			public BasePoshiElement newPoshiElement(Element element) {
				if (isElementType(_ELEMENT_NAME, element)) {
					return new ElsePoshiElement(element);
				}

				return null;
			}

		};

		BasePoshiElement.addPoshiElementFactory(poshiElementFactory);
	}

}