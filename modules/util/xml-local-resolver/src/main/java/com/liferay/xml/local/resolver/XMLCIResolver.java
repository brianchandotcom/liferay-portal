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

package com.liferay.xml.local.resolver;

import com.liferay.xml.local.resolver.internal.util.DefinitionsUtil;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.xmlresolver.tools.ResolvingXMLReader;

/**
 * @author Peter Shin
 */
public class XMLCIResolver extends ResolvingXMLReader {

	public XMLCIResolver() {
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
		throws IOException, SAXException {

		Class<?> clazz = getClass();

		InputSource inputSource = DefinitionsUtil.resolve(
			publicId, systemId, clazz.getClassLoader());

		if (inputSource == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to resolve local file for public id:");
			sb.append(publicId);
			sb.append(" system id:");
			sb.append(systemId);

			throw new IOException(sb.toString());
		}

		return inputSource;
	}

	@Override
	public InputSource resolveEntity(
			String name, String publicId, String baseURI, String systemId)
		throws IOException, SAXException {

		Class<?> clazz = getClass();

		InputSource inputSource = DefinitionsUtil.resolve(
			publicId, systemId, clazz.getClassLoader());

		if (inputSource == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to resolve local file for public id:");
			sb.append(publicId);
			sb.append(" system id:");
			sb.append(systemId);

			throw new IOException(sb.toString());
		}

		return inputSource;
	}

}