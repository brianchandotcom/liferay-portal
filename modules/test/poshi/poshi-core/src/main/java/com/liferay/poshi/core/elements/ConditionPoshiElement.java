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
public class ConditionPoshiElement extends ExecutePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ConditionPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ConditionPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void validateSemicolon(String poshiScript) {
	}

	protected ConditionPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ConditionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ConditionPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ConditionPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String createPoshiScriptSnippet(List<String> assignments) {
		String poshiScriptSnippet = super.createPoshiScriptSnippet(assignments);

		poshiScriptSnippet = poshiScriptSnippet.trim();

		if (poshiScriptSnippet.endsWith(";")) {
			poshiScriptSnippet = poshiScriptSnippet.substring(
				0, poshiScriptSnippet.length() - 1);
		}

		return poshiScriptSnippet;
	}

	@Override
	protected Pattern getConditionPattern() {
		return _conditionPattern;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (isNestedCondition(poshiScript)) {
			return false;
		}

		return isConditionElementType(parentPoshiElement, poshiScript);
	}

	private static final String _ELEMENT_NAME = "condition";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^(?!isSet|contains)[\\w\\.]+\\([\\s\\S]*\\)$");

}