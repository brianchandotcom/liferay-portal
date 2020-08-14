/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.elements;

import com.liferay.poshi.runner.script.PoshiScriptParserException;
import com.liferay.poshi.runner.util.StringUtil;

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

		addAttribute("string", matcher.group(1));
		addAttribute("substring", matcher.group(2));
	}

	@Override
	public String toPoshiScript() {
		return StringUtil.combine(
			_ELEMENT_NAME, "(\"" + attributeValue("string") + "\", \"",
			attributeValue("substring"), "\")");
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
		"^" + _ELEMENT_NAME + "\\(\"(.*)\"[\\s]*,[\\s]*\"(.*)\"\\)$");

}