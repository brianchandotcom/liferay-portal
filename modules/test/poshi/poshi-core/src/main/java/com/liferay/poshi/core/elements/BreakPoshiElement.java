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

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Calum Ragan
 */
public class BreakPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new BreakPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new BreakPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {
	}

	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\t");
		sb.append(_ELEMENT_NAME + ";");

		return sb.toString();
	}

	protected BreakPoshiElement() {
		this(_ELEMENT_NAME);
	}

	protected BreakPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected BreakPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected BreakPoshiElement(String name) {
		super(name);
	}

	@Override
	protected String getBlockName() {
		return "break";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		poshiScript = poshiScript.trim();

		if (!(parentPoshiElement instanceof ThenPoshiElement)) {
			return false;
		}

		return isValidPoshiScriptStatement(_returnPattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "break";

	private static final Pattern _returnPattern = Pattern.compile("^break;$");

}