/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.StringUtil;

import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class PropertyPoshiElement extends VarPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new PropertyPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(poshiScript)) {
			return new PropertyPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	protected PropertyPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected PropertyPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected PropertyPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected PropertyPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected Pattern getStatementPattern() {
		return _statementPattern;
	}

	private boolean _isElementType(String poshiScript) {
		return isValidPoshiScriptStatement(
			_partialStatementPattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "property";

	private static final String _POSHI_SCRIPT_KEYWORD = _ELEMENT_NAME;

	private static final String _PROPERTY_VALUE_REGEX = StringUtil.combine(
		"(", "\".*?\"", "|", "'''.*?'''", ")");

	private static final Pattern _partialStatementPattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + "[\\s]+[\\w\\.-]+" + ASSIGNMENT_REGEX +
			_PROPERTY_VALUE_REGEX,
		Pattern.DOTALL);
	private static final Pattern _statementPattern = Pattern.compile(
		"^" + _POSHI_SCRIPT_KEYWORD + "[\\s]+[\\w\\.-]+" + ASSIGNMENT_REGEX +
			_PROPERTY_VALUE_REGEX + "(;|)$",
		Pattern.DOTALL | Pattern.MULTILINE);

}