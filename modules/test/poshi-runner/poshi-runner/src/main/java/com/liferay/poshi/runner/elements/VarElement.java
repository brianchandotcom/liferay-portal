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

import com.liferay.poshi.runner.util.Dom4JUtil;

import java.io.IOException;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class VarElement extends PoshiElement {

	public VarElement(Element element) {
		this("var", element);

		initValueAttributeName(element);
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

	public void addAttributes(String readableSyntax) {
		String[] items = readableSyntax.split("\\|", -1);

		addAttribute("name", items[1].trim());

		String value = items[2].trim();

		if (value.contains("Util#")) {
			valueAttributeName = "method";
		}
		else {
			valueAttributeName = "value";
		}

		addAttribute(valueAttributeName, value);
	}

	@Override
	public void addElements(String readableSyntax) {
	}

	public String getVarName() {
		return attributeValue("name");
	}

	public String getVarValue() {
		return attributeValue(valueAttributeName);
	}

	public void setNamePadLength(int namePadLength) {
		this.namePadLength = namePadLength;
	}

	public void setValuePadLength(int valuePadLength) {
		this.valuePadLength = valuePadLength;
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		Element parentElement = getParent();

		String parentElementName = parentElement.getName();

		if (parentElementName.equals("command") ||
			parentElementName.equals("set-up") ||
			parentElementName.equals("tear-down")) {

			String previousSiblingElementName = null;

			Element previousSiblingElement = getPreviousSibling();

			if (previousSiblingElement != null) {
				previousSiblingElementName = getPreviousSibling().getName();
			}

			if ((previousSiblingElement == null) ||
				!previousSiblingElementName.equals(getName())) {

				sb.append("\n\t");
				sb.append(getReadableExecuteKey());
				sb.append(" ");
				sb.append(getReadableVariableKey());
			}
		}

		sb.append("\n\t\t");
		sb.append("|");
		sb.append(_pad(getVarName(), namePadLength));
		sb.append("|");
		sb.append(_pad(getVarValue(), valuePadLength));
		sb.append("|");

		return sb.toString();
	}

	protected String getReadableVariableKey() {
		return THESE_VARIABLES;
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

		try {
			throw new IllegalArgumentException(
				"Invalid variable element " + Dom4JUtil.format(element));
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException("Invalid variable element");
		}
	}

	protected int namePadLength;
	protected String valueAttributeName;
	protected int valuePadLength;

	private String _pad(String s, int padLength) {
		if (s == null) {
			s = "";
		}

		int length = s.length();

		if (length <= padLength) {
			int pad = 1 + padLength - length;

			StringBuilder sb = new StringBuilder();

			sb.append(" ");
			sb.append(s);

			for (int i = 0; i < pad; i++) {
				sb.append(" ");
			}

			return sb.toString();
		}

		return s;
	}

}