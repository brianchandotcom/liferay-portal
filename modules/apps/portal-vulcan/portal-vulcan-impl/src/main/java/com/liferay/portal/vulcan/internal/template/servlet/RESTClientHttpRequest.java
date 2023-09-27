/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;

import java.security.Principal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

/**
 * @author Alejandro Tardín
 */
public class RESTClientHttpRequest implements HttpServletRequest {

	public RESTClientHttpRequest(HttpServletRequest httpServletRequest) {
		_headers = HashMapBuilder.put(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).put(
			"Accept-Language",
			() -> {
				Locale locale = PortalUtil.getLocale(httpServletRequest);

				return locale.toLanguageTag();
			}
		).build();
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public boolean authenticate(HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		return _httpServletRequest.authenticate(httpServletResponse);
	}

	@Override
	public String changeSessionId() {
		return _httpServletRequest.changeSessionId();
	}

	public AsyncContext getAsyncContext() {
		return _httpServletRequest.getAsyncContext();
	}

	@Override
	public Object getAttribute(String name) {
		return _attributes.getOrDefault(
			name, _httpServletRequest.getAttribute(name));
	}

	@Override
	public Enumeration getAttributeNames() {
		return _httpServletRequest.getAttributeNames();
	}

	@Override
	public String getAuthType() {
		return _httpServletRequest.getAuthType();
	}

	@Override
	public String getCharacterEncoding() {
		return _httpServletRequest.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return _httpServletRequest.getContentLength();
	}

	@Override
	public long getContentLengthLong() {
		return _httpServletRequest.getContentLengthLong();
	}

	@Override
	public String getContentType() {
		return _httpServletRequest.getContentType();
	}

	@Override
	public String getContextPath() {
		return _httpServletRequest.getContextPath();
	}

	@Override
	public Cookie[] getCookies() {
		return _httpServletRequest.getCookies();
	}

	@Override
	public long getDateHeader(String name) {
		return _httpServletRequest.getDateHeader(name);
	}

	public DispatcherType getDispatcherType() {
		return _httpServletRequest.getDispatcherType();
	}

	@Override
	public String getHeader(String name) {
		return _headers.get(name);
	}

	@Override
	public Enumeration getHeaderNames() {
		return _httpServletRequest.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		String value = _headers.get(name);

		if (Validator.isNotNull(value)) {
			return Collections.enumeration(Arrays.asList(value));
		}

		return Collections.emptyEnumeration();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return _httpServletRequest.getInputStream();
	}

	@Override
	public int getIntHeader(String name) {
		return _httpServletRequest.getIntHeader(name);
	}

	@Override
	public String getLocalAddr() {
		return _httpServletRequest.getLocalAddr();
	}

	@Override
	public Locale getLocale() {
		return _httpServletRequest.getLocale();
	}

	@Override
	public Enumeration getLocales() {
		return _httpServletRequest.getLocales();
	}

	@Override
	public String getLocalName() {
		return _httpServletRequest.getLocalName();
	}

	@Override
	public int getLocalPort() {
		return _httpServletRequest.getLocalPort();
	}

	@Override
	public String getMethod() {
		return HttpMethods.GET;
	}

	@Override
	public String getParameter(String name) {
		return _httpServletRequest.getParameter(name);
	}

	@Override
	public Map getParameterMap() {
		return _httpServletRequest.getParameterMap();
	}

	@Override
	public Enumeration getParameterNames() {
		return _httpServletRequest.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		return _httpServletRequest.getParameterValues(name);
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		return _httpServletRequest.getPart(name);
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return _httpServletRequest.getParts();
	}

	@Override
	public String getPathInfo() {
		return _httpServletRequest.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return _httpServletRequest.getPathTranslated();
	}

	@Override
	public String getProtocol() {
		return _httpServletRequest.getProtocol();
	}

	@Override
	public String getQueryString() {
		return _httpServletRequest.getQueryString();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return _httpServletRequest.getReader();
	}

	@Override
	public String getRealPath(String path) {
		return _httpServletRequest.getRealPath(path);
	}

	@Override
	public String getRemoteAddr() {
		return _httpServletRequest.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return _httpServletRequest.getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		return _httpServletRequest.getRemotePort();
	}

	@Override
	public String getRemoteUser() {
		return _httpServletRequest.getRemoteUser();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return _httpServletRequest.getRequestDispatcher(path);
	}

	@Override
	public String getRequestedSessionId() {
		return _httpServletRequest.getRequestedSessionId();
	}

	@Override
	public String getRequestURI() {
		return _httpServletRequest.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return _httpServletRequest.getRequestURL();
	}

	@Override
	public String getScheme() {
		return _httpServletRequest.getScheme();
	}

	@Override
	public String getServerName() {
		return _httpServletRequest.getServerName();
	}

	@Override
	public int getServerPort() {
		return _httpServletRequest.getServerPort();
	}

	@Override
	public ServletContext getServletContext() {
		return _httpServletRequest.getServletContext();
	}

	@Override
	public String getServletPath() {
		return _httpServletRequest.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return _httpServletRequest.getSession();
	}

	@Override
	public HttpSession getSession(boolean create) {
		return _httpServletRequest.getSession(create);
	}

	@Override
	public Principal getUserPrincipal() {
		return _httpServletRequest.getUserPrincipal();
	}

	@Override
	public boolean isAsyncStarted() {
		return _httpServletRequest.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		return _httpServletRequest.isAsyncSupported();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return _httpServletRequest.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return _httpServletRequest.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return _httpServletRequest.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return _httpServletRequest.isRequestedSessionIdValid();
	}

	@Override
	public boolean isSecure() {
		return _httpServletRequest.isSecure();
	}

	@Override
	public boolean isUserInRole(String role) {
		return _httpServletRequest.isUserInRole(role);
	}

	@Override
	public void login(String userName, String password)
		throws ServletException {

		_httpServletRequest.login(userName, password);
	}

	@Override
	public void logout() throws ServletException {
		_httpServletRequest.logout();
	}

	@Override
	public void removeAttribute(String name) {
		_attributes.remove(name);
	}

	@Override
	public void setAttribute(String name, Object object) {
		_attributes.put(name, object);
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) {
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return _httpServletRequest.startAsync();
	}

	@Override
	public AsyncContext startAsync(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IllegalStateException {

		return _httpServletRequest.startAsync(servletRequest, servletResponse);
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
		throws IOException, ServletException {

		return _httpServletRequest.upgrade(handlerClass);
	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private final Map<String, String> _headers;
	private final HttpServletRequest _httpServletRequest;

}