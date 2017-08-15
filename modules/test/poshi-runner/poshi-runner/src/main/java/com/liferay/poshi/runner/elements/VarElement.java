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

import com.liferay.poshi.runner.util.Dom4JUtil;

import java.io.IOException;

import org.dom4j.CDATA;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class VarElement extends PoshiElement {

	public static final String ELEMENT_NAME = "var";

	static {
		PoshiElementFactory varElementFactory = new PoshiElementFactory() {

			@Override
			public PoshiElement newPoshiElement(Element element) {
				if (isElementType(ELEMENT_NAME, element)) {
					return new VarElement(element);
				}

				return null;
			}

			@Override
			public PoshiElement newPoshiElement(
				PoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new VarElement(readableSyntax);
				}

				return null;
			}

		};

		PoshiElement.addPoshiElementFactory(varElementFactory);
	}

	public static boolean isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.endsWith(";")) {
			return false;
		}

		if (!readableSyntax.startsWith("var ")) {
			return false;
		}

		if (readableSyntax.contains(" = return(")) {
			return false;
		}

		return true;
	}

	public String getVarValue() {
		if (valueAttributeName == null) {
			for (Node node : Dom4JUtil.toNodeList(content())) {
				if (node instanceof CDATA) {
					return node.getText();
				}
			}
		}

		return attributeValue(valueAttributeName);
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		String name = getNameFromAssignment(readableSyntax);

		addAttribute("name", name);

		String value = getQuotedContent(readableSyntax);

		if (value.contains("Util.")) {
			value = value.replace("Util.", "Util#");

			addAttribute("method", value);

			return;
		}

		if (value.contains("\n")) {
			addCDATA(value);

			return;
		}

		addAttribute("value", value);
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\t");

		PoshiElement parentElement = (PoshiElement)getParent();

		String parentElementName = parentElement.getName();

		if (!parentElementName.equals("execute")) {
			sb.append(getName());
			sb.append(" ");
		}

		String name = attributeValue("name");

		sb.append(name);

		sb.append(" = \"");

		String value = getVarValue();

		value = value.replace("Util#", "Util.");

		sb.append(value);

		sb.append("\"");

		if (!parentElementName.equals("execute")) {
			sb.append(";");
		}

		return sb.toString();
	}

	protected VarElement(Element element) {
		this(ELEMENT_NAME, element);
	}

	protected VarElement(String readableSyntax) {
		this(ELEMENT_NAME, readableSyntax);
	}

	protected VarElement(String name, Element element) {
		super(name, element);

		if (isElementType(name, element)) {
			initValueAttributeName(element);
		}
	}

	protected VarElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return null;
	}

	protected void initValueAttributeName(Element element) {
		if (element.attribute("method") != null) {
			valueAttributeName = "method";

			return;
		}

		if (element.attribute("value") != null) {
			valueAttributeName = "value";

			return;
		}

		for (Node node : Dom4JUtil.toNodeList(element.content())) {
			if (node instanceof CDATA) {
				add((CDATA)node.clone());

				return;
			}
		}

		try {
			throw new IllegalArgumentException(
				"Invalid variable element " + Dom4JUtil.format(element));
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException("Invalid variable element");
		}
	}

	protected String valueAttributeName;

}