/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.Dom4JUtil;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

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

			String value = matcher.group(3);

			if (value.startsWith("\"") && value.endsWith("\"")) {
				value = getDoubleQuotedContent(value);
			}

			if (value.contains("\"")) {
				int escapedQuoteCount = StringUtils.countMatches(value, "\\\"");
				int quoteCount = StringUtils.countMatches(value, "\"");

				if (escapedQuoteCount != quoteCount) {
					throw new PoshiScriptParserException(
						"Unescaped quotes in list value: " + value, poshiScript,
						(PoshiElement)getParent());
				}
			}

			addAttribute(matcher.group(2), value);
		}
		else {
			throw new PoshiScriptParserException(
				"Invalid parameter syntax: " + parentheticalContent,
				poshiScript, (PoshiElement)getParent());
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
		sb.append(" ");

		String typeAttributeValue = attributeValue(typeAttributeName);

		if (isQuotedContent(typeAttributeValue)) {
			typeAttributeValue = "\"" + typeAttributeValue + "\"";
		}

		sb.append(typeAttributeValue);
		sb.append(")");

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
		"var[\\s]*([\\w]*)[\\s]*:[\\s]*([\\w]*)[\\s]*(?:(\\$\\{.*}|.*))");

}