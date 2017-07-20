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

	public VarElement(Element element) {
		this("var", element);
	}

	public VarElement(String readableSyntax) {
		this("var", readableSyntax);
	}

	public VarElement(String name, Element element) {
		super(name, element);

		initValueAttributeName(element);
	}

	public VarElement(String name, String readableSyntax) {
		super(name, readableSyntax);
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