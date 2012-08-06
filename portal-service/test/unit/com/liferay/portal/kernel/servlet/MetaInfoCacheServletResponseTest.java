/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;

/**
 * @author Shuyang Zhou
 */
public class MetaInfoCacheServletResponseTest extends TestCase {

	public void testAddCookie() {
		Cookie cookie1 = new Cookie("testCookieName1", "testCookieValue1");
		Cookie cookie2 = new Cookie("testCookieName2", "testCookieValue2");

		final List<Cookie> cookieList = new ArrayList<Cookie>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addCookie(Cookie cookie) {
					cookieList.add(cookie);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		Collection<String> headerNames =
			metaInfoCacheServletResponse.getHeaderNames();

		assertEquals(0, headerNames.size());

		assertNull(metaInfoCacheServletResponse.getHeader("Set-Cookie"));
		assertEquals(
			0, metaInfoCacheServletResponse.getHeaders("Set-Cookie").size());

		// First add

		metaInfoCacheServletResponse.addCookie(cookie1);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("Set-Cookie"));

		Header cookieHeader1 = new Header(cookie1);

		Set<Header> cookieHeaders = headers.get("Set-Cookie");

		assertEquals(1, cookieHeaders.size());
		assertTrue(cookieHeaders.contains(cookieHeader1));
		assertEquals(1, cookieList.size());
		assertEquals(cookie1, cookieList.get(0));

		Collection<String> headerStrings =
			metaInfoCacheServletResponse.getHeaders("Set-Cookie");

		assertEquals(1, headerStrings.size());
		assertTrue(headerStrings.contains(cookieHeader1.toString()));

		assertEquals(
			cookieHeader1.toString(),
			metaInfoCacheServletResponse.getHeader("Set-Cookie"));

		assertTrue(headerNames.contains("Set-Cookie"));

		// Second add

		metaInfoCacheServletResponse.addCookie(cookie2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("Set-Cookie"));

		Header cookieHeader2 = new Header(cookie2);

		cookieHeaders = headers.get("Set-Cookie");

		assertEquals(2, cookieHeaders.size());
		assertTrue(cookieHeaders.contains(cookieHeader2));
		assertEquals(2, cookieList.size());
		assertEquals(cookie2, cookieList.get(1));

		headerStrings = metaInfoCacheServletResponse.getHeaders("Set-Cookie");

		assertEquals(2, headerStrings.size());
		assertTrue(headerStrings.contains(cookieHeader2.toString()));
	}

	public void testAddDateHeader() {
		final List<ObjectValuePair<String, Long>> dateList =
			new ArrayList<ObjectValuePair<String, Long>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {
				@Override
				public void addDateHeader(String name, long value) {
					dateList.add(
						new ObjectValuePair<String, Long>(name, value));
				}
			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// First add

		metaInfoCacheServletResponse.addDateHeader("date1", 1);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date1"));

		Set<Header> dateHeaders1 = headers.get("date1");

		assertEquals(1, dateHeaders1.size());
		assertTrue(dateHeaders1.contains(new Header(1L)));
		assertEquals(1, dateList.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 1L), dateList.get(0));

		// Second add

		metaInfoCacheServletResponse.addDateHeader("date1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date1"));

		dateHeaders1 = headers.get("date1");

