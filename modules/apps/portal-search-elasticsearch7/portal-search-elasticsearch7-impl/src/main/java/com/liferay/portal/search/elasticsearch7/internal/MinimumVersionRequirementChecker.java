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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Bryan Engler
 */
public class MinimumVersionRequirementChecker {

	public MinimumVersionRequirementChecker(String minimumVersion) {
		int[] minimumVersionParts = _parse(minimumVersion);

		_minimumMajorVersion = minimumVersionParts[0];
		_minimumMinorVersion = minimumVersionParts[1];
		_minimumRevisionVersion = minimumVersionParts[2];
	}

	public boolean meetsRequirement(String version) {
		int[] versionParts = _parse(version);

		if (_meetsMajorVersionRequirement(versionParts[0]) ||
			_meetsMinorVersionRequirement(versionParts[0], versionParts[1]) ||
			_meetsRevisionVersionRequirement(
				versionParts[0], versionParts[1], versionParts[2])) {

			return true;
		}

		return false;
	}

	private boolean _meetsMajorVersionRequirement(int majorVersion) {
		if (majorVersion > _minimumMajorVersion) {
			return true;
		}

		return false;
	}

	private boolean _meetsMinorVersionRequirement(
		int majorVersion, int minorVersion) {

		if ((majorVersion == _minimumMajorVersion) &&
			(minorVersion > _minimumMinorVersion)) {

			return true;
		}

		return false;
	}

	private boolean _meetsRevisionVersionRequirement(
		int majorVersion, int minorVersion, int revisionVersion) {

		if ((majorVersion == _minimumMajorVersion) &&
			(minorVersion == _minimumMinorVersion) &&
			(revisionVersion >= _minimumRevisionVersion)) {

			return true;
		}

		return false;
	}

	private int[] _parse(String version) {
		int[] versionParts = {0, 0, 0};

		int[] parts = StringUtil.split(version, StringPool.PERIOD, 0);

		for (int i = 0; i < parts.length; i++) {
			versionParts[i] = parts[i];
		}

		return versionParts;
	}

	private final int _minimumMajorVersion;
	private final int _minimumMinorVersion;
	private final int _minimumRevisionVersion;

}