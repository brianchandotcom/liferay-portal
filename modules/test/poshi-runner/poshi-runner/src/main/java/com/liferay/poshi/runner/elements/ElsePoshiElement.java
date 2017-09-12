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

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ElsePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new ElsePoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public String toReadableSyntax() {
		String readableSyntax = super.toReadableSyntax();

		return createReadableBlock(readableSyntax);
	}

	protected ElsePoshiElement() {
	}

	protected ElsePoshiElement(Element element) {
		super("else", element);
	}

	protected ElsePoshiElement(String readableSyntax) {
		super("else", readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "else";
	}

	private static boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (parentPoshiElement instanceof IfPoshiElement &&
			readableSyntax.startsWith("else {")) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "else";

}