		assertEquals(2, dateHeaders1.size());
		assertTrue(dateHeaders1.contains(new Header(2L)));
		assertEquals(2, dateList.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 2L), dateList.get(1));

		// Third add

		metaInfoCacheServletResponse.addDateHeader("date2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date2"));

		Set<Header> dateHeaders2 = headers.get("date2");

		assertEquals(1, dateHeaders2.size());
		assertTrue(dateHeaders2.contains(new Header(1L)));
		assertEquals(3, dateList.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date2", 1L), dateList.get(2));
	}

	public void testAddHeader() {
		final List<ObjectValuePair<String, String>> headerList =
			new ArrayList<ObjectValuePair<String, String>>();

		final AtomicReference<String> contentTypeReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addHeader(String name, String value) {
					headerList.add(
						new ObjectValuePair<String, String>(name, value));
				}

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setContentType(String contentType) {
					contentTypeReference.set(contentType);
				}
			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// Special add for ContentType

		metaInfoCacheServletResponse.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.TEXT_HTML);

		assertEquals(0, headers.size());
		assertEquals(
			ContentTypes.TEXT_HTML,
			metaInfoCacheServletResponse.getContentType());
		assertEquals(ContentTypes.TEXT_HTML, contentTypeReference.get());

		// First add

		metaInfoCacheServletResponse.addHeader("name1", "value1");

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		Set<Header> headers1 = headers.get("name1");

		assertEquals(1, headers1.size());
		assertTrue(headers1.contains(new Header("value1")));
		assertEquals(1, headerList.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value1"),
			headerList.get(0));

		// Second add

		metaInfoCacheServletResponse.addHeader("name1", "value2");

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		headers1 = headers.get("name1");

		assertEquals(2, headers1.size());
		assertTrue(headers1.contains(new Header("value2")));
		assertEquals(2, headerList.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value2"),
			headerList.get(1));

		// Third add

		metaInfoCacheServletResponse.addHeader("name2", "value1");

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> headers2 = headers.get("name2");

		assertEquals(1, headers2.size());
		assertTrue(headers2.contains(new Header("value1")));
		assertEquals(3, headerList.size());
		assertEquals(
			new ObjectValuePair<String, String>("name2", "value1"),
			headerList.get(2));
	}

	public void testAddIntHeader() {
		final List<ObjectValuePair<String, Integer>> intList =
			new ArrayList<ObjectValuePair<String, Integer>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {
				@Override
				public void addIntHeader(String name, int value) {
					intList.add(
						new ObjectValuePair<String, Integer>(name, value));
				}
			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// First add

		metaInfoCacheServletResponse.addIntHeader("name1", 1);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		Set<Header> intHeaders1 = headers.get("name1");

		assertEquals(1, intHeaders1.size());
		assertTrue(intHeaders1.contains(new Header(1)));
		assertEquals(1, intList.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 1), intList.get(0));

		// Second add

		metaInfoCacheServletResponse.addIntHeader("name1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		intHeaders1 = headers.get("name1");

		assertEquals(2, intHeaders1.size());
		assertTrue(intHeaders1.contains(new Header(2)));
		assertEquals(2, intList.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 2), intList.get(1));

		// Third add

		metaInfoCacheServletResponse.addIntHeader("name2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> intHeaders2 = headers.get("name2");

		assertEquals(1, intHeaders2.size());
		assertTrue(intHeaders2.contains(new Header(1)));
		assertEquals(3, intList.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name2", 1), intList.get(2));
	}

	public void testConstructor() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		assertSame(
			stubHttpServletResponse,
			metaInfoCacheServletResponse.getResponse());
	}

	public void testFinishResponse() throws IOException {
		final AtomicLong contentLengthReference = new AtomicLong();
		final AtomicReference<String> locationReference =
			new AtomicReference<String>();
		final AtomicReference<String> messageReference =
			new AtomicReference<String>();
		final AtomicInteger statusCode = new AtomicInteger();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addHeader(String name, String value) {
				}

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void resetBuffer() {
				}

				@Override
				public void sendError(int status, String errorMessage)
					throws IOException {

					statusCode.set(status);
					messageReference.set(errorMessage);
				}

				@Override
				public void sendRedirect(String location) throws IOException {
					locationReference.set(location);
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
				}

				@Override
				public void setContentLength(int contentLength) {
					contentLengthReference.set(contentLength);
				}

				@Override
				public void setContentType(String contentType) {
				}

				@Override
				public void setHeader(String name, String value) {
				}

				@Override
				public void setLocale(Locale locale) {
				}

				@Override
				public void setStatus(int status, String message) {
					statusCode.set(status);
					messageReference.set(message);
				}

			};

		// Clean
		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		metaInfoCacheServletResponse.finishResponse();

		// Headers transfer
		MetaInfoCacheServletResponse innerResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);
		MetaInfoCacheServletResponse outterResponse =
			new MetaInfoCacheServletResponse(innerResponse);

		innerResponse.addHeader("name1", "value3");
		innerResponse.addHeader("name2", "value3");

		outterResponse.addHeader("name1", "value1");
		outterResponse.addHeader("name1", "value2");
		outterResponse.addHeader("name2", "value1");

		outterResponse.finishResponse();

		Map<String, Set<Header>> headers = innerResponse.getHeaders();

		assertEquals(2, headers.size());

		Set<Header> headers1 = headers.get("name1");

		assertEquals(2, headers1.size());
		assertTrue(headers1.contains(new Header("value1")));
		assertTrue(headers1.contains(new Header("value2")));

		Set<Header> headers2 = headers.get("name2");

		assertEquals(1, headers2.size());
		assertTrue(headers2.contains(new Header("value1")));

		// SendRedirect
		MetaInfoCacheServletResponse fromResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.sendRedirect("testURL");

		MetaInfoCacheServletResponse toResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.setResponse(toResponse);

		locationReference.set(null);

		fromResponse.finishResponse();

		assertEquals("testURL", locationReference.get());

		// SendError
		fromResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.sendError(400, "Bad Page");

		toResponse = new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.setResponse(toResponse);

		statusCode.set(0);
		messageReference.set(null);

		fromResponse.finishResponse();

		assertEquals(400, statusCode.get());
		assertEquals("Bad Page", messageReference.get());

		// Normal
		fromResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
		fromResponse.setLocale(Locale.ENGLISH);
		fromResponse.setContentLength(2048);
		fromResponse.setStatus(302, "moved");

		toResponse = new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.setResponse(toResponse);

		contentLengthReference.set(0);
		statusCode.set(0);
		messageReference.set(null);

		fromResponse.finishResponse();

		assertEquals(StringPool.UTF8, toResponse.getCharacterEncoding());
		assertEquals(ContentTypes.TEXT_HTML, toResponse.getContentType());
		assertEquals(Locale.ENGLISH, toResponse.getLocale());
		assertEquals(2048, contentLengthReference.get());
		assertEquals(302, statusCode.get());
		assertEquals("moved", messageReference.get());

		// FinishResponse after commit

		fromResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.sendRedirect("testURL");

		toResponse = new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromResponse.setResponse(toResponse);

		locationReference.set(null);

		toResponse.flushBuffer();

		fromResponse.finishResponse();

		assertNull(locationReference.get());
	}

	public void testFlushBuffer() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		assertFalse(metaInfoCacheServletResponse.isCommitted());

		metaInfoCacheServletResponse.flushBuffer();

		assertTrue(metaInfoCacheServletResponse.isCommitted());
	}

	public void testGetOutputStream() throws IOException {
		final AtomicBoolean calledGetOutputStream = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public ServletOutputStream getOutputStream()
					throws IOException {

					calledGetOutputStream.set(true);

					return null;
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		assertFalse(metaInfoCacheServletResponse.calledGetOutputStream);
		assertFalse(calledGetOutputStream.get());

		metaInfoCacheServletResponse.getOutputStream();

		assertTrue(metaInfoCacheServletResponse.calledGetOutputStream);
		assertTrue(calledGetOutputStream.get());
	}

	public void testGetSetBufferSize() {
		final AtomicInteger bufferSizeReference = new AtomicInteger();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setBufferSize(int bufferSzie) {
					bufferSizeReference.set(bufferSzie);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Get default
		assertEquals(0, metaInfoCacheServletResponse.getBufferSize());

		// Normal set
		metaInfoCacheServletResponse.setBufferSize(1024);

		assertEquals(1024, metaInfoCacheServletResponse.getBufferSize());
		assertEquals(1024, bufferSizeReference.get());

		// Set after commit
		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.setBufferSize(2048);
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testGetSetCharacterEncoding() throws IOException {
		final AtomicReference<String> characterEncodingReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
					characterEncodingReference.set(characterEncoding);
				}

				@Override
				public PrintWriter getWriter() throws IOException {
					return null;
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Get default

		assertEquals(
			StringPool.DEFAULT_CHARSET_NAME,
			metaInfoCacheServletResponse.getCharacterEncoding());

		// Null set

		metaInfoCacheServletResponse.setCharacterEncoding(null);

		assertEquals(
			StringPool.DEFAULT_CHARSET_NAME,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertNull(characterEncodingReference.get());

		// Normal set

		metaInfoCacheServletResponse.setCharacterEncoding(
			StringPool.ISO_8859_1);

		assertEquals(
			StringPool.ISO_8859_1,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertEquals(StringPool.ISO_8859_1, characterEncodingReference.get());

		characterEncodingReference.set(null);

		// Set after getWriter

		metaInfoCacheServletResponse.getWriter();

		metaInfoCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		assertEquals(
			StringPool.ISO_8859_1,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertNull(characterEncodingReference.get());

		// Set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setCharacterEncoding(StringPool.UTF8);

		assertEquals(
			StringPool.ISO_8859_1,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertNull(characterEncodingReference.get());
	}

	public void testGetSetContentType() {
		final AtomicReference<String> characterEncodingReference =
			new AtomicReference<String>();
		final AtomicReference<String> contentTypeReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
					characterEncodingReference.set(characterEncoding);
				}

				@Override
				public void setContentType(String contentType) {
					contentTypeReference.set(contentType);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Get default

		assertNull(metaInfoCacheServletResponse.getContentType());

		// Null set

		metaInfoCacheServletResponse.setContentType(null);

		assertNull(metaInfoCacheServletResponse.getContentType());
		assertNull(contentTypeReference.get());

		// Set with character encoding

		metaInfoCacheServletResponse.setContentType(
			ContentTypes.TEXT_HTML_UTF8);

		assertEquals(
			StringPool.UTF8,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertEquals(StringPool.UTF8, characterEncodingReference.get());
		assertEquals(
			ContentTypes.TEXT_HTML_UTF8,
			metaInfoCacheServletResponse.getContentType());

		characterEncodingReference.set(null);

		// Set without character encoding

		metaInfoCacheServletResponse.setContentType(ContentTypes.TEXT_HTML);

		assertEquals(
			StringPool.DEFAULT_CHARSET_NAME,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertNull(characterEncodingReference.get());
		assertEquals(
			ContentTypes.TEXT_HTML,
			metaInfoCacheServletResponse.getContentType());
		assertEquals(ContentTypes.TEXT_HTML, contentTypeReference.get());

		// Set with broken character encoding

		metaInfoCacheServletResponse.setContentType(
			"text/html; charset0=UTF-8");

		assertEquals(
			StringPool.DEFAULT_CHARSET_NAME,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertNull(characterEncodingReference.get());
		assertEquals(
			ContentTypes.TEXT_HTML,
			metaInfoCacheServletResponse.getContentType());
		assertEquals("text/html; charset0=UTF-8", contentTypeReference.get());

		characterEncodingReference.set(null);
		contentTypeReference.set(null);

		// Set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setContentType(
			ContentTypes.TEXT_HTML_UTF8);

		assertEquals(
			StringPool.DEFAULT_CHARSET_NAME,
			metaInfoCacheServletResponse.getCharacterEncoding());
		assertEquals(
			ContentTypes.TEXT_HTML,
			metaInfoCacheServletResponse.getContentType());
		assertNull(characterEncodingReference.get());
		assertNull(contentTypeReference.get());
	}

	public void testGetSetLocale() {
		final AtomicReference<Locale> localeReference =
			new AtomicReference<Locale>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setLocale(Locale locale) {
					localeReference.set(locale);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal set

		metaInfoCacheServletResponse.setLocale(Locale.ENGLISH);

		assertEquals(Locale.ENGLISH, metaInfoCacheServletResponse.getLocale());
		assertEquals(Locale.ENGLISH, localeReference.get());

		localeReference.set(null);

		// Set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setLocale(Locale.FRENCH);

		assertEquals(Locale.ENGLISH, metaInfoCacheServletResponse.getLocale());
		assertNull(localeReference.get());
	}

	public void testGetWriter() throws IOException {
		final AtomicBoolean calledGetWriter = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public PrintWriter getWriter() throws IOException {
					calledGetWriter.set(true);

					return null;
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		assertFalse(metaInfoCacheServletResponse.calledGetWriter);
		assertFalse(calledGetWriter.get());

		metaInfoCacheServletResponse.getWriter();

		assertTrue(metaInfoCacheServletResponse.calledGetWriter);
		assertTrue(calledGetWriter.get());
	}

	public void testIsCommitted() {
		final AtomicBoolean committed = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return committed.get();
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		assertFalse(metaInfoCacheServletResponse.isCommitted());

		// Commit wrapped instance

		committed.set(true);

		assertTrue(metaInfoCacheServletResponse.isCommitted());

		committed.set(false);

		// Commit wrapper

		metaInfoCacheServletResponse.flushBuffer();

		assertTrue(metaInfoCacheServletResponse.isCommitted());

		// Commit both

		committed.set(true);

		assertTrue(metaInfoCacheServletResponse.isCommitted());
	}

	public void testReset() {
		final AtomicBoolean calledReset = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void reset() {
					calledReset.set(true);
				}

				@Override
				public void resetBuffer() {
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal resetBuffer

		metaInfoCacheServletResponse.reset();

		assertTrue(calledReset.get());

		// ResetBuffer after commit

		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.reset();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testResetBuffer() {
		final AtomicBoolean calledResetBuffer = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void resetBuffer() {
					calledResetBuffer.set(true);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal resetBuffer

		metaInfoCacheServletResponse.resetBuffer();

		assertTrue(calledResetBuffer.get());

		// ResetBuffer after commit

		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.resetBuffer();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testSendError() throws IOException {
		final AtomicInteger statusCode = new AtomicInteger();
		final AtomicReference<String> messageReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void resetBuffer() {
				}

				@Override
				public void sendError(int status, String message)
					throws IOException {

					statusCode.set(status);
					messageReference.set(message);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Set both status and message

		metaInfoCacheServletResponse.sendError(400, "Bad Page");

		assertEquals(400, metaInfoCacheServletResponse.getStatus());
		assertEquals(400, statusCode.get());
		assertEquals("Bad Page", messageReference.get());
		assertTrue(metaInfoCacheServletResponse.isCommitted());

		statusCode.set(0);
		messageReference.set(null);

		// Set status

		metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		metaInfoCacheServletResponse.sendError(404);

		assertEquals(404, metaInfoCacheServletResponse.getStatus());
		assertEquals(404, statusCode.get());
		assertNull(messageReference.get());
		assertTrue(metaInfoCacheServletResponse.isCommitted());

		statusCode.set(0);
		messageReference.set(null);

		// Set after commit

		metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.sendError(500, "After commit");
			fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testSendRedirect() throws IOException {
		final AtomicReference<String> locationReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void resetBuffer() {
				}

				@Override
				public void sendRedirect(String location) throws IOException {
					locationReference.set(location);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal send

		metaInfoCacheServletResponse.sendRedirect("testURL");

		assertEquals("testURL", locationReference.get());

		// Send after commit

		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.sendRedirect("testURL");

			fail();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testSetContentLength() {
		final AtomicLong contentLength = new AtomicLong();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setContentLength(int i) {
					contentLength.set(i);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal set

		metaInfoCacheServletResponse.setContentLength(1024);

		assertEquals(1024, contentLength.get());

		contentLength.set(0);

		// set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setContentLength(2048);

		assertEquals(0, contentLength.get());
	}

	public void testSetDateHeader() {
		final List<ObjectValuePair<String, Long>> dateList =
			new ArrayList<ObjectValuePair<String, Long>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {
				@Override
				public void setDateHeader(String name, long value) {
					dateList.add(
						new ObjectValuePair<String, Long>(name, value));
				}
			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// First set

		metaInfoCacheServletResponse.setDateHeader("date1", 1);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date1"));

		Set<Header> dateHeaders1 = headers.get("date1");

		assertEquals(1, dateHeaders1.size());
		assertTrue(dateHeaders1.contains(new Header(1L)));
		assertEquals(1, dateList.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 1L), dateList.get(0));

		// Second set

		metaInfoCacheServletResponse.setDateHeader("date1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date1"));

		dateHeaders1 = headers.get("date1");

		assertEquals(1, dateHeaders1.size());
		assertTrue(dateHeaders1.contains(new Header(2L)));
		assertEquals(2, dateList.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 2L), dateList.get(1));

		// Third set

		metaInfoCacheServletResponse.setDateHeader("date2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date2"));

		Set<Header> dateHeaders2 = headers.get("date2");

		assertEquals(1, dateHeaders2.size());
		assertTrue(dateHeaders2.contains(new Header(1L)));
		assertEquals(3, dateList.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date2", 1L), dateList.get(2));
	}

	public void testSetGetStatus() {
		final AtomicInteger statusCode = new AtomicInteger();
		final AtomicReference<String> messageReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setStatus(int status, String message) {
					statusCode.set(status);
					messageReference.set(message);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Set both status and message

		metaInfoCacheServletResponse.setStatus(400, "Bad Page");

		assertEquals(400, metaInfoCacheServletResponse.getStatus());
		assertEquals(400, statusCode.get());
		assertEquals("Bad Page", messageReference.get());

		statusCode.set(0);
		messageReference.set(null);

		// Set status

		metaInfoCacheServletResponse.setStatus(404);

		assertEquals(404, metaInfoCacheServletResponse.getStatus());
		assertEquals(404, statusCode.get());
		assertNull(messageReference.get());

		statusCode.set(0);
		messageReference.set(null);

		// Set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setStatus(500, "After commit");

		assertEquals(404, metaInfoCacheServletResponse.getStatus());
		assertEquals(0, statusCode.get());
		assertNull(messageReference.get());
	}

	public void testSetHeader() {
		final List<ObjectValuePair<String, String>> headerList =
			new ArrayList<ObjectValuePair<String, String>>();

		final AtomicReference<String> contentTypeReference =
			new AtomicReference<String>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void setHeader(String name, String value) {
					headerList.add(
						new ObjectValuePair<String, String>(name, value));
				}

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setContentType(String contentType) {
					contentTypeReference.set(contentType);
				}
			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// Special set for ContentType

		metaInfoCacheServletResponse.setHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.TEXT_HTML);

		assertEquals(0, headers.size());
		assertEquals(
			ContentTypes.TEXT_HTML,
			metaInfoCacheServletResponse.getContentType());
		assertEquals(ContentTypes.TEXT_HTML, contentTypeReference.get());

		// First set

		metaInfoCacheServletResponse.setHeader("name1", "value1");

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		Set<Header> headers1 = headers.get("name1");

		assertEquals(1, headers1.size());
		assertTrue(headers1.contains(new Header("value1")));
		assertEquals(1, headerList.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value1"),
			headerList.get(0));

		// Second add

		metaInfoCacheServletResponse.setHeader("name1", "value2");

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		headers1 = headers.get("name1");

		assertEquals(1, headers1.size());
		assertTrue(headers1.contains(new Header("value2")));
		assertEquals(2, headerList.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value2"),
			headerList.get(1));

		// Third add

		metaInfoCacheServletResponse.setHeader("name2", "value1");

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> headers2 = headers.get("name2");

		assertEquals(1, headers2.size());
		assertTrue(headers2.contains(new Header("value1")));
		assertEquals(3, headerList.size());
		assertEquals(
			new ObjectValuePair<String, String>("name2", "value1"),
			headerList.get(2));
	}

	public void testSetIntHeader() {
		final List<ObjectValuePair<String, Integer>> intList =
			new ArrayList<ObjectValuePair<String, Integer>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {
				@Override
				public void setIntHeader(String name, int value) {
					intList.add(
						new ObjectValuePair<String, Integer>(name, value));
				}
			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// First set

		metaInfoCacheServletResponse.setIntHeader("name1", 1);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		Set<Header> intHeaders1 = headers.get("name1");

		assertEquals(1, intHeaders1.size());
		assertTrue(intHeaders1.contains(new Header(1)));
		assertEquals(1, intList.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 1), intList.get(0));

		// Second set

		metaInfoCacheServletResponse.setIntHeader("name1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		intHeaders1 = headers.get("name1");

		assertEquals(1, intHeaders1.size());
		assertTrue(intHeaders1.contains(new Header(2)));
		assertEquals(2, intList.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 2), intList.get(1));

		// Third set

		metaInfoCacheServletResponse.setIntHeader("name2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> intHeaders2 = headers.get("name2");

		assertEquals(1, intHeaders2.size());
		assertTrue(intHeaders2.contains(new Header(1)));
		assertEquals(3, intList.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name2", 1), intList.get(2));
	}

	public void testToString() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		String toString = metaInfoCacheServletResponse.toString();
		assertEquals(
			"{status=200, headers={}, location=null, error=false, " +
			"errorMessage=null, charsetName=null, contentType=null, " +
			"locale=null, bufferSize=0, contentLength=-1, " +
			"committed=false||false}", toString);
	}

}