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

import com.liferay.poshi.runner.script.PoshiScriptParserException;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

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
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new DescriptionPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public int getPoshiScriptLineNumber() {
		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		return parentPoshiElement.getPoshiScriptLineNumber(true);
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		String message = getDoubleQuotedContent(poshiScript);

		addAttribute("message", message);
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("@");
		sb.append(_ELEMENT_NAME);
		sb.append(" = \"");
		sb.append(attributeValue("message"));
		sb.append("\"");

		return sb.toString();
	}

	protected DescriptionPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected DescriptionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected DescriptionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected DescriptionPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "description";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if ((parentPoshiElement instanceof CommandPoshiElement) &&
			poshiScript.startsWith("@description")) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "description";

}