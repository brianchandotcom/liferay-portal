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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.connector.SharepointException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Iván Zaera
 */
public class QueryServiceSoapResult {

	public QueryServiceSoapResult(String queryServiceSoapResultString)
		throws SharepointException {

		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();

			xmlReader.setContentHandler(_defaultHandler);
			xmlReader.setErrorHandler(_defaultHandler);

			parse(xmlReader, queryServiceSoapResultString);
		}
		catch (IOException ioe) {
			throw new SharepointException(
				"Unable to parse response from the Sharepoint server", ioe);
		}
		catch (SAXException saxe) {
			throw new SharepointException(
				"Unable to parse response from the Sharepoint server", saxe);
		}
	}

	public String getDebugErrorMessage() {
		return _debugErrorMessage;
	}

	public List<String> getLinkURLs() {
		return _linkURLs;
	}

	public String getStatus() {
		return _status;
	}

	public boolean isEmpty() {
		if (isSuccess() &&
			_status.equals(
				SharepointConstants.SYMBOLIC_STATUS_NO_RESULTS_FOUND)) {

			return true;
		}

		return false;
	}

	public boolean isSuccess() {
		if (!_status.equals(SharepointConstants.SYMBOLIC_STATUS_SUCCESS) &&
			!_status.equals(
				SharepointConstants.SYMBOLIC_STATUS_NO_RESULTS_FOUND)) {

			return true;
		}

		return false;
	}

	protected void parse(
			XMLReader xmlReader, String queryServiceSoapResultString)
		throws IOException, SAXException {

		byte[] bytes = queryServiceSoapResultString.getBytes(StringPool.UTF8);

		xmlReader.parse(new InputSource(new ByteArrayInputStream(bytes)));
	}

	private void _setDebugErrorMessage(String debugErrorMessage) {
		_debugErrorMessage = debugErrorMessage;
	}

	private void _setStatus(String status) {
		_status = status;
	}

	private String _debugErrorMessage;

	private final DefaultHandler _defaultHandler = new DefaultHandler() {

		@Override
		public void characters(char[] chars, int start, int length) {
			_sb.append(chars, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (localName.equals("DebugErrorMessage")) {
				_setDebugErrorMessage(_sb.toString());
			}
			else if (localName.equals("LinkUrl")) {
				_linkURLs.add(_sb.toString());
			}
			else if (localName.equals("Status")) {
				_setStatus(_sb.toString());
			}
		}

		@Override
		public void startElement(
			String uri, String localName, String qName, Attributes attributes) {

			_sb.setLength(0);
		}

		private final StringBuilder _sb = new StringBuilder();

	};

	private final List<String> _linkURLs = new ArrayList<>();
	private String _status;

}