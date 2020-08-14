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
public class IsSetPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new IsSetPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new IsSetPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		String issetContent = getParentheticalContent(poshiScript);

		addAttribute("var", issetContent);
	}

	@Override
	public String toPoshiScript() {
		return "isSet(" + attributeValue("var") + ")";
	}

	protected IsSetPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected IsSetPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected IsSetPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected IsSetPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return _POSHI_SCRIPT_KEYWORD;
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		return isConditionElementType(parentPoshiElement, poshiScript);
	}

	private static final String _ELEMENT_NAME = "isset";

	private static final String _POSHI_SCRIPT_KEYWORD = "isSet";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + "\\([\\w]*\\)$");

}