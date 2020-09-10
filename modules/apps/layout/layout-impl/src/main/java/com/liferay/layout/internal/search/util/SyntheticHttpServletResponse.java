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

package com.liferay.layout.internal.search.util;

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public class SyntheticHttpServletResponse implements HttpServletResponse {

	@Override
	public void addCookie(Cookie cookie) {
	}

	@Override
	public void addDateHeader(String s, long l) {
	}

	@Override
	public void addHeader(String s, String s1) {
	}

	@Override
	public void addIntHeader(String s, int i) {
	}

	@Override
	public boolean containsHeader(String s) {
		return false;
	}

	@Override
	public String encodeRedirectUrl(String s) {
		return null;
	}

	@Override
	public String encodeRedirectURL(String s) {
		return null;
	}

	@Override
	public String encodeUrl(String s) {
		return null;
	}

	@Override
	public String encodeURL(String s) {
		return null;
	}

	@Override
	public void flushBuffer() throws IOException {
	}

	@Override
	public int getBufferSize() {
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public String getHeader(String s) {
		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		return null;
	}

	@Override
	public Collection<String> getHeaders(String s) {
		return null;
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return _unsyncPrintWriter;
	}

	@Override
	public boolean isCommitted() {
		return false;
	}

	@Override
	public void reset() {
	}

	@Override
	public void resetBuffer() {
	}

	@Override
	public void sendError(int i) throws IOException {
	}

	@Override
	public void sendError(int i, String s) throws IOException {
	}

	@Override
	public void sendRedirect(String s) throws IOException {
	}

	@Override
	public void setBufferSize(int i) {
	}

	@Override
	public void setCharacterEncoding(String s) {
	}

	@Override
	public void setContentLength(int i) {
	}

	@Override
	public void setContentLengthLong(long l) {
	}

	@Override
	public void setContentType(String s) {
	}

	@Override
	public void setDateHeader(String s, long l) {
	}

	@Override
	public void setHeader(String s, String s1) {
	}

	@Override
	public void setIntHeader(String s, int i) {
	}

	@Override
	public void setLocale(Locale locale) {
	}

	@Override
	public void setStatus(int i) {
	}

	@Override
	public void setStatus(int i, String s) {
	}

	private final UnsyncPrintWriter _unsyncPrintWriter = new UnsyncPrintWriter(
		new UnsyncStringWriter());

}