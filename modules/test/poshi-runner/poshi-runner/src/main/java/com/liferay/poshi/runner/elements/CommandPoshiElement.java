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
import com.liferay.poshi.runner.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class CommandPoshiElement extends BasePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new CommandPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(readableSyntax)) {
			return new CommandPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.endsWith("}") || readableBlock.endsWith(";") ||
				readableBlock.startsWith("@description")) {

				add(PoshiElementFactory.newPoshiElement(this, readableBlock));

				continue;
			}

			if (readableBlock.endsWith("{")) {
				String name = RegexUtil.getGroup(
					readableBlock, "test([\\w]*)", 1);

				addAttribute("name", name);

				continue;
			}

			if (readableBlock.startsWith("@")) {
				String name = getNameFromAssignment(readableBlock);
				String value = getQuotedContent(readableBlock);

				addAttribute(name, value);
			}
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement :
				toPoshiElements(elements("description"))) {

			sb.append("\n\t@description = \"");
			sb.append(poshiElement.attributeValue("message"));
			sb.append("\"");
		}

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			String name = poshiElementAttribute.getName();

			if (name.equals("name")) {
				continue;
			}

			sb.append("\n\t@");
			sb.append(poshiElementAttribute.toReadableSyntax());
		}

		List<String> readableBlocks = new ArrayList<>();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			readableBlocks.add(poshiElement.toReadableSyntax());
		}

		sb.append(createReadableBlock(readableBlocks));

		return sb.toString();
	}

	protected CommandPoshiElement() {
	}

	protected CommandPoshiElement(Element element) {
		this(_ELEMENT_NAME, element);
	}

	protected CommandPoshiElement(String readableSyntax) {
		this(_ELEMENT_NAME, readableSyntax);
	}

	protected CommandPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected CommandPoshiElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	protected String createReadableBlock(List<String> items) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");

		String pad = getPad();

		sb.append(pad);

		sb.append(getBlockName());
		sb.append(" {");

		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);

			if (i == 0) {
				if (item.startsWith("\n\n")) {
					item = item.replaceFirst("\n\n", "\n");
				}
			}

			if (isCDATAVar(item)) {
				item = item.replaceFirst("var ", pad + "var ");

				sb.append(item);

				continue;
			}

			item = item.replaceAll("\n", "\n" + pad);

			sb.append(item.replaceAll("\n\t\n", "\n\n"));
		}

		sb.append("\n");
		sb.append(pad);
		sb.append("}");

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		return getReadableCommandTitle();
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			line = line.trim();

			if (line.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (line.startsWith("setUp") || line.startsWith("tearDown")) {
				continue;
			}

			if ((line.endsWith(" {") && line.startsWith("test")) ||
				line.startsWith("@")) {

				readableBlocks.add(line);

				continue;
			}

			if (!line.startsWith("else {") && !line.startsWith("else if")) {
				String readableBlock = sb.toString();

				readableBlock = readableBlock.trim();

				if (isValidReadableBlock(readableBlock)) {
					readableBlocks.add(readableBlock);

					sb.setLength(0);
				}
			}

			sb.append(line);
			sb.append("\n");
		}

		return readableBlocks;
	}

	protected String getReadableCommandTitle() {
		return "test" + attributeValue("name");
	}

	protected boolean isCDATAVar(String readableSyntax) {
		String trimmedReadableSyntax = readableSyntax.trim();

		if (!readableSyntax.contains("return(\n") &&
			(StringUtil.count(readableSyntax, "\n") > 1) &&
			trimmedReadableSyntax.startsWith("var ")) {

			return true;
		}

		return false;
	}

	private boolean _isElementType(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (!readableSyntax.endsWith("}")) {
			return false;
		}

		for (String line : readableSyntax.split("\n")) {
			line = line.trim();

			if (line.startsWith("@")) {
				continue;
			}

			if (!(line.endsWith("{") && line.startsWith("test"))) {
				return false;
			}

			break;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "command";

}