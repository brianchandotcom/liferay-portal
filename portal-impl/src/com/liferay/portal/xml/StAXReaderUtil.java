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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.util.StringPool;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class StAXReaderUtil {

	public static XMLInputFactory getXMLInputFactory() {
		return _xmlInputFactory;
	}

	public static String read(XMLEventReader xmlEventReader)
		throws XMLStreamException {

		XMLEvent xmlEvent = xmlEventReader.peek();

		if (xmlEvent.isCharacters()) {
			xmlEvent = xmlEventReader.nextEvent();

			return xmlEvent.asCharacters().getData();
		}
		else {
			return StringPool.BLANK;
		}
	}

	private static XMLInputFactory _createXMLInputFactory() {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

		xmlInputFactory.setProperty(
			XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlInputFactory.setProperty(
			XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);

		return xmlInputFactory;
	}

	private static final XMLInputFactory _xmlInputFactory =
		_createXMLInputFactory();

}