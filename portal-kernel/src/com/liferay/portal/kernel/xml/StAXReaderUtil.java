/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xml;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
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

			Characters characters = xmlEvent.asCharacters();

			return characters.getData();
		}

		return StringPool.BLANK;
	}

	private static XMLInputFactory _createXMLInputFactory() {
		XMLInputFactory xmlInputFactory =
			SecureXMLFactoryProviderUtil.newXMLInputFactory();

		xmlInputFactory.setProperty(
			XMLInputFactory.IS_COALESCING, Boolean.TRUE);

		return xmlInputFactory;
	}

	private static final XMLInputFactory _xmlInputFactory =
		_createXMLInputFactory();

}