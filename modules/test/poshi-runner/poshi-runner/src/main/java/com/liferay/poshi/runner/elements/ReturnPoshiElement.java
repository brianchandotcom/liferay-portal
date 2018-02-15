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

import com.liferay.poshi.runner.util.RegexUtil;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ReturnPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ReturnPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new ReturnPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		String returnFrom = RegexUtil.getGroup(readableSyntax, ".*,(.*)\\)", 1);

		addAttribute("from", returnFrom.trim());

		String returnName = RegexUtil.getGroup(readableSyntax, "var(.*?)=", 1);

		addAttribute("name", returnName.trim());
	}

	@Override
	public String toReadableSyntax() {
		return "";
	}

	protected ReturnPoshiElement() {
	}

	protected ReturnPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ReturnPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String createReadableBlock(String content) {
		StringBuilder sb = new StringBuilder();

		String blockName = getBlockName();
		String pad = getPad();

		sb.append("\n\n");
		sb.append(pad);
		sb.append(blockName);
		sb.append("(");

		String trimmedContent = content.trim();

		if (!trimmedContent.equals("")) {
			if (content.contains("\n")) {
				content = content.replace("\n\n", "\n");
				content = content.replaceAll("\n", "\n" + pad);
			}

			if (trimmedContent.endsWith(";")) {
				int index = content.lastIndexOf(";");

				content = content.substring(0, index);
			}

			sb.append(content);
			sb.append(",");

			String contentPad = RegexUtil.getGroup(content, "([\\s]*).*", 1);

			sb.append(contentPad);

			sb.append(attributeValue("from"));
			sb.append("\n");
			sb.append(pad);
		}

		sb.append(");");

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		StringBuilder sb = new StringBuilder();

		sb.append("var ");
		sb.append(attributeValue("name"));
		sb.append(" = return");

		return sb.toString();
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if ((parentPoshiElement instanceof ExecutePoshiElement) &&
			readableSyntax.contains("return(\n")) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "return";

}