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

package com.liferay.portal.tools;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A dummy container for friendly URL route data used by source formatter.
 * Implements the comparable interface to sort routes by increasing generality.
 *
 * @author Connor McKay
 * @author Hugo Huijser
 */
public class ComparableRoute implements Comparable<ComparableRoute> {

	public static boolean isCaptureFragment(String fragment) {
		return fragment.contains("{");
	}

	public static boolean isMatchAny(String fragment) {
		return fragment.matches(".*\\{.+?:\\.\\*\\}.*");
	}

	public ComparableRoute(String pattern) {
		_pattern = pattern;
	}

	public void addGeneratedParameter(String name, String pattern) {
		_generatedParameters.put(name, pattern);
	}

	public void addIgnoredParameter(String name) {
		_ignoredParameters.add(name);
	}

	public void addImplicitParameter(String name, String value) {
		_implicitParameters.put(name, value);
	}

	public void addOverriddenParameter(String name, String value) {
		_overriddenParameters.put(name, value);
	}

	@Override
	public int compareTo(ComparableRoute comparableRoute) {

		// Don't split on .*

		String[] _fragments = _pattern.split("[/\\.](?!\\*)");

		String pattern = comparableRoute.getPattern();

		String[] fragments = pattern.split("[/\\.](?!\\*)");

		// Having more fragments is more general

		if (_fragments.length != fragments.length) {
			return _fragments.length - fragments.length;
		}

		// Having more capture fragments is more general

		int _captureFragmentsCount = getCaptureFragmentsCount(_fragments);
		int captureFragmentsCount = getCaptureFragmentsCount(fragments);

		if (_captureFragmentsCount != captureFragmentsCount) {
			return _captureFragmentsCount - _captureFragmentsCount;
		}

		int i;

		for (i = 0; (i < _fragments.length) && (i < fragments.length); i++) {
			String _fragment = _fragments[i];
			String fragment = fragments[i];

			// Capture fragments are more general than static ones

			if (!isCaptureFragment(_fragment) && isCaptureFragment(fragment)) {
				return -1;
			}

			if (isCaptureFragment(_fragment) && !isCaptureFragment(fragment)) {
				return 1;
			}

			// A fragment matching .* is more general than anything

			if (!isMatchAny(_fragment) && isMatchAny(fragment)) {
				return -1;
			}

			if (isMatchAny(_fragment) && !isMatchAny(fragment)) {
				return 1;
			}
		}

		// Having fewer implicit parameters is more general

		Map<String, String> implicitParameters =
			comparableRoute.getImplicitParameters();

		if (_implicitParameters.size() != implicitParameters.size()) {
			return implicitParameters.size() - _implicitParameters.size();
		}

		return _pattern.compareTo(comparableRoute.getPattern());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ComparableRoute)) {
			return false;
		}

		ComparableRoute comparableRoute = (ComparableRoute)obj;

		if (compareTo(comparableRoute) == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getCaptureFragmentsCount(String[] fragments) {
		int count = 0;

		for (String fragment : fragments) {
			if (isCaptureFragment(fragment)) {
				count++;
			}
		}

		return count;
	}

	public Map<String, String> getGeneratedParameters() {
		return _generatedParameters;
	}

	public Set<String> getIgnoredParameters() {
		return _ignoredParameters;
	}

	public Map<String, String> getImplicitParameters() {
		return _implicitParameters;
	}

	public Map<String, String> getOverriddenParameters() {
		return _overriddenParameters;
	}

	public String getPattern() {
		return _pattern;
	}

	private Map<String, String> _generatedParameters =
		new TreeMap<String, String>();
	private Set<String> _ignoredParameters = new TreeSet<String>();
	private Map<String, String> _implicitParameters =
		new TreeMap<String, String>();
	private Map<String, String> _overriddenParameters =
		new TreeMap<String, String>();
	private String _pattern;

}