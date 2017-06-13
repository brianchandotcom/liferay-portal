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

import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.AND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.BACKGROUND;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.FEATURE;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.GIVEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SCENARIO;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.SET_UP;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.TEAR_DOWN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THESE_PROPERTIES;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.WHEN;
import static com.liferay.poshi.runner.util.StringPool.COLON;
import static com.liferay.poshi.runner.util.StringPool.PIPE;
import static com.liferay.poshi.runner.util.StringPool.TAB;

import com.liferay.poshi.runner.util.Dom4JUtil;
import com.liferay.poshi.runner.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * @author Kenji Heigel
 */
public abstract class PoshiElement extends DefaultElement {

	public PoshiElement(String name, Element element) {
		super(name);

		_addAttributes(element);
		_addElements(element);
	}

	public PoshiElement(String name, String readableSyntax) {
		super(name);

		addAttributes(readableSyntax);
		addElements(readableSyntax);
	}

	public abstract void addAttributes(String readableSyntax);

	public abstract void addElements(String readableSyntax);

	public void addVariableElements(String readableSyntax) {
		List<String> readableVariableBlocks = StringUtil.partition(
			readableSyntax, READABLE_VARIABLE_BLOCK_KEYS);

		for (String readableVariableBlock : readableVariableBlocks) {
			if (!readableVariableBlock.contains(PIPE)) {
				continue;
			}

			if (readableSyntax.contains(THESE_PROPERTIES)) {
				Element element = new PropertyElement(readableVariableBlock);

				add(element);

				continue;
			}

			PoshiElement poshiElement = PoshiElementFactory.newPoshiElement(
				readableVariableBlock);

			add(poshiElement);
		}
	}

	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			sb.append(poshiElement.toReadableSyntax());
		}

		return sb.toString();
	}

	protected static String toPhrase(String s) {
		String phrase = s.replaceAll(_PHRASE_REGEX, " $0");

		if (phrase.startsWith(" ")) {
			return phrase.substring(1);
		}

		return phrase;
	}

	protected String getAttributeValue(String startKey, String readableSyntax) {
		return getAttributeValue(startKey, "\n", readableSyntax);
	}

	protected String getAttributeValue(
		String startKey, String endKey, String readableSyntax) {

		int start = readableSyntax.indexOf(startKey);

		int end = readableSyntax.indexOf(endKey, start + startKey.length());

		String substring = readableSyntax.substring(
			start + startKey.length(), end);

		return substring.trim();
	}

	protected int getNamePadLength() {
		return _namePadLength;
	}

	protected String getReadableExecuteKey() {
		List<Element> siblingElements = getSiblings();

		int index = siblingElements.indexOf(this);

		if (index == 0) {
			return GIVEN;
		}

		if (index == 1) {
			return WHEN;
		}

		if (index == (siblingElements.size() - 1)) {
			return THEN;
		}

		return AND;
	}

	protected List<Element> getSiblings() {
		Element parentElement = getParent();

		return Dom4JUtil.toElementList(parentElement.elements());
	}

	protected int getValuePadLength() {
		return _valuePadLength;
	}

	protected String getVariableValueAttribute() {
		if (attributeValue("method") != null) {
			return attributeValue("method");
		}

		if (attributeValue("value") != null) {
			return attributeValue("value");
		}

		return null;
	}

	protected void setPadLengths() {
		if ((_namePadLength >= 0) && (_valuePadLength >= 0)) {
			return;
		}

		for (PoshiElement poshiElement : toPoshiElements(elements())) {
			String name = poshiElement.attributeValue("name");
			String value = poshiElement.getVariableValueAttribute();

			if ((name == null) || (value == null)) {
				continue;
			}

			if (name.length() > _namePadLength) {
				_namePadLength = name.length();
			}

			if (value.length() > _valuePadLength) {
				_valuePadLength = value.length();
			}
		}
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

	protected static final String[] READABLE_COMMAND_BLOCK_KEYS = {
		BACKGROUND + COLON, FEATURE + COLON, SCENARIO + COLON, SET_UP + COLON,
		TEAR_DOWN + COLON
	};

	protected static final String[] READABLE_EXECUTE_BLOCK_KEYS = {
		TAB + AND, TAB + GIVEN, TAB + THEN, TAB + WHEN
	};

	protected static final String[] READABLE_VARIABLE_BLOCK_KEYS = {
		TAB + TAB + PIPE
	};

	private void _addAttributes(Element element) {
		for (Attribute attribute :
				Dom4JUtil.toAttributeList(element.attributes())) {

			add((Attribute)attribute.clone());
		}
	}

	private void _addElements(Element element) {
		for (Element childElement :
				Dom4JUtil.toElementList(element.elements())) {

			add(PoshiElementFactory.newPoshiElement(childElement));
		}
	}

	private static final String _PHRASE_REGEX =
		"([\\d]+|[A-Z][a-z]+|[A-Z]+(?![a-z]))";

	private int _namePadLength = -1;
	private int _valuePadLength = -1;

}