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
public class ExecutePoshiElement extends BasePoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ExecutePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String readableSyntax) {

		if (_isElementType(parentPoshiElement, readableSyntax)) {
			return new ExecutePoshiElement(readableSyntax);
		}

		return null;
	}

	@Override
	public void parseReadableSyntax(String readableSyntax) {
		if (readableSyntax.contains("return(\n")) {
			PoshiElement returnPoshiElement =
				PoshiElementFactory.newPoshiElement(this, readableSyntax);

			if (returnPoshiElement instanceof ReturnPoshiElement) {
				add(returnPoshiElement);

				readableSyntax = RegexUtil.getGroup(
					readableSyntax, "return\\((.*),", 1);
			}
		}

		String executeType = "macro";

		String content = getParentheticalContent(readableSyntax);

		String[] functionAttributeNames =
			{"locator1", "locator2", "value1", "value2"};

		for (String functionAttributeName : functionAttributeNames) {
			if (content.startsWith(functionAttributeName)) {
				executeType = "function";

				break;
			}
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

			boolean functionAttributeAdded = false;

			for (String functionAttributeName : functionAttributeNames) {
				if (assignment.startsWith(functionAttributeName)) {
					String name = getNameFromAssignment(assignment);
					String value = getQuotedContent(assignment);

					addAttribute(name, value);

					functionAttributeAdded = true;

					break;
				}
			}

			if (functionAttributeAdded) {
				continue;
			}

			assignment = "var " + assignment + ";";

			add(PoshiElementFactory.newPoshiElement(this, assignment));
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

			for (PoshiElement poshiElement : toPoshiElements(elements())) {
				String readableSyntax = poshiElement.toReadableSyntax();

				if (poshiElement instanceof VarPoshiElement) {
					sb.append(readableSyntax.trim());
					sb.append(", ");

					continue;
				}
			}

			if (sb.length() > 2) {
				sb.setLength(sb.length() - 2);
			}

			return createReadableBlock(sb.toString());
		}

		StringBuilder sb = new StringBuilder();

		ReturnPoshiElement returnPoshiElement = null;

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			if (poshiElement instanceof ReturnPoshiElement) {
				returnPoshiElement = (ReturnPoshiElement)poshiElement;

				continue;
			}

			sb.append(poshiElement.toReadableSyntax());
		}

		String readableBlock = createReadableBlock(sb.toString());

		if (returnPoshiElement == null) {
			return readableBlock;
		}

		return returnPoshiElement.createReadableBlock(readableBlock);
	}

	protected ExecutePoshiElement() {
	}

	protected ExecutePoshiElement(Element element) {
		super("execute", element);
	}

	protected ExecutePoshiElement(String readableSyntax) {
		super("execute", readableSyntax);
	}

	protected ExecutePoshiElement(String name, Element element) {
		super(name, element);
	}

	protected ExecutePoshiElement(String name, String readableSyntax) {
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

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String readableSyntax) {

		readableSyntax = readableSyntax.trim();

		if (parentPoshiElement instanceof ExecutePoshiElement) {
			return false;
		}

		if (!isBalancedReadableSyntax(readableSyntax)) {
			return false;
		}

		if (readableSyntax.startsWith("echo(") ||
			readableSyntax.startsWith("fail(") ||
			readableSyntax.startsWith("property ") ||
			readableSyntax.startsWith("takeScreenshot")) {

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

	private static final String _ELEMENT_NAME = "execute";

	private static final Pattern _assignmentPattern = Pattern.compile(
		"([^,]*? = \".*?\")");

}