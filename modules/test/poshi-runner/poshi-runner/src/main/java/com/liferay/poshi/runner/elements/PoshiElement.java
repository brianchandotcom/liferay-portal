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

import com.google.common.reflect.ClassPath;

import com.liferay.poshi.runner.PoshiRunnerContext;
import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.PropsUtil;
import com.liferay.poshi.runner.util.RegexUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Comment;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

/**
 * @author Kenji Heigel
 */
public abstract class PoshiElement
	extends DefaultElement implements PoshiNode<Element, PoshiElement> {

	public PoshiElement() {
		super("");
	}

	@Override
	public void add(Attribute attribute) {
		if (attribute instanceof PoshiElementAttribute) {
			super.add(attribute);

			return;
		}

		super.add(new PoshiElementAttribute(attribute));
	}

	public abstract PoshiElement clone(
		PoshiElement parentPoshiElement, String poshiScript);

	public PoshiElement clone(String poshiScript) {
		return clone(null, poshiScript);
	}

	public boolean isPoshiScriptComment(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.startsWith("//")) {
			return true;
		}

		if (isMultilinePoshiScriptComment(poshiScript)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean remove(Attribute attribute) {
		if (attribute instanceof PoshiElementAttribute) {
			return super.remove(attribute);
		}

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributes())) {

			if (poshiElementAttribute.getAttribute() == attribute) {
				return super.remove(poshiElementAttribute);
			}
		}

		return false;
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		for (Node node : Dom4JUtil.toNodeList(content())) {
			if (node instanceof PoshiComment) {
				PoshiComment poshiComment = (PoshiComment)node;

				sb.append(poshiComment.toPoshiScript());
			}
			else if (node instanceof PoshiElement) {
				PoshiElement poshiElement = (PoshiElement)node;

				sb.append(poshiElement.toPoshiScript());
			}
		}

		return sb.toString();
	}

	protected PoshiElement(String name, Element element) {
		super(name);

		if (!isElementType(name, element)) {
			throw new RuntimeException(
				"Element does not match expected Poshi element name\n" +
					element.toString());
		}

		_addAttributes(element);
		_addNodes(element);
	}

	protected PoshiElement(
		String name, List<Attribute> attributes, List<Node> nodes) {

		super(name);

		if (attributes != null) {
			for (Attribute attribute : attributes) {
				add(attribute);
			}
		}

		if (nodes != null) {
			for (Node node : nodes) {
				add(node);
			}
		}
	}

	protected PoshiElement(
		String name, PoshiElement parentPoshiElement, String poshiScript) {

		super(name);

		setParent(parentPoshiElement);

		parsePoshiScript(poshiScript);

		detach();
	}

	protected String createPoshiScriptSnippet(String content) {
		StringBuilder sb = new StringBuilder();

		String pad = getPad();

		sb.append("\n");
		sb.append(pad);
		sb.append(getBlockName());
		sb.append(" {");

		if (content.startsWith("\n\n")) {
			content = content.replaceFirst("\n\n", "\n");
		}

		content = content.replaceAll("\n", "\n" + pad);

		sb.append(content.replaceAll("\n\t\n", "\n\n"));

		sb.append("\n");
		sb.append(pad);
		sb.append("}");

		return sb.toString();
	}

	protected abstract String getBlockName();

	protected String getBlockName(String poshiScriptBlock) {
		StringBuilder sb = new StringBuilder();

		for (char c : poshiScriptBlock.toCharArray()) {
			if (isBalancedPoshiScript(sb.toString()) && (c == '{')) {
				String blockName = sb.toString();

				return blockName.trim();
			}

			sb.append(c);
		}

		throw new RuntimeException(
			"Unable to get Poshi script block name from:\n" + poshiScriptBlock);
	}

	protected String getBracedContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\\{(.*)\\}", 1);
	}

	protected String getFileType() {
		PoshiElement poshiParentElement = (PoshiElement)getParent();

		return poshiParentElement.getFileType();
	}

	protected String getNameFromAssignment(String assignment) {
		String name = assignment.split("=")[0];

		name = name.trim();
		name = name.replaceAll("@", "");
		name = name.replaceAll("property ", "");

		return name.replaceAll("var ", "");
	}

	protected String getPad() {
		return "\t";
	}

	protected String getParentheticalContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\\((.*)\\)", 1);
	}

	protected String getPoshiScriptEscapedContent(String poshiScript) {
		poshiScript = poshiScript.trim();

		return poshiScript.substring(3, poshiScript.length() - 3);
	}

	protected String getPoshiScriptKeyword() {
		PoshiElement poshiParentElement = (PoshiElement)getParent();

		return poshiParentElement.getPoshiScriptKeyword();
	}

	protected String getQuotedContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\"(.*)\"", 1);
	}

	protected String getSingleQuotedContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\'(.*)\'", 1);
	}

	protected String getValueFromAssignment(String assignment) {
		assignment = assignment.trim();

		int start = assignment.indexOf("=");

		int end = assignment.length();

		if (assignment.endsWith(";")) {
			end = end - 1;
		}

		String value = assignment.substring(start + 1, end);

		return value.trim();
	}

	protected boolean isBalancedPoshiScript(String poshiScript) {
		poshiScript = poshiScript.replaceAll("/\\*.*?\\*/", "");

		poshiScript = poshiScript.replaceAll("\'\'\'.*?\'\'\'", "\"\"");

		Stack<Character> stack = new Stack<>();

		for (char c : poshiScript.toCharArray()) {
			if (!stack.isEmpty()) {
				Character topCodeBoundary = stack.peek();

				if (c == _codeBoundariesMap.get(topCodeBoundary)) {
					stack.pop();

					continue;
				}

				if (topCodeBoundary == '\"') {
					continue;
				}
			}

			if (_codeBoundariesMap.containsKey(c)) {
				stack.push(c);

				continue;
			}

			if (_codeBoundariesMap.containsValue(c)) {
				return false;
			}
		}

		return stack.isEmpty();
	}

	protected boolean isBalanceValidationRequired(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.endsWith(";") || poshiScript.endsWith("}")) {
			return true;
		}

		return false;
	}

	protected boolean isConditionValidInParent(
		PoshiElement parentPoshiElement) {

		if (parentPoshiElement instanceof AndPoshiElement ||
			parentPoshiElement instanceof ElseIfPoshiElement ||
			parentPoshiElement instanceof IfPoshiElement ||
			parentPoshiElement instanceof NotPoshiElement ||
			parentPoshiElement instanceof OrPoshiElement) {

			return true;
		}

		return false;
	}

	protected boolean isElementType(String name, Element element) {
		if (name.equals(element.getName())) {
			return true;
		}

		return false;
	}

	protected boolean isMacroReturnVar(String poshiScript) {
		poshiScript = poshiScript.trim();

		String value = getValueFromAssignment(poshiScript);

		if (!value.matches("(?s)^\".*\"$") && !value.matches("(?s)^'.*'$") &&
			!isValidFunctionFileName(value) && !isValidUtilClassName(value)) {

			return true;
		}

		return false;
	}

	protected boolean isMultilinePoshiScriptComment(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.endsWith("*/") && poshiScript.startsWith("/*")) {
			return true;
		}

		return false;
	}

	protected boolean isValidFunctionFileName(String classCommandName) {
		classCommandName = classCommandName.trim();

		for (String functionFileName : functionFileNames) {
			if (classCommandName.startsWith(functionFileName)) {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidPoshiScriptBlock(
		Pattern poshiScriptBlockNamePattern, String poshiScript) {

		poshiScript = poshiScript.trim();

		if (!isBalancedPoshiScript(poshiScript)) {
			return false;
		}

		Matcher poshiScriptBlockMatcher = _poshiScriptBlockPattern.matcher(
			poshiScript);

		if (poshiScriptBlockMatcher.find()) {
			Matcher poshiScriptBlockNameMatcher =
				poshiScriptBlockNamePattern.matcher(getBlockName(poshiScript));

			if (poshiScriptBlockNameMatcher.find()) {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidPoshiScriptSnippet(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.startsWith("property") ||
			poshiScript.startsWith("static var") ||
			poshiScript.startsWith("var")) {

			if (poshiScript.endsWith("\'\'\';") ||
				poshiScript.endsWith("\";") || poshiScript.endsWith(");")) {

				return true;
			}

			return false;
		}

		if (isPoshiScriptComment(poshiScript)) {
			return true;
		}

		if (isBalanceValidationRequired(poshiScript)) {
			return isBalancedPoshiScript(poshiScript);
		}

		return false;
	}

	protected boolean isValidUtilClassName(String classCommandName) {
		classCommandName = classCommandName.trim();

		for (String utilClassName : utilClassNames) {
			if (classCommandName.startsWith(utilClassName)) {
				return true;
			}
		}

		return false;
	}

	protected String quoteContent(String content) {
		return "\"" + content + "\"";
	}

	protected List<PoshiElementAttribute> toPoshiElementAttributes(
		List<?> list) {

		if (list == null) {
			return null;
		}

		List<PoshiElementAttribute> poshiElementAttributes = new ArrayList<>(
			list.size());

		for (Object object : list) {
			poshiElementAttributes.add((PoshiElementAttribute)object);
		}

		return poshiElementAttributes;
	}

	protected List<PoshiElement> toPoshiElements(List<?> list) {
		if (list == null) {
			return null;
		}

		List<PoshiElement> poshiElements = new ArrayList<>(list.size());

		for (Object object : list) {
			poshiElements.add((PoshiElement)object);
		}

		return poshiElements;
	}

	protected static final String BLOCK_NAME_ANNOTATION_REGEX = "(@.*=.*|)";

	protected static final String BLOCK_NAME_PARAMETER_REGEX =
		"[\\s]*\\(.*?\\)$";

	protected static final Set<String> functionFileNames = new TreeSet<>();
	protected static final Pattern nestedVarAssignmentPattern = Pattern.compile(
		"(\\w*? = \".*?\"|\\w*? = \'\'\'.*?\'\'\'|\\w*? = .*?\\(.*?\\))" +
			"($|\\s|,)",
		Pattern.DOTALL);
	protected static final Set<String> utilClassNames = new TreeSet<>();

	private void _addAttributes(Element element) {
		for (Attribute attribute :
				Dom4JUtil.toAttributeList(element.attributes())) {

			add(new PoshiElementAttribute((Attribute)attribute.clone()));
		}
	}

	private void _addNodes(Element element) {
		for (Node node : Dom4JUtil.toNodeList(element.content())) {
			if (node instanceof Comment || node instanceof Element) {
				add(PoshiNodeFactory.newPoshiNode(node));
			}
		}
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<>();
	private static final Pattern _namespacedfunctionFileNamePattern =
		Pattern.compile(".*?\\.(.*?)\\.function");
	private static final Pattern _poshiScriptBlockPattern = Pattern.compile(
		".*?\\{.*\\}$", Pattern.DOTALL);

	static {
		_codeBoundariesMap.put('\"', '\"');
		_codeBoundariesMap.put('(', ')');
		_codeBoundariesMap.put('{', '}');
		_codeBoundariesMap.put('[', ']');

		try {
			ClassPath classPath = ClassPath.from(
				PropsUtil.class.getClassLoader());

			for (ClassPath.ClassInfo classInfo :
					classPath.getTopLevelClasses(
						"com.liferay.poshi.runner.util")) {

				utilClassNames.add(classInfo.getName());
				utilClassNames.add(classInfo.getSimpleName());
			}

			utilClassNames.add("selenium");
			utilClassNames.add("TestPropsUtil");
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		for (String namespacedFunctionFileName :
				PoshiRunnerContext.getFilePathKeys()) {

			Matcher matcher = _namespacedfunctionFileNamePattern.matcher(
				namespacedFunctionFileName);

			if (matcher.find()) {
				functionFileNames.add(matcher.group(1));
			}
		}
	}

}