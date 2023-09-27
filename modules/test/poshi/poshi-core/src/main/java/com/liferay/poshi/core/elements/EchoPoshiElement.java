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
public class EchoPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new EchoPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(poshiScript)) {
			return new EchoPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		validateSemicolon(poshiScript);

		String trimmedPoshiScript = poshiScript.trim();

		if (!trimmedPoshiScript.endsWith(";")) {
			throw new PoshiScriptParserException(
				"Missing semicolon", poshiScript, (PoshiElement)getParent());
		}

		String parentheticalContent = getParentheticalContent(poshiScript);

		if (!isQuotedContent(parentheticalContent)) {
			addAttribute("message", parentheticalContent);
		}
		else {
			String content = getDoubleQuotedContent(poshiScript);

			addAttribute("message", content);
		}
	}

	@Override
	public String toPoshiScript() {
		String message = attributeValue("message");

		return createPoshiScriptSnippet(message);
	}

	protected EchoPoshiElement() {
		this(_ELEMENT_NAME);
	}

	protected EchoPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected EchoPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected EchoPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected EchoPoshiElement(String name) {
		super(name);
	}

	protected EchoPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected EchoPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected EchoPoshiElement(
			String name, PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(name, parentPoshiElement, poshiScript);
	}

	@Override
	protected String createPoshiScriptSnippet(String content) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n");
		sb.append(getPad());
		sb.append(getBlockName());
		sb.append("(");

		if (isQuotedContent(content)) {
			sb.append("\"");
			sb.append(content);
			sb.append("\"");
		}
		else {
			sb.append(content);
		}

		sb.append(");");

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		return "echo";
	}

	@Override
	protected Pattern getStatementPattern() {
		return _statementPattern;
	}

	private boolean _isElementType(String poshiScript) {
		return isValidPoshiScriptStatement(
			_partialStatementPattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "echo";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final Pattern _partialStatementPattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + PARAMETER_REGEX);
	private static final Pattern _statementPattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + PARAMETER_REGEX + "(;|)$");

}