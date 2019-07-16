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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.CloseShieldInputStream;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
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

		_webImageMimeTypes = SetUtil.fromArray(
			PropsValues.MIME_TYPES_WEB_IMAGES);
	}

	public void afterPropertiesSet() {
		URL url = org.apache.tika.mime.MimeTypes.class.getResource(
			"tika-mimetypes.xml");

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL customMimeTypesUrl = classLoader.getResource(
			"tika/custom-mimetypes.xml");

		try {
			read(url.openStream(), _extensionsMap);
			read(customMimeTypesUrl.openStream(), _customExtensionsMap);
		}
		catch (Exception e) {
			_log.error("Unable to populate extensions map", e);
		}
	}

	@Override
	public String getContentType(File file) {
		return getContentType(file, file.getName());
	}

	@Override
	public String getContentType(File file, String fileName) {
		if ((file == null) || !file.exists()) {
			return getContentType(fileName);
		}

		try (InputStream is = TikaInputStream.get(file)) {
			return getContentType(is, fileName);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		return getContentType(fileName);
	}

	@Override
	public String getContentType(InputStream inputStream, String fileName) {
		if (inputStream == null) {
			return getContentType(fileName);
		}

		String contentType = null;

		try (TikaInputStream tikaInputStream = TikaInputStream.get(
				new CloseShieldInputStream(inputStream))) {

			contentType = getCustomContentType(FileUtil.getExtension(fileName));

			if (ContentTypes.APPLICATION_OCTET_STREAM.equals(contentType)) {
				Metadata metadata = new Metadata();

				metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);

				MediaType mediaType = _detector.detect(
					tikaInputStream, metadata);

				contentType = mediaType.toString();
			}

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

	@Override
	public String getContentType(String fileName) {
		if (Validator.isNull(fileName)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		String contentType = null;

		try {
			contentType = getCustomContentType(FileUtil.getExtension(fileName));

			if (ContentTypes.APPLICATION_OCTET_STREAM.equals(contentType)) {
				Metadata metadata = new Metadata();

				metadata.set(Metadata.RESOURCE_NAME_KEY, fileName);

				MediaType mediaType = _detector.detect(null, metadata);

				contentType = mediaType.toString();
			}

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

	@Override
	public String getExtensionContentType(String extension) {
		if (Validator.isNull(extension)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		return getContentType("A.".concat(extension));
	}

	@Override
	public Set<String> getExtensions(String contentType) {
		Set<String> extensions = _extensionsMap.get(contentType);

		if (extensions == null) {
			extensions = Collections.emptySet();
		}

		return extensions;
	}

	@Override
	public boolean isWebImage(String mimeType) {
		return _webImageMimeTypes.contains(mimeType);
	}

	protected String getCustomContentType(String extension) {
		if (Validator.isNull(extension)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		for (Map.Entry<String, Set<String>> entry :
				_customExtensionsMap.entrySet()) {

			Set<String> set = entry.getValue();

			if (set.contains(".".concat(extension))) {
				return entry.getKey();
			}
		}

		return ContentTypes.APPLICATION_OCTET_STREAM;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #read(InputStream,
	 *             Map)}
	 */
	@Deprecated
	protected void read(InputStream stream) throws Exception {
		read(stream, _extensionsMap);
	}

	protected void read(
			InputStream stream, Map<String, Set<String>> extensionsMap)
		throws Exception {

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(new InputSource(stream));

		Element element = document.getDocumentElement();

		if ((element == null) || !MIME_INFO_TAG.equals(element.getTagName())) {
			throw new SystemException("Invalid configuration file");
		}

		NodeList nodeList = element.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element childElement = (Element)node;

			if (MIME_TYPE_TAG.equals(childElement.getTagName())) {
				readMimeType(childElement, extensionsMap);
			}
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #readMimeType(Element, Map)}
	 */
	@Deprecated
	protected void readMimeType(Element element) {
		readMimeType(element, _extensionsMap);
	}

	protected void readMimeType(
		Element element, Map<String, Set<String>> extensionsMap) {

		Set<String> mimeTypes = new HashSet<>();

		Set<String> extensions = new HashSet<>();

		String name = element.getAttribute(MIME_TYPE_TYPE_ATTR);

		mimeTypes.add(name);

		NodeList nodeList = element.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element childElement = (Element)node;

			if (ALIAS_TAG.equals(childElement.getTagName())) {
				String alias = childElement.getAttribute(ALIAS_TYPE_ATTR);

				mimeTypes.add(alias);
			}
			else if (GLOB_TAG.equals(childElement.getTagName())) {
				boolean regex = GetterUtil.getBoolean(
					childElement.getAttribute(ISREGEX_ATTR));

				if (regex) {
					continue;
				}

				String pattern = childElement.getAttribute(PATTERN_ATTR);

				if (!pattern.startsWith("*")) {
					continue;
				}

				String extension = pattern.substring(1);

				if (!extension.contains("*") && !extension.contains("?") &&
					!extension.contains("[")) {

					extensions.add(extension);
				}
			}
		}

		if (extensions.isEmpty()) {
			return;
		}

		if (extensions.size() == 1) {
			Iterator<String> iterator = extensions.iterator();

			extensions = Collections.singleton(iterator.next());
		}

		for (String mimeType : mimeTypes) {
			extensionsMap.put(mimeType, extensions);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(MimeTypesImpl.class);

	private final Map<String, Set<String>> _customExtensionsMap =
		new HashMap<>();
	private final Detector _detector;
	private final Map<String, Set<String>> _extensionsMap = new HashMap<>();
	private final Set<String> _webImageMimeTypes;

}