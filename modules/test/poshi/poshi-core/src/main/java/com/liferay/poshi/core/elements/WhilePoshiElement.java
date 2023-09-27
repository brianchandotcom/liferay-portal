/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class WhilePoshiElement extends IfPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new WhilePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new WhilePoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		for (String poshiScriptSnippet :
				getPoshiScriptSnippets(poshiScript, false)) {

			String trimmedPoshiScriptSnippet = poshiScriptSnippet.trim();

			if (!trimmedPoshiScriptSnippet.startsWith(
					getPoshiScriptKeyword())) {

				continue;
			}

			String blockName = getBlockName(poshiScriptSnippet);

			add(PoshiNodeFactory.newPoshiNode(this, getCondition(blockName)));

			add(new ThenPoshiElement(this, poshiScriptSnippet));

			if (!blockName.contains("&& (maxIterations = ")) {
				continue;
			}

			int index = blockName.lastIndexOf("&&");

			String maxIterationsAssignment = blockName.substring(index + 2);

			maxIterationsAssignment = getParentheticalContent(
				maxIterationsAssignment);

			String maxIterationsValue = getValueFromAssignment(
				maxIterationsAssignment);

			addAttribute(
				"max-iterations", getDoubleQuotedContent(maxIterationsValue));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		PoshiElement thenElement = (PoshiElement)element("then");

		sb.append(createPoshiScriptBlock(thenElement.getPoshiNodes()));

		return sb.toString();
	}

	protected WhilePoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected WhilePoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected WhilePoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected WhilePoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		String parentheticalContent = getParentheticalContent(
			super.getBlockName());

		StringBuilder sb = new StringBuilder();

		sb.append(getPoshiScriptKeyword());
		sb.append(" (");

		List<Element> equalsPoshiElement = elements("equals");

		if ((equalsPoshiElement.size() == 1) ||
			(attributeValue("max-iterations") != null)) {

			parentheticalContent = "(" + parentheticalContent + ")";
		}

		sb.append(parentheticalContent);

		if (attributeValue("max-iterations") != null) {
			sb.append(" && (maxIterations = \"");
			sb.append(attributeValue("max-iterations"));
			sb.append("\")");
		}

		sb.append(")");

		return sb.toString();
	}

	@Override
	protected String getPoshiScriptKeyword() {
		return _ELEMENT_NAME;
	}

	protected static final Pattern blockNamePattern;

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (WhilePoshiElement.class.equals(parentPoshiElement.getClass())) {
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

	private static final String _ELEMENT_NAME = "while";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	static {
		blockNamePattern = Pattern.compile(
			"^" + _POSHI_SCRIPT_KEYWORD + BLOCK_NAME_PARAMETER_REGEX,
			Pattern.DOTALL);
	}

}