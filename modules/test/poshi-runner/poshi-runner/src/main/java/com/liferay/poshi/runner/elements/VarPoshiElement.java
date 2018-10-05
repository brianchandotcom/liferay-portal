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

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.StringUtil;
import com.liferay.poshi.runner.util.Validator;

import java.io.IOException;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class VarPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new VarPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (_isElementType(poshiScript)) {
			return new VarPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	public String getVarValue() {
		if (valueAttributeName == null) {
			for (Node node : Dom4JUtil.toNodeList(content())) {
				if (node instanceof CDATA) {
					StringBuilder sb = new StringBuilder();

					sb.append("\'\'\'");
					sb.append(node.getText());
					sb.append("\'\'\'");

					return sb.toString();
				}
			}
		}

		return attributeValue(valueAttributeName);
	}

	@Override
	public void parsePoshiScript(String poshiScript) {
		if (poshiScript.startsWith("static")) {
			addAttribute("static", "true");

			poshiScript = poshiScript.replaceFirst("static", "");

			poshiScript = poshiScript.trim();
		}

		String name = getNameFromAssignment(poshiScript);

		if (name.contains(" ")) {
			int index = name.indexOf(" ");

			name = name.substring(index);
		}

		name = name.trim();

		addAttribute("name", name);

		String value = getValueFromAssignment(poshiScript);

		if (value.startsWith("\'\'\'")) {
			addCDATA(getPoshiScriptEscapedContent(value));

			return;
		}

		if (value.startsWith("new ")) {
			addAttribute("from", getDoubleQuotedContent(value));

			value = value.replace("new ", "");

			int index = value.indexOf("(");

			String type = value.substring(0, index);

			addAttribute("type", type);

			return;
		}

		if (value.endsWith("\"") && value.startsWith("\"")) {
			value = getDoubleQuotedContent(value);

			if (value.endsWith("}") && value.startsWith("${")) {
				String bracedContent = getBracedContent(value);

				if (bracedContent.contains(".hash(")) {
					int index = bracedContent.indexOf(".");

					String fromValue = StringUtil.combine(
						"${", bracedContent.substring(0, index), "}");

					addAttribute("from", fromValue);

					addAttribute("hash", getSingleQuotedContent(bracedContent));

					return;
				}

				if (bracedContent.contains("[")) {
					int index = bracedContent.indexOf("[");

					String fromValue = StringUtil.combine(
						"${", bracedContent.substring(0, index), "}");

					addAttribute("from", fromValue);

					addAttribute("index", getBracketedContent(bracedContent));

					return;
				}
			}

			value = StringEscapeUtils.unescapeXml(value);

			addAttribute("value", value);

			return;
		}

		if (isValidUtilityClassName(value) || value.startsWith("selenium.")) {
			value = value.replaceFirst("\\.", "#");

			String content = getParentheticalContent(value);

			if (!content.equals("")) {
				value = value.replace(
					content, swapParameterQuotations(content));
			}

			addAttribute("method", value);
		}
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n\t");

		String staticAttribute = attributeValue("static");

		if (staticAttribute != null) {
			sb.append("static ");
		}

		PoshiElement parentElement = (PoshiElement)getParent();

		if (!(parentElement instanceof ExecutePoshiElement)) {
			sb.append(getName());
			sb.append(" ");
		}

		if (Validator.isNotNull(valueAttributeName)) {
			if (valueAttributeName.equals("from")) {
				if (attribute("type") != null) {
					sb.append(attributeValue("type"));
					sb.append(" ");
				}
			}
		}

		String name = attributeValue("name");

		sb.append(name);

		sb.append(" = ");

		String value = getVarValue();

		if (Validator.isNotNull(valueAttributeName)) {
			if (valueAttributeName.equals("from")) {
				if (attribute("hash") != null) {
					String innerValue = getBracedContent(value);

					String newInnerValue = StringUtil.combine(
						innerValue, ".hash('", attributeValue("hash"), "')");

					value = value.replace(innerValue, newInnerValue);

					value = doubleQuoteContent(value);
				}
				else if (attribute("index") != null) {
					String innerValue = getBracedContent(value);

					String newInnerValue = StringUtil.combine(
						innerValue, "[", attributeValue("index"), "]");

					value = value.replace(innerValue, newInnerValue);

					value = doubleQuoteContent(value);
				}
				else if (attribute("type") != null) {
					value = StringUtil.combine(
						"new ", attributeValue("type"), "(\"", value, "\")");
				}
			}
			else if (valueAttributeName.equals("method")) {
				if (isValidUtilityClassName(value) ||
					value.startsWith("selenium#")) {

					value = value.replaceFirst("#", ".");

					String content = getParentheticalContent(value);

					if (!content.equals("")) {
						value = value.replace(
							content, swapParameterQuotations(content));
					}
				}
			}
			else {
				value = StringEscapeUtils.escapeXml10(value);

				value = doubleQuoteContent(value);
			}
		}

		sb.append(value);

		if (!(parentElement instanceof ExecutePoshiElement)) {
			sb.append(";");
		}

		return sb.toString();
	}

	protected VarPoshiElement() {
	}

	protected VarPoshiElement(Element element) {
		this(_ELEMENT_NAME, element);
	}

	protected VarPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected VarPoshiElement(
		PoshiElement parentPoshiElement, String poshiScript) {

		this(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected VarPoshiElement(String name, Element element) {
		super(name, element);

		if (isElementType(name, element)) {
			initValueAttributeName(element);
		}
	}

	protected VarPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected VarPoshiElement(
		String name, PoshiElement parentPoshiElement, String poshiScript) {

		super(name, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return null;
	}

	protected void initValueAttributeName(Element element) {
		if (element.attribute("from") != null) {
			valueAttributeName = "from";

			return;
		}

		if (element.attribute("method") != null) {
			valueAttributeName = "method";

			return;
		}

		if (element.attribute("value") != null) {
			valueAttributeName = "value";

			return;
		}

		for (Node node : Dom4JUtil.toNodeList(element.content())) {
			if (node instanceof CDATA) {
				add((CDATA)node.clone());

				return;
			}
		}

		try {
			throw new IllegalArgumentException(
				"Invalid variable element " + Dom4JUtil.format(element));
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException("Invalid variable element", ioe);
		}
	}

	protected String swapParameterQuotations(String parametersString) {
		StringBuilder sb = new StringBuilder();

		parametersString = parametersString.trim();

		boolean singleQuote = false;

		if (parametersString.endsWith("'") &&
			parametersString.startsWith("'")) {

			singleQuote = true;
		}

		List<String> parameters = getMethodParameters(parametersString);

		for (String parameter : parameters) {
			if (singleQuote) {
				parameter = getSingleQuotedContent(parameter);

				parameter = parameter.replace("\\\'", "'");
				parameter = parameter.replace("\"", "&quot;");

				parameter = doubleQuoteContent(parameter);
			}
			else {
				parameter = getDoubleQuotedContent(parameter);

				parameter = parameter.replace("'", "\\\'");
				parameter = parameter.replace("&quot;", "\"");

				parameter = singleQuoteContent(parameter);
			}

			sb.append(parameter);

			sb.append(", ");
		}

		sb.setLength(sb.length() - 2);

		return sb.toString();
	}

	protected String valueAttributeName;

	private boolean _isElementType(String poshiScript) {
		if (isValidPoshiScriptStatement(_statementPattern, poshiScript) ||
			isVarAssignedToMacroInvocation(poshiScript)) {

			return true;
		}

		return false;
	}

	private static final String _ELEMENT_NAME = "var";

	private static final Pattern _statementPattern = Pattern.compile(
		"^" + VAR_NAME_REGEX + ASSIGNMENT_REGEX + ".*" + STATEMENT_END_REGEX,
		Pattern.DOTALL);

}