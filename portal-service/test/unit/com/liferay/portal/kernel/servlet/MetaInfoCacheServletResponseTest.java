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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

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
		final List<Cookie> cookies = new ArrayList<Cookie>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addCookie(Cookie cookie) {
					cookies.add(cookie);
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
		assertNull(
			metaInfoCacheServletResponse.getHeader(HttpHeaders.SET_COOKIE));

		Collection<String> setCookieHeaders =
			metaInfoCacheServletResponse.getHeaders(HttpHeaders.SET_COOKIE);

		assertEquals(0, setCookieHeaders.size());

		// First add

		Cookie cookie1 = new Cookie("testCookieName1", "testCookieValue1");

		metaInfoCacheServletResponse.addCookie(cookie1);

		assertEquals(1, headers.size());
		assertTrue(
			metaInfoCacheServletResponse.containsHeader(
				HttpHeaders.SET_COOKIE));

		Header cookieHeader1 = new Header(cookie1);

		Set<Header> cookieHeaders = headers.get(HttpHeaders.SET_COOKIE);

		assertEquals(1, cookieHeaders.size());
		assertTrue(cookieHeaders.contains(cookieHeader1));
		assertEquals(1, cookies.size());
		assertEquals(cookie1, cookies.get(0));

		setCookieHeaders = metaInfoCacheServletResponse.getHeaders(
			HttpHeaders.SET_COOKIE);

		assertEquals(1, setCookieHeaders.size());
		assertTrue(setCookieHeaders.contains(cookieHeader1.toString()));
		assertEquals(
			cookieHeader1.toString(),
			metaInfoCacheServletResponse.getHeader(HttpHeaders.SET_COOKIE));
		assertTrue(headerNames.contains(HttpHeaders.SET_COOKIE));

		// Second add

		Cookie cookie2 = new Cookie("testCookieName2", "testCookieValue2");

		metaInfoCacheServletResponse.addCookie(cookie2);

		assertEquals(1, headers.size());
		assertTrue(
			metaInfoCacheServletResponse.containsHeader(
				HttpHeaders.SET_COOKIE));

		Header cookieHeader2 = new Header(cookie2);

		cookieHeaders = headers.get(HttpHeaders.SET_COOKIE);

		assertEquals(2, cookieHeaders.size());
		assertTrue(cookieHeaders.contains(cookieHeader2));
		assertEquals(2, cookies.size());
		assertEquals(cookie2, cookies.get(1));

		setCookieHeaders = metaInfoCacheServletResponse.getHeaders(
			HttpHeaders.SET_COOKIE);

		assertEquals(2, setCookieHeaders.size());
		assertTrue(setCookieHeaders.contains(cookieHeader2.toString()));
	}

	public void testAddDateHeader() {
		final List<ObjectValuePair<String, Long>> objectValuePairs =
			new ArrayList<ObjectValuePair<String, Long>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addDateHeader(String name, long value) {
					objectValuePairs.add(
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
		assertEquals(1, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 1L),
			objectValuePairs.get(0));

		// Second add

		metaInfoCacheServletResponse.addDateHeader("date1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date1"));

		dateHeaders1 = headers.get("date1");

		assertEquals(2, dateHeaders1.size());
		assertTrue(dateHeaders1.contains(new Header(2L)));
		assertEquals(2, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 2L),
			objectValuePairs.get(1));

		// Third add

		metaInfoCacheServletResponse.addDateHeader("date2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date2"));

		Set<Header> dateHeaders2 = headers.get("date2");

		assertEquals(1, dateHeaders2.size());
		assertTrue(dateHeaders2.contains(new Header(1L)));
		assertEquals(3, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date2", 1L),
			objectValuePairs.get(2));
	}

	public void testAddHeader() {
		final AtomicReference<String> contentTypeReference =
			new AtomicReference<String>();
		final List<ObjectValuePair<String, String>> objectValuePairs =
			new ArrayList<ObjectValuePair<String, String>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addHeader(String name, String value) {
					objectValuePairs.add(
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

		// Add content type

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
		assertEquals(1, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value1"),
			objectValuePairs.get(0));

		// Second add

		metaInfoCacheServletResponse.addHeader("name1", "value2");

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		headers1 = headers.get("name1");

		assertEquals(2, headers1.size());
		assertTrue(headers1.contains(new Header("value2")));
		assertEquals(2, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value2"),
			objectValuePairs.get(1));

		// Third add

		metaInfoCacheServletResponse.addHeader("name2", "value1");

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> headers2 = headers.get("name2");

		assertEquals(1, headers2.size());
		assertTrue(headers2.contains(new Header("value1")));
		assertEquals(3, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, String>("name2", "value1"),
			objectValuePairs.get(2));
	}

	public void testAddIntHeader() {
		final List<ObjectValuePair<String, Integer>> objectValuePairs =
			new ArrayList<ObjectValuePair<String, Integer>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void addIntHeader(String name, int value) {
					objectValuePairs.add(
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
		assertEquals(1, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 1),
			objectValuePairs.get(0));

		// Second add

		metaInfoCacheServletResponse.addIntHeader("name1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		intHeaders1 = headers.get("name1");

		assertEquals(2, intHeaders1.size());
		assertTrue(intHeaders1.contains(new Header(2)));
		assertEquals(2, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 2),
			objectValuePairs.get(1));

		// Third add

		metaInfoCacheServletResponse.addIntHeader("name2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> intHeaders2 = headers.get("name2");

		assertEquals(1, intHeaders2.size());
		assertTrue(intHeaders2.contains(new Header(1)));
		assertEquals(3, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name2", 1),
			objectValuePairs.get(2));
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
		final AtomicInteger statusReference = new AtomicInteger();

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
				public void sendError(int status, String errorMessage) {
					statusReference.set(status);
					messageReference.set(errorMessage);
				}

				@Override
				public void sendRedirect(String location) {
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
					statusReference.set(status);
					messageReference.set(message);
				}

			};

		// Clean

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		metaInfoCacheServletResponse.finishResponse();

		// Transfer headers

		MetaInfoCacheServletResponse innerMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);
		MetaInfoCacheServletResponse outerMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(innerMetaInfoCacheServletResponse);

		innerMetaInfoCacheServletResponse.addHeader("name1", "value3");
		innerMetaInfoCacheServletResponse.addHeader("name2", "value3");

		outerMetaInfoCacheServletResponse.addHeader("name1", "value1");
		outerMetaInfoCacheServletResponse.addHeader("name1", "value2");
		outerMetaInfoCacheServletResponse.addHeader("name2", "value1");

		outerMetaInfoCacheServletResponse.finishResponse();

		Map<String, Set<Header>> headers =
			innerMetaInfoCacheServletResponse.getHeaders();

		assertEquals(2, headers.size());

		Set<Header> headers1 = headers.get("name1");

		assertEquals(2, headers1.size());
		assertTrue(headers1.contains(new Header("value1")));
		assertTrue(headers1.contains(new Header("value2")));

		Set<Header> headers2 = headers.get("name2");

		assertEquals(1, headers2.size());
		assertTrue(headers2.contains(new Header("value1")));

		// Send redirect

		MetaInfoCacheServletResponse fromMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.sendRedirect("testURL");

		MetaInfoCacheServletResponse toMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.setResponse(
			toMetaInfoCacheServletResponse);

		locationReference.set(null);

		fromMetaInfoCacheServletResponse.finishResponse();

		assertEquals("testURL", locationReference.get());

		// Send error

		fromMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.sendError(400, "Bad Page");

		toMetaInfoCacheServletResponse = new MetaInfoCacheServletResponse(
			stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.setResponse(
			toMetaInfoCacheServletResponse);

		messageReference.set(null);
		statusReference.set(0);

		fromMetaInfoCacheServletResponse.finishResponse();

		assertEquals("Bad Page", messageReference.get());
		assertEquals(400, statusReference.get());

		// Normal

		fromMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.setContentLength(2048);
		fromMetaInfoCacheServletResponse.setContentType(
			ContentTypes.TEXT_HTML_UTF8);
		fromMetaInfoCacheServletResponse.setLocale(Locale.ENGLISH);
		fromMetaInfoCacheServletResponse.setStatus(302, "moved");

		toMetaInfoCacheServletResponse = new MetaInfoCacheServletResponse(
			stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.setResponse(
			toMetaInfoCacheServletResponse);

		contentLengthReference.set(0);
		messageReference.set(null);
		statusReference.set(0);

		fromMetaInfoCacheServletResponse.finishResponse();

		assertEquals(
			StringPool.UTF8,
			toMetaInfoCacheServletResponse.getCharacterEncoding());
		assertEquals(
			ContentTypes.TEXT_HTML,
			toMetaInfoCacheServletResponse.getContentType());
		assertEquals(
			Locale.ENGLISH, toMetaInfoCacheServletResponse.getLocale());
		assertEquals(2048, contentLengthReference.get());
		assertEquals("moved", messageReference.get());
		assertEquals(302, statusReference.get());

		// Finish response after commit

		fromMetaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.sendRedirect("testURL");

		toMetaInfoCacheServletResponse = new MetaInfoCacheServletResponse(
			stubHttpServletResponse);

		fromMetaInfoCacheServletResponse.setResponse(
			toMetaInfoCacheServletResponse);

		locationReference.set(null);

		toMetaInfoCacheServletResponse.flushBuffer();

		fromMetaInfoCacheServletResponse.finishResponse();

		assertNull(locationReference.get());
	}

	public void testFlushBuffer() throws IOException {
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

	@SuppressWarnings("cast")
	public void testGetMetaInfoDataBag() {
		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse();

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		MetaInfoCacheServletResponse.MetaData metaData =
			metaInfoCacheServletResponse.getMetaData();

		assertNotNull(metaData);
		assertTrue(metaData instanceof Serializable);
	}

	public void testGetOutputStream() throws IOException {
		final AtomicBoolean calledGetOutputStreamReference =
			new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public ServletOutputStream getOutputStream() {
					calledGetOutputStreamReference.set(true);

					return null;
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		assertFalse(metaInfoCacheServletResponse.calledGetOutputStream);
		assertFalse(calledGetOutputStreamReference.get());

		metaInfoCacheServletResponse.getOutputStream();

		assertTrue(metaInfoCacheServletResponse.calledGetOutputStream);
		assertTrue(calledGetOutputStreamReference.get());
	}

	public void testGetSetBufferSize() throws IOException {
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
				public PrintWriter getWriter() {
					return null;
				}

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setCharacterEncoding(String characterEncoding) {
					characterEncodingReference.set(characterEncoding);
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

		// Set after get writer

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

	public void testGetSetContentType() throws IOException {
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

	public void testGetSetLocale() throws IOException {
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
				public PrintWriter getWriter() {
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

	public void testIsCommitted() throws IOException {
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

	public void testReset() throws IOException {
		final AtomicBoolean calledResetReference = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void reset() {
					calledResetReference.set(true);
				}

				@Override
				public void resetBuffer() {
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal reset buffer

		metaInfoCacheServletResponse.reset();

		assertTrue(calledResetReference.get());

		// Reset buffer after commit

		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.reset();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testResetBuffer() throws IOException {
		final AtomicBoolean calledResetBufferReference = new AtomicBoolean();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void resetBuffer() {
					calledResetBufferReference.set(true);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal reset buffer

		metaInfoCacheServletResponse.resetBuffer();

		assertTrue(calledResetBufferReference.get());

		// Reset buffer after commit

		metaInfoCacheServletResponse.flushBuffer();

		try {
			metaInfoCacheServletResponse.resetBuffer();
		}
		catch (IllegalStateException ise) {
		}
	}

	public void testSendError() throws IOException {
		final AtomicReference<String> messageReference =
			new AtomicReference<String>();
		final AtomicInteger statusReference = new AtomicInteger();

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
				public void sendError(int status, String message) {
					statusReference.set(status);
					messageReference.set(message);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Set both status and message

		metaInfoCacheServletResponse.sendError(400, "Bad Page");

		assertEquals("Bad Page", messageReference.get());
		assertTrue(metaInfoCacheServletResponse.isCommitted());
		assertEquals(400, metaInfoCacheServletResponse.getStatus());
		assertEquals(400, statusReference.get());

		messageReference.set(null);
		statusReference.set(0);

		// Set status

		metaInfoCacheServletResponse = new MetaInfoCacheServletResponse(
			stubHttpServletResponse);

		metaInfoCacheServletResponse.sendError(404);

		assertNull(messageReference.get());
		assertTrue(metaInfoCacheServletResponse.isCommitted());
		assertEquals(404, metaInfoCacheServletResponse.getStatus());
		assertEquals(404, statusReference.get());

		messageReference.set(null);
		statusReference.set(0);

		// Set after commit

		metaInfoCacheServletResponse = new MetaInfoCacheServletResponse(
			stubHttpServletResponse);

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
				public void sendRedirect(String location) {
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

	public void testSetContentLength() throws IOException {
		final AtomicLong contentLengthReference = new AtomicLong();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setContentLength(int i) {
					contentLengthReference.set(i);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Normal set

		metaInfoCacheServletResponse.setContentLength(1024);

		assertEquals(1024, contentLengthReference.get());

		contentLengthReference.set(0);

		// set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setContentLength(2048);

		assertEquals(0, contentLengthReference.get());
	}

	public void testSetDateHeader() {
		final List<ObjectValuePair<String, Long>> objectValuePairs =
			new ArrayList<ObjectValuePair<String, Long>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void setDateHeader(String name, long value) {
					objectValuePairs.add(
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
		assertEquals(1, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 1L),
			objectValuePairs.get(0));

		// Second set

		metaInfoCacheServletResponse.setDateHeader("date1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date1"));

		dateHeaders1 = headers.get("date1");

		assertEquals(1, dateHeaders1.size());
		assertTrue(dateHeaders1.contains(new Header(2L)));
		assertEquals(2, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date1", 2L),
			objectValuePairs.get(1));

		// Third set

		metaInfoCacheServletResponse.setDateHeader("date2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("date2"));

		Set<Header> dateHeaders2 = headers.get("date2");

		assertEquals(1, dateHeaders2.size());
		assertTrue(dateHeaders2.contains(new Header(1L)));
		assertEquals(3, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Long>("date2", 1L),
			objectValuePairs.get(2));
	}

	public void testSetGetStatus() throws IOException {
		final AtomicReference<String> messageReference =
			new AtomicReference<String>();
		final AtomicInteger statusReference = new AtomicInteger();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setStatus(int status, String message) {
					statusReference.set(status);
					messageReference.set(message);
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		// Set both status and message

		metaInfoCacheServletResponse.setStatus(400, "Bad Page");

		assertEquals("Bad Page", messageReference.get());
		assertEquals(400, metaInfoCacheServletResponse.getStatus());
		assertEquals(400, statusReference.get());

		messageReference.set(null);
		statusReference.set(0);

		// Set status

		metaInfoCacheServletResponse.setStatus(404);

		assertNull(messageReference.get());
		assertEquals(404, metaInfoCacheServletResponse.getStatus());
		assertEquals(404, statusReference.get());

		messageReference.set(null);
		statusReference.set(0);

		// Set after commit

		metaInfoCacheServletResponse.flushBuffer();

		metaInfoCacheServletResponse.setStatus(500, "After commit");

		assertNull(messageReference.get());
		assertEquals(404, metaInfoCacheServletResponse.getStatus());
		assertEquals(0, statusReference.get());
	}

	public void testSetHeader() {
		final AtomicReference<String> contentTypeReference =
			new AtomicReference<String>();
		final List<ObjectValuePair<String, String>> objectValuePairs =
			new ArrayList<ObjectValuePair<String, String>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public boolean isCommitted() {
					return false;
				}

				@Override
				public void setContentType(String contentType) {
					contentTypeReference.set(contentType);
				}

				@Override
				public void setHeader(String name, String value) {
					objectValuePairs.add(
						new ObjectValuePair<String, String>(name, value));
				}

			};

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(stubHttpServletResponse);

		Map<String, Set<Header>> headers =
			metaInfoCacheServletResponse.getHeaders();

		assertEquals(0, headers.size());

		// Set content type

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
		assertEquals(1, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value1"),
			objectValuePairs.get(0));

		// Second add

		metaInfoCacheServletResponse.setHeader("name1", "value2");

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		headers1 = headers.get("name1");

		assertEquals(1, headers1.size());
		assertTrue(headers1.contains(new Header("value2")));
		assertEquals(2, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, String>("name1", "value2"),
			objectValuePairs.get(1));

		// Third add

		metaInfoCacheServletResponse.setHeader("name2", "value1");

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> headers2 = headers.get("name2");

		assertEquals(1, headers2.size());
		assertTrue(headers2.contains(new Header("value1")));
		assertEquals(3, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, String>("name2", "value1"),
			objectValuePairs.get(2));
	}

	public void testSetIntHeader() {
		final List<ObjectValuePair<String, Integer>> objectValuePairs =
			new ArrayList<ObjectValuePair<String, Integer>>();

		StubHttpServletResponse stubHttpServletResponse =
			new StubHttpServletResponse() {

				@Override
				public void setIntHeader(String name, int value) {
					objectValuePairs.add(
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
		assertEquals(1, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 1),
			objectValuePairs.get(0));

		// Second set

		metaInfoCacheServletResponse.setIntHeader("name1", 2);

		assertEquals(1, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name1"));

		intHeaders1 = headers.get("name1");

		assertEquals(1, intHeaders1.size());
		assertTrue(intHeaders1.contains(new Header(2)));
		assertEquals(2, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name1", 2),
			objectValuePairs.get(1));

		// Third set

		metaInfoCacheServletResponse.setIntHeader("name2", 1);

		assertEquals(2, headers.size());
		assertTrue(metaInfoCacheServletResponse.containsHeader("name2"));

		Set<Header> intHeaders2 = headers.get("name2");

		assertEquals(1, intHeaders2.size());
		assertTrue(intHeaders2.contains(new Header(1)));
		assertEquals(3, objectValuePairs.size());
		assertEquals(
			new ObjectValuePair<String, Integer>("name2", 1),
			objectValuePairs.get(2));
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

		StringBundler sb = new StringBundler(4);

		sb.append("{bufferSize=0, charsetName=null, committed=false, ");
		sb.append("contentLength=-1, contentType=null, error=false, ");
		sb.append("errorMessage=null, headers={}, location=null, ");
		sb.append("locale=null, status=200}");

		assertEquals(sb.toString(), metaInfoCacheServletResponse.toString());
	}

}