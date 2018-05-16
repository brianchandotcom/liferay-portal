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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.util.Iterator;
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
		String name, String scope, String version, String markup) {

		// TODO

		throw new UnsupportedOperationException();
	}

	@Override
	public void flushBuffer() throws IOException {
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
	public OutputStream getPortletOutputStream() throws IOException {
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
	public PrintWriter getWriter() throws IOException {
		if (_calledGetPortletOutputStream) {
			throw new IllegalStateException(
				"Cannot obtain Writer because OutputStream is already in use");
		}

		if (_printWriter == null) {
			if (_bufferSize > 0) {
				_printWriter = new HeaderPrintWriter(
					new StringWriter(_bufferSize));
			}
			else {
				_printWriter = new HeaderPrintWriter(new StringWriter());
			}
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

	private void _addMarkupToHead(
			String name, String scope, String version, String markup)
		throws IllegalArgumentException {

		markup = StringUtil.trim(markup);

		if (Validator.isBlank(markup)) {
			return;
		}

		String xmlMarkup = markup;

		if ((xmlMarkup.startsWith("<link") || xmlMarkup.startsWith("<LINK")) &&
			!(xmlMarkup.endsWith("</link>") || xmlMarkup.endsWith("</LINK>"))) {

			xmlMarkup += "</link>";
		}

		XMLStreamReader xmlStreamReader = null;

		try {
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xmlMarkup));

			while (xmlStreamReader.hasNext()) {
				int event = xmlStreamReader.next();

				if ((event == XMLStreamConstants.START_ELEMENT) ||
					(event == XMLStreamConstants.END_ELEMENT)) {

					String elementName = xmlStreamReader.getLocalName();

					elementName = StringUtil.toLowerCase(elementName);

					if (!elementName.equals("script") &&
						!elementName.equals("link")) {

						throw new IllegalArgumentException(
							"Invalid markup: " + markup);
					}
				}
			}
		}
		catch (XMLStreamException xmlse) {
			throw new IllegalArgumentException(
				"Invalid markup: " + markup, xmlse);
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}

		if (Validator.isNull(scope)) {
			scope = StringPool.BLANK;
		}

		if (Validator.isNull(version)) {
			version = StringPool.ASCII_TABLE[48];
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
				existingKey, StringPool.COLON);

			if (existingKeyParts.length == 3) {
				String existingName = existingKeyParts[0];
				String existingScope = existingKeyParts[1];
				String existingVersion = existingKeyParts[2];

				if (name.equals(existingName) && scope.equals(existingScope)) {
					SemVer existingSemVer = new SemVer(existingVersion);
					SemVer newSemVer = new SemVer(version);

					if (existingSemVer.compareTo(newSemVer) < 0) {
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
			outputData.addData(
				outputKey, WebKeys.PAGE_TOP, new StringBundler(markup));

			outputData.addOutputKey(outputKey);
		}
	}

	private void _flush() {
		String scope = getNamespace();
		Portlet portlet = getPortlet();

		String version = String.valueOf(portlet.getMvccVersion());

		try {
			if (_portletOutputStream != null) {
				_addMarkupToHead(
					portletName, scope, version,
					_portletOutputStream.toString());
			}
			else if (_printWriter != null) {
				_addMarkupToHead(
					portletName, scope, version, _printWriter.toString());
			}
		}
		catch (IllegalArgumentException iae) {
			_log.error(iae, iae);
		}
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

	private static class SemVer implements Comparable<SemVer> {

		public SemVer(String version) {
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

		@Override
		public int compareTo(SemVer semVer) {
			if (_major < semVer.getMajor()) {
				return -1;
			}
			else if (_major > semVer.getMajor()) {
				return 1;
			}

			if (_minor < semVer.getMinor()) {
				return -1;
			}
			else if (_minor > semVer.getMinor()) {
				return -1;
			}

			if (_micro < semVer.getMicro()) {
				return -1;
			}
			else if (_micro > semVer.getMicro()) {
				return 1;
			}

			return 0;
		}

		public int getMajor() {
			return _major;
		}

		public int getMicro() {
			return _micro;
		}

		public int getMinor() {
			return _minor;
		}

		private final int _major;
		private final int _micro;
		private final int _minor;

	}

	private class HeaderOutputStream extends ByteArrayOutputStream {

		@Override
		public void close() throws IOException {
			_flush();
		}

		@Override
		public synchronized String toString() {
			try {
				return new String(toByteArray(), StringPool.UTF8);
			}
			catch (UnsupportedEncodingException uee) {
				_log.error(uee, uee);
			}

			return StringPool.BLANK;
		}

	}

	private class HeaderPrintWriter extends PrintWriter {

		public HeaderPrintWriter(StringWriter stringWriter) {
			super(stringWriter);

			_stringWriter = stringWriter;
		}

		@Override
		public void flush() {
			_flush();
		}

		@Override
		public String toString() {
			return _stringWriter.toString();
		}

		private final StringWriter _stringWriter;

	}

}