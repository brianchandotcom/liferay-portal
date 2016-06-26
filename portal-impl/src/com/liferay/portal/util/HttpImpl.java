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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncFilterInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.memory.FinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.lang.ref.Reference;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.SocketFactory;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HostParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 * @author Shuyang Zhou
 */
@DoPrivileged
public class HttpImpl implements Http {

	public HttpImpl() {

		// Override the default protocol socket factory because it uses
		// reflection for JDK 1.4 compatibility, which we do not need. It also
		// attemps to create a new socket in a different thread so that we
		// cannot track which class loader initiated the call.

		Protocol protocol = new Protocol(
			"http", new FastProtocolSocketFactory(), 80);

		Protocol.registerProtocol("http", protocol);

		// Mimic behavior found in
		// http://java.sun.com/j2se/1.5.0/docs/guide/net/properties.html

		if (Validator.isNotNull(_NON_PROXY_HOSTS)) {
			String nonProxyHostsRegEx = _NON_PROXY_HOSTS;

			nonProxyHostsRegEx = nonProxyHostsRegEx.replaceAll("\\.", "\\\\.");
			nonProxyHostsRegEx = nonProxyHostsRegEx.replaceAll("\\*", ".*?");
			nonProxyHostsRegEx = nonProxyHostsRegEx.replaceAll("\\|", ")|(");

			nonProxyHostsRegEx = "(" + nonProxyHostsRegEx + ")";

			_nonProxyHostsPattern = Pattern.compile(nonProxyHostsRegEx);
		}
		else {
			_nonProxyHostsPattern = null;
		}

		MultiThreadedHttpConnectionManager httpConnectionManager =
			new MultiThreadedHttpConnectionManager();

		HttpConnectionManagerParams httpConnectionManagerParams =
			httpConnectionManager.getParams();

		httpConnectionManagerParams.setConnectionTimeout(_TIMEOUT);
		httpConnectionManagerParams.setDefaultMaxConnectionsPerHost(
			Integer.valueOf(_MAX_CONNECTIONS_PER_HOST));
		httpConnectionManagerParams.setMaxTotalConnections(
			Integer.valueOf(_MAX_TOTAL_CONNECTIONS));
		httpConnectionManagerParams.setSoTimeout(_TIMEOUT);

		_httpClient.setHttpConnectionManager(httpConnectionManager);
		_proxyHttpClient.setHttpConnectionManager(httpConnectionManager);

		if (!hasProxyConfig() || Validator.isNull(_PROXY_USERNAME)) {
			_proxyCredentials = null;

			return;
		}

		List<String> authPrefs = new ArrayList<>();

		if (_PROXY_AUTH_TYPE.equals("username-password")) {
			_proxyCredentials = new UsernamePasswordCredentials(
				_PROXY_USERNAME, _PROXY_PASSWORD);

			authPrefs.add(AuthPolicy.BASIC);
			authPrefs.add(AuthPolicy.DIGEST);
			authPrefs.add(AuthPolicy.NTLM);
		}
		else if (_PROXY_AUTH_TYPE.equals("ntlm")) {
			_proxyCredentials = new NTCredentials(
				_PROXY_USERNAME, _PROXY_PASSWORD, _PROXY_NTLM_HOST,
				_PROXY_NTLM_DOMAIN);

			authPrefs.add(AuthPolicy.NTLM);
			authPrefs.add(AuthPolicy.BASIC);
			authPrefs.add(AuthPolicy.DIGEST);
		}
		else {
			_proxyCredentials = null;
		}

		HttpClientParams httpClientParams = _proxyHttpClient.getParams();

		httpClientParams.setParameter(
			AuthPolicy.AUTH_SCHEME_PRIORITY, authPrefs);
	}

	@Override
	public String addParameter(String url, String name, boolean value) {
		return addParameter(url, name, String.valueOf(value));
	}

	@Override
	public String addParameter(String url, String name, double value) {
		return addParameter(url, name, String.valueOf(value));
	}

	@Override
	public String addParameter(String url, String name, int value) {
		return addParameter(url, name, String.valueOf(value));
	}

	@Override
	public String addParameter(String url, String name, long value) {
		return addParameter(url, name, String.valueOf(value));
	}

	@Override
	public String addParameter(String url, String name, short value) {
		return addParameter(url, name, String.valueOf(value));
	}

	@Override
	public String addParameter(String url, String name, String value) {
		if (url == null) {
			return null;
		}

		String[] urlArray = PortalUtil.stripURLAnchor(url, StringPool.POUND);

		url = urlArray[0];

		String anchor = urlArray[1];

		StringBundler sb = new StringBundler(6);

		sb.append(url);

		if (url.indexOf(CharPool.QUESTION) == -1) {
			sb.append(StringPool.QUESTION);
		}
		else if (!url.endsWith(StringPool.QUESTION) &&
				 !url.endsWith(StringPool.AMPERSAND)) {

			sb.append(StringPool.AMPERSAND);
		}

		sb.append(name);
		sb.append(StringPool.EQUAL);
		sb.append(encodeURL(value));
		sb.append(anchor);

		String result = sb.toString();

		if (result.length() > URL_MAXIMUM_LENGTH) {
			result = shortenURL(result, 2);
		}

		return result;
	}

