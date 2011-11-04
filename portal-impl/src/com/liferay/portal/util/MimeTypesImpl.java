/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypesReaderMetKeys;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MimeTypesImpl implements MimeTypes, MimeTypesReaderMetKeys {

	public MimeTypesImpl() {
		_detector = new DefaultDetector(
			org.apache.tika.mime.MimeTypes.getDefaultMimeTypes());

		URL url = org.apache.tika.mime.MimeTypes.class.getResource(
			"tika-mimetypes.xml");

		try {
			read(url.openStream());
		}
		catch (Exception e) {
			_log.error("Problems populating extensions map", e);
		}
	}

	public String getContentType(File file) {
		return getContentType(file, file.getName());
	}

	public String getContentType(File file, String title) {
		InputStream is = null;

		try {
			is = TikaInputStream.get(file);

			return getContentType(is, title);
		}
		catch (FileNotFoundException fnfe) {
			return getContentType(title);
		}
		finally {
			StreamUtil.cleanUp(is);
		}
	}

	public String getContentType(InputStream inputStream, String fileName) {
		if ((inputStream == null) && Validator.isNull(fileName)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		String contentType = null;

		try {
			Metadata metadata = new Metadata();

			metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);

			MediaType mediaType = _detector.detect(
				TikaInputStream.get(inputStream), metadata);

			contentType = mediaType.toString();

			if (contentType.contains("tika")) {
				if (_log.isDebugEnabled()) {
					_log.debug("Retrieved invalid content type " + contentType);
				}

				contentType = getContentType(fileName);
			}

			if (contentType.contains("tika")) {
				if (_log.isDebugEnabled()) {
					_log.debug("Retrieved invalid content type " + contentType);
				}

				contentType = ContentTypes.APPLICATION_OCTET_STREAM;
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			contentType = ContentTypes.APPLICATION_OCTET_STREAM;
		}

		return contentType;
	}

	public String getContentType(String fileName) {
		if (Validator.isNull(fileName)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		try {
			Metadata metadata = new Metadata();

			metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);

			MediaType mediaType = _detector.detect(null, metadata);

			String contentType = mediaType.toString();

			if (!contentType.contains("tika")) {
				return contentType;
			}
			else if (_log.isDebugEnabled()) {
				_log.debug("Retrieved invalid content type " + contentType);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return ContentTypes.APPLICATION_OCTET_STREAM;
	}

	public Set<String> getExtensions(String contentType) {
		Set<String> extensions = _extensionsMap.get(contentType);

		if (extensions == null) {
			extensions = Collections.emptySet();
		}

		return extensions;
	}

	protected void read(InputStream stream) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(new InputSource(stream));

		Element element = document.getDocumentElement();

		if ((element == null) ||
			!element.getTagName().equals(MIME_INFO_TAG)) {

			throw new SystemException("Invalid configuration file");
		}

		NodeList nodes = element.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element)node;

				if (childElement.getTagName().equals(MIME_TYPE_TAG)) {
					readMimeType(childElement);
				}
			}
		}
	}

	protected void readMimeType(Element element) throws MimeTypeException {
		Set<String> mimeTypes = new HashSet<String>();

		Set<String> extensions = new HashSet<String>();

		String name = element.getAttribute(MIME_TYPE_TYPE_ATTR);

		mimeTypes.add(name);

		NodeList nodes = element.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element)node;

				if (childElement.getTagName().equals(GLOB_TAG)) {
					boolean isRegex = GetterUtil.getBoolean(
						childElement.getAttribute(ISREGEX_ATTR));

					String pattern = childElement.getAttribute(PATTERN_ATTR);

					if (!isRegex && pattern.startsWith("*")) {
						String extension = pattern.substring(1);

						if (!extension.contains("*") &&
							!extension.contains("?") &&
							!extension.contains("[")) {

							extensions.add(extension);
						}
					}
				}
				else if (childElement.getTagName().equals(ALIAS_TAG)) {
					String alias = childElement.getAttribute(ALIAS_TYPE_ATTR);

					mimeTypes.add(alias);
				}
			}
		}

		for (String mimeType : mimeTypes) {
			_extensionsMap.put(mimeType, extensions);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MimeTypesImpl.class);

	private Detector _detector;

	private Map<String, Set<String>> _extensionsMap =
		new HashMap<String, Set<String>>();

}