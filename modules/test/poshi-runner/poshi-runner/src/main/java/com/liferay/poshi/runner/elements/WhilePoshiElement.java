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
public class WhilePoshiElement extends IfPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new WhilePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(readableSyntax)) {
			return new WhilePoshiElement(readableSyntax);
		}

		return null;
	}

	protected WhilePoshiElement() {
	}

	protected WhilePoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected WhilePoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		String parentheticalContent = getParentheticalContent(
			super.getBlockName());

		StringBuilder sb = new StringBuilder();

		sb.append(getReadableName());
		sb.append(" (");
		sb.append(parentheticalContent);

		if (attributeValue("max-iterations") != null) {
			sb.append(" && (maxIterations = \"");
			sb.append(attributeValue("max-iterations"));
			sb.append("\")");
		}

		sb.append(")");

		return sb.toString();
	}

	@Override
	protected String getCondition(String readableSyntax) {
		String parentheticalContent = getParentheticalContent(readableSyntax);

		if (parentheticalContent.contains("&& (maxIterations = ")) {
			int index = parentheticalContent.lastIndexOf("&&");

			String maxIterationsAssignment = parentheticalContent.substring(
				index + 2);

			maxIterationsAssignment = getParentheticalContent(
				maxIterationsAssignment);

			String maxIterationsValue = getValueFromAssignment(
				maxIterationsAssignment);

			addAttribute(
				"max-iterations", getQuotedContent(maxIterationsValue));

			parentheticalContent = parentheticalContent.substring(0, index);
		}

		return parentheticalContent.trim();
	}

	private boolean _isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.startsWith("while (")) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "while";

}