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

package com.liferay.portal.search.web.internal.custom.filter.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class CustomFilterDisplayBuilder {

	public static CustomFilterDisplayBuilder builder() {
		return new CustomFilterDisplayBuilder();
	}

	public CustomFilterDisplayContext build() {
		CustomFilterDisplayContext customFilterDisplayContext =
			new CustomFilterDisplayContext();

		customFilterDisplayContext.setFilterValue(getFilterValue());
		customFilterDisplayContext.setHeading(getHeading());
		customFilterDisplayContext.setImmutable(_immutable);
		customFilterDisplayContext.setParameterName(_parameterName);
		customFilterDisplayContext.setRenderNothing(isRenderNothing());
		customFilterDisplayContext.setSearchURL(getURLCurrentPath());

		return customFilterDisplayContext;
	}

	public CustomFilterDisplayBuilder customHeadingOptional(
		Optional<String> customHeadingOptional) {

		_customHeadingOptional = customHeadingOptional;

		return this;
	}

	public CustomFilterDisplayBuilder disabled(boolean disabled) {
		_disabled = disabled;

		return this;
	}

	public CustomFilterDisplayBuilder filterFieldOptional(
		Optional<String> filterFieldOptional) {

		_filterFieldOptional = filterFieldOptional;

		return this;
	}

	public CustomFilterDisplayBuilder filterValueOptional(
		Optional<String> filterValueOptional) {

		_filterValueOptional = filterValueOptional;

		return this;
	}

	public CustomFilterDisplayBuilder http(Http http) {
		_http = http;

		return this;
	}

	public CustomFilterDisplayBuilder immutable(boolean immutable) {
		_immutable = immutable;

		return this;
	}

	public CustomFilterDisplayBuilder parameterName(String parameterName) {
		_parameterName = parameterName;

		return this;
	}

	public CustomFilterDisplayBuilder parameterValueOptional(
		Optional<String> parameterValueOptional) {

		_parameterValueOptional = parameterValueOptional;

		return this;
	}

	public CustomFilterDisplayBuilder queryNameOptional(
		Optional<String> queryNameOptional) {

		_queryNameOptional = queryNameOptional;

		return this;
	}

	public CustomFilterDisplayBuilder themeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;

		return this;
	}

	protected String getFilterValue() {
		if (_immutable) {
			return SearchOptionalUtil.findFirstPresent(
				Stream.of(_filterValueOptional), StringPool.BLANK);
		}

		return SearchOptionalUtil.findFirstPresent(
			Stream.of(_parameterValueOptional, _filterValueOptional),
			StringPool.BLANK);
	}

	protected String getHeading() {
		return SearchOptionalUtil.findFirstPresent(
			Stream.of(
				_customHeadingOptional, _queryNameOptional,
				_filterFieldOptional),
			"custom");
	}

	protected String getURLCurrentPath() {
		return _http.getPath(_themeDisplay.getURLCurrent());
	}

	protected boolean isRenderNothing() {
		if (_disabled) {
			return true;
		}

		return false;
	}

	private Optional<String> _customHeadingOptional = Optional.empty();
	private boolean _disabled;
	private Optional<String> _filterFieldOptional = Optional.empty();
	private Optional<String> _filterValueOptional = Optional.empty();
	private Http _http;
	private boolean _immutable;
	private String _parameterName;
	private Optional<String> _parameterValueOptional = Optional.empty();
	private Optional<String> _queryNameOptional = Optional.empty();
	private ThemeDisplay _themeDisplay;

}