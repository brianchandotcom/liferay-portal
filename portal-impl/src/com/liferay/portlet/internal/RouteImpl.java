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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringEncoder;
import com.liferay.portal.kernel.util.StringParser;
import com.liferay.portal.kernel.util.URLStringEncoder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RouteImpl implements Route {

	public RouteImpl(String pattern) {
		_pattern = pattern;

		_stringParser = StringParser.create(pattern);

		_stringParser.setStringEncoder(_urlEncoder);
	}

	@Override
	public void addGeneratedParameter(String name, String pattern) {
		if (_generatedParameters == null) {
			_generatedParameters = new HashMap<>();
		}

		StringParser stringParser = StringParser.create(pattern);

		_generatedParameters.put(name, stringParser);
	}

	@Override
	public void addIgnoredParameter(String name) {
		if (_ignoredParameters == null) {
			_ignoredParameters = new HashSet<>();
		}

		_ignoredParameters.add(name);
	}

	@Override
	public void addImplicitParameter(String name, String value) {
		if (_implicitParameters == null) {
			_implicitParameters = new HashMap<>();
		}

		_implicitParameters.put(name, value);
	}

	@Override
	public void addOverriddenParameter(String name, String value) {
		if (_overriddenParameters == null) {
			_overriddenParameters = new HashMap<>();
		}

		_overriddenParameters.put(name, value);
	}

	@Override
	public Map<String, StringParser> getGeneratedParameters() {
		if (_generatedParameters == null) {
			return Collections.emptyMap();
		}

		return _generatedParameters;
	}

	@Override
	public Set<String> getIgnoredParameters() {
		if (_ignoredParameters == null) {
			return Collections.emptySet();
		}

		return _ignoredParameters;
	}

	@Override
	public Map<String, String> getImplicitParameters() {
		if (_implicitParameters == null) {
			return Collections.emptyMap();
		}

		return _implicitParameters;
	}

	@Override
	public Map<String, String> getOverriddenParameters() {
		if (_overriddenParameters == null) {
			return Collections.emptyMap();
		}

		return _overriddenParameters;
	}

	@Override
	public String getPattern() {
		return _pattern;
	}

	@Override
	public String parametersToUrl(Map<String, String> parameters) {
		InheritableMap<String, String> allParameters = new InheritableMap<>();

		allParameters.setParentMap(parameters);

		// The order is important because virtual parameters may sometimes be
		// checked by implicit parameters

		Map<String, StringParser> generatedParameters =
			getGeneratedParameters();

		for (Map.Entry<String, StringParser> entry :
				generatedParameters.entrySet()) {

			String name = entry.getKey();
			StringParser stringParser = entry.getValue();

			String value = MapUtil.getString(allParameters, name);

			if (!stringParser.parse(value, allParameters)) {
				return null;
			}
		}

		Map<String, String> implicitParameters = getImplicitParameters();

		for (Map.Entry<String, String> entry : implicitParameters.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			if (!value.equals(MapUtil.getString(allParameters, name))) {
				return null;
			}
		}

		String url = _stringParser.build(allParameters);

		if (Validator.isNull(url)) {
			return null;
		}

		for (String name : generatedParameters.keySet()) {

			// Virtual parameters will never be placed in the query string, so
			// parameters is modified directly instead of allParameters

			parameters.remove(name);
		}

		for (String name : implicitParameters.keySet()) {
			parameters.remove(name);
		}

		for (String name : getIgnoredParameters()) {
			parameters.remove(name);
		}

		return url;
	}

	@Override
	public boolean urlToParameters(String url, Map<String, String> parameters) {
		if (!_stringParser.parse(url, parameters)) {
			return false;
		}

		parameters.putAll(getImplicitParameters());
		parameters.putAll(getOverriddenParameters());

		// The order is important because generated parameters may be dependent
		// on implicit parameters or overridden parameters

		Map<String, StringParser> generatedParameters =
			getGeneratedParameters();

		for (Map.Entry<String, StringParser> entry :
				generatedParameters.entrySet()) {

			StringParser stringParser = entry.getValue();

			String value = stringParser.build(parameters);

			// Generated parameters are not guaranteed to be created. The format
			// of the virtual parameters in the route pattern must match their
			// format in the generated parameter.

			if (value != null) {
				parameters.put(entry.getKey(), value);
			}
		}

		return true;
	}

	private static final StringEncoder _urlEncoder = new URLStringEncoder();

	private Map<String, StringParser> _generatedParameters;
	private Set<String> _ignoredParameters;
	private Map<String, String> _implicitParameters;
	private Map<String, String> _overriddenParameters;
	private final String _pattern;
	private final StringParser _stringParser;

}