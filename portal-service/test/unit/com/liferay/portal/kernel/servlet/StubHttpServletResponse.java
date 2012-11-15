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

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class StubHttpServletResponse implements HttpServletResponse {

	public void addCookie(Cookie cookie) {
		throw new UnsupportedOperationException();
	}

	public void addDateHeader(String name, long value) {
		throw new UnsupportedOperationException();
	}

	public void addHeader(String name, String value) {
		throw new UnsupportedOperationException();
	}

	public void addIntHeader(String name, int value) {
		throw new UnsupportedOperationException();
	}

	public boolean containsHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public String encodeRedirectUrl(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeRedirectURL(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeUrl(String string) {
		throw new UnsupportedOperationException();
	}

	public String encodeURL(String string) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void flushBuffer() throws IOException {
		throw new UnsupportedOperationException();
	}

	public int getBufferSize() {
		throw new UnsupportedOperationException();
	}

	public String getCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public ServletOutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public PrintWriter getWriter() throws IOException {
		throw new UnsupportedOperationException();
	}

	public boolean isCommitted() {
		throw new UnsupportedOperationException();
	}

	public void reset() {
		throw new UnsupportedOperationException();
	}

	public void resetBuffer() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void sendError(int status) throws IOException {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void sendError(int status, String errorMessage) throws IOException {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unused")
	public void sendRedirect(String location) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void setBufferSize(int bufferSzie) {
		throw new UnsupportedOperationException();
	}

	public void setCharacterEncoding(String characterEncoding) {
		throw new UnsupportedOperationException();
	}

	public void setContentLength(int contentLength) {
		throw new UnsupportedOperationException();
	}

	public void setContentType(String contentType) {
		throw new UnsupportedOperationException();
	}

	public void setDateHeader(String name, long value) {
		throw new UnsupportedOperationException();
	}

	public void setHeader(String name, String value) {
		throw new UnsupportedOperationException();
	}

	public void setIntHeader(String name, int value) {
		throw new UnsupportedOperationException();
	}

	public void setLocale(Locale locale) {
		throw new UnsupportedOperationException();
	}

	public void setStatus(int status) {
		throw new UnsupportedOperationException();
	}

	public void setStatus(int status, String message) {
		throw new UnsupportedOperationException();
	}

}