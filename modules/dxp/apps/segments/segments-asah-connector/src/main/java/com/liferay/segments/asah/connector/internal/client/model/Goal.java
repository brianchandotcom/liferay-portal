/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcellus Tavares
 * @author Sarai Díaz
 * @author David Arques
 */
public class Goal {

	public Goal() {
	}

	public Goal(GoalMetric goalMetric, String target) {
		_goalMetric = goalMetric;
		_target = target;
	}

	@JsonProperty("metric")
	public GoalMetric getGoalMetric() {
		return _goalMetric;
	}

	public String getTarget() {
		return _target;
	}

	public void setGoalMetric(GoalMetric goalMetric) {
		_goalMetric = goalMetric;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private GoalMetric _goalMetric;
	private String _target;

}