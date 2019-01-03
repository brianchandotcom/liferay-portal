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

package com.liferay.portal.apio.internal.architect.provider;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.PrintWriter;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;

/**
 * Lets resources provide {@code ThemeDisplay} as a parameter to route builder
 * methods.
 *
 * @author Eduardo Pérez
 */
@Component(immediate = true, service = Provider.class)
public class ThemeDisplayProvider implements Provider<ThemeDisplay> {

	@Override
	public ThemeDisplay createContext(HttpServletRequest httpServletRequest) {
		try {
			HttpServletResponse response = new EmptyHttpServletResponse();

			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				response);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			themeDisplay.setLocale(httpServletRequest.getLocale());

			return themeDisplay;
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static class EmptyHttpServletResponse
		implements HttpServletResponse {

		@Override
		public void addCookie(Cookie cookie) {
		}

		@Override
		public void addDateHeader(String name, long date) {
		}

		@Override
		public void addHeader(String name, String value) {
		}

		@Override
		public void addIntHeader(String name, int value) {
		}

		@Override
		public boolean containsHeader(String name) {
			return false;
		}

		@Override
		public String encodeRedirectUrl(String url) {
			return null;
		}

		@Override
		public String encodeRedirectURL(String url) {
			return null;
		}

		@Override
		public String encodeUrl(String url) {
			return null;
		}

		@Override
		public String encodeURL(String url) {
			return null;
		}

		@Override
		public void flushBuffer() {
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
		public String getHeader(String name) {
			return null;
		}

		@Override
		public Collection<String> getHeaderNames() {
			return null;
		}

		@Override
		public Collection<String> getHeaders(String name) {
			return null;
		}

		@Override
		public Locale getLocale() {
			return null;
		}

		@Override
		public ServletOutputStream getOutputStream() {
			return null;
		}

		@Override
		public int getStatus() {
			return 0;
		}

		@Override
		public PrintWriter getWriter() {
			return null;
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
		public void sendError(int status) {
		}

		@Override
		public void sendError(int status, String message) {
		}

		@Override
		public void sendRedirect(String location) {
		}

		@Override
		public void setBufferSize(int size) {
		}

		@Override
		public void setCharacterEncoding(String charset) {
		}

		@Override
		public void setContentLength(int len) {
		}

		@Override
		public void setContentType(String contentLength) {
		}

		@Override
		public void setDateHeader(String name, long date) {
		}

		@Override
		public void setHeader(String name, String value) {
		}

		@Override
		public void setIntHeader(String name, int value) {
		}

		@Override
		public void setLocale(Locale locale) {
		}

		@Override
		public void setStatus(int status) {
		}

		@Override
		public void setStatus(int status, String message) {
		}

	}

}