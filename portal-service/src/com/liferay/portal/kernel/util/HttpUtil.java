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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalSocketPermission;

import java.io.IOException;

import java.net.URL;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class HttpUtil {

	public static String addParameter(String url, String name, boolean value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, double value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, int value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, long value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, short value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String addParameter(String url, String name, String value) {
		return getHttp().addParameter(url, name, value);
	}

	public static String decodePath(String path) {
		return getHttp().decodePath(path);
	}

	public static String decodeURL(String url) {
		return getHttp().decodeURL(url);
	}

	public static String decodeURL(String url, boolean unescapeSpaces) {
		return getHttp().decodeURL(url, unescapeSpaces);
	}

	public static String encodeParameters(String url) {
		return getHttp().encodeParameters(url);
	}

	public static String encodePath(String path) {
		return getHttp().encodePath(path);
	}

	public static String encodeURL(String url) {
		return getHttp().encodeURL(url);
	}

	public static String encodeURL(String url, boolean escapeSpaces) {
		return getHttp().encodeURL(url, escapeSpaces);
	}

	public static String fixPath(String path) {
		return getHttp().fixPath(path);
	}

	public static String fixPath(
		String path, boolean leading, boolean trailing) {

		return getHttp().fixPath(path, leading, trailing);
	}

	public static String getCompleteURL(HttpServletRequest request) {
		return getHttp().getCompleteURL(request);
	}

	public static Cookie[] getCookies() {
		return getHttp().getCookies();
	}

	public static String getDomain(String url) {
		return getHttp().getDomain(url);
	}

	public static Http getHttp() {
		PortalRuntimePermission.checkGetBeanProperty(HttpUtil.class);

		return _http;
	}

	public static String getIpAddress(String url) {
		return getHttp().getIpAddress(url);
	}

	public static String getParameter(String url, String name) {
		return getHttp().getParameter(url, name);
	}

	public static String getParameter(
		String url, String name, boolean escaped) {

		return getHttp().getParameter(url, name, escaped);
	}

	public static Map<String, String[]> getParameterMap(String queryString) {
		return getHttp().getParameterMap(queryString);
	}

	public static String getPath(String url) {
		return getHttp().getPath(url);
	}

	public static String getProtocol(ActionRequest actionRequest) {
		return getHttp().getProtocol(actionRequest);
	}

	public static String getProtocol(boolean secure) {
		return getHttp().getProtocol(secure);
	}

	public static String getProtocol(HttpServletRequest request) {
		return getHttp().getProtocol(request);
	}

	public static String getProtocol(RenderRequest renderRequest) {
		return getHttp().getProtocol(renderRequest);
	}

	public static String getProtocol(String url) {
		return getHttp().getProtocol(url);
	}

	public static String getQueryString(String url) {
		return getHttp().getQueryString(url);
	}

	public static String getRequestURL(HttpServletRequest request) {
		return getHttp().getRequestURL(request);
	}

	public static boolean hasDomain(String url) {
		return getHttp().hasDomain(url);
	}

	public static boolean hasProtocol(String url) {
		return getHttp().hasProtocol(url);
	}

	public static boolean hasProxyConfig() {
		return getHttp().hasProxyConfig();
	}

	public static boolean isNonProxyHost(String host) {
		return getHttp().isNonProxyHost(host);
	}

	public static boolean isProxyHost(String host) {
		return getHttp().isProxyHost(host);
	}

	public static boolean isSecure(String url) {
		return getHttp().isSecure(url);
	}

	public static Map<String, String[]> parameterMapFromString(
		String queryString) {

		return getHttp().parameterMapFromString(queryString);
	}

	public static String parameterMapToString(
		Map<String, String[]> parameterMap) {

		return getHttp().parameterMapToString(parameterMap);
	}

	public static String parameterMapToString(
		Map<String, String[]> parameterMap, boolean addQuestion) {

		return getHttp().parameterMapToString(parameterMap, addQuestion);
	}

	public static String protocolize(String url, ActionRequest actionRequest) {
		return getHttp().protocolize(url, actionRequest);
	}

	public static String protocolize(String url, boolean secure) {
		return getHttp().protocolize(url, secure);
	}

	public static String protocolize(String url, HttpServletRequest request) {
		return getHttp().protocolize(url, request);
	}

	public static String protocolize(String url, int port, boolean secure) {
		return getHttp().protocolize(url, port, secure);
	}

	public static String protocolize(String url, RenderRequest renderRequest) {
		return getHttp().protocolize(url, renderRequest);
	}

	public static String removeDomain(String url) {
		return getHttp().removeDomain(url);
	}

	public static String removeParameter(String url, String name) {
		return getHttp().removeParameter(url, name);
	}

	public static String removePathParamters(String uri) {
		return getHttp().removePathParameters(uri);
	}

	public static String removeProtocol(String url) {
		return getHttp().removeProtocol(url);
	}

	public static String sanitizeHeader(String header) {
		return getHttp().sanitizeHeader(header);
	}

	public static String setParameter(String url, String name, boolean value) {
		return getHttp().setParameter(url, name, value);
	}

	public static String setParameter(String url, String name, double value) {
		return getHttp().setParameter(url, name, value);
	}

	public static String setParameter(String url, String name, int value) {
		return getHttp().setParameter(url, name, value);
	}

	public static String setParameter(String url, String name, long value) {
		return getHttp().setParameter(url, name, value);
	}

	public static String setParameter(String url, String name, short value) {
		return getHttp().setParameter(url, name, value);
	}

	public static String setParameter(String url, String name, String value) {
		return getHttp().setParameter(url, name, value);
	}

	public static byte[] URLtoByteArray(Http.Options options)
		throws IOException {

		PortalSocketPermission.checkConnect(options);

		return getHttp().URLtoByteArray(options);
	}

	public static byte[] URLtoByteArray(String location) throws IOException {
		PortalSocketPermission.checkConnect(location);

		return getHttp().URLtoByteArray(location);
	}

	public static byte[] URLtoByteArray(String location, boolean post)
		throws IOException {

		PortalSocketPermission.checkConnect(location);

		return getHttp().URLtoByteArray(location, post);
	}

	public static String URLtoString(Http.Options options) throws IOException {
		PortalSocketPermission.checkConnect(options);

		return getHttp().URLtoString(options);
	}

	public static String URLtoString(String location) throws IOException {
		PortalSocketPermission.checkConnect(location);

		return getHttp().URLtoString(location);
	}

	public static String URLtoString(String location, boolean post)
		throws IOException {

		PortalSocketPermission.checkConnect(location);

		return getHttp().URLtoString(location, post);
	}

	/**
	 * This method only uses the default Commons HttpClient implementation when
	 * the URL object represents a HTTP resource. The URL object could also
	 * represent a file or some JNDI resource. In that case, the default Java
	 * implementation is used.
	 *
	 * @param  url the URL
	 * @return A string representation of the resource referenced by the URL
	 *         object
	 * @throws IOException if an IO Exception occurred
	 */
	public static String URLtoString(URL url) throws IOException {
		PortalSocketPermission.checkConnect(url);

		return getHttp().URLtoString(url);
	}

	public void setHttp(Http http) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_http = http;
	}

	private static Http _http;

}