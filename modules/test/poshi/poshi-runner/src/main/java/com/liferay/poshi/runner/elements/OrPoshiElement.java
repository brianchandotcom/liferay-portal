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
public class OrPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new OrPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new OrPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		for (String nestedCondition : getNestedConditions(poshiScript, "||")) {
			nestedCondition = nestedCondition.trim();

			if (nestedCondition.endsWith(")") &&
				nestedCondition.startsWith("(")) {

				nestedCondition = getParentheticalContent(nestedCondition);
			}

			add(PoshiNodeFactory.newPoshiNode(this, nestedCondition));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append("(");
			sb.append(poshiElement.toPoshiScript());
			sb.append(") || ");
		}

		sb.setLength(sb.length() - 4);

		return sb.toString();
	}

	protected OrPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected OrPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected OrPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected OrPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "or";
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!isConditionElementType(parentPoshiElement, poshiScript)) {
			return false;
		}

		List<String> nestedConditions = getNestedConditions(poshiScript, "||");

		return !nestedConditions.isEmpty();
	}

	private static final String _ELEMENT_NAME = "or";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^(?!!|else)[\\s\\S]*\\|\\|[\\s\\S]*$");

}