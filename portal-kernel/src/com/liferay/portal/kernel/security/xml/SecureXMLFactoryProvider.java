/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.TransformerFactory;

import org.xml.sax.XMLReader;

/**
 * @author Tomas Polesovsky
 */
public interface SecureXMLFactoryProvider {

	public DocumentBuilderFactory newDocumentBuilderFactory();

	public TransformerFactory newTransformerFactory();

	public XMLInputFactory newXMLInputFactory();

	public XMLReader newXMLReader();

}