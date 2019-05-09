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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #FilterMapping(String, Filter, FilterConfig, List<String>,
	 *             Set<Dispatcher>)}
	 */
	@Deprecated
	public FilterMapping(
		String filterName, Filter filter, FilterConfig filterConfig,
		List<String> urlPatterns, List<String> dispatchers) {

		this(
			filterName, filter, filterConfig, urlPatterns,
			_toDispatchers(dispatchers));
	}

	public FilterMapping(
		String filterName, Filter filter, FilterConfig filterConfig,
		List<String> urlPatterns, Set<Dispatcher> dispatchers) {

		_filterName = filterName;
		_filter = filter;

		_urlPatterns = urlPatterns.toArray(new String[0]);

		String urlRegexPattern = filterConfig.getInitParameter(
			"url-regex-pattern");

		if (Validator.isNull(urlRegexPattern)) {
			_urlRegexPattern = null;
		}
		else {
			_urlRegexPattern = Pattern.compile(urlRegexPattern);
		}

		String urlRegexIgnorePattern = filterConfig.getInitParameter(
			"url-regex-ignore-pattern");

		if (Validator.isNull(urlRegexIgnorePattern)) {
			_urlRegexIgnorePattern = null;
		}
		else {
			_urlRegexIgnorePattern = Pattern.compile(urlRegexIgnorePattern);
		}

		_dispatchers = dispatchers;

		if (_dispatchers.isEmpty()) {
			_dispatchers.add(Dispatcher.REQUEST);
		}
	}

	public Filter getFilter() {
		return _filter;
	}

	public String getFilterName() {
		return _filterName;
	}

	public boolean isMatch(
		HttpServletRequest httpServletRequest, Dispatcher dispatcher,
		String uri) {

		if (!_dispatchers.contains(dispatcher) || (uri == null)) {
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

		if (matchURLPattern &&
			isMatchURLRegexPattern(httpServletRequest, uri)) {

			return true;
		}

		return false;
	}

	public boolean isMatchURLRegexPattern(
		HttpServletRequest httpServletRequest, String uri) {

		String url = uri;

		String queryString = httpServletRequest.getQueryString();

		if (Validator.isNotNull(queryString)) {
			url = url.concat(
				StringPool.QUESTION
			).concat(
				queryString
			);
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
			_filterName, filter, _urlPatterns, _dispatchers,
			_urlRegexIgnorePattern, _urlRegexPattern);
	}

	protected boolean isMatchURLPattern(String uri, String urlPattern) {
		if (urlPattern.equals(uri) || urlPattern.equals(_SLASH_STAR)) {
			return true;
		}

		if (urlPattern.endsWith(_SLASH_STAR)) {
			if (uri.equals(urlPattern.substring(0, urlPattern.length() - 2)) ||
				uri.startsWith(
					urlPattern.substring(0, urlPattern.length() - 1))) {

				return true;
			}
		}
		else if (urlPattern.startsWith(_STAR_PERIOD) &&
				 (uri.indexOf(CharPool.SLASH) != -1) &&
				 uri.endsWith(urlPattern.substring(1))) {

			return true;
		}

		return false;
	}

	private static Set<Dispatcher> _toDispatchers(List<String> dispatchers) {
		Set<Dispatcher> dispatcherSet = new HashSet<>();

		for (String dispatcher : dispatchers) {
			dispatcherSet.add(Dispatcher.valueOf(dispatcher));
		}

		return dispatcherSet;
	}

	private FilterMapping(
		String filterName, Filter filter, String[] urlPatterns,
		Set<Dispatcher> dispatchers, Pattern urlRegexIgnorePattern,
		Pattern urlRegexPattern) {

		_filterName = filterName;
		_filter = filter;
		_urlPatterns = urlPatterns;
		_dispatchers = dispatchers;
		_urlRegexIgnorePattern = urlRegexIgnorePattern;
		_urlRegexPattern = urlRegexPattern;
	}

	private static final String _SLASH_STAR = "/*";

	private static final String _STAR_PERIOD = "*.";

	private static final Log _log = LogFactoryUtil.getLog(FilterMapping.class);

	private final Set<Dispatcher> _dispatchers;
	private final Filter _filter;
	private final String _filterName;
	private final String[] _urlPatterns;
	private final Pattern _urlRegexIgnorePattern;
	private final Pattern _urlRegexPattern;

}