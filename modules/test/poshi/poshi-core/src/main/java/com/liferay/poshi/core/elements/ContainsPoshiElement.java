/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ContainsPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ContainsPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ContainsPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		Matcher matcher = _conditionPattern.matcher(poshiScript);

		matcher.find();

		String string = matcher.group(1);

		if (isQuotedContent(string)) {
			string = getDoubleQuotedContent(string);
		}

		addAttribute("string", string);

		String substring = matcher.group(2);

		if (isQuotedContent(substring)) {
			substring = getDoubleQuotedContent(substring);
		}

		addAttribute("substring", substring);
	}

	@Override
	public String toPoshiScript() {
		String stringValue = attributeValue("string");

		if (isQuotedContent(stringValue)) {
			stringValue = "\"" + stringValue + "\"";
		}

		String substringValue = attributeValue("substring");

		if (isQuotedContent(substringValue)) {
			substringValue = "\"" + substringValue + "\"";
		}

		return StringUtil.combine(
			_ELEMENT_NAME, "(" + stringValue + ", ", substringValue, ")");
	}

	protected ContainsPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ContainsPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ContainsPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return _ELEMENT_NAME;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!isConditionValidInParent(parentPoshiElement)) {
			return false;
		}

		Matcher matcher = _conditionPattern.matcher(poshiScript);

		return matcher.find();
	}

	private static final String _ELEMENT_NAME = "contains";

	private static final Pattern _conditionPattern = Pattern.compile(
		"^" + _ELEMENT_NAME + "\\((.*)[\\s]*,[\\s]*(\\d+|(?:\\$\\{|\\\")" +
			"[\\s\\S]*(?:\\}|\"))\\)$");

}