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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class AndPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new AndPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new AndPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			add(PoshiNodeFactory.newPoshiNode(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append("(");
			sb.append(poshiElement.toReadableSyntax());
			sb.append(") && ");
		}

		sb.setLength(sb.length() - 4);

		return sb.toString();
	}

	protected AndPoshiElement() {
	}

	protected AndPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected AndPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "and";
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		List<String> readableBlocks = new ArrayList<>();

		for (String condition : readableSyntax.split(" && ")) {
			condition = getParentheticalContent(condition);

			readableBlocks.add(condition);
		}

		return readableBlocks;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (!isConditionValidInParent(parentPoshiElement)) {
			return false;
		}

		if (readableSyntax.contains(" || ") ||
			readableSyntax.startsWith("else if (")) {

			return false;
		}

		if (readableSyntax.contains(" && ")) {
			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "and";

}