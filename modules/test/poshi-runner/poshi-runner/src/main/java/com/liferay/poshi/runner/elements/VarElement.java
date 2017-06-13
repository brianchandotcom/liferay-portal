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

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_VARIABLES;

import org.dom4j.Element;

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
	}

	public VarElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	public void addAttributes(String readableSyntax) {
		String[] items = readableSyntax.split("\\|", -1);

		addAttribute("name", items[1].trim());

		String value = items[2].trim();

		if (value.contains("Util#")) {
			addAttribute("method", value);
		}
		else {
			addAttribute("value", value);
		}
	}

	@Override
	public void addElements(String readableSyntax) {
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		Element parentElement = getParent();

		String parentElementName = parentElement.getName();

		if (parentElementName.equals("command") ||
			parentElementName.equals("set-up") ||
			parentElementName.equals("tear-down")) {

			sb.append("\n\t");
			sb.append(getReadableExecuteKey());
			sb.append(" ");
			sb.append(getReadableVariableKey());
		}

		sb.append("\n\t\t");
		sb.append("|");
		sb.append(attributeValue("name"));
		sb.append("|");
		sb.append(getVariableValueAttribute());
		sb.append("|");

		return sb.toString();
	}

	protected String getReadableVariableKey() {
		return THESE_VARIABLES;
	}

}