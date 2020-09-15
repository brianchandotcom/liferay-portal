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

import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ElseIfPoshiElement extends IfPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ElseIfPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ElseIfPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		PoshiElement thenElement = (PoshiElement)element("then");

		sb.append(createPoshiScriptBlock(thenElement.getPoshiNodes()));

		return sb.toString();
	}

	protected ElseIfPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ElseIfPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ElseIfPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ElseIfPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getPoshiScriptKeyword() {
		return "else if";
	}

	protected static final Pattern blockNamePattern;

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (ElseIfPoshiElement.class.equals(parentPoshiElement.getClass())) {
			return false;
		}

		if (!(parentPoshiElement instanceof IfPoshiElement)) {
			return false;
		}

		return isValidPoshiScriptBlock(blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "elseif";

	private static final String _POSHI_SCRIPT_KEYWORD = "else if";

	private static final String _POSHI_SCRIPT_KEYWORD_REGEX;

	static {
		_POSHI_SCRIPT_KEYWORD_REGEX = StringUtil.replace(
			_POSHI_SCRIPT_KEYWORD, " ", "[\\s]*");

		blockNamePattern = Pattern.compile(
			"^" + _POSHI_SCRIPT_KEYWORD_REGEX + BLOCK_NAME_PARAMETER_REGEX,
			Pattern.DOTALL);
	}

}