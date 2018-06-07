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
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class IfPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new IfPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (_isElementType(poshiScript)) {
			return new IfPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript) {
		for (String poshiScriptSnippet : getPoshiScriptSnippets(poshiScript)) {
			if (poshiScriptSnippet.startsWith(getName() + " (")) {
				add(
					PoshiNodeFactory.newPoshiNode(
						this, getCondition(poshiScriptSnippet)));

				continue;
			}

			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		PoshiElement thenElement = (PoshiElement)element("then");

		String thenPoshiScript = thenElement.toPoshiScript();

		sb.append(createPoshiScriptSnippet(thenPoshiScript));

		for (PoshiElement elseIfElement : toPoshiElements(elements("elseif"))) {
			sb.append(elseIfElement.toPoshiScript());
		}

		if (element("else") != null) {
			PoshiElement elseElement = (PoshiElement)element("else");

			sb.append(elseElement.toPoshiScript());
		}

		return sb.toString();
	}

	protected IfPoshiElement() {
	}

	protected IfPoshiElement(Element element) {
		super("if", element);
	}

	protected IfPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected IfPoshiElement(
		PoshiElement parentPoshiElement, String poshiScript) {

		super("if", parentPoshiElement, poshiScript);
	}

	protected IfPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected IfPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected IfPoshiElement(
		String name, PoshiElement parentPoshiElement, String poshiScript) {

		super(name, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getPoshiScriptKeyword());

		for (String conditionName : _CONDITION_NAMES) {
			if (element(conditionName) != null) {
				PoshiElement poshiElement = (PoshiElement)element(
					conditionName);

				sb.append(" (");
				sb.append(poshiElement.toPoshiScript());
				sb.append(")");

				break;
			}
		}

		return sb.toString();
	}

	protected String getCondition(String poshiScript) {
		return getParentheticalContent(poshiScript);
	}

	protected String getPoshiScriptKeyword() {
		return getName();
	}

	protected List<String> getPoshiScriptSnippets(String poshiScript) {
		StringBuilder sb = new StringBuilder();

		List<String> poshiScriptSnippets = new ArrayList<>();

		for (String line : poshiScript.split("\n")) {
			String trimmedLine = line.trim();

			String poshiScriptSnippet = sb.toString();

			poshiScriptSnippet = poshiScriptSnippet.trim();

			if (trimmedLine.startsWith(getPoshiScriptKeyword() + " (") &&
				trimmedLine.endsWith("{") &&
				(poshiScriptSnippet.length() == 0)) {

				poshiScriptSnippets.add(line);

				sb.append("{\n");

				continue;
			}

			sb.append(line);
			sb.append("\n");

			poshiScriptSnippet = sb.toString();

			poshiScriptSnippet = poshiScriptSnippet.trim();

			if (isValidPoshiScriptSnippet(poshiScriptSnippet)) {
				poshiScriptSnippets.add(poshiScriptSnippet);

				sb.setLength(0);
			}
		}

		return poshiScriptSnippets;
	}

	private boolean _isElementType(String poshiScript) {
		return isValidPoshiScriptBlock(_blockNamePattern, poshiScript);
	}

	private static final String[] _CONDITION_NAMES =
		{"and", "condition", "equals", "isset", "not", "or"};

	private static final String _ELEMENT_NAME = "if";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _blockNamePattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + BLOCK_NAME_PARAMETER_REGEX,
		Pattern.DOTALL);

}