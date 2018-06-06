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
public class TaskPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new TaskPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (_isElementType(poshiScript)) {
			return new TaskPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript) {
		for (String poshiScriptSnippet : getPoshiScriptSnippets(poshiScript)) {
			if (poshiScriptSnippet.startsWith("task (")) {
				String parentheticalContent = getParentheticalContent(
					poshiScriptSnippet);

				String summary = getQuotedContent(parentheticalContent);

				addAttribute("summary", summary);

				continue;
			}

			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		StringBuilder content = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			content.append(poshiElement.toPoshiScript());
		}

		String poshiScriptSnippet = createPoshiScriptSnippet(
			content.toString());

		sb.append(poshiScriptSnippet);

		return sb.toString();
	}

	protected TaskPoshiElement() {
	}

	protected TaskPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected TaskPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected TaskPoshiElement(
		PoshiElement parentPoshiElement, String poshiScript) {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append("task (\"");
		sb.append(attributeValue("summary"));
		sb.append("\")");

		return sb.toString();
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

				continue;
			}

			if (trimmedLine.endsWith("{") && poshiScriptSnippets.isEmpty()) {
				continue;
			}

			if (isValidPoshiScriptSnippet(poshiScriptSnippet)) {
				poshiScriptSnippets.add(poshiScriptSnippet);

				sb.setLength(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		return poshiScriptSnippets;
	}

	private boolean _isElementType(String poshiScript) {
		return isValidPoshiScriptBlock(_blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "task";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _blockNamePattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + BLOCK_NAME_PARAMETER_REGEX,
		Pattern.DOTALL);

}