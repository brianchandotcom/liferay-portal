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
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ConditionPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	protected ConditionPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ConditionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ConditionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ConditionPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String createPoshiScriptSnippet(List<String> assignments) {
		String poshiScriptSnippet = super.createPoshiScriptSnippet(assignments);

		poshiScriptSnippet = poshiScriptSnippet.trim();

		if (poshiScriptSnippet.endsWith(";")) {
			poshiScriptSnippet = poshiScriptSnippet.substring(
				0, poshiScriptSnippet.length() - 1);
		}

		return poshiScriptSnippet;
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (isNestedCondition(poshiScript)) {
			return false;
		}

		return isConditionElementType(parentPoshiElement, poshiScript);
	}

	private static final String _ELEMENT_NAME = "condition";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^(?!isSet|contains)[\\w\\.]+\\([\\s\\S]*\\)$");

}