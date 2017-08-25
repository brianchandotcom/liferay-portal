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
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.RegexUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Kenji Heigel
 */
public abstract class BasePoshiElement
	extends DefaultElement implements PoshiElement {

	public static boolean isElementType(String name, Element element) {
		if (name.equals(element.getName())) {
			return true;
		}

		return false;
	}

	public BasePoshiElement() {
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

	public abstract void parseReadableSyntax(String readableSyntax);

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

	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (BasePoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append(poshiElement.toReadableSyntax());
		}

		return sb.toString();
	}

	protected static void addPoshiElementFactory(
		PoshiElementFactory poshiElementFactory) {

		_poshiElementFactories.add(poshiElementFactory);
	}

	protected static String getBracedContent(String readableSyntax) {
		return RegexUtil.getGroup(readableSyntax, ".*?\\{(.*)\\}", 1);
	}

	protected static String getNameFromAssignment(String assignment) {
		String name = assignment.split("=")[0];

		name = name.trim();
		name = name.replaceAll("@", "");
		name = name.replaceAll("property ", "");

		return name.replaceAll("var ", "");
	}

	protected static String getParentheticalContent(String readableSyntax) {
		return RegexUtil.getGroup(readableSyntax, ".*?\\((.*)\\)", 1);
	}

	protected static String getQuotedContent(String readableSyntax) {
		return RegexUtil.getGroup(readableSyntax, ".*?\"(.*)\"", 1);
	}

	protected static boolean isBalancedReadableSyntax(String readableSyntax) {
		Stack<Character> stack = new Stack<>();

		for (char c : readableSyntax.toCharArray()) {
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

	protected BasePoshiElement(String name, Element element) {
		super(name);

		if (!isElementType(name, element)) {
			throw new RuntimeException(
				"Element does not match expected BasePoshiElement name\n" +
					element.toString());
		}

		_addAttributes(element);
		_addElements(element);
	}

	protected BasePoshiElement(String name, String readableSyntax) {
		super(name);

		parseReadableSyntax(readableSyntax);
	}

	protected String createReadableBlock(String content) {
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

	protected String getPad() {
		return "\t";
	}

	protected boolean isBalanceValidationRequired(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (readableSyntax.endsWith(";") || readableSyntax.endsWith("}")) {
			return true;
		}

		return false;
	}

	protected boolean isValidReadableBlock(String readableSyntax) {
		readableSyntax = readableSyntax.trim();

		if (readableSyntax.startsWith("property") ||
			readableSyntax.startsWith("var")) {

			if (readableSyntax.endsWith("\";") ||
				readableSyntax.endsWith(");")) {

				return true;
			}

			return false;
		}

		if (isBalanceValidationRequired(readableSyntax)) {
			return isBalancedReadableSyntax(readableSyntax);
		}

		return false;
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

	protected List<BasePoshiElement> toPoshiElements(List<?> list) {
		if (list == null) {
			return null;
		}

		List<BasePoshiElement> poshiElements = new ArrayList<>(list.size());

		for (Object object : list) {
			poshiElements.add((BasePoshiElement)object);
		}

		return poshiElements;
	}

	private void _addAttributes(Element element) {
		for (Attribute attribute :
				Dom4JUtil.toAttributeList(element.attributes())) {

			add(new PoshiElementAttribute((Attribute)attribute.clone()));
		}
	}

	private void _addElements(Element element) {
		for (Element childElement :
				Dom4JUtil.toElementList(element.elements())) {

			add(PoshiElementFactory.newPoshiElement(childElement));
		}
	}

	private static final Map<Character, Character> _codeBoundariesMap =
		new HashMap<>();
	private static final Set<PoshiElementFactory> _poshiElementFactories =
		new HashSet<>();

	static {
		String[] elementClassNames = {
			"CommandPoshiElement", "ConditionPoshiElement",
			"DefinitionPoshiElement", "DescriptionPoshiElement",
			"ElsePoshiElement", "EqualsPoshiElement", "ExecutePoshiElement",
			"ForPoshiElement", "IfPoshiElement", "IsSetPoshiElement",
			"PropertyPoshiElement", "ReturnPoshiElement", "SetUpPoshiElement",
			"TearDownPoshiElement", "ThenPoshiElement", "VarPoshiElement",
			"WhilePoshiElement"
		};

		for (String elementClassName : elementClassNames) {
			try {
				Class.forName(
					"com.liferay.poshi.runner.elements." + elementClassName);
			}
			catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(
					elementClassName + " not found", cnfe);
			}
		}

		_codeBoundariesMap.put('\"', '\"');
		_codeBoundariesMap.put('(', ')');
		_codeBoundariesMap.put('{', '}');
		_codeBoundariesMap.put('[', ']');
	}

}