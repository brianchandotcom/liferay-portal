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

			if (line.startsWith("@")) {
				String name = getNameFromAssignment(line);
				String value = getValueFromAssignment(line);

				addAttribute(name, value);

				continue;
			}

			sb.append(line);
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

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

	protected String getReadableCommandTitle() {
		return "test" + attributeValue("name");
	}

}