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

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AT_LOCATOR;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THE_VALUE;

import com.liferay.poshi.runner.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ExecuteElement extends PoshiElement {

	public ExecuteElement(Element element) {
		super("execute", element);
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
				if (attributeValue(functionAttribute) != null) {
					if (functionAttribute.startsWith("locator")) {
						sb.append(" ");
						sb.append(AT_LOCATOR);
					}
					else {
						sb.append(" ");
						sb.append(THE_VALUE);
					}

					sb.append(" '");

					sb.append(attributeValue(functionAttribute));

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

}