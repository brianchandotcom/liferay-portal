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

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AT_LOCATOR;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.GIVEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THE_VALUE;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.WHEN;

import com.liferay.poshi.runner.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ExecuteElement extends PoshiElement {

	public ExecuteElement(Element element) {
		super("execute", element);
	}

	public ExecuteElement(String readableSyntax) {
		super("execute", readableSyntax);
	}

	@Override
	public void addAttributes(String readableSyntax) {
		if (readableSyntax.contains(AT_LOCATOR) ||
			readableSyntax.contains(THE_VALUE)) {

			_attributes = new TreeMap<>();

			_addFunctionAttributes(readableSyntax);

			return;
		}

		addAttribute("macro", _getClassCommandName(readableSyntax));
	}

	@Override
	public void addElements(String readableSyntax) {
		List<String> readableBlocks = StringUtil.partition(
			readableSyntax, READABLE_VARIABLE_BLOCK_KEYS);

		for (String readableBlock : readableBlocks) {
			readableBlock = readableBlock.trim();

			if (readableBlock.contains(AND) || readableBlock.contains(GIVEN) ||
				readableBlock.contains(THEN) || readableBlock.contains(WHEN)) {

				continue;
			}

			PoshiElement poshiElement = PoshiElementFactory.newPoshiElement(
				readableBlock);

			add(poshiElement);
		}
	}

	@Override
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\t");
		sb.append(getReadableExecuteKey());

		if (attributeValue("function") != null) {
			sb.append(" ");

			String function = attributeValue("function");

			sb.append(_getReadableSyntaxCommandPhrase(function));

			List<String> functionAttributes = Arrays.asList(
				"value1", "locator1", "value2", "locator2");

			for (String functionAttribute : functionAttributes) {
				String functionAttributeValue = attributeValue(
					functionAttribute);

				if (functionAttributeValue != null) {
					if (functionAttribute.startsWith("locator")) {
						sb.append(" ");
						sb.append(AT_LOCATOR);
					}
					else {
						sb.append(" ");
						sb.append(THE_VALUE);
					}

					sb.append(" '");
					sb.append(functionAttributeValue);
					sb.append("'");
				}
			}
		}
		else if (attributeValue("macro") != null) {
			sb.append(" ");

			String macro = attributeValue("macro");

			sb.append(_getReadableSyntaxCommandPhrase(macro));
		}

		sb.append(super.toReadableSyntax());

		return sb.toString();
	}

	private void _addFunctionAttribute(
		String readableSyntax, String attributeType) {

		String attributeValue = getAttributeValue("'", "'", readableSyntax);

		if (attributeValue(attributeType + "1") == null) {
			_attributes.put(attributeType + "1", attributeValue);

			return;
		}

		_attributes.put(attributeType + "2", attributeValue);
	}

	private void _addFunctionAttributes(String readableSyntax) {
		String[] keys = {AT_LOCATOR, THE_VALUE};

		List<String> functionItems = StringUtil.partition(readableSyntax, keys);

		for (String functionItem : functionItems) {
			if (functionItem.contains(AT_LOCATOR)) {
				_addFunctionAttribute(functionItem, "locator");

				continue;
			}

			if (functionItem.contains(THE_VALUE)) {
				_addFunctionAttribute(functionItem, "value");

				continue;
			}

			_attributes.put("function", _getClassCommandName(functionItem));
		}

		for (String key : _attributes.keySet()) {
			addAttribute(key, _attributes.get(key));
		}
	}

	private String _getClassCommandName(String readableSyntax) {
		int index = readableSyntax.indexOf("\n");

		if (index < 0) {
			index = readableSyntax.length();
		}

		String line = readableSyntax.substring(0, index);

		for (String key : READABLE_EXECUTE_BLOCK_KEYS) {
			if (line.startsWith(key)) {
				Pattern pattern = Pattern.compile(
					".*?" + key + ".*?.([A-z]*)(.*)");

				Matcher matcher = pattern.matcher(line);

				if (matcher.find()) {
					StringBuilder sb = new StringBuilder();

					sb.append(matcher.group(1));

					String commandName = matcher.group(2);

					commandName = StringUtil.removeSpaces(commandName);

					if (commandName.length() > 0) {
						sb.append("#");
						sb.append(commandName);
					}

					return sb.toString();
				}
			}
		}

		return null;
	}

	private String _getReadableSyntaxCommandPhrase(String classCommandName) {
		StringBuilder sb = new StringBuilder();

		if (classCommandName.contains("#")) {
			String className = classCommandName.split("#")[0];

			sb.append(className);

			sb.append(" ");

			String commandName = classCommandName.split("#")[1];

			String commandPhrase = toPhrase(commandName);

			sb.append(commandPhrase);

			return sb.toString();
		}

		return classCommandName;
	}

	private Map<String, String> _attributes;

}