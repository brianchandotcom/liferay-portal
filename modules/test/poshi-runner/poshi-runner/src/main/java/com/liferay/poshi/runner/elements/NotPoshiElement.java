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
public class NotPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new NotPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new NotPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		add(
			PoshiNodeFactory.newPoshiNode(
				this, getParentheticalContent(readableSyntax)));
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append("!(");

			sb.append(poshiElement.toReadableSyntax());

			sb.append(")");
		}

		return sb.toString();
	}

	protected NotPoshiElement() {
	}

	protected NotPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected NotPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "not";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (!isConditionValidInParent(parentPoshiElement)) {
			return false;
		}

		readableSyntax = readableSyntax.trim();

		if (readableSyntax.startsWith("else if (")) {
			return false;
		}

		if (readableSyntax.startsWith("!")) {
			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "not";

}