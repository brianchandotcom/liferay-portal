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

import com.liferay.poshi.runner.PoshiRunnerContext;
import com.liferay.poshi.runner.PoshiRunnerGetterUtil;
import com.liferay.poshi.runner.script.PoshiScriptParserException;
import com.liferay.poshi.runner.script.UnbalancedCodeException;
import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.RegexUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
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

	@Override
	public void add(CDATA cdata) {
		if (cdata instanceof PoshiCDATA) {
			super.add(cdata);

			return;
		}

		super.add(new PoshiCDATA(cdata));
	}

	public abstract PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException;

	@Override
	public PoshiElement clone(String poshiScript)
		throws PoshiScriptParserException {

		return clone(null, poshiScript);
	}

	public String getPoshiLogDescriptor() {
		return getPoshiScript();
	}

	@Override
	public String getPoshiScript() {
		return _poshiScript;
	}

	public int getPoshiScriptLineNumber(Boolean includeAnnotation) {
		int poshiScriptLineNumber = PoshiNode.super.getPoshiScriptLineNumber();

		if (!includeAnnotation) {
			poshiScriptLineNumber += StringUtil.count(
				getBlockName(getPoshiScript()), "\n");
		}

		return poshiScriptLineNumber;
	}

	public boolean isPoshiScriptComment(String poshiScript) {
		Matcher matcher = _poshiScriptCommentPattern.matcher(poshiScript);

		return matcher.find();
	}

	public boolean isValidPoshiScript() throws PoshiScriptParserException {
		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			poshiElementAttribute.validatePoshiScript();
		}

		String originalPoshiScript = getPoshiScript();
		String generatedPoshiScript = toPoshiScript();

		originalPoshiScript = originalPoshiScript.replaceAll("\\s+", "");

		generatedPoshiScript = generatedPoshiScript.replaceAll("\\s+", "");

		if ((elements().size() == 0) &&
			!originalPoshiScript.equals(generatedPoshiScript)) {

			return false;
		}

		if (originalPoshiScript.length() != generatedPoshiScript.length()) {
			return false;
		}

		return true;
	}

	@Override
	public boolean remove(Attribute attribute) {
		if (attribute instanceof PoshiElementAttribute) {
			return super.remove(attribute);
		}

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributes())) {

			if (poshiElementAttribute == attribute) {
				return super.remove(poshiElementAttribute);
			}
		}

		return false;
	}

	@Override
	public boolean remove(CDATA cdata) {
		if (cdata instanceof PoshiCDATA) {
			return super.remove(cdata);
		}

		for (PoshiNode<?, ?> poshiNode : toPoshiNodes(content())) {
			if (poshiNode == cdata) {
				return super.remove(poshiNode);
			}
		}

		return false;
	}

	@Override
	public void setPoshiScript(String poshiScript) {
		_poshiScript = poshiScript;
	}

	public List<PoshiElement> toPoshiElements(List<?> list) {
		if (list == null) {
			return null;
		}

		List<PoshiElement> poshiElements = new ArrayList<>(list.size());

		for (Object object : list) {
			poshiElements.add((PoshiElement)object);
		}

		return poshiElements;
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

	@Override
	public void validatePoshiScript() throws PoshiScriptParserException {
		if (!isValidPoshiScript() && !isValidPoshiXML()) {
			throw new PoshiScriptParserException(
				PoshiScriptParserException.TRANSLATION_LOSS_MESSAGE, this);
		}
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

	protected PoshiElement(String name, Element element, URL url) {
		this(name, element);

		setFilePath(url);
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
			String name, PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		this(name, parentPoshiElement, poshiScript, null);
	}

	protected PoshiElement(
			String name, PoshiElement parentPoshiElement, String poshiScript,
			URL url)
		throws PoshiScriptParserException {

		super(name);

		setFilePath(url);

		setParent(parentPoshiElement);

		setPoshiScript(poshiScript);

		try {
			parsePoshiScript(poshiScript.trim());

			if (PropsValues.TEST_POSHI_SCRIPT_VALIDATION &&
				!PoshiNodeFactory.validationInitialized.contains(getURL())) {

				PoshiNodeFactory.validationInitialized.add(getURL());

				validatePoshiScript();
			}
		}
		catch (PoshiScriptParserException poshiScriptParserException) {
		}

		detach();
	}

	protected String createPoshiScriptBlock(List<PoshiNode<?, ?>> poshiNodes) {
		StringBuilder sb = new StringBuilder();

		String pad = getPad();

		sb.append("\n");
		sb.append(pad);
		sb.append(getBlockName());
		sb.append(" {");

		PoshiNode<?, ?> previousPoshiNode = null;

		for (Iterator<PoshiNode<?, ?>> iterator = poshiNodes.iterator();
			 iterator.hasNext();) {

			PoshiNode<?, ?> poshiNode = iterator.next();

			if (poshiNode instanceof DescriptionPoshiElement) {
				continue;
			}

			String poshiScriptSnippet = poshiNode.toPoshiScript();

			if (((previousPoshiNode == null) ||
				 ((previousPoshiNode instanceof VarPoshiElement) &&
				  (poshiNode instanceof VarPoshiElement))) &&
				poshiScriptSnippet.startsWith("\n\n")) {

				poshiScriptSnippet = poshiScriptSnippet.replaceFirst(
					"\n\n", "\n");
			}

			sb.append(padPoshiScriptSnippet(poshiScriptSnippet));

			previousPoshiNode = poshiNode;
		}

		sb.append("\n");
		sb.append(pad);
		sb.append("}");

		return sb.toString();
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

	protected String doubleQuoteContent(String content) {
		return "\"" + content + "\"";
	}

	protected String getBlockContent(String poshiScriptBlock) {
		String blockName = getBlockName(poshiScriptBlock);

		int index = blockName.length();

		String blockContent = poshiScriptBlock.substring(index);

		return getBracedContent(blockContent);
	}

	protected abstract String getBlockName();

	protected String getBlockName(String poshiScriptBlock) {
		StringBuilder sb = new StringBuilder();

		for (char c : poshiScriptBlock.toCharArray()) {
			if ((c == '{') && isBalancedPoshiScript(sb.toString())) {
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

	protected String getBracketedContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\\[(.*)\\]", 1);
	}

	protected String getClassName(String classCommand) {
		classCommand = classCommand.trim();

		if (classCommand.contains("(")) {
			int index = classCommand.indexOf("(");

			classCommand = classCommand.substring(0, index);
		}

		int index = classCommand.length();

		if (classCommand.contains(".")) {
			index = classCommand.lastIndexOf(".");
		}
		else if (classCommand.contains("#")) {
			index = classCommand.lastIndexOf("#");
		}

		return classCommand.substring(0, index);
	}

	protected String getCommandName(String classCommand) {
		classCommand = classCommand.trim();

		if (classCommand.contains("(")) {
			int index = classCommand.indexOf("(");

			classCommand = classCommand.substring(0, index);
		}

		if (classCommand.contains(".")) {
			int index = classCommand.lastIndexOf(".");

			return classCommand.substring(index + 1);
		}

		return classCommand;
	}

	protected Pattern getConditionPattern() {
		return null;
	}

	protected String getDoubleQuotedContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\"(.*)\"", 1);
	}

	protected List<String> getMethodParameters(String content) {
		try {
			return getMethodParameters(content, null);
		}
		catch (PoshiScriptParserException poshiScriptParserException) {
			poshiScriptParserException.printStackTrace();

			return new ArrayList<>();
		}
	}

	protected List<String> getMethodParameters(
			String content, Pattern parameterPattern)
		throws PoshiScriptParserException {

		List<String> methodParameters = new ArrayList<>();

		if (content.length() == 0) {
			return methodParameters;
		}

		StringBuilder sb = new StringBuilder();

		String methodParameter = sb.toString();

		for (char c : content.toCharArray()) {
			if ((c == ',') && isBalancedPoshiScript(methodParameter)) {
				if (parameterPattern != null) {
					Matcher matcher = parameterPattern.matcher(methodParameter);

					if (!matcher.matches()) {
						sb.append(c);

						continue;
					}
				}

				methodParameters.add(methodParameter);

				sb.setLength(0);

				continue;
			}

			sb.append(c);

			methodParameter = sb.toString();
		}

		if (parameterPattern != null) {
			Matcher matcher = parameterPattern.matcher(methodParameter);

			if (!matcher.matches()) {
				throw new PoshiScriptParserException(
					"Invalid Poshi Script parameter syntax", this);
			}
		}

		methodParameters.add(methodParameter);

		return methodParameters;
	}

	protected String getNameFromAssignment(String assignment) {
		String name = assignment.split("=")[0];

		name = name.trim();
		name = StringUtil.replace(name, "@", "");
		name = StringUtil.replace(name, "property ", "");

		return StringUtil.replace(name, "var ", "");
	}

	protected List<String> getNestedConditions(
		String poshiScript, String operator) {

		List<String> nestedConditions = new ArrayList<>();

		Set<Integer> tokenIndices = new TreeSet<>();

		int index = poshiScript.indexOf(operator);

		while (index >= 0) {
			tokenIndices.add(index);

			index = poshiScript.indexOf(operator, index + 1);
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = poshiScript.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			if (tokenIndices.contains(i) &&
				isBalancedPoshiScript(sb.toString())) {

				nestedConditions.add(sb.toString());

				sb.setLength(0);

				i++;

				continue;
			}

			if (i == (chars.length - 1)) {
				sb.append(c);

				if (isBalancedPoshiScript(sb.toString()) &&
					!nestedConditions.isEmpty()) {

					nestedConditions.add(sb.toString());
				}
			}

			sb.append(c);
		}

		return nestedConditions;
	}

	protected String getPad() {
		return "\t";
	}

	protected String getParentheticalContent(String poshiScript) {
		return RegexUtil.getGroup(poshiScript, ".*?\\((.*)\\)", 1);
	}

	protected List<PoshiNode<?, ?>> getPoshiNodes() {
		return toPoshiNodes(content());
	}

	protected String getPoshiScriptEscapedContent(String poshiScript) {
		poshiScript = poshiScript.trim();

		return poshiScript.substring(3, poshiScript.length() - 3);
	}

	protected String getPoshiScriptKeyword() {
		PoshiElement poshiParentElement = (PoshiElement)getParent();

		return poshiParentElement.getPoshiScriptKeyword();
	}

	protected List<String> getPoshiScriptSnippets(
		String poshiScriptBlockContent) {

		return getPoshiScriptSnippets(poshiScriptBlockContent, true);
	}

	protected List<String> getPoshiScriptSnippets(
		String poshiScriptBlockContent, boolean splitElseBlocks) {

		StringBuilder sb = new StringBuilder();

		List<String> poshiScriptSnippets = new ArrayList<>();

		int index = 0;
		boolean skipBalanceCheck = false;
		Stack<Integer> storedIndices = new Stack<>();

		for (char c : poshiScriptBlockContent.toCharArray()) {
			sb.append(c);

			String poshiScriptSnippet = sb.toString();

			String trimmedPoshiScriptSnippet = poshiScriptSnippet.trim();

			if (trimmedPoshiScriptSnippet.startsWith("//")) {
				if (c == '\n') {
					poshiScriptSnippet = poshiScriptSnippet.substring(
						0, poshiScriptSnippet.length() - 1);

					poshiScriptSnippets.add(poshiScriptSnippet);

					sb.setLength(0);

					sb.append(c);
				}

				continue;
			}

			if (trimmedPoshiScriptSnippet.startsWith("/*")) {
				if (trimmedPoshiScriptSnippet.endsWith("*/")) {
					poshiScriptSnippets.add(poshiScriptSnippet);

					sb.setLength(0);
				}

				continue;
			}

			index++;

			if (c == '\'') {
				if (storedIndices.isEmpty()) {
					storedIndices.push(index);
				}
				else if (storedIndices.peek() == (index - 1)) {
					storedIndices.push(index);
				}

				if ((storedIndices.size() == 3) ||
					(storedIndices.size() == 6)) {

					skipBalanceCheck = !skipBalanceCheck;
				}

				if (storedIndices.size() > 6) {
					throw new RuntimeException(
						"Invalid multiline string: \n" + poshiScriptSnippet);
				}
			}
			else {
				storedIndices.clear();
			}

			if (skipBalanceCheck) {
				continue;
			}

			if ((c != '}') && (c != ';')) {
				continue;
			}

			if (trimmedPoshiScriptSnippet.startsWith("var") && (c != ';')) {
				continue;
			}

			if (isBalancedPoshiScript(poshiScriptSnippet)) {
				if (splitElseBlocks &&
					(isValidPoshiScriptBlock(
						ElseIfPoshiElement.blockNamePattern,
						poshiScriptSnippet) ||
					 isValidPoshiScriptBlock(
						 ElsePoshiElement.blockNamePattern,
						 poshiScriptSnippet))) {

					int lastIndex = poshiScriptSnippets.size() - 1;

					String lastPoshiScriptSnippet = poshiScriptSnippets.get(
						lastIndex);

					poshiScriptSnippets.set(
						lastIndex, lastPoshiScriptSnippet + poshiScriptSnippet);

					sb.setLength(0);

					continue;
				}

				poshiScriptSnippets.add(poshiScriptSnippet);

				sb.setLength(0);
			}
		}

		String poshiScriptSnippet = sb.toString();

		poshiScriptSnippet = poshiScriptSnippet.trim();

		if (!poshiScriptSnippet.isEmpty()) {
			poshiScriptSnippets.add(poshiScriptSnippet);
		}

		return poshiScriptSnippets;
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
		try {
			return isBalancedPoshiScript(poshiScript, false);
		}
		catch (Exception exception) {
			return false;
		}
	}

	protected boolean isBalancedPoshiScript(
			String poshiScript, boolean throwException)
		throws UnbalancedCodeException {

		poshiScript = _fixPoshiScript(poshiScript);

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < poshiScript.length(); i++) {
			char c = poshiScript.charAt(i);

			if (!stack.isEmpty()) {
				int topIndex = stack.peek();

				Character topCodeBoundary = poshiScript.charAt(topIndex);

				if (c == _codeBoundariesMap.get(topCodeBoundary)) {
					stack.pop();

					continue;
				}

				if ((topCodeBoundary == '\"') || (topCodeBoundary == '\'')) {
					continue;
				}
			}

			if (_codeBoundariesMap.containsKey(c)) {
				stack.push(i);

				continue;
			}

			if (_codeBoundariesMap.containsValue(c)) {
				if (throwException) {
					throw new UnbalancedCodeException(
						"Unexpected closing boundary", i, poshiScript);
				}

				return false;
			}
		}

		boolean balanced = stack.isEmpty();

		if (!balanced && throwException) {
			throw new UnbalancedCodeException(
				"Unmatched opening boundary", stack.peek(), poshiScript);
		}

		return balanced;
	}

	protected final boolean isConditionElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!isConditionValidInParent(parentPoshiElement)) {
			return false;
		}

		poshiScript = poshiScript.trim();

		Pattern conditionPattern = getConditionPattern();

		if (conditionPattern == null) {
			throw new RuntimeException(
				"Condition pattern has not been defined");
		}

		Matcher matcher = conditionPattern.matcher(poshiScript);

		return matcher.find();
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

	protected boolean isNestedCondition(String poshiScript) {
		Matcher matcher = _nestedConditionPattern.matcher(poshiScript);

		if (matcher.find()) {
			List<String> nestedConditions = getNestedConditions(
				poshiScript, matcher.group(0));

			if (nestedConditions.size() > 1) {
				return true;
			}
		}

		return false;
	}

	protected boolean isValidFunctionFileName(String poshiScriptInvocation) {
		for (String functionFileName :
				PoshiRunnerContext.getFunctionFileNames()) {

			if (poshiScriptInvocation.matches(
					"(?s)" + Pattern.quote(functionFileName) + "[\\.\\(]+.*")) {

				return true;
			}
		}

		return false;
	}

	protected boolean isValidPoshiScriptBlock(
		Pattern poshiScriptBlockNamePattern, String poshiScript) {

		poshiScript = poshiScript.trim();

		Matcher poshiScriptBlockMatcher = poshiScriptBlockPattern.matcher(
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

	protected boolean isValidPoshiScriptStatement(
		Pattern poshiScriptStatementPattern, String poshiScript) {

		poshiScript = poshiScript.trim();

		Matcher poshiScriptStatementMatcher =
			poshiScriptStatementPattern.matcher(poshiScript);

		if (poshiScriptStatementMatcher.find()) {
			return true;
		}

		return false;
	}

	protected boolean isValidUtilityClassName(String classCommandName) {
		String className = getClassName(classCommandName);

		if (className.equals("selenium")) {
			return true;
		}

		try {
			if (!className.contains(".")) {
				className = PoshiRunnerGetterUtil.getUtilityClassName(
					className);
			}

			if (PoshiRunnerGetterUtil.isValidUtilityClass(className)) {
				return true;
			}
		}
		catch (IllegalArgumentException illegalArgumentException) {
			return false;
		}

		return false;
	}

	protected boolean isVarAssignedToMacroInvocation(String poshiScript) {
		poshiScript = poshiScript.trim();

		String value = getValueFromAssignment(poshiScript);

		if (isValidPoshiScriptStatement(
				_varInvocationAssignmentStatementPattern, poshiScript) &&
			!isValidFunctionFileName(value) &&
			!isValidUtilityClassName(value)) {

			return true;
		}

		return false;
	}

	protected String padPoshiScriptSnippet(String poshiScriptSnippet) {
		if (!poshiScriptSnippet.contains("'''") &&
			!poshiScriptSnippet.contains("/*") &&
			!poshiScriptSnippet.contains("*/")) {

			poshiScriptSnippet = StringUtil.replace(
				poshiScriptSnippet, "\n", "\n" + getPad());

			poshiScriptSnippet = StringUtil.replace(
				poshiScriptSnippet, "\n\t\n", "\n\n");

			return StringUtil.replace(poshiScriptSnippet, "\n\n\n", "\n\n");
		}

		Stack<String> stack = new Stack<>();
		StringBuilder sb = new StringBuilder();

		if (poshiScriptSnippet.startsWith("\n\n")) {
			poshiScriptSnippet = poshiScriptSnippet.replaceFirst("\n\n", "\n");
		}

		for (String line : poshiScriptSnippet.split("\n")) {
			String trimmedLine = line.trim();

			sb.append("\n");

			String stackPeek = "";

			if (stack.isEmpty()) {
				if (!trimmedLine.isEmpty()) {
					line = getPad() + line;
				}
			}
			else {
				stackPeek = stack.peek();
			}

			sb.append(line);

			if (trimmedLine.startsWith("/*") && !stack.contains("/*")) {
				stack.push("/*");
			}

			if ((StringUtil.count(trimmedLine, "'''") % 2) == 1) {
				if (stackPeek.equals("'''")) {
					stack.pop();
				}
				else {
					stack.push("'''");
				}
			}

			if (trimmedLine.endsWith("*/") && stackPeek.equals("/*")) {
				stack.pop();
			}
		}

		return sb.toString();
	}

	protected void setFilePath(URL url) {
	}

	protected String singleQuoteContent(String content) {
		return "'" + content + "'";
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

	protected List<PoshiNode<?, ?>> toPoshiNodes(List<?> list) {
		if (list == null) {
			return null;
		}

		List<PoshiNode<?, ?>> poshiNodes = new ArrayList<>(list.size());

		for (Object object : list) {
			poshiNodes.add((PoshiNode)object);
		}

		return poshiNodes;
	}

	protected static final String ASSIGNMENT_REGEX = "[\\s]*=[\\s]*";

	protected static final String BLOCK_NAME_ANNOTATION_REGEX = "(@.*=.*|)";

	protected static final String BLOCK_NAME_PARAMETER_REGEX =
		"[\\s]*\\(.*?\\)$";

	protected static final String INVOCATION_REGEX;

	protected static final String PARAMETER_REGEX = "\\(.*\\)";

	protected static final String STATEMENT_END_REGEX = ";$";

	protected static final String VAR_NAME_REGEX =
		"((static[\\s]*|)var|)([\\s]*[A-Z][\\w]*|)[\\s]*[\\w]*";

	protected static final String VAR_STATEMENT_END_REGEX = "(;|)$";

	protected static final Pattern poshiScriptAnnotationPattern =
		Pattern.compile("@[\\w-]*[\\s]*?=[\\s]\".*?\"", Pattern.DOTALL);
	protected static final Pattern poshiScriptBlockPattern = Pattern.compile(
		"^[^{]*\\{[\\s\\S]*\\}$");

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

			if (node instanceof CDATA) {
				add(new PoshiCDATA((CDATA)node.clone()));
			}
		}
	}

	private String _fixPoshiScript(String poshiScript) {
		if (poshiScript.contains("/*") && poshiScript.contains("*/")) {
			poshiScript = poshiScript.replaceAll("(?s)/\\*.*?\\*/", "/\\*\\*/");
		}

		if (poshiScript.contains("'''")) {
			poshiScript = poshiScript.replaceAll(
				"(?s)\'\'\'.*?\'\'\'", "\'\'\'\'\'\'");
		}

		if (poshiScript.contains("//")) {
			poshiScript = poshiScript.replaceAll("(?m)\n[\\s]*//.*?$", "//\n");
		}

		return poshiScript.trim();
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<Character, Character>() {
			{
				put('(', ')');
				put('[', ']');
				put('\"', '\"');
				put('\'', '\'');
				put('{', '}');
			}
		};
	private static final Pattern _nestedConditionPattern = Pattern.compile(
		"(\\|{2}|\\&{2})");
	private static final Pattern _poshiScriptCommentPattern = Pattern.compile(
		"^[\\s]*(\\/\\/.*?(\\n|$)|\\/\\*.*?\\*\\/)", Pattern.DOTALL);
	private static final Pattern _varInvocationAssignmentStatementPattern;

	static {
		INVOCATION_REGEX = "[\\s]*[\\w\\.]*" + PARAMETER_REGEX;

		_varInvocationAssignmentStatementPattern = Pattern.compile(
			"^" + VAR_NAME_REGEX + ASSIGNMENT_REGEX + INVOCATION_REGEX +
				VAR_STATEMENT_END_REGEX,
			Pattern.DOTALL);
	}

	private String _poshiScript;

}