/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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