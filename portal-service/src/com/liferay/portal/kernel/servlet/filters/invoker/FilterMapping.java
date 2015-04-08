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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class FilterMapping {

	public FilterMapping(
		Filter filter, FilterConfig filterConfig, List<String> urlPatterns,
		List<String> dispatchers) {

		_filter = filter;
		_urlPatterns = urlPatterns;

		String urlRegexPattern = GetterUtil.getString(
			filterConfig.getInitParameter("url-regex-pattern"));

		if (Validator.isNotNull(urlRegexPattern)) {
			_urlRegexPattern = Pattern.compile(urlRegexPattern);
		}
		else {
			_urlRegexPattern = null;
		}

		String urlRegexIgnorePattern = GetterUtil.getString(
			filterConfig.getInitParameter("url-regex-ignore-pattern"));

		if (Validator.isNotNull(urlRegexIgnorePattern)) {
			_urlRegexIgnorePattern = Pattern.compile(urlRegexIgnorePattern);
		}
		else {
			_urlRegexIgnorePattern = null;
		}

		boolean dispatcherError = false;
		boolean dispatcherForward = false;
		boolean dispatcherInclude = false;
		boolean dispatcherRequest = false;

		for (String dispatcher : dispatchers) {
			switch(dispatcher) {
				case "ERROR":
					dispatcherError = true;
					break;
				case "FORWARD":
					dispatcherForward = true;
					break;
				case "INCLUDE":
					dispatcherInclude = true;
					break;
				case "REQUEST":
					dispatcherRequest = true;
					break;
				default:
					throw new IllegalArgumentException(
						"Invalid dispatcher " + dispatcher);
			}
		}

		_dispatcherError = dispatcherError;
		_dispatcherForward = dispatcherForward;
		_dispatcherInclude = dispatcherInclude;

		if (!_dispatcherError && !_dispatcherForward && !_dispatcherInclude &&
			!dispatcherRequest) {

			dispatcherRequest = true;
		}

		_dispatcherRequest = dispatcherRequest;
	}

	public Filter getFilter() {
		return _filter;
	}

	public boolean isMatch(
		HttpServletRequest request, Dispatcher dispatcher, String uri) {

		if (!isMatchDispatcher(dispatcher)) {
			return false;
		}

		if (uri == null) {
			return false;
		}

		boolean matchURLPattern = false;

		for (String urlPattern : _urlPatterns) {
			if (isMatchURLPattern(uri, urlPattern)) {
				matchURLPattern = true;

				break;
			}
		}

		if (_log.isDebugEnabled()) {
			if (matchURLPattern) {
				_log.debug(
					_filter.getClass() + " has a pattern match with " + uri);
			}
			else {
				_log.debug(
					_filter.getClass() +
						" does not have a pattern match with " + uri);
			}
		}

		if (!matchURLPattern) {
			return false;
		}

		if (isMatchURLRegexPattern(request, uri)) {
			return true;
		}

		return false;
	}

	public boolean isMatchURLRegexPattern(
		HttpServletRequest request, String uri) {

		String url = uri;

		String queryString = request.getQueryString();

		if (Validator.isNotNull(queryString)) {
			url = url.concat(StringPool.QUESTION).concat(queryString);
		}

		boolean matchURLRegexPattern = true;

		if (_urlRegexPattern != null) {
			Matcher matcher = _urlRegexPattern.matcher(url);

			matchURLRegexPattern = matcher.find();
		}

		if (matchURLRegexPattern && (_urlRegexIgnorePattern != null)) {
			Matcher matcher = _urlRegexIgnorePattern.matcher(url);

			matchURLRegexPattern = !matcher.find();
		}

		if (_log.isDebugEnabled()) {
			if (matchURLRegexPattern) {
				_log.debug(
					_filter.getClass() + " has a regex match with " + url);
			}
			else {
				_log.debug(
					_filter.getClass() + " does not have a regex match with " +
						url);
			}
		}

		return matchURLRegexPattern;
	}

	public FilterMapping replaceFilter(Filter filter) {
		return new FilterMapping(
			filter, _urlPatterns, _dispatcherError,
			_dispatcherForward, _dispatcherInclude, _dispatcherRequest,
			_urlRegexIgnorePattern, _urlRegexPattern);
	}

	protected boolean isMatchDispatcher(Dispatcher dispatcher) {
		if (((dispatcher == Dispatcher.ERROR) && _dispatcherError) ||
			((dispatcher == Dispatcher.FORWARD) && _dispatcherForward) ||
			((dispatcher == Dispatcher.INCLUDE) && _dispatcherInclude) ||
			((dispatcher == Dispatcher.REQUEST) && _dispatcherRequest)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isMatchURLPattern(String uri, String urlPattern) {
		if (urlPattern.equals(uri)) {
			return true;
		}

		if (urlPattern.equals(_SLASH_STAR)) {
			return true;
		}

		if (urlPattern.endsWith(_SLASH_STAR)) {
			if (urlPattern.regionMatches(0, uri, 0, urlPattern.length() - 2)) {
				if (uri.length() == (urlPattern.length() - 2)) {
					return true;
				}
				else if (CharPool.SLASH ==
							uri.charAt(urlPattern.length() - 2)) {

					return true;
				}
			}
		}
		else if (urlPattern.startsWith(_STAR_PERIOD)) {
			int slashPos = uri.lastIndexOf(CharPool.SLASH);
			int periodPos = uri.lastIndexOf(CharPool.PERIOD);

			if ((slashPos >= 0) && (periodPos > slashPos) &&
				(periodPos != (uri.length() - 1)) &&
				((uri.length() - periodPos) == (urlPattern.length() - 1))) {

				if (urlPattern.regionMatches(
						2, uri, periodPos + 1, urlPattern.length() - 2)) {

					return true;
				}
			}
		}

		return false;
	}

	private FilterMapping(
		Filter filter, List<String> urlPatterns,
		boolean dispatcherError, boolean dispatcherForward,
		boolean dispatcherInclude, boolean dispatcherRequest,
		Pattern urlRegexIgnorePattern, Pattern urlRegexPattern) {

		_dispatcherError = dispatcherError;
		_dispatcherForward = dispatcherForward;
		_dispatcherInclude = dispatcherInclude;
		_dispatcherRequest = dispatcherRequest;
		_filter = filter;
		_urlPatterns = urlPatterns;
		_urlRegexIgnorePattern = urlRegexIgnorePattern;
		_urlRegexPattern = urlRegexPattern;
	}

	private static final String _SLASH_STAR = "/*";

	private static final String _STAR_PERIOD = "*.";

	private static final Log _log = LogFactoryUtil.getLog(FilterMapping.class);

	private final boolean _dispatcherError;
	private final boolean _dispatcherForward;
	private final boolean _dispatcherInclude;
	private final boolean _dispatcherRequest;
	private final Filter _filter;
	private final List<String> _urlPatterns;
	private final Pattern _urlRegexIgnorePattern;
	private final Pattern _urlRegexPattern;

}