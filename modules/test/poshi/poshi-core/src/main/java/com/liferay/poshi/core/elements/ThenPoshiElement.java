/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ThenPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ThenPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ThenPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public int getPoshiScriptLineNumber() {
		if (this instanceof ElsePoshiElement) {
			return super.getPoshiScriptLineNumber();
		}

		PoshiElement parentPoshiElement = (PoshiElement)getParent();

		return parentPoshiElement.getPoshiScriptLineNumber();
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		String blockContent = getBlockContent(poshiScript);

		for (String poshiScriptSnippet : getPoshiScriptSnippets(blockContent)) {
			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public void validatePoshiScript() throws PoshiScriptParserException {
	}

	protected ThenPoshiElement() {
		this(_ELEMENT_NAME);
	}

	protected ThenPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ThenPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected ThenPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected ThenPoshiElement(String name) {
		super(name);
	}

	protected ThenPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected ThenPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected ThenPoshiElement(
			String name, PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(name, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return "then";
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!(parentPoshiElement instanceof IfPoshiElement)) {
			return false;
		}

		if (isValidPoshiScriptBlock(
				ElseIfPoshiElement.blockNamePattern, poshiScript) ||
			isValidPoshiScriptBlock(
				IfPoshiElement.blockNamePattern, poshiScript) ||
			isValidPoshiScriptBlock(
				WhilePoshiElement.blockNamePattern, poshiScript)) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "then";

}