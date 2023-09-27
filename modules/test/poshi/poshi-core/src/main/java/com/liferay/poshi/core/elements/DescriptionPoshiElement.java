/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class DescriptionPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new DescriptionPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new DescriptionPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public int getPoshiScriptLineNumber() {
		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		return parentPoshiElement.getPoshiScriptLineNumber(true);
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (!poshiScript.endsWith("\"") &&
			poshiProperties.testPoshiScriptValidation) {

			throw new PoshiScriptParserException(
				"The description message must be a single line and enclosed " +
					"by double quotes (\")",
				poshiScript, (PoshiElement)getParent());
		}

		String message = getDoubleQuotedContent(poshiScript);

		Matcher matcher = _messagePattern.matcher(message);

		if (matcher.find()) {
			message = StringEscapeUtils.escapeXml(message);
		}

		addAttribute("message", message);
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("@");
		sb.append(_ELEMENT_NAME);
		sb.append(" = \"");

		String message = attributeValue("message");

		if (message.contains("&lt;") || message.contains("&gt;")) {
			message = StringEscapeUtils.unescapeXml(message);
		}

		sb.append(message);
		sb.append("\"");

		return sb.toString();
	}

	protected DescriptionPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected DescriptionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected DescriptionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected DescriptionPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "description";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if ((parentPoshiElement instanceof CommandPoshiElement) &&
			poshiScript.startsWith("@description")) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "description";

	private static final Pattern _messagePattern = Pattern.compile("<.*>");

}