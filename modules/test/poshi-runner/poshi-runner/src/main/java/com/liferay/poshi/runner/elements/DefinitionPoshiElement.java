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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class DefinitionPoshiElement extends BasePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new DefinitionPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(readableSyntax)) {
			return new DefinitionPoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		for (String readableBlock : getReadableBlocks(readableSyntax)) {
			if (readableBlock.startsWith("@") &&
				!readableBlock.startsWith("@description") &&
				!readableBlock.startsWith("@priority")) {

				String name = getNameFromAssignment(readableBlock);
				String value = getQuotedContent(readableBlock);

				addAttribute(name, value);

				continue;
			}

			add(PoshiElementFactory.newPoshiElement(this, readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			sb.append("\n@");

			sb.append(poshiElementAttribute.toReadableSyntax());
		}

		StringBuilder content = new StringBuilder();

		for (PoshiElement poshiElement :
				toPoshiElements(elements("property"))) {

			content.append(poshiElement.toReadableSyntax());
		}

		content.append("\n");

		for (PoshiElement poshiElement : toPoshiElements(elements("var"))) {
			content.append(poshiElement.toReadableSyntax());
		}

		content.append("\n");

		for (PoshiElement poshiElement : toPoshiElements(elements("set-up"))) {
			content.append(poshiElement.toReadableSyntax());
		}

		content.append("\n");

		for (PoshiElement poshiElement :
				toPoshiElements(elements("tear-down"))) {

			content.append(poshiElement.toReadableSyntax());
		}

		for (PoshiElement poshiElement : toPoshiElements(elements("command"))) {
			content.append("\n");
			content.append(poshiElement.toReadableSyntax());
		}

		sb.append(createReadableBlock(content.toString()));

		String string = sb.toString();

		return string.trim();
	}

	protected DefinitionPoshiElement() {
	}

	protected DefinitionPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected DefinitionPoshiElement(String readableSyntax) {
		super(_ELEMENT_NAME, readableSyntax);
	}

	@Override
	protected String getBlockName() {
		return "definition";
	}

	@Override
	protected String getPad() {
		return "";
	}

	protected List<String> getReadableBlocks(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		List<String> readableBlocks = new ArrayList<>();

		for (String line : readableSyntax.split("\n")) {
			String trimmedLine = line.trim();

			if (trimmedLine.length() == 0) {
				sb.append("\n");

				continue;
			}

			if (trimmedLine.startsWith("@") &&
				!trimmedLine.startsWith("@description") &&
				!trimmedLine.startsWith("@priority")) {

				readableBlocks.add(line);

				continue;
			}

			if (trimmedLine.startsWith("definition {")) {
				continue;
			}

			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}

			sb.append(line);
			sb.append("\n");
		}

		return readableBlocks;
	}

	@Override
	protected boolean isBalanceValidationRequired(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if ((readableSyntax.startsWith("@") && readableSyntax.contains("{")) ||
			readableSyntax.startsWith("setUp") ||
			readableSyntax.startsWith("tearDown") ||
			readableSyntax.startsWith("test")) {

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

			if (!line.equals("definition {")) {
				return false;
			}

			break;
		}

		return true;
	}

	private static final String _ELEMENT_NAME = "definition";

}