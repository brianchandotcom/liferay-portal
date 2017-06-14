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

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.BACKGROUND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.DESCRIPTION;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.FEATURE;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.PRIORITY;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SCENARIO;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SET_UP;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.TEAR_DOWN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_PROPERTIES;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_VARIABLES;
import static com.liferay.poshi.runner.util.StringPool.COLON;
import static com.liferay.poshi.runner.util.StringPool.SPACE;

import com.liferay.poshi.runner.util.StringUtil;

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
	public void addAttributes(String readableSyntax) {
		if (readableSyntax.contains(DESCRIPTION + COLON)) {
			_addDescriptionAttribute(readableSyntax);
		}

		addAttribute("name", _getCommandName(readableSyntax));

		_addPriorityAttribute(readableSyntax);
	}

	@Override
	public void addElements(String readableSyntax) {
		List<String> readableBlocks = StringUtil.partition(
			readableSyntax, READABLE_EXECUTE_BLOCK_KEYS);

		for (String readableBlock : readableBlocks) {
			if (readableBlock.contains(BACKGROUND) ||
				readableBlock.contains(FEATURE) ||
				readableBlock.contains(SCENARIO) ||
				readableBlock.contains(SET_UP) ||
				readableBlock.contains(TEAR_DOWN)) {

				continue;
			}

			if (readableBlock.contains(THESE_PROPERTIES) ||
				readableBlock.contains(THESE_VARIABLES)) {

				addVariableElements(readableBlock);

				continue;
			}

			add(PoshiElementFactory.newPoshiElement(readableBlock));
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");
		sb.append(getReadableCommandTitle());

		if (attributeValue("name") != null) {
			String name = attributeValue("name");

			sb.append(toPhrase(name));
		}

		if (attributeValue("description") != null) {
			String description = attributeValue("description");

			sb.append("\n");
			sb.append(DESCRIPTION);
			sb.append(": ");
			sb.append(description);
		}

		if (attributeValue("priority") != null) {
			String priority = attributeValue("priority");

			sb.append("\n");
			sb.append(PRIORITY);
			sb.append(": ");
			sb.append(priority);
		}

		sb.append(super.toReadableSyntax());

		return sb.toString();
	}

	protected String getReadableCommandTitle() {
		return SCENARIO + COLON + SPACE;
	}

	private void _addDescriptionAttribute(String readableSyntax) {
		String description = getAttributeValue(
			DESCRIPTION + COLON, readableSyntax);

		addAttribute("description", description);
	}

	private void _addPriorityAttribute(String readableSyntax) {
		String priority = getAttributeValue(PRIORITY + COLON, readableSyntax);

		addAttribute("priority", priority);
	}

	private String _getCommandName(String readableSyntax) {
		String scenario = getAttributeValue(SCENARIO + COLON, readableSyntax);

		return StringUtil.removeSpaces(scenario);
	}

}