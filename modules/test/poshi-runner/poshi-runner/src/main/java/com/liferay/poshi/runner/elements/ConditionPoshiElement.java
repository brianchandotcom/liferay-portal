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
public class ConditionPoshiElement extends ExecutePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ConditionPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new ConditionPoshiElement(readableSyntax);
		}

		return null;
	}

	protected ConditionPoshiElement() {
	}

	protected ConditionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ConditionPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String createFunctionReadableBlock(String content) {
		String readableBlock = super.createFunctionReadableBlock(content);

		readableBlock = readableBlock.trim();

		if (readableBlock.endsWith(";")) {
			readableBlock = readableBlock.substring(
				0, readableBlock.length() - 1);
		}

		return readableBlock;
	}

	@Override
	protected String getBlockName() {
		return attributeValue("function");
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (!isConditionValidInParent(parentPoshiElement)) {
			return false;
		}

		if (readableSyntax.contains(" && ") ||
			readableSyntax.contains(" || ") || readableSyntax.startsWith("!") ||
			readableSyntax.startsWith("else if (")) {

			return false;
		}

		if (readableSyntax.endsWith(")") &&
			!readableSyntax.startsWith("isSet(")) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "condition";

}