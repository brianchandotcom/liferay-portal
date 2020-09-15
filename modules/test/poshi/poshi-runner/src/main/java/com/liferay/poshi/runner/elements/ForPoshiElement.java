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
import com.liferay.poshi.runner.util.Dom4JUtil;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ForPoshiElement extends PoshiElement {

	@Override
	public Element addAttribute(String name, String value) {
		if (name.equals("list") || name.equals("table")) {
			typeAttributeName = name;
		}

		return super.addAttribute(name, value);
	}

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ForPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ForPoshiElement(parentPoshiElement, poshiScript);
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

		String parentheticalContent = getParentheticalContent(
			getBlockName(poshiScript));

		Matcher matcher = _blockParameterPattern.matcher(parentheticalContent);

		if (matcher.find()) {
			addAttribute("param", matcher.group(1));

			addAttribute(matcher.group(2), matcher.group(3));
		}
		else {
			throw new RuntimeException(
				"Invalid parameter syntax:\n" + parentheticalContent);
		}

		String blockContent = getBlockContent(poshiScript);

		for (String poshiScriptSnippet : getPoshiScriptSnippets(blockContent)) {
			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public String toPoshiScript() {
		return "\n" + createPoshiScriptBlock(getPoshiNodes());
	}

	protected ForPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ForPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);

		initTypeAttributeName(element);
	}

	protected ForPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ForPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append("for (var ");
		sb.append(attributeValue("param"));
		sb.append(" : ");
		sb.append(typeAttributeName);
		sb.append(" \"");
		sb.append(attributeValue(typeAttributeName));
		sb.append("\")");

		return sb.toString();
	}

	protected void initTypeAttributeName(Element element) {
		if (element.attribute("list") != null) {
			typeAttributeName = "list";

			return;
		}

		if (element.attribute("table") != null) {
			typeAttributeName = "table";

			return;
		}

		try {
			throw new IllegalArgumentException(
				"Invalid 'for' element " + Dom4JUtil.format(element));
		}
		catch (IOException ioException) {
			throw new IllegalArgumentException(
				"Invalid 'for' element", ioException);
		}
	}

	protected String typeAttributeName;

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!(parentPoshiElement instanceof CommandPoshiElement) &&
			!(parentPoshiElement instanceof ForPoshiElement) &&
			!(parentPoshiElement instanceof TaskPoshiElement) &&
			!(parentPoshiElement instanceof ThenPoshiElement)) {

			return false;
		}

		return isValidPoshiScriptBlock(_blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "for";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _blockNamePattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + BLOCK_NAME_PARAMETER_REGEX,
		Pattern.DOTALL);
	private static final Pattern _blockParameterPattern = Pattern.compile(
		"var[\\s]*([\\w]*)[\\s]*:[\\s]*([\\w]*)[\\s]*\"(.*)\"");

}