	@Override
	public String decodePath(String path) {
		if (Validator.isNull(path)) {
			return path;
		}

		path = StringUtil.replace(path, CharPool.SLASH, _TEMP_SLASH);
		path = decodeURL(path, true);
		path = StringUtil.replace(path, _TEMP_SLASH, StringPool.SLASH);

		return path;
	}

	@Override
	public String decodeURL(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		try {
			return URLCodec.decodeURL(url, StringPool.UTF8);
		}
		catch (IllegalArgumentException iae) {
			_log.error(iae.getMessage());
		}

		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #decodeURL(String)}
	 */
	@Deprecated
	@Override
	public String decodeURL(String url, boolean unescapeSpaces) {
		return decodeURL(url);
	}

	public void destroy() {
		MultiThreadedHttpConnectionManager.shutdownAll();
	}

	@Override
	public String encodeParameters(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		String queryString = getQueryString(url);

		if (Validator.isNull(queryString)) {
			return url;
		}

		String encodedQueryString = parameterMapToString(
			parameterMapFromString(queryString), false);

		return StringUtil.replace(url, queryString, encodedQueryString);
	}

	@Override
	public String encodePath(String path) {
		if (Validator.isNull(path)) {
			return path;
		}

		path = StringUtil.replace(path, CharPool.SLASH, _TEMP_SLASH);
		path = encodeURL(path, true);
		path = StringUtil.replace(path, _TEMP_SLASH, StringPool.SLASH);

		return path;
	}

	@Override
	public String encodeURL(String url) {
		return encodeURL(url, false);
	}

	@Override
	public String encodeURL(String url, boolean escapeSpaces) {
		if (Validator.isNull(url)) {
			return url;
		}

		return URLCodec.encodeURL(url, StringPool.UTF8, escapeSpaces);
	}

	@Override
	public String fixPath(String path) {
		return fixPath(path, true, true);
	}

	@Override
	public String fixPath(String path, boolean leading, boolean trailing) {
		if (path == null) {
			return StringPool.BLANK;
		}

		int leadingSlashCount = 0;
		int trailingSlashCount = 0;

		if (leading) {
			for (int i = 0; i < path.length(); i++) {
				if (path.charAt(i) == CharPool.SLASH) {
					leadingSlashCount++;
				}
				else {
					break;
				}
			}
		}

		if (trailing) {
			for (int i = path.length() - 1; i >= 0; i--) {
				if (path.charAt(i) == CharPool.SLASH) {
					trailingSlashCount++;
				}
				else {
					break;
				}
			}
		}

		int slashCount = leadingSlashCount + trailingSlashCount;

		if (slashCount > path.length()) {
			return StringPool.BLANK;
		}

		if (slashCount > 0) {
			path = path.substring(
				leadingSlashCount, path.length() - trailingSlashCount);
		}

		return path;
	}

	public HttpClient getClient(HostConfiguration hostConfiguration) {
		if (isProxyHost(hostConfiguration.getHost())) {
			return _proxyHttpClient;
		}

		return _httpClient;
	}

	@Override
	public String getCompleteURL(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();

		if (sb == null) {
			sb = new StringBuffer();
		}

		if (request.getQueryString() != null) {
			sb.append(StringPool.QUESTION);
			sb.append(request.getQueryString());
		}

		String proxyPath = PortalUtil.getPathProxy();

		if (Validator.isNotNull(proxyPath)) {
			int x =
				sb.indexOf(Http.PROTOCOL_DELIMITER) +
					Http.PROTOCOL_DELIMITER.length();
			int y = sb.indexOf(StringPool.SLASH, x);

			sb.insert(y, proxyPath);
		}

		String completeURL = sb.toString();

		if (request.isRequestedSessionIdFromURL()) {
			HttpSession session = request.getSession();

			String sessionId = session.getId();

			completeURL = PortalUtil.getURLWithSessionId(
				completeURL, sessionId);
		}

		if (_log.isWarnEnabled()) {
			if (completeURL.contains("?&")) {
				_log.warn("Invalid url " + completeURL);
			}
		}

		return completeURL;
	}

	@Override
	public Cookie[] getCookies() {
		return _cookies.get();
	}

	@Override
	public String getDomain(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		url = removeProtocol(url);

		int pos = url.indexOf(CharPool.SLASH);

		if (pos != -1) {
			return url.substring(0, pos);
		}

		return url;
	}

	public HostConfiguration getHostConfiguration(String location)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Location is " + location);
		}

		HostConfiguration hostConfiguration = new HostConfiguration();

		hostConfiguration.setHost(new URI(location, false));

		if (isProxyHost(hostConfiguration.getHost())) {
			hostConfiguration.setProxy(_PROXY_HOST, _PROXY_PORT);
		}

		HttpConnectionManager httpConnectionManager =
			_httpClient.getHttpConnectionManager();

		HttpConnectionManagerParams httpConnectionManagerParams =
			httpConnectionManager.getParams();

		int defaultMaxConnectionsPerHost =
			httpConnectionManagerParams.getMaxConnectionsPerHost(
				hostConfiguration);

		int maxConnectionsPerHost = GetterUtil.getInteger(
			PropsUtil.get(
				HttpImpl.class.getName() + ".max.connections.per.host",
				new Filter(hostConfiguration.getHost())));

