/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ArgPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ArgPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ArgPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		if (isQuotedContent(poshiScript)) {
			poshiScript = getDoubleQuotedContent(poshiScript);

			poshiScript = poshiScript.replace("\\\"", "\"");

			poshiScript = StringEscapeUtils.unescapeXml(poshiScript);
		}

		addAttribute("value", poshiScript);
	}

	@Override
	public String toPoshiScript() {
		String attributeValue = attributeValue("value");

		attributeValue = attributeValue.replace("\"", "\\\"");

		if (isQuotedContent(attributeValue)) {
			attributeValue = doubleQuoteContent(attributeValue);
		}

		return attributeValue;
	}

	protected ArgPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ArgPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ArgPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ArgPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return null;
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!(parentPoshiElement instanceof ExecutePoshiElement)) {
			return false;
		}

		poshiScript = poshiScript.trim();

		if (!poshiScript.startsWith("\"") && isQuotedContent(poshiScript)) {
			return false;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "arg";

}