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

import org.apache.commons.lang3.StringEscapeUtils;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ExecutePoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ExecutePoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ExecutePoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public void parsePoshiScript(String poshiScript) {
		String executeType = "macro";
		String fileType = getFileType();

		if (fileType.equals("function")) {
			executeType = "function";

			if (poshiScript.startsWith("selenium.")) {
				executeType = "selenium";

				poshiScript = poshiScript.replace("selenium.", "");
			}
		}
		else if (isValidUtilityClassName(poshiScript)) {
			executeType = "class";
		}
		else if (isValidFunctionFileName(poshiScript)) {
			executeType = "function";
		}

		if (executeType.equals("class")) {
			if (isValidUtilityClassName(poshiScript)) {
				addAttribute("class", getClassName(poshiScript));
				addAttribute("method", getCommandName(poshiScript));
			}

			String parentheticalContent = getParentheticalContent(poshiScript);

			for (String parameter : getMethodParameters(parentheticalContent)) {
				add(PoshiNodeFactory.newPoshiNode(this, parameter));
			}

			return;
		}

		if (poshiScript.startsWith("var ")) {
			PoshiNode returnPoshiNode = PoshiNodeFactory.newPoshiNode(
				this, poshiScript);

			if (returnPoshiNode instanceof ReturnPoshiElement) {
				add(returnPoshiNode);

				poshiScript = getValueFromAssignment(poshiScript);
			}
		}

		String executeCommandName = RegexUtil.getGroup(
			poshiScript, "([^\\s]*?)\\(", 1);

		executeCommandName = executeCommandName.replace(".", "#");

		addAttribute(executeType, executeCommandName);

		String content = getParentheticalContent(poshiScript);

		if (content.length() == 0) {
			return;
		}

		if (executeType.equals("selenium")) {
			List<String> methodParameters = getMethodParameters(content);

			for (int i = 0; i < methodParameters.size(); i++) {
				String methodParameter = methodParameters.get(i);

				String value = getDoubleQuotedContent(methodParameter);

				addAttribute("argument" + (i + 1), value);
			}

			return;
		}

		List<String> assignments = new ArrayList<>();

		Matcher matcher = nestedVarAssignmentPattern.matcher(content);

		while (matcher.find()) {
			assignments.add(matcher.group());
		}

		for (String assignment : assignments) {
			assignment = assignment.trim();

			boolean functionAttributeAdded = false;

			for (String functionAttributeName : _FUNCTION_ATTRIBUTE_NAMES) {
				if (assignment.startsWith(functionAttributeName)) {
					String name = getNameFromAssignment(assignment);

					String value = getDoubleQuotedContent(assignment);

					value = StringEscapeUtils.unescapeXml(value);

					addAttribute(name, value);

					functionAttributeAdded = true;

					break;
				}
			}

			if (functionAttributeAdded) {
				continue;
			}

			if (executeType.equals("selenium")) {
				String name = getNameFromAssignment(assignment);

				String value = getValueFromAssignment(assignment);

				value = getDoubleQuotedContent(value);

				addAttribute(name, value);

				continue;
			}

			if (assignment.endsWith(",")) {
				assignment = assignment.substring(0, assignment.length() - 1);
			}

			assignment = "var " + assignment + ";";

			add(PoshiNodeFactory.newPoshiNode(this, assignment));
		}
	}

	@Override
	public String toPoshiScript() {
		List<String> assignments = new ArrayList<>();

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributes())) {

			String poshiElementAttributeName = poshiElementAttribute.getName();

			if (poshiElementAttributeName.equals("class") ||
				poshiElementAttributeName.equals("function") ||
				poshiElementAttributeName.equals("macro") ||
				poshiElementAttributeName.equals("method") ||
				poshiElementAttributeName.equals("selenium")) {

				continue;
			}

			String fileType = getFileType();

			if (fileType.equals("function")) {
				String poshiElementAttributeValue =
					poshiElementAttribute.getValue();

				assignments.add(doubleQuoteContent(poshiElementAttributeValue));

				continue;
			}

			String poshiScript = poshiElementAttribute.toPoshiScript();

			assignments.add(poshiScript.trim());
		}

		ReturnPoshiElement returnPoshiElement = null;

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			if (poshiElement instanceof ReturnPoshiElement) {
				returnPoshiElement = (ReturnPoshiElement)poshiElement;

				continue;
			}

			String poshiScript = poshiElement.toPoshiScript();

			assignments.add(poshiScript.trim());
		}

		String poshiScriptSnippet = createPoshiScriptSnippet(assignments);

		if (returnPoshiElement == null) {
			return poshiScriptSnippet;
		}

		return returnPoshiElement.createPoshiScriptSnippet(poshiScriptSnippet);
	}

	protected ExecutePoshiElement() {
	}

	protected ExecutePoshiElement(Element element) {
		super("execute", element);
	}

	protected ExecutePoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected ExecutePoshiElement(
		PoshiElement parentPoshiElement, String poshiScript) {

		super("execute", parentPoshiElement, poshiScript);
	}

	protected ExecutePoshiElement(String name, Element element) {
		super(name, element);
	}

	protected ExecutePoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected ExecutePoshiElement(
		String name, PoshiElement parentPoshiElement, String poshiScript) {

		super(name, parentPoshiElement, poshiScript);
	}

	protected String createPoshiScriptSnippet(List<String> assignments) {
		StringBuilder sb = new StringBuilder();

		String blockName = getBlockName();
		String pad = getPad();

		sb.append("\n\n");
		sb.append(pad);
		sb.append(blockName.replace("#", "."));
		sb.append("(");

		boolean multilineSnippet = false;

		String assignmentsString = assignments.toString();

		int invocationStringLength =
			blockName.length() + assignmentsString.length();

		if ((invocationStringLength > 80) &&
			!isConditionValidInParent((PoshiElement)getParent())) {

			multilineSnippet = true;
		}

		for (String assignment : assignments) {
			if (multilineSnippet) {
				sb.append("\n\t");
				sb.append(pad);
			}

			sb.append(assignment);
			sb.append(",");

			if (!multilineSnippet) {
				sb.append(" ");
			}
		}

		if (!assignments.isEmpty()) {
			sb.setLength(sb.length() - 1);

			if (!multilineSnippet) {
				sb.setLength(sb.length() - 1);
			}
		}

		if (multilineSnippet) {
			sb.append("\n");
			sb.append(pad);
		}

		sb.append(");");

		return sb.toString();
	}

	@Override
	protected String getBlockName() {
		if (attributeValue("class") != null) {
			return attributeValue("class") + "." + attributeValue("method");
		}

		if (attributeValue("function") != null) {
			return attributeValue("function");
		}

		if (attributeValue("macro") != null) {
			return attributeValue("macro");
		}

		return "selenium." + attributeValue("selenium");
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (parentPoshiElement instanceof ExecutePoshiElement) {
			return false;
		}

		if ((isVarAssignedToMacroInvocation(poshiScript) ||
			 isValidPoshiScriptStatement(_statementPattern, poshiScript)) &&
			!isValidPoshiScriptStatement(
				_utilityInvocationStatementPattern, poshiScript)) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "execute";

	private static final String[] _FUNCTION_ATTRIBUTE_NAMES =
		{"locator1", "locator2", "value1", "value2"};

	private static final String _UTILITY_INVOCATION_REGEX =
		"(echo|fail|takeScreenshot)\\(.*?\\)";

	private static final Pattern _statementPattern = Pattern.compile(
		"^" + INVOCATION_REGEX + STATEMENT_END_REGEX, Pattern.DOTALL);
	private static final Pattern _utilityInvocationStatementPattern =
		Pattern.compile("^" + _UTILITY_INVOCATION_REGEX + STATEMENT_END_REGEX);

}