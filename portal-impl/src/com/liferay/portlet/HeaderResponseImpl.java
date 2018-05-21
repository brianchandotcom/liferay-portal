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

package com.liferay.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.io.unsync.UnsyncPrintWriter;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.xml.StAXReaderUtil;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Neil Griffin
 */
@ProviderType
public class HeaderResponseImpl
	extends MimeResponseImpl implements HeaderResponse {

	@Override
	public void addDependency(String name, String scope, String version) {
		addDependency(name, scope, version, null);
	}

	@Override
	public void addDependency(
		String name, String scope, String version, String xml) {

		// TODO

		throw new UnsupportedOperationException();
	}

	@Override
	public void flushBuffer() {
		if (_printWriter != null) {
			_printWriter.flush();
		}

		_calledFlushBuffer = true;
	}

	@Override
	public int getBufferSize() {
		return _bufferSize;
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.HEADER_PHASE;
	}

	@Override
	public OutputStream getPortletOutputStream() {
		if (_calledGetWriter) {
			throw new IllegalStateException(
				"Unable to obtain OutputStream because Writer is already in " +
					"use");
		}

		if (_portletOutputStream == null) {
			_portletOutputStream = new HeaderOutputStream();
		}

		_calledGetPortletOutputStream = true;

		return _portletOutputStream;
	}

	public String getTitle() {
		return _title;
	}

	public Boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	@Override
	public PrintWriter getWriter() {
		if (_calledGetPortletOutputStream) {
			throw new IllegalStateException(
				"Cannot obtain Writer because OutputStream is already in use");
		}

		if (_printWriter == null) {
			_printWriter = new HeaderPrintWriter(new UnsyncStringWriter());
		}

		_calledGetWriter = true;

		return _printWriter;
	}

	@Override
	public boolean isCalledFlushBuffer() {
		return _calledFlushBuffer;
	}

	@Override
	public boolean isCalledGetPortletOutputStream() {
		return _calledGetPortletOutputStream;
	}

	@Override
	public boolean isCalledGetWriter() {
		return _calledGetWriter;
	}

	@Override
	public boolean isCommitted() {
		if (_calledFlushBuffer || super.isCommitted()) {
			return true;
		}

		return false;
	}

	@Override
	public void reset() {
		if (_calledFlushBuffer) {
			throw new IllegalStateException(
				"Cannot reset a buffer that has been flushed");
		}

		_portletOutputStream = null;
		_printWriter = null;
	}

	@Override
	public void resetBuffer() {
		reset();
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (_calledFlushBuffer) {
			throw new IllegalStateException(
				"Unable to set buffer size because buffer has been flushed");
		}

		_bufferSize = bufferSize;
	}

	@Override
	public void setContentType(String contentType) {
		if ((_printWriter != null) || (_portletOutputStream != null)) {
			return;
		}

		super.setContentType(contentType);
	}

	@Override
	public void setTitle(String title) {
		_title = title;

		// See LEP-2188

		ThemeDisplay themeDisplay = (ThemeDisplay)
			portletRequestImpl.getAttribute(WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setTitle(_title);
	}

	public void setUseDefaultTemplate(Boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	public void writeToHead() {
		if (_portletOutputStream != null) {
			_addXMLToHead(
				portletName, getNamespace(), _portletOutputStream.toString());
		}
		else if (_printWriter != null) {
			_addXMLToHead(portletName, getNamespace(), _printWriter.toString());
		}
	}

	private void _addDependencyToHead(
		String name, String scope, String version, StringBundler markupSB) {

		if (Validator.isNull(scope)) {
			scope = StringPool.BLANK;
		}

		SemVer semVer = SemVer._DEFAULT;

		if (Validator.isNotNull(version)) {
			semVer = new SemVer(version);
		}

		HeaderRequest headerRequest = (HeaderRequest)getPortletRequest();

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(headerRequest);

		OutputData outputData = (OutputData)httpServletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		if (outputData == null) {
			outputData = new OutputData();

			httpServletRequest.setAttribute(WebKeys.OUTPUT_DATA, outputData);
		}

		Set<String> outputKeys = outputData.getOutputKeys();

		Iterator<String> iterator = outputKeys.iterator();

		while (iterator.hasNext()) {
			String existingKey = iterator.next();

			String[] existingKeyParts = StringUtil.split(
				existingKey, CharPool.COLON);

			if (existingKeyParts.length == 3) {
				String existingName = existingKeyParts[0];
				String existingScope = existingKeyParts[1];
				String existingVersion = existingKeyParts[2];

				if (name.equals(existingName) && scope.equals(existingScope)) {
					SemVer existingSemVer = new SemVer(existingVersion);

					if (existingSemVer.compareTo(semVer) < 0) {
						iterator.remove();
						outputData.setData(existingKey, WebKeys.PAGE_TOP, null);
					}

					break;
				}
			}
		}

		StringBundler sb = new StringBundler(5);

		sb.append(name);
		sb.append(StringPool.COLON);
		sb.append(scope);
		sb.append(StringPool.COLON);
		sb.append(version);

		String outputKey = sb.toString();

		if (!outputKeys.contains(outputKey)) {
			outputData.addData(outputKey, WebKeys.PAGE_TOP, markupSB);

			outputData.addOutputKey(outputKey);
		}
	}

	private void _addXMLToHead(String name, String scope, String xml) {
		xml = StringUtil.trim(xml);

		if (Validator.isBlank(xml)) {
			return;
		}

		List<ParsedElement> parsedElements = _parseElements(xml);

		for (ParsedElement parsedElement : parsedElements) {
			if (parsedElement.isValid()) {
				StringBundler sb = new StringBundler();

				sb.append(StringPool.LESS_THAN);
				sb.append(parsedElement.getName());

				Map<String, String> attributes = parsedElement.getAttributes();

				for (Map.Entry<String, String> entry : attributes.entrySet()) {
					sb.append(StringPool.SPACE);
					sb.append(entry.getKey());
					sb.append(StringPool.EQUAL);
					sb.append(StringPool.QUOTE);
					sb.append(entry.getValue());
					sb.append(StringPool.QUOTE);
				}

				sb.append(StringPool.GREATER_THAN);
				sb.append(parsedElement.getText());
				sb.append(StringPool.LESS_THAN);
				sb.append(StringPool.FORWARD_SLASH);
				sb.append(parsedElement.getName());
				sb.append(StringPool.GREATER_THAN);

				_addDependencyToHead(name, scope, null, sb);
			}
			else {
				_log.error("Invalid element: " + parsedElement.getName());
			}
		}
	}

	private List<ParsedElement> _parseElements(String xml) {
		if (xml == null) {
			return Collections.emptyList();
		}

		List<ParsedElement> parsedElements = new ArrayList<>();

		XMLStreamReader xmlStreamReader = null;

		try {
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			while (xmlStreamReader.hasNext()) {
				int event = xmlStreamReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					String elementName = xmlStreamReader.getLocalName();

					if (elementName.equals("link") ||
						elementName.equals("script")) {

						Map<String, String> elementAttributes =
							new LinkedHashMap<>();

						int attributeCount =
							xmlStreamReader.getAttributeCount();

						for (int i = 0; i < attributeCount; i++) {
							elementAttributes.put(
								xmlStreamReader.getAttributeLocalName(i),
								xmlStreamReader.getAttributeValue(i));
						}

						parsedElements.add(
							new ParsedElement(
								elementName, elementAttributes,
								xmlStreamReader.getElementText(), true));
					}
					else {
						parsedElements.add(
							new ParsedElement(elementName, null, null, false));
					}
				}
			}
		}
		catch (XMLStreamException xmlse) {
			_log.error("Invalid markup: " + xml, xmlse);
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (XMLStreamException xmlse) {
					_log.error(xmlse, xmlse);
				}
			}
		}

		return parsedElements;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HeaderResponseImpl.class);

	private int _bufferSize;
	private boolean _calledFlushBuffer;
	private boolean _calledGetPortletOutputStream;
	private boolean _calledGetWriter;
	private OutputStream _portletOutputStream;
	private PrintWriter _printWriter;
	private String _title;
	private Boolean _useDefaultTemplate;

	private static class ParsedElement {

		public Map<String, String> getAttributes() {
			return _attributes;
		}

		public String getName() {
			return _name;
		}

		public String getText() {
			return _text;
		}

		public boolean isValid() {
			return _valid;
		}

		private ParsedElement(
			String name, Map<String, String> attributes, String text,
			boolean valid) {

			_name = name;
			_attributes = attributes;
			_text = text;
			_valid = valid;
		}

		private final Map<String, String> _attributes;
		private final String _name;
		private final String _text;
		private final boolean _valid;

	}

	private static class SemVer implements Comparable<SemVer> {

		@Override
		public int compareTo(SemVer semVer) {
			int result = Integer.compare(_major, semVer._major);

			if (result != 0) {
				return result;
			}

			result = Integer.compare(_minor, semVer._minor);

			if (result != 0) {
				return result;
			}

			return Integer.compare(_micro, semVer._micro);
		}

		private SemVer(int major, int minor, int micro) {
			_major = major;
			_minor = minor;
			_micro = micro;
		}

		private SemVer(String version) {
			String[] parts = StringUtil.split(version, CharPool.PERIOD);

			if (parts.length > 0) {
				_major = GetterUtil.getInteger(parts[0]);
			}
			else {
				_major = 0;
			}

			if (parts.length > 1) {
				_minor = GetterUtil.getInteger(parts[1]);
			}
			else {
				_minor = 0;
			}

			if (parts.length > 2) {
				_micro = GetterUtil.getInteger(parts[2]);
			}
			else {
				_micro = 0;
			}
		}

		private static final SemVer _DEFAULT = new SemVer(0, 0, 0);

		private final int _major;
		private final int _micro;
		private final int _minor;

	}

	private class HeaderOutputStream extends UnsyncByteArrayOutputStream {

		@Override
		public String toString() {
			try {
				return toString(getCharacterEncoding());
			}
			catch (UnsupportedEncodingException uee) {
				_log.error(uee, uee);
			}

			return StringPool.BLANK;
		}

	}

	private class HeaderPrintWriter extends UnsyncPrintWriter {

		@Override
		public String toString() {
			return _unsyncStringWriter.toString();
		}

		private HeaderPrintWriter(UnsyncStringWriter unsyncStringWriter) {
			super(unsyncStringWriter);

			_unsyncStringWriter = unsyncStringWriter;
		}

		private final UnsyncStringWriter _unsyncStringWriter;

	}

}