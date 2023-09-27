/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xml;

import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

/**
 * @author Zsolt Berentey
 */
public class ElementHandler implements ContentHandler {

	public ElementHandler(
		ElementProcessor elementProcessor, String[] triggers) {

		_elementProcessor = elementProcessor;

		for (String trigger : triggers) {
			_triggers.add(trigger);
		}
	}

	@Override
	public void characters(char[] chars, int start, int length) {
		if (_element != null) {
			_element.setText(new String(chars, start, length));
		}
	}

	@Override
	public void endDocument() {
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (_element != null) {
			_elementProcessor.processElement(_element);

			_element = null;
		}
	}

	@Override
	public void endPrefixMapping(String prefix) {
	}

	@Override
	public void ignorableWhitespace(char[] chars, int start, int length) {
	}

	@Override
	public void processingInstruction(String target, String data) {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void skippedEntity(String name) {
	}

	@Override
	public void startDocument() {
	}

	@Override
	public void startElement(
		String uri, String localName, String qName, Attributes attributes) {

		if (_element != null) {
			_elementProcessor.processElement(_element);

			_element = null;
		}

		if (!_triggers.contains(localName)) {
			return;
		}

		Element element = SAXReaderUtil.createElement(qName);

		for (int i = 0; i < attributes.getLength(); i++) {
			element.addAttribute(
				attributes.getQName(i), attributes.getValue(i));
		}

		_element = element;
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) {
	}

	private Element _element;
	private final ElementProcessor _elementProcessor;
	private final Set<String> _triggers = new HashSet<>();

}