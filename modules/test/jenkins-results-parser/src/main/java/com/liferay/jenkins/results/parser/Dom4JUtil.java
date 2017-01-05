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

package com.liferay.jenkins.results.parser;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 * @author Peter Yoo
 */
public class Dom4JUtil {

	public static void addToElement(Element element, Object... items) {
		for (int i = 0; i < items.length; i++) {
			Object item = items[i];

			if (item == null) {
				continue;
			}

			if (item instanceof Element) {
				element.add((Element)item);

				continue;
			}

			if (item instanceof String) {
				element.addText((String)item);

				continue;
			}

			throw new IllegalArgumentException(
				"Only Elements and Strings may be added.");
		}
	}

	public static String format(Element element) throws IOException {
		return format(element, true);
	}

	public static String format(Element element, boolean pretty)
		throws IOException {

		Writer writer = new CharArrayWriter();

		XMLWriter xmlWriter = pretty ? new XMLWriter(
			writer, OutputFormat.createPrettyPrint()) : new XMLWriter(writer);

		xmlWriter.write(element);

		return writer.toString();
	}

	public static Element getNewAnchorElement(
		String href, Element parentElement, Object... items) {

		Element anchorElement = null;

		if (parentElement == null) {
			anchorElement = new DefaultElement("a");
		}
		else {
			anchorElement = getNewElement("a", parentElement);
		}

		anchorElement.addAttribute("href", href);

		addToElement(anchorElement, items);

		return anchorElement;
	}

	public static Element getNewAnchorElement(String href, Object... items) {
		return getNewAnchorElement(href, null, items);
	}

	public static Element getNewElement(
		String childElementTag, Element parentElement, Object... items) {

		Element childElement = new DefaultElement(childElementTag);

		parentElement.add(childElement);

		if ((items != null) && (items.length > 0)) {
			addToElement(childElement, items);
		}

		return childElement;
	}

	public static void replace(
		Element element, boolean cascade, String replacementText,
		String targetText) {

		Iterator<?> attributeIterator = element.attributeIterator();

		while (attributeIterator.hasNext()) {
			Attribute attribute = (Attribute)attributeIterator.next();

			String text = attribute.getValue();

			attribute.setValue(text.replace(targetText, replacementText));
		}

		Iterator<?> nodeIterator = element.nodeIterator();

		while (nodeIterator.hasNext()) {
			Node node = (Node)nodeIterator.next();

			if (node instanceof Text) {
				Text textNode = (Text)node;

				String text = textNode.getText();

				if (text.contains(targetText)) {
					text = text.replace(targetText, replacementText);

					textNode.setText(text);
				}

				continue;
			}

			if (node instanceof Element && cascade) {
				replace((Element)node, cascade, replacementText, targetText);

				continue;
			}
		}
	}

	public static Element toCodeSnippetElement(String content) {
		return wrapWithNewElement(
			wrapWithNewElement(
				JenkinsResultsParserUtil.redact(content), "code"),
			"pre");
	}

	public static Element wrapWithNewElement(
		Element element, String wrapperTag) {

		Element wrapperElement = new DefaultElement(wrapperTag);

		wrapperElement.add(element);

		return wrapperElement;
	}

	public static Element wrapWithNewElement(
		String content, String wrapperTag) {

		Element wrapperElement = new DefaultElement(wrapperTag);

		wrapperElement.addText(content);

		return wrapperElement;
	}

}