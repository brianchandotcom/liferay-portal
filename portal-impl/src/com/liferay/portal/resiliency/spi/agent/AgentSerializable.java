/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.resiliency.spi.agent;

import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxException;
import com.liferay.portal.kernel.nio.intraband.mailbox.MailboxUtil;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.Direction;
import com.liferay.portal.kernel.resiliency.spi.agent.annotation.DistributedRegistry;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ThreadLocalDistributor;
import com.liferay.portal.kernel.util.ThreadLocalDistributorRegistry;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 */
public class AgentSerializable implements Serializable {

	public static Map<String, Serializable> extractDistributedRequestAttributes(
		HttpServletRequest request, Direction direction) {

		Map<String, Serializable> distributedRequestAttributes =
			new HashMap<String, Serializable>();

		Enumeration<String> enumeration = request.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			if (DistributedRegistry.isDistributed(name, direction)) {
				Object value = request.getAttribute(name);

				if (value instanceof Serializable) {
					distributedRequestAttributes.put(name, (Serializable)value);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(
						"Nonserializable distributed request attribute name " +
							name + " with value " + value);
				}
			}
			else if (_log.isDebugEnabled()) {
				_log.debug(
					"Nondistributed request attribute name " + name +
						" with direction " + direction + " and value " +
							request.getAttribute(name));
			}
		}

		return distributedRequestAttributes;
	}

	public static Map<String, List<String>> extractRequestHeaders(
		HttpServletRequest request) {

		Map<String, List<String>> headers = new HashMap<String, List<String>>();

		Enumeration<String> nameEnumeration = request.getHeaderNames();

		while (nameEnumeration.hasMoreElements()) {
			String headerName = nameEnumeration.nextElement();

			// Remove Accept-Encoding header, to prevent content modification

			if (HttpHeaders.ACCEPT_ENCODING.equalsIgnoreCase(headerName)) {
				continue;
			}

			// Directly passing around cookie

			if (HttpHeaders.COOKIE.equalsIgnoreCase(headerName)) {
				continue;
			}

			Enumeration<String> valueEnumeration = request.getHeaders(
				headerName);

			if (valueEnumeration != null) {
				List<String> values = new ArrayList<String>();

				while (valueEnumeration.hasMoreElements()) {
					values.add(valueEnumeration.nextElement());
				}

				if (values.isEmpty()) {
					values = Collections.emptyList();
				}

				headers.put(headerName.toLowerCase(), values);
			}
		}

		if (headers.isEmpty()) {
			headers = Collections.emptyMap();
		}

		return headers;
	}

	public static Map<String, Serializable> extractSessionAttributes(
		HttpSession session) {

		Map<String, Serializable> sessionAttributes =
			new HashMap<String, Serializable>();

		Enumeration<String> enumeration = session.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			Object value = session.getAttribute(name);

			if (value instanceof Serializable) {
				sessionAttributes.put(name, (Serializable)value);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Nonserializable session attribute name " + name +
						" with value " + value);
			}
		}

		return sessionAttributes;
	}

	public static <T extends AgentSerializable> T readFrom(
			InputStream inputStream)
		throws IOException {

		byte[] data = new byte[8];
		int length = 0;

		while (length < 8) {
			int count = inputStream.read(data, length, 8 - length);

			if (count < 0) {
				throw new EOFException();
			}

			length += count;
		}

		long receipt = BigEndianCodec.getLong(data, 0);

		ByteBuffer byteBuffer = MailboxUtil.receiveMail(receipt);

		if (byteBuffer == null) {
			throw new IllegalArgumentException(
				"No mail with receipt " + receipt);
		}

		Deserializer deserializer = new Deserializer(byteBuffer);

		try {
			return deserializer.readObject();
		}
		catch (ClassNotFoundException cnfe) {
			throw new IOException(cnfe);
		}
	}

	public void writeTo(
			RegistrationReference registrationReference,
			OutputStream outputStream)
		throws IOException {

		Serializer serializer = new Serializer();

		serializer.writeObject(this);

		try {
			byte[] data = new byte[8];

			ByteBuffer byteBuffer = serializer.toByteBuffer();

			long receipt = MailboxUtil.sendMail(
				registrationReference, byteBuffer);

			BigEndianCodec.putLong(data, 0, receipt);

			outputStream.write(data);

			outputStream.flush();
		}
		catch (MailboxException me) {
			throw new IOException(me);
		}
	}

	protected void captureThreadLocals() {
		_threadLocalDistributors =
			ThreadLocalDistributorRegistry.getThreadLocalDistributors();

		for (ThreadLocalDistributor threadLocalDistributor :
				_threadLocalDistributors) {

			threadLocalDistributor.capture();
		}
	}

	protected void restoreThreadLocals() {
		for (ThreadLocalDistributor threadLocalDistributor :
				_threadLocalDistributors) {

			threadLocalDistributor.restore();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AgentSerializable.class);

	private ThreadLocalDistributor[] _threadLocalDistributors;

}