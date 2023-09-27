/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map.test;

/**
 * @author Carlos Sierra Andrés
 */
public class TrackedTwo {

	public TrackedTwo(TrackedOne trackedOne) {
		_trackedOne = trackedOne;
	}

	public TrackedOne getTrackedOne() {
		return _trackedOne;
	}

	private final TrackedOne _trackedOne;

}