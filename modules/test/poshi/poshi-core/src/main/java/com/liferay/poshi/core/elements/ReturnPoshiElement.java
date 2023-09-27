/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.RegexUtil;
import com.liferay.poshi.core.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ReturnPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ReturnPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ReturnPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (getParent() instanceof ExecutePoshiElement) {
			String returnName = RegexUtil.getGroup(
				poshiScript, "var\\s*(.+?)\\s*=", 1);

			addAttribute("name", returnName);

			return;
		}

		Matcher matcher = _returnPattern.matcher(poshiScript.trim());

		matcher.find();

		String value = matcher.group(1);

		if (isQuotedContent(value)) {
			value = getDoubleQuotedContent(value);
		}

		addAttribute("value", value);
	}

	@Override
	public String toPoshiScript() {
		if (getParent() instanceof ExecutePoshiElement) {
			return "";
		}

		String value = attributeValue("value");

		if (!value.matches(_UNQUOTED_VALUE_REGEX)) {
			value = "\"" + value + "\"";
		}

		return StringUtil.combine("\n\n", getPad(), "return ", value, ";");
	}

	@Override
	public void validatePoshiScript() throws PoshiScriptParserException {
	}

	protected ReturnPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ReturnPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ReturnPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ReturnPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String createPoshiScriptSnippet(String content) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n");
		sb.append(getPad());
		sb.append(getBlockName());
		sb.append(content.trim());

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append("var ");
		sb.append(attributeValue("name"));
		sb.append(" = ");

		return sb.toString();
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		poshiScript = poshiScript.trim();

		if (parentPoshiElement instanceof ExecutePoshiElement) {
			if (!poshiScript.startsWith("var")) {
				return false;
			}

			if (isVarAssignedToMacroInvocation(poshiScript)) {
				return true;
			}

			return false;
		}

		return isValidPoshiScriptStatement(_returnPattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "return";

	private static final String _UNQUOTED_VALUE_REGEX =
		"(\\$\\{[\\w_-]+\\}|\\d+)";

	private static final Pattern _returnPattern = Pattern.compile(
		"^return[\\s]+(\\$\\{[\\w_-]+\\}|\\d+|\".*\")[\\s]*;$");

}