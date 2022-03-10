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

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.script.PoshiScriptParserUtil;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
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
	public Element addAttribute(String name, String value) {
		if (name.equals("from") || name.equals("method") ||
			name.equals("value")) {

			valueAttributeName = name;
		}

		return super.addAttribute(name, value);
	}

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new VarPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(poshiScript)) {
			return new VarPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	public String getVarValue() {
		if (valueAttributeName == null) {
			List<Node> cdataNodes = new ArrayList<>();

			for (Node node : Dom4JUtil.toNodeList(content())) {
				if (node instanceof CDATA) {
					cdataNodes.add(node);
				}
			}

			StringBuilder sb = new StringBuilder();

			sb.append("\'\'\'");

			for (Node cdataNode : cdataNodes) {
				sb.append(cdataNode.getText());
			}

			sb.append("\'\'\'");

			return sb.toString();
		}

		return attributeValue(valueAttributeName);
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

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
			value = getPoshiScriptEscapedContent(value);

			if (value.contains("CDATA")) {
				Matcher nestedCDATAMatcher = _nestedCDATAPattern.matcher(value);

				nestedCDATAMatcher.find();

				String cdata1 = nestedCDATAMatcher.group("cdata1");
				String cdata2 = nestedCDATAMatcher.group("cdata2");

				add(new PoshiCDATA(cdata1));

				add(new PoshiCDATA(cdata2));

				return;
			}

			add(new PoshiCDATA(value));

			return;
		}

		if (value.startsWith("new ")) {
			addAttribute("from", getDoubleQuotedContent(value));

			value = StringUtil.replace(value, "new ", "");

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

		if ((!isValidFunctionFileName(value) && !isValidMacroFileName(value)) ||
			value.startsWith("selenium.")) {

			Matcher matcher = _varValueMathExpressionPattern.matcher(value);

			if (matcher.matches()) {
				String mathOperation = _mathOperatorsMap.get(matcher.group(2));

				String mathUtilValue = StringUtil.combine(
					"MathUtil#", mathOperation, "('", matcher.group(1), "', '",
					matcher.group(3), "')");

				addAttribute("method", mathUtilValue);

				return;
			}

			value = value.replaceFirst("\\.", "#");

			String content = getParentheticalContent(value);

			if (!content.equals("")) {
				value = StringUtil.replace(
					value, content, swapParameterQuotations(content));
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

		if (Validator.isNotNull(valueAttributeName) &&
			valueAttributeName.equals("from") && (attribute("type") != null)) {

			sb.append(attributeValue("type"));
			sb.append(" ");
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

					value = StringUtil.replace(
						value, innerValue, newInnerValue);

					value = doubleQuoteContent(value);
				}
				else if (attribute("index") != null) {
					String innerValue = getBracedContent(value);

					String newInnerValue = StringUtil.combine(
						innerValue, "[", attributeValue("index"), "]");

					value = StringUtil.replace(
						value, innerValue, newInnerValue);

					value = doubleQuoteContent(value);
				}
				else if (attribute("type") != null) {
					value = StringUtil.combine(
						"new ", attributeValue("type"), "(\"", value, "\")");
				}
			}
			else if (valueAttributeName.equals("method")) {
				if ((!isValidFunctionFileName(value) &&
					 !isValidMacroFileName(value)) ||
					value.startsWith("selenium#")) {

					value = value.replaceFirst("#", ".");

					String content = getParentheticalContent(value);

					if (!content.equals("")) {
						Matcher matcher = _mathUtilMethodCallPattern.matcher(
							value);

						String mathOperator = "";

						if (matcher.find()) {
							for (Map.Entry<String, String> entry :
									_mathOperatorsMap.entrySet()) {

								if (Objects.equals(
										entry.getValue(), matcher.group(1))) {

									mathOperator = " " + entry.getKey() + " ";

									break;
								}
							}
						}

						if (!mathOperator.equals("") &&
							!(parentElement instanceof ExecutePoshiElement)) {

							value =
								matcher.group(2) + mathOperator +
									matcher.group(3);
						}
						else {
							value = StringUtil.replace(
								value, content,
								swapParameterQuotations(content));
						}
					}
				}
			}
			else {
				value = StringUtil.replace(value, "\"", "&quot;");

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
		this(_ELEMENT_NAME);
	}

	protected VarPoshiElement(Element element) {
		this(_ELEMENT_NAME, element);
	}

	protected VarPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected VarPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		this(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected VarPoshiElement(String name) {
		super(name);
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
			String name, PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

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

		if (getText() != null) {
			return;
		}

		try {
			throw new IllegalArgumentException(
				"Invalid variable element " + Dom4JUtil.format(element));
		}
		catch (IOException ioException) {
			throw new IllegalArgumentException(
				"Invalid variable element", ioException);
		}
	}

	protected String swapParameterQuotations(String parametersString) {
		StringBuilder sb = new StringBuilder();

		parametersString = parametersString.trim();

		List<String> methodParameterValues =
			PoshiScriptParserUtil.getMethodParameterValues(parametersString);

		for (String methodParameterValue : methodParameterValues) {
			methodParameterValue = methodParameterValue.trim();

			if (methodParameterValue.endsWith("'") &&
				methodParameterValue.startsWith("'")) {

				methodParameterValue = getSingleQuotedContent(
					methodParameterValue);

				methodParameterValue = StringUtil.replace(
					methodParameterValue, "\\\'", "'");
				methodParameterValue = StringUtil.replace(
					methodParameterValue, "&quot;", "\"");

				methodParameterValue = StringUtil.replace(
					methodParameterValue, "\"", "\\\"");

				methodParameterValue = doubleQuoteContent(methodParameterValue);
			}
			else if (methodParameterValue.endsWith("\"") &&
					 methodParameterValue.startsWith("\"")) {

				methodParameterValue = getDoubleQuotedContent(
					methodParameterValue);

				methodParameterValue = StringUtil.replace(
					methodParameterValue, "'", "\\\'");
				methodParameterValue = StringUtil.replace(
					methodParameterValue, "\\\"", "\"");

				methodParameterValue = singleQuoteContent(methodParameterValue);
			}
			else {
				methodParameterValue = methodParameterValue.trim();
			}

			sb.append(methodParameterValue);

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

	private static final String _VAR_VALUE_MATH_EXPRESSION_REGEX;

	private static final String _VAR_VALUE_MATH_VALUE_REGEX =
		"[\\s]*(\\$\\{[\\w]*\\}|[\\d]*)[\\s]*";

	private static final String _VAR_VALUE_MULTILINE_REGEX = "'''.*?'''";

	private static final String _VAR_VALUE_OBJECT_REGEX =
		"(new[\\s]*|)[\\w\\.]*\\(.*?\\)";

	private static final String _VAR_VALUE_REGEX;

	private static final String _VAR_VALUE_STRING_REGEX = "\".*\"";

	private static final Map<String, String> _mathOperatorsMap =
		new HashMap<String, String>() {
			{
				put("*", "product");
				put("+", "sum");
				put("-", "difference");
				put("/", "quotient");
			}
		};
	private static final Pattern _mathUtilMethodCallPattern = Pattern.compile(
		"MathUtil\\.(\\w+)\\('(.+)', '(.+)'\\)");
	private static final Pattern _nestedCDATAPattern = Pattern.compile(
		"(?<cdata1><.+]])(?<cdata2>>.+>)");
	private static final Pattern _statementPattern;
	private static final Pattern _varValueMathExpressionPattern;

	static {
		_VAR_VALUE_MATH_EXPRESSION_REGEX =
			_VAR_VALUE_MATH_VALUE_REGEX + "([\\+\\-\\*\\/])" +
				_VAR_VALUE_MATH_VALUE_REGEX;

		_VAR_VALUE_REGEX = StringUtil.combine(
			"(", _VAR_VALUE_STRING_REGEX, "|", _VAR_VALUE_MATH_EXPRESSION_REGEX,
			"|", _VAR_VALUE_MULTILINE_REGEX, "|", _VAR_VALUE_OBJECT_REGEX, ")");

		_statementPattern = Pattern.compile(
			"^" + VAR_NAME_REGEX + ASSIGNMENT_REGEX + _VAR_VALUE_REGEX +
				VAR_STATEMENT_END_REGEX,
			Pattern.DOTALL);

		_varValueMathExpressionPattern = Pattern.compile(
			_VAR_VALUE_MATH_EXPRESSION_REGEX);
	}

}