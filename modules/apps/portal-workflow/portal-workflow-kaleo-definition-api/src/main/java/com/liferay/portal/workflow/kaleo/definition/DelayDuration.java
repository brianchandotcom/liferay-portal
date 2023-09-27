/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Michael C. Han
 */
public class DelayDuration {

	public DelayDuration(double duration, DurationScale durationScale) {
		_duration = duration;
		_durationScale = durationScale;
	}

	public double getDuration() {
		return _duration;
	}

	public DurationScale getDurationScale() {
		return _durationScale;
	}

	private final double _duration;
	private final DurationScale _durationScale;

}