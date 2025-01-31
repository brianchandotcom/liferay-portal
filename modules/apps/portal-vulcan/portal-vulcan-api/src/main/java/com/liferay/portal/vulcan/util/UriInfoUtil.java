/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.net.URI;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @author Raymond Augé
 */
public class UriInfoUtil {

	public static String getBasePath(UriInfo uriInfo) {
		return String.valueOf(
			getBaseUriBuilder(
				uriInfo
			).build());
	}

	public static UriBuilder getBaseUriBuilder(
		HttpServletRequest httpServletRequest, UriInfo uriInfo) {

		UriBuilder uriBuilder = getBaseUriBuilder(uriInfo);

		uriBuilder.host(PortalUtil.getForwardedHost(httpServletRequest));

		int port = PortalUtil.getForwardedPort(httpServletRequest);

		boolean secure = PortalUtil.isSecure(httpServletRequest);

		if (((port != Http.HTTP_PORT) && !secure) ||
			((port != Http.HTTPS_PORT) && secure)) {

			uriBuilder.port(port);
		}
		else {
			uriBuilder.port(-1);
		}

		if (secure) {
			uriBuilder.scheme(Http.HTTPS);
		}

		return uriBuilder;
	}

	public static UriBuilder getBaseUriBuilder(
		String applicationPath, UriInfo uriInfo) {

		String basePath = getBasePath(uriInfo);

		if (basePath.endsWith(StringPool.FORWARD_SLASH)) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}

		basePath = basePath.substring(0, basePath.lastIndexOf("/") + 1);

		if (basePath.endsWith("/c/")) {
			basePath = StringUtil.removeLast(basePath, "c/");
		}

		if (applicationPath.startsWith(
				Portal.PATH_MODULE + StringPool.FORWARD_SLASH)) {

			applicationPath = applicationPath.substring(
				Portal.PATH_MODULE.length() +
					StringPool.FORWARD_SLASH.length());
		}

		basePath = basePath + applicationPath;

		return UriBuilder.fromPath(basePath);
	}

	public static UriBuilder getBaseUriBuilder(UriInfo uriInfo) {
		return _updateUriBuilder(uriInfo.getBaseUriBuilder());
	}

	public static UriInfo getVulcanUriInfo(
		String applicationPath, UriInfo uriInfo) {

		if ((applicationPath == null) || (uriInfo == null)) {
			return uriInfo;
		}

		return new UriInfo() {

			@Override
			public URI getAbsolutePath() {
				return _uriInfo.getAbsolutePath();
			}

			@Override
			public UriBuilder getAbsolutePathBuilder() {
				return _uriInfo.getAbsolutePathBuilder();
			}

			@Override
			public URI getBaseUri() {
				UriBuilder uriBuilder = UriInfoUtil.getBaseUriBuilder(
					applicationPath, uriInfo);

				return uriBuilder.build();
			}

			@Override
			public UriBuilder getBaseUriBuilder() {
				return UriInfoUtil.getBaseUriBuilder(applicationPath, uriInfo);
			}

			@Override
			public List<Object> getMatchedResources() {
				return _uriInfo.getMatchedResources();
			}

			@Override
			public List<String> getMatchedURIs() {
				return _uriInfo.getMatchedURIs();
			}

			@Override
			public List<String> getMatchedURIs(boolean b) {
				return _uriInfo.getMatchedURIs(b);
			}

			@Override
			public String getPath() {
				return _uriInfo.getPath();
			}

			@Override
			public String getPath(boolean b) {
				return _uriInfo.getPath(b);
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters() {
				return _uriInfo.getPathParameters();
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters(boolean b) {
				return _uriInfo.getPathParameters(b);
			}

			@Override
			public List<PathSegment> getPathSegments() {
				return _uriInfo.getPathSegments();
			}

			@Override
			public List<PathSegment> getPathSegments(boolean b) {
				return _uriInfo.getPathSegments(b);
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters() {
				return _uriInfo.getQueryParameters();
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters(
				boolean b) {

				return _uriInfo.getQueryParameters(b);
			}

			@Override
			public URI getRequestUri() {
				return _uriInfo.getRequestUri();
			}

			@Override
			public UriBuilder getRequestUriBuilder() {
				return _uriInfo.getRequestUriBuilder();
			}

			@Override
			public URI relativize(URI uri) {
				return _uriInfo.relativize(uri);
			}

			@Override
			public URI resolve(URI uri) {
				return _uriInfo.resolve(uri);
			}

			private final UriInfo _uriInfo = uriInfo;

		};
	}

	private static String _getHost(UriBuilder uriBuilder) {
		try {
			if (_uriBuilderHostField == null) {
				_uriBuilderHostField = ReflectionUtil.getDeclaredField(
					uriBuilder.getClass(), "host");
			}

			return (String)_uriBuilderHostField.get(uriBuilder);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private static boolean _isHttpsEnabled() {
		if (Http.HTTPS.equals(
				PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL)) ||
			Http.HTTPS.equals(PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL))) {

			return true;
		}

		return false;
	}

	private static UriBuilder _updateUriBuilder(UriBuilder uriBuilder) {
		if (!Validator.isBlank(PortalUtil.getPathContext())) {
			URI uri = uriBuilder.build();

			uriBuilder.replacePath(PortalUtil.getPathContext(uri.getPath()));
		}

		if (Validator.isNotNull(_getHost(uriBuilder)) && _isHttpsEnabled()) {
			uriBuilder.scheme(Http.HTTPS);
		}

		return uriBuilder;
	}

	private static volatile Field _uriBuilderHostField;

}