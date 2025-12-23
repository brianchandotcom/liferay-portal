/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Map;

/**
 * @author Nilton Vieira
 */
public class RealTimeMembershipMetric {

	public Map<String, Object> getAverageSegmentMembershipDuration() {
		return _averageSegmentMembershipDuration;
	}

	public Map<String, Object> getEntryRate() {
		return _entryRate;
	}

	public Map<String, Object> getExitRate() {
		return _exitRate;
	}

	public Map<String, Object> getTotalMembers() {
		return _totalMembers;
	}

	public void setAverageSegmentMembershipDuration(
		Map<String, Object> averageSegmentMembershipDuration) {

		_averageSegmentMembershipDuration = averageSegmentMembershipDuration;
	}

	public void setEntryRate(Map<String, Object> entryRate) {
		_entryRate = entryRate;
	}

	public void setExitRate(Map<String, Object> exitRate) {
		_exitRate = exitRate;
	}

	public void setTotalMembers(Map<String, Object> totalMembers) {
		_totalMembers = totalMembers;
	}

	private Map<String, Object> _averageSegmentMembershipDuration;
	private Map<String, Object> _entryRate;
	private Map<String, Object> _exitRate;
	private Map<String, Object> _totalMembers;

}