		if ((maxConnectionsPerHost > 0) &&
			(maxConnectionsPerHost != defaultMaxConnectionsPerHost)) {

			httpConnectionManagerParams.setMaxConnectionsPerHost(
				hostConfiguration, maxConnectionsPerHost);
		}

		int timeout = GetterUtil.getInteger(
			PropsUtil.get(
				HttpImpl.class.getName() + ".timeout",
				new Filter(hostConfiguration.getHost())));

		if (timeout > 0) {
			HostParams hostParams = hostConfiguration.getParams();

			hostParams.setIntParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, timeout);
			hostParams.setIntParameter(
				HttpConnectionParams.SO_TIMEOUT, timeout);
		}

		return hostConfiguration;
	}

	@Override
	public String getIpAddress(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		try {
			URL urlObj = new URL(url);

			InetAddress address = InetAddress.getByName(urlObj.getHost());

			return address.getHostAddress();
		}
		catch (Exception e) {
			return url;
		}
	}

	@Override
	public String getParameter(String url, String name) {
		return getParameter(url, name, true);
	}

	@Override
	public String getParameter(String url, String name, boolean escaped) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return StringPool.BLANK;
		}

		String[] parts = StringUtil.split(url, CharPool.QUESTION);

		if (parts.length == 2) {
			String[] params = null;

			if (escaped) {
				params = StringUtil.split(parts[1], "&amp;");
			}
			else {
				params = StringUtil.split(parts[1], CharPool.AMPERSAND);
			}

			for (String param : params) {
				String[] kvp = StringUtil.split(param, CharPool.EQUAL);

				if ((kvp.length == 2) && kvp[0].equals(name)) {
					return kvp[1];
				}
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public Map<String, String[]> getParameterMap(String queryString) {
		return parameterMapFromString(queryString);
	}

	@Override
	public String getPath(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		if (url.startsWith(Http.HTTP)) {
			int pos = url.indexOf(
				CharPool.SLASH, Http.HTTPS_WITH_SLASH.length());

			url = url.substring(pos);
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		return url.substring(0, pos);
	}

	@Override
	public String getProtocol(ActionRequest actionRequest) {
		return getProtocol(actionRequest.isSecure());
	}

	@Override
	public String getProtocol(boolean secure) {
		if (!secure) {
			return Http.HTTP;
		}

		return Http.HTTPS;
	}

	@Override
	public String getProtocol(HttpServletRequest request) {
		return getProtocol(request.isSecure());
	}

	@Override
	public String getProtocol(RenderRequest renderRequest) {
		return getProtocol(renderRequest.isSecure());
	}

	@Override
	public String getProtocol(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		int pos = url.indexOf(Http.PROTOCOL_DELIMITER);

		if (pos != -1) {
			return url.substring(0, pos);
		}

		return Http.HTTP;
	}

	@Override
	public String getQueryString(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return StringPool.BLANK;
		}

		return url.substring(pos + 1);
	}

	@Override
	public String getRequestURL(HttpServletRequest request) {
		return String.valueOf(request.getRequestURL());
	}

	@Override
	public boolean hasDomain(String url) {
		if (Validator.isNull(url)) {
			return false;
		}

		return Validator.isNotNull(getDomain(url));
	}

	@Override
	public boolean hasProtocol(String url) {
		if (Validator.isNull(url)) {
			return false;
		}

		int pos = url.indexOf(Http.PROTOCOL_DELIMITER);

		if (pos != -1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasProxyConfig() {
		if (Validator.isNotNull(_PROXY_HOST) && (_PROXY_PORT > 0)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isNonProxyHost(String host) {
		if (Validator.isNull(host)) {
			return false;
		}

		if (_nonProxyHostsPattern != null) {
			Matcher matcher = _nonProxyHostsPattern.matcher(host);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isProxyHost(String host) {
		if (Validator.isNull(host)) {
			return false;
		}

		if (hasProxyConfig() && !isNonProxyHost(host)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isSecure(String url) {
		String protocol = getProtocol(url);

		return StringUtil.equalsIgnoreCase(protocol, Http.HTTPS);
	}

	@Override
	public String normalizePath(String uri) {
		if (Validator.isNull(uri)) {
			return uri;
		}

		uri = removePathParameters(uri);

		String path = null;
		String queryString = null;

		int pos = uri.indexOf('?');

		if (pos != -1) {
			path = uri.substring(0, pos);
			queryString = uri.substring(pos + 1);
		}
		else {
			path = uri;
		}

		String[] uriParts = StringUtil.split(path.substring(1), CharPool.SLASH);

		List<String> parts = new ArrayList<>(uriParts.length);

		String prevUriPart = null;

		for (String uriPart : uriParts) {
			String curUriPart = URLCodec.decodeURL(uriPart);

			if (curUriPart.equals(StringPool.DOUBLE_PERIOD)) {
				if ((prevUriPart != null) &&
					!prevUriPart.equals(StringPool.PERIOD)) {

					parts.remove(parts.size() - 1);
				}
			}
			else if ((curUriPart.length() > 0) &&
					 !curUriPart.equals(StringPool.PERIOD)) {

				parts.add(URLCodec.encodeURL(curUriPart));
			}

			prevUriPart = curUriPart;
		}

		if (parts.isEmpty()) {
			return StringPool.SLASH;
		}

		StringBundler sb = new StringBundler(parts.size() * 2 + 2);

		for (String part : parts) {
			sb.append(StringPool.SLASH);
			sb.append(part);
		}

		if (Validator.isNotNull(queryString)) {
			sb.append(StringPool.QUESTION);
			sb.append(queryString);
		}

		return sb.toString();
	}

	@Override
	public Map<String, String[]> parameterMapFromString(String queryString) {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		if (Validator.isNull(queryString)) {
			return parameterMap;
		}

		Map<String, List<String>> tempParameterMap = new LinkedHashMap<>();

		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

				if (kvp.length == 0) {
					continue;
				}

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					try {
						value = decodeURL(kvp[1]);
					}
					catch (IllegalArgumentException iae) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Skipping parameter with key " + key +
									" because of invalid value " + kvp[1],
								iae);
						}

						continue;
					}
				}

				List<String> values = tempParameterMap.get(key);

				if (values == null) {
					values = new ArrayList<>();

					tempParameterMap.put(key, values);
				}

				values.add(value);
			}
		}

		for (Map.Entry<String, List<String>> entry :
				tempParameterMap.entrySet()) {

			String key = entry.getKey();
			List<String> values = entry.getValue();

			parameterMap.put(key, values.toArray(new String[values.size()]));
		}

		return parameterMap;
	}

	@Override
	public String parameterMapToString(Map<String, String[]> parameterMap) {
		return parameterMapToString(parameterMap, true);
	}

	@Override
	public String parameterMapToString(
		Map<String, String[]> parameterMap, boolean addQuestion) {

		if (parameterMap.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		if (addQuestion) {
			sb.append(StringPool.QUESTION);
		}

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();

			for (String value : values) {
				sb.append(name);
				sb.append(StringPool.EQUAL);
				sb.append(encodeURL(value));
				sb.append(StringPool.AMPERSAND);
			}
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	@Override
	public String protocolize(String url, ActionRequest actionRequest) {
		return protocolize(url, actionRequest.isSecure());
	}

	@Override
	public String protocolize(String url, boolean secure) {
		return protocolize(url, -1, secure);
	}

	@Override
	public String protocolize(String url, HttpServletRequest request) {
		return protocolize(url, request.isSecure());
	}

	@Override
	public String protocolize(String url, int port, boolean secure) {
		if (Validator.isNull(url)) {
			return url;
		}

		try {
			URL urlObj = new URL(url);

			String protocol = Http.HTTP;

			if (secure) {
				protocol = Http.HTTPS;
			}

			if (port == -1) {
				port = urlObj.getPort();
			}

			urlObj = new URL(
				protocol, urlObj.getHost(), port, urlObj.getFile());

			return urlObj.toString();
		}
		catch (Exception e) {
			return url;
		}
	}

	@Override
	public String protocolize(String url, RenderRequest renderRequest) {
		return protocolize(url, renderRequest.isSecure());
	}

	public void proxifyState(
		HttpState httpState, HostConfiguration hostConfiguration) {

		Credentials proxyCredentials = _proxyCredentials;

		String host = hostConfiguration.getHost();

		if (isProxyHost(host) && (proxyCredentials != null)) {
			AuthScope scope = new AuthScope(_PROXY_HOST, _PROXY_PORT, null);

			httpState.setProxyCredentials(scope, proxyCredentials);
		}
	}

	@Override
	public String removeDomain(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		url = removeProtocol(url);

		int pos = url.indexOf(CharPool.SLASH);

		if (pos > 0) {
			return url.substring(pos);
		}

		return url;
	}

	@Override
	public String removeParameter(String url, String name) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return url;
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		String[] array = PortalUtil.stripURLAnchor(url, StringPool.POUND);

		url = array[0];

		String anchor = array[1];

		StringBundler sb = new StringBundler();

		sb.append(url.substring(0, pos + 1));

		String[] parameters = StringUtil.split(
			url.substring(pos + 1, url.length()), CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				if (!key.equals(name)) {
					sb.append(key);
					sb.append(StringPool.EQUAL);
					sb.append(value);
					sb.append(StringPool.AMPERSAND);
				}
			}
		}

		url = StringUtil.replace(
			sb.toString(), StringPool.AMPERSAND + StringPool.AMPERSAND,
			StringPool.AMPERSAND);

		if (url.endsWith(StringPool.AMPERSAND)) {
			url = url.substring(0, url.length() - 1);
		}

		if (url.endsWith(StringPool.QUESTION)) {
			url = url.substring(0, url.length() - 1);
		}

		return url + anchor;
	}

	@Override
	public String removePathParameters(String uri) {
		if (Validator.isNull(uri)) {
			return uri;
		}

		int pos = uri.indexOf(CharPool.SEMICOLON);

		if (pos == -1) {
			return uri;
		}

		String[] uriParts = StringUtil.split(uri.substring(1), CharPool.SLASH);

		StringBundler sb = new StringBundler(uriParts.length * 2);

		for (String uriPart : uriParts) {
			pos = uriPart.indexOf(CharPool.SEMICOLON);

			if (pos == -1) {
				sb.append(StringPool.SLASH);
				sb.append(uriPart);
			}
			else if (pos == 0) {
				continue;
			}
			else {
				sb.append(StringPool.SLASH);
				sb.append(uriPart.substring(0, pos));
			}
		}

		if (sb.length() == 0) {
			return StringPool.SLASH;
		}

		return sb.toString();
	}

	@Override
	public String removeProtocol(String url) {
		if (Validator.isNull(url)) {
			return url;
		}

		Matcher matcher = _relativeUrlPattern.matcher(url);

		if (matcher.lookingAt()) {
			return url;
		}

		boolean modifiedURL;

		do {
			modifiedURL = false;

			matcher = _absoluteUrlPattern.matcher(url);

			if (matcher.lookingAt()) {
				url = url.substring(matcher.end());

				modifiedURL = true;
			}

			matcher = _protocolRelativeUrlPattern.matcher(url);

			if (matcher.lookingAt()) {
				url = url.substring(matcher.end());

				modifiedURL = true;
			}
		} while (modifiedURL);

		return url;
	}

	@Override
	public String sanitizeHeader(String header) {
		if (header == null) {
			return null;
		}

		StringBuilder sb = null;

		for (int i = 0; i < header.length(); i++) {
			char c = header.charAt(i);

			if (((c <= 31) && (c != 9)) || (c == 127) || (c > 255)) {
				if (sb == null) {
					sb = new StringBuilder(header);
				}

				sb.setCharAt(i, CharPool.SPACE);
			}
		}

		if (sb != null) {
			header = sb.toString();
		}

		return header;
	}

	@Override
	public String setParameter(String url, String name, boolean value) {
		return setParameter(url, name, String.valueOf(value));
	}

	@Override
	public String setParameter(String url, String name, double value) {
		return setParameter(url, name, String.valueOf(value));
	}

	@Override
	public String setParameter(String url, String name, int value) {
		return setParameter(url, name, String.valueOf(value));
	}

	@Override
	public String setParameter(String url, String name, long value) {
		return setParameter(url, name, String.valueOf(value));
	}

	@Override
	public String setParameter(String url, String name, short value) {
		return setParameter(url, name, String.valueOf(value));
	}

	@Override
	public String setParameter(String url, String name, String value) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return url;
		}

		url = removeParameter(url, name);

		return addParameter(url, name, value);
	}

	@Override
	public String shortenURL(String url, int count) {
		if (count == 0) {
			return null;
		}

		StringBundler sb = new StringBundler();

		String[] params = StringUtil.split(url, CharPool.AMPERSAND);

		for (int i = 0; i < params.length; i++) {
			String param = params[i];

			if (param.contains("_backURL=") || param.contains("_redirect=") ||
				param.contains("_returnToFullPageURL=") ||
				param.startsWith("redirect")) {

				int pos = param.indexOf(CharPool.EQUAL);

				String qName = param.substring(0, pos);

				String redirect = param.substring(pos + 1);

				try {
					redirect = decodeURL(redirect);
				}
				catch (IllegalArgumentException iae) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Skipping undecodable parameter " + param, iae);
					}

					continue;
				}

				String newURL = shortenURL(redirect, count - 1);

				if (newURL != null) {
					newURL = encodeURL(newURL);

					sb.append(qName);
					sb.append(StringPool.EQUAL);
					sb.append(newURL);

					if (i < (params.length - 1)) {
						sb.append(StringPool.AMPERSAND);
					}
				}
			}
			else {
				sb.append(param);

				if (i < (params.length - 1)) {
					sb.append(StringPool.AMPERSAND);
				}
			}
		}

		return sb.toString();
	}

	@Override
	public byte[] URLtoByteArray(Http.Options options) throws IOException {
		return URLtoByteArray(
			options.getLocation(), options.getMethod(), options.getHeaders(),
			options.getCookies(), options.getAuth(), options.getBody(),
			options.getFileParts(), options.getParts(), options.getResponse(),
			options.isFollowRedirects());
	}

	@Override
	public byte[] URLtoByteArray(String location) throws IOException {
		Http.Options options = new Http.Options();

		options.setLocation(location);

		return URLtoByteArray(options);
	}

	@Override
	public byte[] URLtoByteArray(String location, boolean post)
		throws IOException {

		Http.Options options = new Http.Options();

		options.setLocation(location);
		options.setPost(post);

		return URLtoByteArray(options);
	}

	@Override
	public InputStream URLtoInputStream(Http.Options options)
		throws IOException {

		return URLtoInputStream(
			options.getLocation(), options.getMethod(), options.getHeaders(),
			options.getCookies(), options.getAuth(), options.getBody(),
			options.getFileParts(), options.getParts(), options.getResponse(),
			options.isFollowRedirects());
	}

	@Override
	public InputStream URLtoInputStream(String location) throws IOException {
		Http.Options options = new Http.Options();

		options.setLocation(location);

		return URLtoInputStream(options);
	}

	@Override
	public InputStream URLtoInputStream(String location, boolean post)
		throws IOException {

		Http.Options options = new Http.Options();

		options.setLocation(location);
		options.setPost(post);

		return URLtoInputStream(options);
	}

	@Override
	public String URLtoString(Http.Options options) throws IOException {
		return new String(URLtoByteArray(options));
	}

	@Override
	public String URLtoString(String location) throws IOException {
		return new String(URLtoByteArray(location));
	}

	@Override
	public String URLtoString(String location, boolean post)
		throws IOException {

		return new String(URLtoByteArray(location, post));
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
	 * @throws IOException if an IO exception occurred
	 */
	@Override
	public String URLtoString(URL url) throws IOException {
		String xml = null;

		if (url == null) {
			return null;
		}

		String protocol = StringUtil.toLowerCase(url.getProtocol());

		if (protocol.startsWith(Http.HTTP) || protocol.startsWith(Http.HTTPS)) {
			return URLtoString(url.toString());
		}

		URLConnection urlConnection = url.openConnection();

		if (urlConnection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to open a connection to " + url);
			}

			return null;
		}

		try (InputStream inputStream = urlConnection.getInputStream();
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			byte[] bytes = new byte[512];

			for (int i = inputStream.read(bytes, 0, 512); i != -1;
				i = inputStream.read(bytes, 0, 512)) {

				unsyncByteArrayOutputStream.write(bytes, 0, i);
			}

			xml = new String(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}

		return xml;
	}

	protected boolean hasRequestHeader(HttpMethod httpMethod, String name) {
		Header[] headers = httpMethod.getRequestHeaders(name);

		if (headers.length == 0) {
			return false;
		}

		return true;
	}

	protected void processPostMethod(
		PostMethod postMethod, List<Http.FilePart> fileParts,
		Map<String, String> parts) {

		if ((fileParts == null) || fileParts.isEmpty()) {
			if (parts != null) {
				for (Map.Entry<String, String> entry : parts.entrySet()) {
					String value = entry.getValue();

					if (value != null) {
						postMethod.addParameter(entry.getKey(), value);
					}
				}
			}
		}
		else {
			List<Part> partsList = new ArrayList<>();

			if (parts != null) {
				for (Map.Entry<String, String> entry : parts.entrySet()) {
					String value = entry.getValue();

					if (value != null) {
						StringPart stringPart = new StringPart(
							entry.getKey(), value);

						partsList.add(stringPart);
					}
				}
			}

			for (Http.FilePart filePart : fileParts) {
				partsList.add(toCommonsFilePart(filePart));
			}

			MultipartRequestEntity multipartRequestEntity =
				new MultipartRequestEntity(
					partsList.toArray(new Part[partsList.size()]),
					postMethod.getParams());

			postMethod.setRequestEntity(multipartRequestEntity);
		}
	}

	protected org.apache.commons.httpclient.Cookie toCommonsCookie(
		Cookie cookie) {

		org.apache.commons.httpclient.Cookie commonsCookie =
			new org.apache.commons.httpclient.Cookie(
				cookie.getDomain(), cookie.getName(), cookie.getValue(),
				cookie.getPath(), cookie.getMaxAge(), cookie.getSecure());

		commonsCookie.setVersion(cookie.getVersion());

		return commonsCookie;
	}

	protected org.apache.commons.httpclient.Cookie[] toCommonsCookies(
		Cookie[] cookies) {

		if (cookies == null) {
			return null;
		}

		org.apache.commons.httpclient.Cookie[] commonCookies =
			new org.apache.commons.httpclient.Cookie[cookies.length];

		for (int i = 0; i < cookies.length; i++) {
			commonCookies[i] = toCommonsCookie(cookies[i]);
		}

		return commonCookies;
	}

	protected org.apache.commons.httpclient.methods.multipart.FilePart
		toCommonsFilePart(Http.FilePart filePart) {

		return new org.apache.commons.httpclient.methods.multipart.FilePart(
			filePart.getName(),
			new ByteArrayPartSource(
				filePart.getFileName(), filePart.getValue()),
			filePart.getContentType(), filePart.getCharSet());
	}

	protected Cookie toServletCookie(
		org.apache.commons.httpclient.Cookie commonsCookie) {

		Cookie cookie = new Cookie(
			commonsCookie.getName(), commonsCookie.getValue());

		if (!PropsValues.SESSION_COOKIE_USE_FULL_HOSTNAME) {
			String domain = commonsCookie.getDomain();

			if (Validator.isNotNull(domain)) {
				cookie.setDomain(domain);
			}
		}

		Date expiryDate = commonsCookie.getExpiryDate();

		if (expiryDate != null) {
			int maxAge =
				(int)(expiryDate.getTime() - System.currentTimeMillis());

			maxAge = maxAge / 1000;

			if (maxAge > -1) {
				cookie.setMaxAge(maxAge);
			}
		}

		String path = commonsCookie.getPath();

		if (Validator.isNotNull(path)) {
			cookie.setPath(path);
		}

		cookie.setSecure(commonsCookie.getSecure());
		cookie.setVersion(commonsCookie.getVersion());

		return cookie;
	}

	protected Cookie[] toServletCookies(
		org.apache.commons.httpclient.Cookie[] commonsCookies) {

		if (commonsCookies == null) {
			return null;
		}

		Cookie[] cookies = new Cookie[commonsCookies.length];

		for (int i = 0; i < commonsCookies.length; i++) {
			cookies[i] = toServletCookie(commonsCookies[i]);
		}

		return cookies;
	}

	protected byte[] URLtoByteArray(
			String location, Http.Method method, Map<String, String> headers,
			Cookie[] cookies, Http.Auth auth, Http.Body body,
			List<Http.FilePart> fileParts, Map<String, String> parts,
			Http.Response response, boolean followRedirects)
		throws IOException {

		InputStream inputStream = URLtoInputStream(
			location, method, headers, cookies, auth, body, fileParts, parts,
			response, followRedirects);

		if (inputStream == null) {
			return null;
		}

		try {
			long contentLengthLong = response.getContentLengthLong();

			if (contentLengthLong > _MAX_BYTE_ARRAY_LENGTH) {
				StringBundler sb = new StringBundler(5);

				sb.append("Retrieving ");
				sb.append(location);
				sb.append(" yields a file of size ");
				sb.append(contentLengthLong);
				sb.append(
					" bytes that is too large to convert to a byte array");

				throw new OutOfMemoryError(sb.toString());
			}

			return FileUtil.getBytes(inputStream);
		}
		finally {
			inputStream.close();
		}
	}

	protected InputStream URLtoInputStream(
			String location, Http.Method method, Map<String, String> headers,
			Cookie[] cookies, Http.Auth auth, Http.Body body,
			List<Http.FilePart> fileParts, Map<String, String> parts,
			Http.Response response, boolean followRedirects)
		throws IOException {

		HttpMethod httpMethod = null;
		HttpState httpState = null;

		try {
			_cookies.set(null);

			if (location == null) {
				return null;
			}
			else if (!location.startsWith(Http.HTTP_WITH_SLASH) &&
					 !location.startsWith(Http.HTTPS_WITH_SLASH)) {

				location = Http.HTTP_WITH_SLASH + location;
			}

			HostConfiguration hostConfiguration = getHostConfiguration(
				location);

			HttpClient httpClient = getClient(hostConfiguration);

			if (method.equals(Http.Method.POST) ||
				method.equals(Http.Method.PUT)) {

				if (method.equals(Http.Method.POST)) {
					httpMethod = new PostMethod(location);
				}
				else {
					httpMethod = new PutMethod(location);
				}

				if (body != null) {
					RequestEntity requestEntity = new StringRequestEntity(
						body.getContent(), body.getContentType(),
						body.getCharset());

					EntityEnclosingMethod entityEnclosingMethod =
						(EntityEnclosingMethod)httpMethod;

					entityEnclosingMethod.setRequestEntity(requestEntity);
				}
				else if (method.equals(Http.Method.POST)) {
					PostMethod postMethod = (PostMethod)httpMethod;

					if (!hasRequestHeader(
							postMethod, HttpHeaders.CONTENT_TYPE)) {

						HttpClientParams httpClientParams =
							httpClient.getParams();

						httpClientParams.setParameter(
							HttpMethodParams.HTTP_CONTENT_CHARSET,
							StringPool.UTF8);
					}

					processPostMethod(postMethod, fileParts, parts);
				}
			}
			else if (method.equals(Http.Method.DELETE)) {
				httpMethod = new DeleteMethod(location);
			}
			else if (method.equals(Http.Method.HEAD)) {
				httpMethod = new HeadMethod(location);
			}
			else {
				httpMethod = new GetMethod(location);
			}

			if (headers != null) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httpMethod.addRequestHeader(
						header.getKey(), header.getValue());
				}
			}

			if ((method.equals(Http.Method.POST) ||
				 method.equals(Http.Method.PUT)) &&
				((body != null) ||
				 ((fileParts != null) && !fileParts.isEmpty()) ||
				 ((parts != null) && !parts.isEmpty())) &&
				!hasRequestHeader(httpMethod, HttpHeaders.CONTENT_TYPE)) {

				httpMethod.addRequestHeader(
					HttpHeaders.CONTENT_TYPE,
					ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED_UTF8);
			}

			if (!hasRequestHeader(httpMethod, HttpHeaders.USER_AGENT)) {
				httpMethod.addRequestHeader(
					HttpHeaders.USER_AGENT, _DEFAULT_USER_AGENT);
			}

			httpState = new HttpState();

			if (ArrayUtil.isNotEmpty(cookies)) {
				org.apache.commons.httpclient.Cookie[] commonsCookies =
					toCommonsCookies(cookies);

				httpState.addCookies(commonsCookies);

				HttpMethodParams httpMethodParams = httpMethod.getParams();

				httpMethodParams.setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			}

			if (auth != null) {
				httpMethod.setDoAuthentication(true);

				httpState.setCredentials(
					new AuthScope(
						auth.getHost(), auth.getPort(), auth.getRealm()),
					new UsernamePasswordCredentials(
						auth.getUsername(), auth.getPassword()));
			}

			proxifyState(httpState, hostConfiguration);

			int responseCode = httpClient.executeMethod(
				hostConfiguration, httpMethod, httpState);

			response.setResponseCode(responseCode);

			Header locationHeader = httpMethod.getResponseHeader("location");

			if ((locationHeader != null) && !locationHeader.equals(location)) {
				String redirect = locationHeader.getValue();

				if (followRedirects) {
					httpMethod.releaseConnection();

					return URLtoInputStream(
						redirect, Http.Method.GET, headers, cookies, auth, body,
						fileParts, parts, response, followRedirects);
				}
				else {
					response.setRedirect(redirect);
				}
			}

			long contentLengthLong = 0;

			Header contentLengthHeader = httpMethod.getResponseHeader(
				HttpHeaders.CONTENT_LENGTH);

			if (contentLengthHeader != null) {
				contentLengthLong = GetterUtil.getLong(
					contentLengthHeader.getValue());

				response.setContentLengthLong(contentLengthLong);

				if (contentLengthLong > _MAX_BYTE_ARRAY_LENGTH) {
					response.setContentLength(-1);
				}
				else {
					int contentLength = (int)contentLengthLong;

					response.setContentLength(contentLength);
				}
			}

			Header contentType = httpMethod.getResponseHeader(
				HttpHeaders.CONTENT_TYPE);

			if (contentType != null) {
				response.setContentType(contentType.getValue());
			}

			for (Header header : httpMethod.getResponseHeaders()) {
				response.addHeader(header.getName(), header.getValue());
			}

			InputStream inputStream = httpMethod.getResponseBodyAsStream();

			final HttpMethod referenceHttpMethod = httpMethod;

			final Reference<InputStream> reference = FinalizeManager.register(
				inputStream,
				new FinalizeAction() {

					@Override
					public void doFinalize(Reference<?> reference) {
						referenceHttpMethod.releaseConnection();
					}

				},
				FinalizeManager.WEAK_REFERENCE_FACTORY);

			return new UnsyncFilterInputStream(inputStream) {

				@Override
				public void close() throws IOException {
					super.close();

					referenceHttpMethod.releaseConnection();

					reference.clear();
				}

			};
		}
		finally {
			try {
				if (httpState != null) {
					_cookies.set(toServletCookies(httpState.getCookies()));
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static final String _DEFAULT_USER_AGENT =
		"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)";

	private static final int _MAX_BYTE_ARRAY_LENGTH = Integer.MAX_VALUE - 8;

	private static final int _MAX_CONNECTIONS_PER_HOST = GetterUtil.getInteger(
		PropsUtil.get(HttpImpl.class.getName() + ".max.connections.per.host"),
		2);

	private static final int _MAX_TOTAL_CONNECTIONS = GetterUtil.getInteger(
		PropsUtil.get(HttpImpl.class.getName() + ".max.total.connections"), 20);

	private static final String _NON_PROXY_HOSTS = SystemProperties.get(
		"http.nonProxyHosts");

	private static final String _PROXY_AUTH_TYPE = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.auth.type"));

	private static final String _PROXY_HOST = GetterUtil.getString(
		SystemProperties.get("http.proxyHost"));

	private static final String _PROXY_NTLM_DOMAIN = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.ntlm.domain"));

	private static final String _PROXY_NTLM_HOST = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.ntlm.host"));

	private static final String _PROXY_PASSWORD = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.password"));

	private static final int _PROXY_PORT = GetterUtil.getInteger(
		SystemProperties.get("http.proxyPort"));

	private static final String _PROXY_USERNAME = GetterUtil.getString(
		PropsUtil.get(HttpImpl.class.getName() + ".proxy.username"));

	private static final String _TEMP_SLASH = "_LIFERAY_TEMP_SLASH_";

	private static final int _TIMEOUT = GetterUtil.getInteger(
		PropsUtil.get(HttpImpl.class.getName() + ".timeout"), 5000);

	private static final Log _log = LogFactoryUtil.getLog(HttpImpl.class);

	private static final ThreadLocal<Cookie[]> _cookies = new ThreadLocal<>();

	private final Pattern _absoluteUrlPattern = Pattern.compile(
		"^[a-zA-Z0-9]+://");
	private final HttpClient _httpClient = new HttpClient();
	private final Pattern _nonProxyHostsPattern;
	private final Pattern _protocolRelativeUrlPattern = Pattern.compile(
		"^[\\s\\\\/]+");
	private final Credentials _proxyCredentials;
	private final HttpClient _proxyHttpClient = new HttpClient();
	private final Pattern _relativeUrlPattern = Pattern.compile(
		"^\\s*/[a-zA-Z0-9]+");

	private static class FastProtocolSocketFactory
		extends DefaultProtocolSocketFactory {

		@Override
		public Socket createSocket(
				final String host, final int port,
				final InetAddress localInetAddress, final int localPort,
				final HttpConnectionParams httpConnectionParams)
			throws ConnectTimeoutException, IOException, UnknownHostException {

			int connectionTimeout = httpConnectionParams.getConnectionTimeout();

			if (connectionTimeout == 0) {
				return createSocket(host, port, localInetAddress, localPort);
			}

			SocketFactory socketFactory = SocketFactory.getDefault();

			Socket socket = socketFactory.createSocket();

			SocketAddress localSocketAddress = new InetSocketAddress(
				localInetAddress, localPort);

			SocketAddress remoteSocketAddress = new InetSocketAddress(
				host, port);

			socket.bind(localSocketAddress);

			socket.connect(remoteSocketAddress, connectionTimeout);

			return socket;
		}

	}

}