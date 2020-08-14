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
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new NotPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (poshiScript.contains("!=")) {
			List<String> nestedConditions = getNestedConditions(
				poshiScript, "!=");

			poshiScript =
				nestedConditions.get(0) + "==" + nestedConditions.get(1);
		}
		else {
			poshiScript = getParentheticalContent(poshiScript);
		}

		add(PoshiNodeFactory.newPoshiNode(this, poshiScript));
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append("!(");

			sb.append(poshiElement.toPoshiScript());

			sb.append(")");
		}

		return sb.toString();
	}

	protected NotPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected NotPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected NotPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected NotPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "not";
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		return isConditionElementType(parentPoshiElement, poshiScript);
	}

	private static final String _ELEMENT_NAME = "not";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^(![\\s\\S]*|\"[\\s\\S]*\"[\\s]*!=[\\s]*\"[\\s\\S]*\")$");

}