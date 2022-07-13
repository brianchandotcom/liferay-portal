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

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

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
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new WhilePoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	protected WhilePoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected WhilePoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected WhilePoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected WhilePoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		String parentheticalContent = getParentheticalContent(
			super.getBlockName());

		StringBuilder sb = new StringBuilder();

		sb.append(getPoshiScriptKeyword());
		sb.append(" (");

		List<Element> equalsPoshiElement = elements("equals");

		if (equalsPoshiElement.size() == 1) {
			parentheticalContent = "(" + parentheticalContent + ")";
		}

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
	protected String getPoshiScriptKeyword() {
		return _ELEMENT_NAME;
	}

	protected static final Pattern blockNamePattern;

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (WhilePoshiElement.class.equals(parentPoshiElement.getClass())) {
			return false;
		}

		if (!(parentPoshiElement instanceof CommandPoshiElement) &&
			!(parentPoshiElement instanceof ForPoshiElement) &&
			!(parentPoshiElement instanceof TaskPoshiElement) &&
			!(parentPoshiElement instanceof ThenPoshiElement)) {

			return false;
		}

		return isValidPoshiScriptBlock(blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "while";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	static {
		blockNamePattern = Pattern.compile(
			"^" + _POSHI_SCRIPT_KEYWORD + BLOCK_NAME_PARAMETER_REGEX,
			Pattern.DOTALL);
	}

}