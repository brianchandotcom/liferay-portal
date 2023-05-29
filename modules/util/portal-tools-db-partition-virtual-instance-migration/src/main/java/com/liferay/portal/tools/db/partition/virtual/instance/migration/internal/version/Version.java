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

package com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Luis Ortiz
 */
public class Version implements Comparable<Version> {

	public static Version parseVersion(String version) {
		if (version == null) {
			return new Version(0, 0, 0);
		}

		version = version.trim();

		if (version.isEmpty()) {
			return new Version(0, 0, 0);
		}

		version = version.trim();

		Matcher matcher = _versionPattern.matcher(version);

		if (!matcher.matches()) {
			return new Version(0, 0, 0);
		}

		int major = 0;
		int minor = 0;
		int micro = 0;
		String qualifier = "";

		String match;

		try {
			match = matcher.group(1);

			if (match != null) {
				major = Integer.parseInt(match.trim());
			}
		}
		catch (NumberFormatException numberFormatException) {
		}

		try {
			match = matcher.group(3);

			if (match != null) {
				minor = Integer.parseInt(match.trim());
			}
		}
		catch (NumberFormatException numberFormatException) {
		}

		try {
			match = matcher.group(5);

			if (match != null) {
				micro = Integer.parseInt(match.trim());
			}
		}
		catch (NumberFormatException numberFormatException) {
		}

		match = matcher.group(7);

		if (match != null) {
			qualifier = match.trim();
		}

		return new Version(major, minor, micro, qualifier);
	}

	public Version(int major, int minor, int micro) {
		_major = major;
		_minor = minor;
		_micro = micro;

		_qualifier = "";
	}

	public Version(int major, int minor, int micro, String qualifier) {
		_major = major;
		_minor = minor;
		_micro = micro;
		_qualifier = qualifier;
	}

	@Override
	public int compareTo(Version version) {
		int result = Integer.compare(_major, version._major);

		if (result != 0) {
			return result;
		}

		result = Integer.compare(_minor, version._minor);

		if (result != 0) {
			return result;
		}

		result = Integer.compare(_micro, version._micro);

		if (result != 0) {
			return result;
		}

		String qualifier = version._qualifier;

		result = _qualifier.compareTo(qualifier);

		if (_qualifier.isEmpty() || qualifier.isEmpty()) {
			return result * -1;
		}

		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Version)) {
			return false;
		}

		Version version = (Version)object;

		if ((_major == version._major) && (_minor == version._minor) &&
			(_micro == version._micro) &&
			_qualifier.equals(version._qualifier)) {

			return true;
		}

		return false;
	}

	public int getMajor() {
		return _major;
	}

	public int getMicro() {
		return _micro;
	}

	public int getMinor() {
		return _minor;
	}

	public String getQualifier() {
		return _qualifier;
	}

	@Override
	public int hashCode() {
		int hash = _major;

		hash = (hash * 11) + _minor;
		hash = (hash * 11) + _micro;

		return (hash * 11) + ((_qualifier == null) ? 0 : _qualifier.hashCode());
	}

	@Override
	public String toString() {
		if (getQualifier().isEmpty()) {
			return _major + "." + _minor + "." + _micro;
		}

		return _major + "." + _minor + "." + _micro + "." + _qualifier;
	}

	private static final Pattern _versionPattern = Pattern.compile(
		"(\\d{1,10})(\\.(\\d{1,10})(\\.(\\d{1,10})" +
			"(\\.([-_\\da-zA-Z]+))?)?)?");

	private final int _major;
	private final int _micro;
	private final int _minor;
	private final String _qualifier;

}