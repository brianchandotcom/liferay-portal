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

import com.liferay.poshi.runner.util.Dom4JUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public abstract class DefinitionPoshiElement extends PoshiElement {

	@Override
	public void parsePoshiScript(String poshiScript) {
		for (String poshiScriptSnippet : getPoshiScriptSnippets(poshiScript)) {
			if (poshiScriptSnippet.startsWith("@") &&
				!poshiScriptSnippet.endsWith("}")) {

				String name = getNameFromAssignment(poshiScriptSnippet);
				String value = getQuotedContent(poshiScriptSnippet);

				addAttribute(name, value);

				continue;
			}

			if (isPoshiScriptComment(poshiScriptSnippet)) {
				add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));

				continue;
			}

			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			sb.append("\n@");

			sb.append(poshiElementAttribute.toPoshiScript());
		}

		StringBuilder content = new StringBuilder();

		Node previousNode = null;

		for (Node node : Dom4JUtil.toNodeList(content())) {
			if (node instanceof PoshiComment) {
				PoshiComment poshiComment = (PoshiComment)node;

				content.append("\n");
				content.append(poshiComment.toPoshiScript());
			}
			else if (node instanceof PoshiElement) {
				content.append("\n");

				if (previousNode == null) {
					content.deleteCharAt(content.length() - 1);
				}
				else if ((node instanceof PropertyPoshiElement) &&
						 (previousNode instanceof PropertyPoshiElement)) {

					content.deleteCharAt(content.length() - 1);
				}
				else if ((node instanceof VarPoshiElement) &&
						 (previousNode instanceof VarPoshiElement)) {

					content.deleteCharAt(content.length() - 1);
				}

				PoshiElement poshiElement = (PoshiElement)node;

				content.append(poshiElement.toPoshiScript());
			}

			previousNode = node;
		}

		sb.append(createPoshiScriptSnippet(content.toString()));

		String string = sb.toString();

		return string.trim();
	}

	protected DefinitionPoshiElement() {
	}

	protected DefinitionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected DefinitionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected DefinitionPoshiElement(
		PoshiElement parentPoshiElement, String poshiScript) {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "definition";
	}

	protected String getElementName() {
		return _ELEMENT_NAME;
	}

	protected String getFileType() {
		return null;
	}

	@Override
	protected String getPad() {
		return "";
	}

	protected String getPoshiScriptKeyword() {
		if (getFileType().equals("testcase")) {
			return "test";
		}

		return getFileType();
	}

	protected List<String> getPoshiScriptSnippets(String poshiScript) {
		StringBuilder sb = new StringBuilder();

		List<String> poshiScriptSnippets = new ArrayList<>();

		for (String line : poshiScript.split("\n")) {
			String trimmedLine = line.trim();

			if (trimmedLine.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (trimmedLine.equals(line) && trimmedLine.startsWith("@")) {
				poshiScriptSnippets.add(line);

				continue;
			}

			if (trimmedLine.startsWith("definition {")) {
				continue;
			}

			String poshiScriptSnippet = sb.toString();

			poshiScriptSnippet = poshiScriptSnippet.trim();

			if (isValidPoshiScriptSnippet(poshiScriptSnippet)) {
				poshiScriptSnippets.add(poshiScriptSnippet);

				sb.setLength(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		return poshiScriptSnippets;
	}

	@Override
	protected boolean isBalanceValidationRequired(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.endsWith("}") &&
			(poshiScript.startsWith("@") || poshiScript.startsWith("setUp") ||
			 poshiScript.startsWith("tearDown") ||
			 poshiScript.startsWith(getPoshiScriptKeyword()))) {

			return true;
		}

		return false;
	}

	protected boolean isElementType(String poshiScript) {
		return isValidPoshiScriptBlock(_blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "definition";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _blockNamePattern = Pattern.compile(
		"^" + BLOCK_NAME_ANNOTATION_REGEX + _POSHI_SCRIPT_KEYWORD,
		Pattern.DOTALL);

}