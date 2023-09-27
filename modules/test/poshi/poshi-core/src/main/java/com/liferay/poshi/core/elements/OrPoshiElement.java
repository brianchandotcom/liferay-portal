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
public class OrPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new OrPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new OrPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		for (String nestedCondition : getNestedConditions(poshiScript, "||")) {
			nestedCondition = nestedCondition.trim();

			if (nestedCondition.endsWith(")") &&
				nestedCondition.startsWith("(")) {

				nestedCondition = getParentheticalContent(nestedCondition);
			}

			add(PoshiNodeFactory.newPoshiNode(this, nestedCondition));
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		List<PoshiElement> poshiElements = toPoshiElements(elements());

		for (PoshiElement poshiElement : poshiElements) {
			String poshiScript = poshiElement.toPoshiScript();

			if (poshiScript.startsWith("(") || poshiScript.startsWith("!(") ||
				poshiScript.startsWith("isSet(") ||
				poshiScript.startsWith("contains(")) {

				sb.append(poshiScript);
			}
			else {
				sb.append("(");
				sb.append(poshiElement.toPoshiScript());
				sb.append(")");
			}

			sb.append(" || ");
		}

		sb.setLength(sb.length() - 4);

		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		if ((poshiElements.size() > 1) &&
			!(parentPoshiElement instanceof NotPoshiElement)) {

			sb.insert(0, "(");
			sb.append(")");
		}

		return sb.toString();
	}

	protected OrPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected OrPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected OrPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected OrPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "or";
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!isConditionElementType(parentPoshiElement, poshiScript)) {
			return false;
		}

		List<String> nestedConditions = getNestedConditions(poshiScript, "||");

		return !nestedConditions.isEmpty();
	}

	private static final String _ELEMENT_NAME = "or";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^(?!else)[\\s\\S]*\\|\\|[\\s\\S]*$");

}