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
import com.liferay.poshi.core.util.ListUtil;
import com.liferay.poshi.core.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class CommandPoshiElement extends PoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new CommandPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new CommandPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	public List<String> generateRequiredArguments(String blockContent) {
		Set<String> argumentSet = new HashSet<>();
		Matcher variableMatcher = _variablePattern.matcher(blockContent);

		while (variableMatcher.find()) {
			argumentSet.add(variableMatcher.group(1));
		}

		Matcher declarationMatcher = _declarationVariablePattern.matcher(
			blockContent);

		while (declarationMatcher.find()) {
			argumentSet.remove(declarationMatcher.group(1));
		}

		return new ArrayList<>(argumentSet);
	}

	@Override
	public String getPoshiLogDescriptor() {
		return getBlockName();
	}

	@Override
	public int getPoshiScriptLineNumber() {
		return getPoshiScriptLineNumber(false);
	}

	@Override
	public void parsePoshiScript(String poshiScript)
		throws PoshiScriptParserException {

		String blockName = getBlockName(poshiScript);

		Matcher poshiScriptAnnotationMatcher =
			poshiScriptAnnotationPattern.matcher(blockName);

		List<String> simpleAnnotations = new ArrayList<>();

		while (poshiScriptAnnotationMatcher.find()) {
			String name = poshiScriptAnnotationMatcher.group("name");

			if (name.equals("description")) {
				add(
					PoshiNodeFactory.newPoshiNode(
						this, poshiScriptAnnotationMatcher.group()));

				continue;
			}

			String value = poshiScriptAnnotationMatcher.group("value");

			if (value == null) {
				simpleAnnotations.add(name);

				continue;
			}

			if (value.startsWith("\"") && value.endsWith("\"")) {
				value = getDoubleQuotedContent(value);
			}

			addAttribute(name, value);
		}

		if (!simpleAnnotations.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			for (String simpleAnnotation : simpleAnnotations) {
				sb.append(simpleAnnotation);
				sb.append(",");
			}

			if (sb.length() != 0) {
				sb.setLength(sb.length() - 1);
			}

			addAttribute("annotations", sb.toString());
		}

		Matcher blockNameMatcher = _blockNamePattern.matcher(blockName);

		String blockContent = getBlockContent(poshiScript);

		if (blockNameMatcher.find()) {
			addAttribute("name", blockNameMatcher.group(3));

			String commandType = blockNameMatcher.group(2);

			if (commandType.equals("macro")) {
				String arguments = getParentheticalContent(
					blockNameMatcher.group(4));

				if (Validator.isNull(arguments)) {
					StringBuilder sb = new StringBuilder();

					for (String argument :
							generateRequiredArguments(blockContent)) {

						sb.append(argument);
						sb.append(" = null,");
					}

					String newArguments = sb.toString();

					if (!newArguments.isEmpty()) {
						addAttribute(
							"arguments",
							newArguments.substring(
								0, newArguments.length() - 1));
					}
				}
				else {
					addAttribute("arguments", arguments);
				}
			}
		}

		for (String poshiScriptSnippet : getPoshiScriptSnippets(blockContent)) {
			add(PoshiNodeFactory.newPoshiNode(this, poshiScriptSnippet));
		}
	}

	@Override
	public String toPoshiScript() {
		DescriptionPoshiElement descriptionPoshiElement =
			(DescriptionPoshiElement)element("description");

		List<String> annotations = new ArrayList<>();

		if (descriptionPoshiElement != null) {
			annotations.add("\t" + descriptionPoshiElement.toPoshiScript());
		}

		for (PoshiElementAttribute poshiElementAttribute :
				toPoshiElementAttributes(attributeList())) {

			String name = poshiElementAttribute.getName();

			if (name.equals("arguments") || name.equals("name")) {
				continue;
			}

			if (name.equals("annotations")) {
				String annotationsValue = poshiElementAttribute.getValue();

				for (String annotation : annotationsValue.split(",")) {
					annotations.add("\t@" + annotation);
				}

				continue;
			}

			annotations.add("\t@" + poshiElementAttribute.toPoshiScript());
		}

		Collections.sort(
			annotations,
			new Comparator<String>() {

				@Override
				public int compare(String annotation1, String annotation2) {
					String annotationName1 = _getAnnotationName(annotation1);
					String annotationName2 = _getAnnotationName(annotation2);

					return annotationName1.compareTo(annotationName2);
				}

				private String _getAnnotationName(String annotation) {
					return annotation.replaceFirst("(.+)( .+|)", "$1");
				}

			});

		String annotationsString = ListUtil.toString(annotations, "\n");

		if (annotationsString.length() > 0) {
			return "\n\n" + annotationsString +
				createPoshiScriptBlock(getPoshiNodes());
		}

		return "\n" + createPoshiScriptBlock(getPoshiNodes());
	}

	protected CommandPoshiElement() {
		this(_ELEMENT_NAME);
	}

	protected CommandPoshiElement(Element element) {
		this(_ELEMENT_NAME, element);
	}

	protected CommandPoshiElement(
		List<Attribute> attributes, List<Node> nodes) {

		this(_ELEMENT_NAME, attributes, nodes);
	}

	protected CommandPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		this(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	protected CommandPoshiElement(String name) {
		super(name);
	}

	protected CommandPoshiElement(String name, Element element) {
		super(name, element);
	}

	protected CommandPoshiElement(
		String elementName, List<Attribute> attributes, List<Node> nodes) {

		super(elementName, attributes, nodes);
	}

	protected CommandPoshiElement(
			String name, PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(name, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getBlockName() {
		return getPoshiScriptKeyword() + " " + attributeValue("name");
	}

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (!(parentPoshiElement instanceof DefinitionPoshiElement)) {
			return false;
		}

		return isValidPoshiScriptBlock(_blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "command";

	private static final String _POSHI_SCRIPT_KEYWORD_REGEX =
		"(function|macro|test)[\\s]+";

	private static final Pattern _blockNamePattern = Pattern.compile(
		"^" + BLOCK_NAME_ANNOTATION_REGEX + _POSHI_SCRIPT_KEYWORD_REGEX +
			"[\\s]*([\\w]*)[\\s]*(\\(.*\\)|)",
		Pattern.DOTALL);
	private static final Pattern _declarationVariablePattern = Pattern.compile(
		"var\\s*(\\S*)\\s*=");
	private static final Pattern _variablePattern = Pattern.compile(
		"\\$\\{(\\w*|\\d*)\\}");

}