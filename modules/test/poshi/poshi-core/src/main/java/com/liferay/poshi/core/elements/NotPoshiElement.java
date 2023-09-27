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
public class NotPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new NotPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new NotPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (poshiScript.contains("!=")) {
			List<String> nestedConditions = getNestedConditions(
				poshiScript, "!=");

			poshiScript =
				nestedConditions.get(0) + "==" + nestedConditions.get(1);
		}
		else {
			poshiScript = getParentheticalContent(poshiScript);
		}

		add(PoshiNodeFactory.newPoshiNode(this, poshiScript));
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			if (poshiElement instanceof EqualsPoshiElement) {
				PoshiElement parentPoshiElement = (PoshiElement)getParent();

				if (parentPoshiElement instanceof AndPoshiElement ||
					parentPoshiElement instanceof OrPoshiElement) {

					sb.append("(");
					sb.append(
						_toNotEqualsPoshiScript(
							(EqualsPoshiElement)poshiElement));
					sb.append(")");
				}
				else {
					sb.append(
						_toNotEqualsPoshiScript(
							(EqualsPoshiElement)poshiElement));
				}
			}
			else {
				sb.append("!(");

				sb.append(poshiElement.toPoshiScript());
				sb.append(")");
			}
		}

		return sb.toString();
	}

	protected NotPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected NotPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected NotPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected NotPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "not";
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		return isConditionElementType(parentPoshiElement, poshiScript);
	}

	private String _toNotEqualsPoshiScript(
		EqualsPoshiElement equalsPoshiElement) {

		StringBuilder sb = new StringBuilder();

		String arg1 = equalsPoshiElement.attributeValue("arg1");

		if (isQuotedContent(arg1)) {
			arg1 = "\"" + arg1 + "\"";
		}

		sb.append(arg1);

		sb.append(" != ");

		String arg2 = equalsPoshiElement.attributeValue("arg2");

		if (isQuotedContent(arg2)) {
			arg2 = "\"" + arg2 + "\"";
		}

		sb.append(arg2);

		return sb.toString();
	}

	private static final String _ELEMENT_NAME = "not";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^(![\\s\\S]*|(?:\\d+|(?:\\$\\{|\\\")[\\s\\S]*" +
			"(?:\\}|\"))[\\s]*!=[\\s]*[\\s\\S]*(?:\\d+|(?:\\}|\")))$");

}