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
import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.RegexUtil;
import com.liferay.poshi.runner.util.StringUtil;

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

	public String getPoshiLogDescriptor() {
		return getPoshiScript();
	}

	@Override
	public String getPoshiScript() {
		if (_poshiScript == null) {
			return toPoshiScript();
		}

		return _poshiScript;
	}

	public boolean isPoshiScriptComment(String poshiScript) {
		Matcher matcher = _poshiScriptCommentPattern.matcher(poshiScript);

		return matcher.find();
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
	public void setPoshiScript(String poshiScript) {
		_poshiScript = poshiScript;
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

		setPoshiScript(poshiScript);

		parsePoshiScript(poshiScript.trim());

		detach();
	}

	protected String createPoshiScriptBlock(List<PoshiNode> poshiNodes) {
		StringBuilder sb = new StringBuilder();

		String pad = getPad();

		sb.append("\n");
		sb.append(pad);
		sb.append(getBlockName());
		sb.append(" {");

		PoshiNode previousPoshiNode = null;

		for (Iterator<PoshiNode> iterator = poshiNodes.iterator();
			 iterator.hasNext();) {

			PoshiNode poshiNode = iterator.next();

			if (poshiNode instanceof DescriptionPoshiElement) {
				continue;
			}

			String poshiScriptSnippet = poshiNode.toPoshiScript();

			if ((previousPoshiNode == null) ||
				((previousPoshiNode instanceof VarPoshiElement) &&
				 (poshiNode instanceof VarPoshiElement))) {

				if (poshiScriptSnippet.startsWith("\n\n")) {
					poshiScriptSnippet = poshiScriptSnippet.replaceFirst(
						"\n\n", "\n");
				}
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
			if (c == '{') {
				if (isBalancedPoshiScript(sb.toString())) {
					String blockName = sb.toString();

					return blockName.trim();
				}
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

	protected String getFileType() {
		PoshiElement poshiParentElement = (PoshiElement)getParent();

		return poshiParentElement.getFileType();
	}

	protected List<String> getMethodParameters(String content) {
		List<String> methodParameters = new ArrayList<>();

		StringBuilder sb = new StringBuilder();

		String methodParameter = sb.toString();

		for (char c : content.toCharArray()) {
			if ((c == ',') && isBalancedPoshiScript(methodParameter)) {
				methodParameters.add(methodParameter.trim());

				sb.setLength(0);

				continue;
			}

			sb.append(c);

			methodParameter = sb.toString();
		}

		methodParameters.add(methodParameter.trim());

		return methodParameters;
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

	protected List<PoshiNode> getPoshiNodes() {
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

			if (isBalancedPoshiScript(poshiScriptSnippet)) {
				if (splitElseBlocks) {
					if (isValidPoshiScriptBlock(
							ElseIfPoshiElement.blockNamePattern,
							poshiScriptSnippet) ||
						isValidPoshiScriptBlock(
							ElsePoshiElement.blockNamePattern,
							poshiScriptSnippet)) {

						int lastIndex = poshiScriptSnippets.size() - 1;

						String lastPoshiScriptSnippet = poshiScriptSnippets.get(
							lastIndex);

						poshiScriptSnippets.set(
							lastIndex,
							lastPoshiScriptSnippet + poshiScriptSnippet);

						sb.setLength(0);

						continue;
					}
				}

				poshiScriptSnippets.add(poshiScriptSnippet);

				sb.setLength(0);
			}
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
		poshiScript = _fixPoshiScript(poshiScript);

		Stack<Character> stack = new Stack<>();

		for (char c : poshiScript.toCharArray()) {
			if (!stack.isEmpty()) {
				Character topCodeBoundary = stack.peek();

				if (c == _codeBoundariesMap.get(topCodeBoundary)) {
					stack.pop();

					continue;
				}

				if ((topCodeBoundary == '\"') || (topCodeBoundary == '\'')) {
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

	protected boolean isMultilinePoshiScriptComment(String poshiScript) {
		poshiScript = poshiScript.trim();

		if (poshiScript.endsWith("*/") && poshiScript.startsWith("/*")) {
			return true;
		}

		return false;
	}

	protected boolean isValidFunctionFileName(String poshiScriptInvocation) {
		for (String functionFileName : functionFileNames) {
			if (poshiScriptInvocation.matches(
					"(?s)" + Pattern.quote(functionFileName) + "[\\.\\(]+.*")) {

				return true;
			}
		}

		return false;
	}

	protected boolean isValidPoshiScriptBlock(
		Pattern poshiScriptBlockNamePattern, String poshiScript) {

		poshiScript = _fixPoshiScript(poshiScript);

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

	protected boolean isValidPoshiScriptStatement(
		Pattern poshiScriptStatementPattern, String poshiScript) {

		poshiScript = _fixPoshiScript(poshiScript);

		if (!isBalancedPoshiScript(poshiScript)) {
			return false;
		}

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
		catch (IllegalArgumentException iae) {
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

			poshiScriptSnippet = poshiScriptSnippet.replace(
				"\n", "\n" + getPad());

			poshiScriptSnippet = poshiScriptSnippet.replace("\n\t\n", "\n\n");

			return poshiScriptSnippet.replace("\n\n\n", "\n\n");
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

			if (trimmedLine.startsWith("/*")) {
				if (!stack.contains("/*")) {
					stack.push("/*");
				}
			}

			if ((StringUtil.count(trimmedLine, "'''") % 2) == 1) {
				if (stackPeek.equals("'''")) {
					stack.pop();
				}
				else {
					stack.push("'''");
				}
			}

			if (trimmedLine.endsWith("*/")) {
				if (stackPeek.equals("/*")) {
					stack.pop();
				}
			}
		}

		return sb.toString();
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

	protected List<PoshiNode> toPoshiNodes(List<?> list) {
		if (list == null) {
			return null;
		}

		List<PoshiNode> poshiNodes = new ArrayList<>(list.size());

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
		"(static[\\s]*|)var([\\s]*[A-Z][\\w]*|)[\\s]*[\\w]*";

	protected static final Set<String> functionFileNames = new TreeSet<>();
	protected static final Pattern nestedVarAssignmentPattern = Pattern.compile(
		"(\\w*\\s*=\\s*\".*?\"|\\w*\\s*=\\s*'''.*?'''|" +
			"\\w*\\s=\\s*[\\w\\.]*\\(.*?\\))($|\\s|,)",
		Pattern.DOTALL);
	protected static final Pattern poshiScriptAnnotationPattern =
		Pattern.compile("@[\\w-]*[\\s]*?=[\\s]\".*?\"", Pattern.DOTALL);
	protected static final Pattern poshiScriptBlockNamePattern =
		Pattern.compile("[\\s\\S]*");

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

	private String _fixPoshiScript(String poshiScript) {
		poshiScript = poshiScript.replaceAll("(?s)/\\*.*?\\*/", "/\\*\\*/");
		poshiScript = poshiScript.replaceAll(
			"(?s)\'\'\'.*?\'\'\'", "\'\'\'\'\'\'");
		poshiScript = poshiScript.replaceAll("(?s)\n[\\s]*//.*?\n", "//\n");

		return poshiScript.trim();
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<Character, Character>() {
			{
				put('\'', '\'');
				put('\"', '\"');
				put('(', ')');
				put('{', '}');
				put('[', ']');
			}
		};
	private static final Pattern _namespacedfunctionFileNamePattern =
		Pattern.compile(".*?\\.(.*?)\\.function");
	private static final Pattern _poshiScriptBlockPattern = Pattern.compile(
		"^[^{]*\\{[\\s\\S]*\\}$");
	private static final Pattern _poshiScriptCommentPattern = Pattern.compile(
		"^[\\s]*(\\/\\/.*?(\\n|$)|\\/\\*.*?\\*\\/)", Pattern.DOTALL);
	private static final Pattern _varInvocationAssignmentStatementPattern;

	static {
		INVOCATION_REGEX = "[\\s]*[\\w\\.]*" + PARAMETER_REGEX;

		_varInvocationAssignmentStatementPattern = Pattern.compile(
			"^" + VAR_NAME_REGEX + ASSIGNMENT_REGEX + INVOCATION_REGEX +
				STATEMENT_END_REGEX,
			Pattern.DOTALL);

		for (String namespacedFunctionFileName :
				PoshiRunnerContext.getFilePathKeys()) {

			Matcher matcher = _namespacedfunctionFileNamePattern.matcher(
				namespacedFunctionFileName);

			if (matcher.find()) {
				functionFileNames.add(matcher.group(1));
			}
		}
	}

	private String _poshiScript;

}