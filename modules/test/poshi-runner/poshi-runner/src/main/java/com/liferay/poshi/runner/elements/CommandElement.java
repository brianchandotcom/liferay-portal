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

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SCENARIO;
import static com.liferay.poshi.runner.util.StringPool.COLON;

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
	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");
		sb.append(getReadableCommandTitle());
		sb.append(" ");

		if (attributeValue("name") != null) {
			String name = attributeValue("name");

			sb.append(toPhrase(name));
		}

		if (attributeValue("description") != null) {
			String description = attributeValue("description");

			sb.append("\n");
			sb.append("Description: ");
			sb.append(description);
		}

		if (attributeValue("priority") != null) {
			String priority = attributeValue("priority");

			sb.append("\n");
			sb.append("Priority: ");
			sb.append(priority);
		}

		sb.append(super.toReadableSyntax());

		return sb.toString();
	}

	protected String getReadableCommandTitle() {
		return SCENARIO + COLON;
	}

}