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

package com.liferay.portlet.blogs.util;

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xmlrpc.Response;
import com.liferay.portal.kernel.xmlrpc.XmlRpcException;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.xml.StAXReaderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

/**
 * @author Alexander Chow
 */
public class LinkbackProducerUtil {

	public static void sendPingback(String sourceUri, String targetUri)
		throws Exception {

		_pingbackQueue.add(new Tuple(new Date(), sourceUri, targetUri));
	}

	public static synchronized void sendQueuedPingbacks()
		throws XmlRpcException {

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, -1);

		Date expiration = cal.getTime();

		while (!_pingbackQueue.isEmpty()) {
			Tuple tuple = _pingbackQueue.get(0);

			Date time = (Date)tuple.getObject(0);

			if (time.before(expiration)) {
				_pingbackQueue.remove(0);

				String sourceUri = (String)tuple.getObject(1);
				String targetUri = (String)tuple.getObject(2);

				String serverUri = _discoverPingbackServer(targetUri);

				if (Validator.isNull(serverUri)) {
					continue;
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"XML-RPC pingback " + serverUri + ", source " +
							sourceUri + ", target " + targetUri);
				}

				Response response = XmlRpcUtil.executeMethod(
					serverUri, "pingback.ping",
					new Object[] {sourceUri, targetUri});

				if (_log.isInfoEnabled()) {
					_log.info(response.toString());
				}
			}
			else {
				break;
			}
		}
	}

	public static boolean sendTrackback(
			String trackback, Map<String, String> parts)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Pinging trackback " + trackback);
		}

		Http.Options options = new Http.Options();

		if (_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
		}
		else if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
			options.addHeader(HttpHeaders.USER_AGENT, ReleaseInfo.getName());
		}
		else {
			options.addHeader(
				HttpHeaders.USER_AGENT, ReleaseInfo.getServerInfo());
		}

		options.setLocation(trackback);
		options.setParts(parts);
		options.setPost(true);

		String xml = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug(xml);
		}

		String error = xml;

		XMLStreamReader xmlStreamReader = null;

		try {
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			xmlStreamReader.nextTag();
			xmlStreamReader.nextTag();

			String name = xmlStreamReader.getLocalName();

			if (name.equals("error")) {
				int status = GetterUtil.getInteger(
					xmlStreamReader.getElementText(), 1);

				if (status == 0) {
					if (_log.isInfoEnabled()) {
						_log.info("Trackback accepted");
					}

					return true;
				}

				xmlStreamReader.nextTag();

				name = xmlStreamReader.getLocalName();

				if (name.equals("message")) {
					error = xmlStreamReader.getElementText();
				}
			}
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_log.error(
			"Error while pinging trackback at " + trackback + ": " + error);

		return false;
	}

	private static String _discoverPingbackServer(String targetUri) {
		String serverUri = null;

		try {
			Http.Options options = new Http.Options();

			if (_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
			}
			else if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
				options.addHeader(
					HttpHeaders.USER_AGENT, ReleaseInfo.getName());
			}
			else {
				options.addHeader(
					HttpHeaders.USER_AGENT, ReleaseInfo.getServerInfo());
			}

			options.setLocation(targetUri);
			options.setHead(true);

			HttpUtil.URLtoByteArray(options);

			Http.Response response = options.getResponse();

			serverUri = response.getHeader("X-Pingback");
		}
		catch (Exception e) {
			_log.error("Unable to call HEAD of " + targetUri, e);
		}

		if (Validator.isNotNull(serverUri)) {
			return serverUri;
		}

		try {
			Source clientSource = new Source(HttpUtil.URLtoString(targetUri));

			List<StartTag> startTags = clientSource.getAllStartTags("link");

			for (StartTag startTag : startTags) {
				String rel = startTag.getAttributeValue("rel");

				if (StringUtil.equalsIgnoreCase(rel, "pingback")) {
					String href = startTag.getAttributeValue("href");

					serverUri = HtmlUtil.escape(href);

					break;
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to call GET of " + targetUri, e);
		}

		return serverUri;
	}

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, ReleaseInfo.getName());

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "partial");

	private static final Log _log = LogFactoryUtil.getLog(
		LinkbackProducerUtil.class);

	private static final List<Tuple> _pingbackQueue =
		Collections.synchronizedList(new ArrayList<Tuple>());

}