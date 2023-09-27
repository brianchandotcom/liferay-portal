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
public class EqualsPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new EqualsPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new EqualsPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (poshiScript.startsWith("(") && poshiScript.endsWith(")")) {
			poshiScript = poshiScript.substring(1, poshiScript.length() - 1);
		}

		String[] equalsContentArray = poshiScript.split("==");

		String arg1 = equalsContentArray[0].trim();

		if (isQuotedContent(arg1)) {
			arg1 = getDoubleQuotedContent(arg1);
		}

		addAttribute("arg1", arg1);

		String arg2 = equalsContentArray[1].trim();

		if (isQuotedContent(arg2)) {
			arg2 = getDoubleQuotedContent(arg2);
		}

		addAttribute("arg2", arg2);
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		String arg1 = attributeValue("arg1");

		String arg2 = attributeValue("arg2");

		if (isQuotedContent(arg1)) {
			arg1 = "\"" + arg1 + "\"";
		}

		sb.append(arg1);

		sb.append(" == ");

		if (isQuotedContent(arg2)) {
			arg2 = "\"" + arg2 + "\"";
		}

		sb.append(arg2);

		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		if (parentPoshiElement instanceof AndPoshiElement ||
			parentPoshiElement instanceof OrPoshiElement) {

			sb.insert(0, "(");
			sb.append(")");
		}

		return sb.toString();
	}

	protected EqualsPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected EqualsPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected EqualsPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected EqualsPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "equals";
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (isConditionElementType(parentPoshiElement, poshiScript)) {
			List<String> nestedConditions = getNestedConditions(
				poshiScript, "||");

			if (nestedConditions.size() > 1) {
				return false;
			}

			nestedConditions = getNestedConditions(poshiScript, "&&");

			if (nestedConditions.size() > 1) {
				return false;
			}

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "equals";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^[\\(]*(?:\\d+|(?:\\$\\{|\\\")[\\s\\S]*(?:\\}|\"))[\\s]*==" +
			"[\\s]*(?:\\d+|(?:\\$\\{|\\\")[\\s\\S]*(?:\\}|\"))[\\)]*$");

}