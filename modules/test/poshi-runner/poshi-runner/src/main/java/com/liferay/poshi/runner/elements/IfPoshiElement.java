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
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new IfPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public String getPoshiLogDescriptor() {
		return getBlockName(getPoshiScript());
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		for (String poshiScriptSnippet :
				getPoshiScriptSnippets(poshiScript, false)) {

			String trimmedPoshiScriptSnippet = poshiScriptSnippet.trim();

			if (trimmedPoshiScriptSnippet.startsWith(getPoshiScriptKeyword())) {
				add(
					PoshiNodeFactory.newPoshiNode(
						this, getCondition(getBlockName(poshiScriptSnippet))));

				add(new ThenPoshiElement(this, poshiScriptSnippet));

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

		sb.append(createPoshiScriptBlock(thenElement.getPoshiNodes()));

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
		this(_ELEMENT_NAME);
	}

	protected IfPoshiElement(Element element) {
		super("if", element);
	}

	protected IfPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected IfPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super("if", parentPoshiElement, poshiScript);
	}

	protected IfPoshiElement(String name) {
		super(name);
	}

	protected IfPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected IfPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected IfPoshiElement(
			String name, PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

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

	@Override
	protected String getPoshiScriptKeyword() {
		return getName();
	}

	protected static final Pattern blockNamePattern;

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (IfPoshiElement.class.equals(parentPoshiElement.getClass())) {
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

	private static final String[] _CONDITION_NAMES = {
		"and", "condition", "contains", "equals", "isset", "not", "or"
	};

	private static final String _ELEMENT_NAME = "if";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	static {
		blockNamePattern = Pattern.compile(
			"^" + _POSHI_SCRIPT_KEYWORD + BLOCK_NAME_PARAMETER_REGEX,
			Pattern.DOTALL);
	}

}