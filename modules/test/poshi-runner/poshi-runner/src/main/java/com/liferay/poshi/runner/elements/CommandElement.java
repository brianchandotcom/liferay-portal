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
public class CommandElement extends PoshiElement {

	public CommandElement(Element element) {
		this("command", element);
	}

	public CommandElement(String readableSyntax) {
		this("command", readableSyntax);
	}

	public CommandElement(String name, Element element) {
		super(name, element);
	}

	public CommandElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		StringBuilder sb = new StringBuilder();

		for (String line : readableSyntax.split("\n")) {
			line = line.trim();

			String endKey = " {";

			if (line.endsWith(endKey)) {
				String startKey = "test";

				if (line.startsWith(startKey)) {
					int start = startKey.length();
					int end = line.length() - endKey.length();

					String name = line.substring(start, end);

					addAttribute("name", name);
				}

				continue;
			}

			if (line.equals(");")) {
				sb.append(line);

				addElementFromReadableSyntax(sb.toString());

				sb.setLength(0);

				continue;
			}

			if (line.endsWith(";")) {
				addElementFromReadableSyntax(line);

				continue;
			}

			if (line.equals("}")) {
				continue;
			}

			if (line.startsWith("@description")) {
				PoshiElement poshiElement = new DescriptionElement(line);

				add(poshiElement);

				continue;
			}

			if (line.startsWith("@")) {
				String name = getNameFromAssignment(line);
				String value = getQuotedContent(line);

				addAttribute(name, value);

				continue;
			}

			sb.append(line);
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

		String readableSyntax = super.toReadableSyntax();

		sb.append(createReadableBlock(readableSyntax));

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

			if (line.startsWith("setUp") || line.startsWith("tearDown")) {
				continue;
			}

			if ((line.endsWith(" {") && line.startsWith("test")) ||
				line.startsWith("@")) {

				readableBlocks.add(line);

				continue;
			}

			sb.append(line);
			sb.append("\n");

			String readableBlock = sb.toString();

			readableBlock = readableBlock.trim();

			if (isValidReadableBlock(readableBlock)) {
				readableBlocks.add(readableBlock);

				sb.setLength(0);
			}
		}

		return readableBlocks;
	}

	protected String getReadableCommandTitle() {
		return "test" + attributeValue("name");
	}

	@Override
	protected boolean isBalanceValidationRequired(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (readableSyntax.endsWith(";") || readableSyntax.endsWith("}")) {
			return true;
		}

		return false;
	}

}