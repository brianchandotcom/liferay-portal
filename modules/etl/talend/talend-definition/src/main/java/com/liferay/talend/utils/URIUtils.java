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

package com.liferay.talend.utils;

import com.liferay.talend.exception.URIPathException;
import com.liferay.talend.runtime.apio.constants.ApioConstants;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class URIUtils {

	public static URI addQueryConditionToURL(
		String resourceURL, String queryCondition) {

		if (StringUtils.isEmpty(queryCondition)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Query condition was empty!");
			}

			return getURI(resourceURL);
		}

		URI decoratedURI = updateWithQueryParameters(
			resourceURL,
			Collections.singletonMap(
				ApioConstants.QUERY_PARAM_FILTER, queryCondition));

		return decoratedURI;
	}

	public static String getLastPathSegment(String url) {
		URI uri = getURI(url);

		String path = uri.getPath();

		if (path == null) {
			throw new URIPathException("Missing URI path from the URL: " + url);
		}

		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		int pos = path.lastIndexOf("/");

		if (pos == -1) {
			throw new URIPathException(
				"Unable to find path segments in the URL: " + url);
		}

		return path.substring(pos + 1);
	}

	public static URI getURI(String url) {
		try {
			return new URI(url);
		}
		catch (URISyntaxException urise) {
			_log.error("Unable to convert URL to URI: " + url);

			throw new RuntimeException(urise);
		}
	}

	public static URI setPaginationLimitOnURL(String resourceURL, int limit) {
		return updateWithQueryParameters(
			resourceURL,
			Collections.singletonMap(
				ApioConstants.QUERY_PARAM_PER_PAGE, String.valueOf(limit)));
	}

	public static URI updateWithQueryParameters(
		String url, Map<String, String> queryParameters) {

		if ((queryParameters == null) || queryParameters.isEmpty()) {
			return getURI(url);
		}

		URI uri = null;

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			UriBuilder uriBuilder = UriBuilder.fromUri(url);

			uri = uriBuilder.replaceQueryParam(
				parameter.getKey(), parameter.getValue()
			).build();
		}

		return uri;
	}

	public static URI updateWithQueryParameters(
		URI uri, Map<String, String> queryParameters) {

		if ((queryParameters == null) || queryParameters.isEmpty()) {
			return uri;
		}

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			UriBuilder uriBuilder = UriBuilder.fromUri(uri);

			uri = uriBuilder.replaceQueryParam(
				parameter.getKey(), parameter.getValue()
			).build();
		}

		return uri;
	}

	private static final Logger _log = LoggerFactory.getLogger(URIUtils.class);

}