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
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.GIVEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.THEN;
import static com.liferay.poshi.runner.elements.ReadableSyntaxKeys.WHEN;

import com.liferay.poshi.runner.util.StringUtil;

import java.util.Iterator;
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
	}

	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		for (Iterator<PoshiElement> i = elementIterator(); i.hasNext();) {
			PoshiElement poshiElement = i.next();

			sb.append(poshiElement.toReadableSyntax());
		}

		return sb.toString();
	}

	protected String getReadableExecuteKey() {
		List<Element> siblingElements = getSiblingElements();

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

	protected List<Element> getSiblingElements() {
		Element parentElement = getParent();

		return parentElement.elements();
	}

	protected static String toPhrase(String s) {
		String phrase = s.replaceAll(_PHRASE_REGEX, " $0");

		if (phrase.startsWith(" ")) {
			return phrase.substring(1);
		}

		return phrase;
	}

	private void _addAttributes(Element element) {
		for (Iterator i = element.attributeIterator(); i.hasNext();) {
			Attribute attribute = (Attribute)i.next();

			add((Attribute)attribute.clone());
		}
	}

	private void _addElements(Element element) {
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element childElement = PoshiElementFactory.newPoshiElement(
				(Element)i.next());

			add(childElement);
		}
	}

	private static final String _PHRASE_REGEX =
		"([\\d]+|[A-Z][a-z]+|[A-Z]+(?![a-z]))";

}