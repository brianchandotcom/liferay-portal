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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

/**
 * @author Kenji Heigel
 */
public class ExecuteElement extends PoshiElement {

	public static boolean isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		readableSyntax = readableSyntax.trim();

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (readableSyntax.startsWith("property ")) {
			return false;
		}

		if (readableSyntax.startsWith("var ") &&
			readableSyntax.contains(" = return(")) {

			return true;
		}

		if (readableSyntax.startsWith("var ")) {
			return false;
		}

		if (!readableSyntax.endsWith(");")) {
			return false;
		}

		return true;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		if (ReturnElement.isElementType(this, readableSyntax)) {
			add(new ReturnElement(readableSyntax));

			readableSyntax = RegexUtil.getGroup(
				readableSyntax, "return\\((.*),", 1);
		}

		String executeType = "macro";

		String content = getParentheticalContent(readableSyntax);

		if (content.contains("locator1") || content.contains("locator2") ||
			content.contains("value1") || content.contains("value2")) {

			executeType = "function";
		}

		String executeCommandName = RegexUtil.getGroup(
			readableSyntax, "([^\\s]*)\\(", 1);

		executeCommandName = executeCommandName.replace(".", "#");

		if (!executeCommandName.contains("#") && (content.length() == 0)) {
			executeType = "function";
		}

		addAttribute(executeType, executeCommandName);

		if (content.length() == 0) {
			return;
		}

		List<String> assignments = new ArrayList<>();

		Matcher matcher = _assignmentPattern.matcher(content);

		while (matcher.find()) {
			assignments.add(matcher.group());
		}

		for (String assignment : assignments) {
			assignment = assignment.trim();

			if (executeType.equals("macro")) {
				assignment = "var " + assignment + ";";

				add(PoshiElement.newPoshiElement(this, assignment));

				continue;
			}

			String name = getNameFromAssignment(assignment);
			String value = getQuotedContent(assignment);

			addAttribute(name, value);
		}
	}

	@Override
	public String toReadableSyntax() {
		if (attributeValue("function") != null) {
			StringBuilder sb = new StringBuilder();

			for (PoshiElementAttribute poshiElementAttribute :
					toPoshiElementAttributes(attributeList())) {

				String name = poshiElementAttribute.getName();

				if (name.equals("function")) {
					continue;
				}

				sb.append(poshiElementAttribute.toReadableSyntax());
				sb.append(", ");
			}

			if (sb.length() > 2) {
				sb.setLength(sb.length() - 2);
			}

			return createReadableBlock(sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		PoshiElement returnElement = null;

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			if (poshiElement instanceof ReturnElement) {
				returnElement = poshiElement;

				continue;
			}

			sb.append(poshiElement.toReadableSyntax());
		}

		String readableBlock = createReadableBlock(sb.toString());

		if (returnElement == null) {
			return readableBlock;
		}

		return returnElement.createReadableBlock(readableBlock);
	}

	protected ExecuteElement(Element element) {
		super("execute", element);
	}

	protected ExecuteElement(String readableSyntax) {
		super("execute", readableSyntax);
	}

	protected ExecuteElement(String name, Element element) {
		super(name, element);
	}

	protected ExecuteElement(String name, String readableSyntax) {
		super(name, readableSyntax);
	}

	@Override
	protected String createReadableBlock(String content) {
		StringBuilder sb = new StringBuilder();

		String blockName = getBlockName();
		String pad = getPad();

		sb.append("\n\n");
		sb.append(pad);
		sb.append(blockName.replace("#", "."));
		sb.append("(");

		String trimmedContent = content.trim();

		if (!trimmedContent.equals("")) {
			if (content.contains("\n")) {
				content = content.replaceAll("\n", ",\n" + pad);
				content = content.replaceFirst(",", "");
				content = content + "\n" + pad;
			}

			sb.append(content);
		}

		sb.append(");");

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		if (attributeValue("function") != null) {
			return attributeValue("function");
		}

		return attributeValue("macro");
	}

	private static final String _ELEMENT_NAME = "execute";

	private static final Pattern _assignmentPattern = Pattern.compile(
		"([^,]*? = \".*?\")");

	static {
		PoshiElementFactory poshiElementFactory = new PoshiElementFactory() {

			@Override
			public PoshiElement newPoshiElement(Element element) {
				if (isElementType(_ELEMENT_NAME, element)) {
					return new ExecuteElement(element);
				}

				return null;
			}

			@Override
			public PoshiElement newPoshiElement(
				PoshiElement parentPoshiElement, String readableSyntax) {

				if (isElementType(parentPoshiElement, readableSyntax)) {
					return new ExecuteElement(readableSyntax);
				}

				return null;
			}

		};

		PoshiElement.addPoshiElementFactory(poshiElementFactory);
	}

}