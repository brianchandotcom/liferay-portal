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

package com.liferay.portal.security.xml;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;

import org.xml.sax.XMLReader;

/**
 * @author Tomas Polesovsky
 */
public class SecureXMLBuilderUtil {

	public static SecureXMLBuilder getSecureDocumentBuilder() {
		PortalRuntimePermission.checkGetBeanProperty(SecureXMLBuilder.class);

		return _secureXMLBuilder;
	}

	public static DocumentBuilderFactory newDocumentBuilderFactory() {
		return getSecureDocumentBuilder().newDocumentBuilderFactory();
	}

	public static XMLInputFactory newXMLInputFactory() {
		return getSecureDocumentBuilder().newXMLInputFactory();
	}

	public static XMLReader newXMLReader() {
		return getSecureDocumentBuilder().newXMLReader();
	}

	public void setSecureXMLBuilder(SecureXMLBuilder secureXMLBuilder) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_secureXMLBuilder = secureXMLBuilder;
	}

	private static SecureXMLBuilder _secureXMLBuilder;

}