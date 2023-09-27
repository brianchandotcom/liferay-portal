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
public class ContinuePoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ContinuePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ContinuePoshiElement(parentPoshiElement, poshiScript);
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

	protected ContinuePoshiElement() {
		this(_ELEMENT_NAME);
	}

	protected ContinuePoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ContinuePoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected ContinuePoshiElement(String name) {
		super(name);
	}

	@Override
	protected String getBlockName() {
		return "continue";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		poshiScript = poshiScript.trim();

		if (!(parentPoshiElement instanceof ThenPoshiElement)) {
			return false;
		}

		return isValidPoshiScriptStatement(_returnPattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "continue";

	private static final Pattern _returnPattern = Pattern.compile(
		"^continue;$");

}