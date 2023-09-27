/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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