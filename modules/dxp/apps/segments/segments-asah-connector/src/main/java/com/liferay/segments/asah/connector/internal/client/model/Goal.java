/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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