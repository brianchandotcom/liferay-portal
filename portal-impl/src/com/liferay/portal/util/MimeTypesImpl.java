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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import org.apache.tika.mime.MimeTypesReaderMetKeys;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
	}

	public void afterPropertiesSet() throws Exception {
		read(
			org.apache.tika.mime.MimeTypes.class.getResourceAsStream(
				"tika-mimetypes.xml"),
			_extensionsMap);

		Map<String, Set<String>> customExtensionsMap = new HashMap<>();

		read(
			MimeTypesImpl.class.getResourceAsStream(
				"/tika/custom-mimetypes.xml"),
			customExtensionsMap);

		for (Map.Entry<String, Set<String>> entry :
				customExtensionsMap.entrySet()) {

			for (String mimeType : entry.getValue()) {
				_customMimeTypes.put(mimeType, entry.getKey());
			}
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

		try (InputStream inputStream = TikaInputStream.get(file)) {
			return getContentType(inputStream, fileName);
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException, ioException);
			}
		}

		return getContentType(fileName);
	}

	@Override
	public String getContentType(InputStream inputStream, String fileName) {
		if (inputStream == null) {
			return getContentType(fileName);
		}

		String contentType = _customMimeTypes.get(_getExtension(fileName));

		if (contentType != null) {
			return contentType;
		}

		Metadata metadata = new Metadata();

		metadata.set(Metadata.RESOURCE_NAME_KEY, HtmlUtil.escapeURL(fileName));

		try (TikaInputStream tikaInputStream = TikaInputStream.get(
				new CloseShieldInputStream(inputStream))) {

			contentType = String.valueOf(
				_detector.detect(tikaInputStream, metadata));
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return contentType;
	}

	@Override
	public String getContentType(String fileName) {
		if (Validator.isNull(fileName)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		String contentType = _customMimeTypes.get(_getExtension(fileName));

		if (contentType != null) {
			return contentType;
		}

		Metadata metadata = new Metadata();

		metadata.set(Metadata.RESOURCE_NAME_KEY, HtmlUtil.escapeURL(fileName));

		try {
			contentType = String.valueOf(_detector.detect(null, metadata));
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return contentType;
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

	protected void read(
			InputStream inputStream, Map<String, Set<String>> extensionsMap)
		throws Exception {

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(new InputSource(inputStream));

		Element element = document.getDocumentElement();

		if ((element == null) || !MIME_INFO_TAG.equals(element.getTagName())) {
			throw new SystemException("Invalid configuration file");
		}

		NodeList nodeList = element.getElementsByTagName(MIME_TYPE_TAG);

		for (int i = 0; i < nodeList.getLength(); i++) {
			readMimeType((Element)nodeList.item(i), extensionsMap);
		}
	}

	protected void readMimeType(
		Element element, Map<String, Set<String>> extensionsMap) {

		Set<String> extensions = new HashSet<>();

		NodeList globNodeList = element.getElementsByTagName(GLOB_TAG);

		for (int i = 0; i < globNodeList.getLength(); i++) {
			Element globElement = (Element)globNodeList.item(i);

			boolean regex = GetterUtil.getBoolean(
				globElement.getAttribute(ISREGEX_ATTR));

			if (regex) {
				continue;
			}

			String pattern = globElement.getAttribute(PATTERN_ATTR);

			if (!pattern.startsWith("*")) {
				continue;
			}

			String extension = pattern.substring(1);

			if (!extension.contains("*") && !extension.contains("?") &&
				!extension.contains("[")) {

				extensions.add(extension);
			}
		}

		if (extensions.isEmpty()) {
			return;
		}

		if (extensions.size() == 1) {
			Iterator<String> iterator = extensions.iterator();

			extensions = Collections.singleton(iterator.next());
		}

		extensionsMap.put(
			element.getAttribute(MIME_TYPE_TYPE_ATTR), extensions);

		NodeList aliasNodeList = element.getElementsByTagName(ALIAS_TAG);

		for (int i = 0; i < aliasNodeList.getLength(); i++) {
			Element aliasElement = (Element)aliasNodeList.item(i);

			extensionsMap.put(
				aliasElement.getAttribute(ALIAS_TYPE_ATTR), extensions);
		}
	}

	private String _getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}

		int pos = fileName.lastIndexOf(CharPool.PERIOD);

		if (pos > 0) {
			return StringUtil.toLowerCase(fileName.substring(pos));
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(MimeTypesImpl.class);

	private final Map<String, String> _customMimeTypes = new HashMap<>();
	private final Detector _detector;
	private final Map<String, Set<String>> _extensionsMap = new HashMap<>();

}