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
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class PropertyPoshiElement extends VarPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new PropertyPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(poshiScript)) {
			return new PropertyPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	protected PropertyPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected PropertyPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected PropertyPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected PropertyPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	private boolean _isElementType(String poshiScript) {
		return isValidPoshiScriptStatement(_statementPattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "property";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _statementPattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + "[\\s]*[\\w\\.-]*" + ASSIGNMENT_REGEX +
			".*" + STATEMENT_END_REGEX);

}