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

package com.liferay.portal.file.install.internal.version;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public class VersionRange implements Serializable {

	public static final int ANY = 40;

	public static final VersionRange ANY_VERSION;

	public static final int EXACT = 0;

	public static final Version INFINITE_VERSION;

	public static final int MAJOR = 3;

	public static final int MICRO = 1;

	public static final int MINOR = 2;

	public static VersionRange newInstance(
		Version pointVersion, int lowerBoundRule, int upperBoundRule) {

		Version floor = null;

		if (lowerBoundRule == ANY) {
			floor = VersionTable.getVersion(0, 0, 0);
		}
		else if (lowerBoundRule == MAJOR) {
			floor = VersionTable.getVersion(pointVersion.getMajor(), 0, 0);
		}
		else if (lowerBoundRule == MINOR) {
			floor = VersionTable.getVersion(
				pointVersion.getMajor(), pointVersion.getMinor(), 0);
		}
		else if (lowerBoundRule == MICRO) {
			floor = VersionTable.getVersion(
				pointVersion.getMajor(), pointVersion.getMinor(),
				pointVersion.getMicro());
		}
		else if (lowerBoundRule == EXACT) {
			floor = pointVersion;
		}

		Version ceiling = null;

		boolean openCeiling = true;

		if (upperBoundRule == ANY) {
			ceiling = INFINITE_VERSION;
		}
		else if (upperBoundRule == MAJOR) {
			ceiling = VersionTable.getVersion(
				pointVersion.getMajor() + 1, 0, 0);
		}
		else if (upperBoundRule == MINOR) {
			ceiling = VersionTable.getVersion(
				pointVersion.getMajor(), pointVersion.getMinor() + 1, 0);
		}
		else if (upperBoundRule == MICRO) {
			ceiling = VersionTable.getVersion(
				pointVersion.getMajor(), pointVersion.getMinor(),
				pointVersion.getMicro() + 1);
		}
		else if (upperBoundRule == EXACT) {
			ceiling = pointVersion;
			openCeiling = false;
		}

		return new VersionRange(false, floor, ceiling, openCeiling);
	}

	public static VersionRange parseVersionRange(String string)
		throws IllegalArgumentException, NumberFormatException {

		if (string == null) {
			return ANY_VERSION;
		}

		string = string.trim();

		if (string.length() == 0) {
			return ANY_VERSION;
		}

		return new VersionRange(string);
	}

	public VersionRange(
		boolean openFloor, Version floor, Version ceiling,
		boolean openCeiling) {

		_openFloor = openFloor;
		_floor = floor;
		_ceiling = ceiling;
		_openCeiling = openCeiling;

		_checkRange();
	}

	public VersionRange(String value)
		throws IllegalArgumentException, NumberFormatException {

		this(value, false);
	}

	public VersionRange(String value, boolean exact)
		throws IllegalArgumentException, NumberFormatException {

		this(value, exact, true);
	}

	public VersionRange(String value, boolean exact, boolean clean)
		throws IllegalArgumentException, NumberFormatException {

		value = _removeQuotesAndWhitespaces(value);

		int first = value.charAt(0);

		if (first == CharPool.OPEN_BRACKET) {
			_openFloor = false;
		}
		else if (first == CharPool.OPEN_PARENTHESIS) {
			_openFloor = true;
		}
		else {
			_openFloor = false;

			_floor = VersionTable.getVersion(value, clean);

			if (exact) {
				_ceiling = _floor;

				_openCeiling = false;
			}
			else {
				_ceiling = INFINITE_VERSION;

				_openCeiling = true;
			}

			return;
		}

		int last = value.charAt(value.length() - 1);

		if (last == CharPool.CLOSE_BRACKET) {
			_openCeiling = false;
		}
		else if (last == CharPool.CLOSE_PARENTHESIS) {
			_openCeiling = true;
		}
		else {
			throw new IllegalArgumentException(
				"Illegal version range syntax " + value +
					": range must end in ')' or ']'");
		}

		int comma = value.indexOf(CharPool.COMMA);

		if (comma == -1) {
			throw new IllegalArgumentException(
				"Illegal version range syntax no comma");
		}

		if (value.indexOf(CharPool.COMMA, comma + 1) > 0) {
			throw new IllegalArgumentException(
				"Illegal version range syntax too many commas");
		}

		String floor = value.substring(1, comma);

		String ceiling = value.substring(comma + 1, value.length() - 1);

		_floor = VersionTable.getVersion(floor, clean);

		if (ceiling.equals(StringPool.STAR)) {
			_ceiling = INFINITE_VERSION;
		}
		else {
			_ceiling = VersionTable.getVersion(ceiling, clean);
		}

		_checkRange();
	}

	public VersionRange(Version atLeast) {
		this(atLeast, false);
	}

	public VersionRange(Version atLeast, boolean exact) {
		_openFloor = false;
		_floor = atLeast;

		if (exact) {
			_ceiling = atLeast;
		}
		else {
			_ceiling = INFINITE_VERSION;
		}

		if (exact) {
			_openCeiling = false;
		}
		else {
			_openCeiling = true;
		}

		_checkRange();
	}

	public boolean contains(Version version) {
		if (version.equals(INFINITE_VERSION)) {
			if (_ceiling.equals(INFINITE_VERSION)) {
				return true;
			}

			return false;
		}

		if (((version.compareTo(_floor) > 0) &&
			 (version.compareTo(_ceiling) < 0)) ||
			(!_openFloor && version.equals(_floor)) ||
			(!_openCeiling && version.equals(_ceiling))) {

			return true;
		}

		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (object == null) {
			return false;
		}

		if (getClass() != object.getClass()) {
			return false;
		}

		VersionRange versionRange = (VersionRange)object;

		Version ceiling = versionRange.getCeiling();

		if (_ceiling == null) {
			if (ceiling != null)

				return false;
		}
		else if (!_ceiling.equals(ceiling)) {
			return false;
		}

		Version floor = versionRange.getFloor();

		if (_floor == null) {
			if (floor != null)

				return false;
		}
		else if (!_floor.equals(floor)) {
			return false;
		}

		if (_openCeiling != versionRange.isOpenCeiling()) {
			return false;
		}

		if (_openFloor != versionRange.isOpenFloor()) {
			return false;
		}

		return true;
	}

	public Version getCeiling() {
		return _ceiling;
	}

	public Version getFloor() {
		return _floor;
	}

	@Override
	public int hashCode() {
		int prime = 31;

		int result = 1;

		if (_ceiling == null) {
			result = prime * result;
		}
		else {
			result = prime * result + _ceiling.hashCode();
		}

		if (_floor == null) {
			result = prime * result;
		}
		else {
			result = prime * result + _floor.hashCode();
		}

		if (_openCeiling) {
			result = prime * result + 1231;
		}
		else {
			result = prime * result + 1237;
		}

		if (_openFloor) {
			result = prime * result + 1231;
		}
		else {
			result = prime * result + 1237;
		}

		return result;
	}

	public VersionRange intersect(VersionRange versionRange) {

		// Use the highest minimum version.

		Version newFloor = null;

		boolean newOpenFloor = false;

		int minCompare = _floor.compareTo(versionRange.getFloor());

		if (minCompare > 0) {
			newFloor = _floor;

			newOpenFloor = _openFloor;
		}
		else if (minCompare < 0) {
			newFloor = versionRange.getFloor();

			newOpenFloor = versionRange.isOpenFloor();
		}
		else {
			newFloor = _floor;

			if (_openFloor || versionRange.isOpenFloor()) {
				newOpenFloor = true;
			}
			else {
				newOpenFloor = false;
			}
		}

		// Use the lowest maximum version.

		Version newCeiling = null;
		boolean newOpenCeiling = false;

		// null maximum version means unbounded, so the highest possible value.

		int maxCompare = _ceiling.compareTo(versionRange.getCeiling());

		if (maxCompare < 0) {
			newCeiling = _ceiling;

			newOpenCeiling = _openCeiling;
		}
		else if (maxCompare > 0) {
			newCeiling = versionRange.getCeiling();

			newOpenCeiling = versionRange.isOpenCeiling();
		}
		else {
			newCeiling = _ceiling;

			if (_openCeiling || versionRange.isOpenCeiling()) {
				newOpenCeiling = true;
			}
			else {
				newOpenCeiling = false;
			}
		}

		VersionRange result = null;

		if (_isRangeValid(newOpenFloor, newFloor, newCeiling, newOpenCeiling)) {
			result = new VersionRange(
				newOpenFloor, newFloor, newCeiling, newOpenCeiling);
		}

		return result;
	}

	public boolean isOpenCeiling() {
		return _openCeiling;
	}

	public boolean isOpenFloor() {
		return _openFloor;
	}

	public boolean isPointVersion() {
		if (!_openFloor && !_openCeiling && _floor.equals(_ceiling)) {
			return true;
		}

		return false;
	}

	public String toString() {
		if (ANY_VERSION.equals(this)) {
			return _makeString(
				_openFloor, Version.emptyVersion, INFINITE_VERSION,
				_openCeiling);
		}

		return _makeString(_openFloor, _floor, _ceiling, _openCeiling);
	}

	private static boolean _isRangeValid(
		boolean openFloor, Version floor, Version ceiling,
		boolean openCeiling) {

		boolean result = false;

		int compare = floor.compareTo(ceiling);

		if (compare > 0) {

			// Minimum larger than maximum is invalid.

			result = false;
		}
		else if ((compare == 0) && (openFloor || openCeiling)) {

			// If floor and ceiling are the same, and either are exclusive,
			// no valid range exists.

			result = false;
		}
		else {

			// Range is valid.

			result = true;
		}

		return result;
	}

	private void _checkRange() {
		if (!_isRangeValid(_openFloor, _floor, _ceiling, _openCeiling)) {
			throw new IllegalArgumentException(
				"Invalid version range: " +
					_makeString(_openFloor, _floor, _ceiling, _openCeiling));
		}
	}

	private boolean _isRemoveable(char c) {
		if (c < 256) {
			return _removeable[c];
		}

		return Character.isWhitespace(c);
	}

	private String _makeString(
		boolean openFloor, Version floor, Version ceiling,
		boolean openCeiling) {

		StringBundler sb = new StringBundler(4);

		if (INFINITE_VERSION.equals(ceiling)) {
			if (Version.emptyVersion.equals(floor)) {
				sb.append("0");
			}
			else {
				sb.append(floor.toString());
			}
		}
		else {
			if (openFloor) {
				sb.append(StringPool.OPEN_PARENTHESIS);
			}
			else {
				sb.append(StringPool.OPEN_BRACKET);
			}

			if (Version.emptyVersion.equals(floor)) {
				sb.append("0");
			}
			else {
				sb.append(floor.toString());
			}

			sb.append(StringPool.COMMA);

			if (openCeiling) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
			else {
				sb.append(StringPool.CLOSE_BRACKET);
			}
		}

		return sb.toString();
	}

	private String _removeQuotesAndWhitespaces(String value) {
		int length = value.length();

		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);

			if (_isRemoveable(c)) {
				StringBuilder sb = new StringBuilder(length);

				sb.append(value, 0, i);

				for (i++; i < length; i++) {
					c = value.charAt(i);

					if (!_isRemoveable(c)) {
						sb.append(c);
					}
				}

				return sb.toString();
			}
		}

		return value;
	}

	private static boolean[] _removeable;
	private static final long serialVersionUID = 1L;

	static {
		_removeable = new boolean[256];

		for (int i = 0; i < 256; i++) {
			_removeable[i] = Character.isWhitespace(i);
		}

		_removeable[CharPool.QUOTE] = true;

		INFINITE_VERSION = new Version(
			Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
			StringPool.BLANK);

		ANY_VERSION = new VersionRange(
			false, Version.emptyVersion, VersionRange.INFINITE_VERSION, true);
	}

	private final Version _ceiling;
	private final Version _floor;
	private final boolean _openCeiling;
	private final boolean _openFloor;

}