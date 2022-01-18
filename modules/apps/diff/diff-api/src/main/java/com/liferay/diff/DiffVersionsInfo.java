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

package com.liferay.diff;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class DiffVersionsInfo {

	public DiffVersionsInfo(
		List<DiffVersion> diffVersions, double nextVersion,
		double previousVersion) {

		_diffVersions = diffVersions;
		_nextVersion = nextVersion;
		_previousVersion = previousVersion;
	}

	public List<DiffVersion> getDiffVersions() {
		return _diffVersions;
	}

	public double getNextVersion() {
		return _nextVersion;
	}

	public double getPreviousVersion() {
		return _previousVersion;
	}

	private final List<DiffVersion> _diffVersions;
	private final double _nextVersion;
	private final double _previousVersion;

}