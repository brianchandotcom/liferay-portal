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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.ActivityAggregation;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ActivityHistoryDisplay {

	public ActivityHistoryDisplay() {
	}

	public ActivityHistoryDisplay(
		List<ActivityAggregation> activityAggregations,
		List<ActivityAggregation> previousActivityAggregations) {

		_activityAggregations = activityAggregations;

		Stream<ActivityAggregation> activityAggregationStream =
			_activityAggregations.stream();

		_count = activityAggregationStream.mapToLong(
			ActivityAggregation::getTotalElements
		).sum();

		Stream<ActivityAggregation> previousActivityAggregationsStream =
			previousActivityAggregations.stream();

		_previousCount = previousActivityAggregationsStream.mapToLong(
			ActivityAggregation::getTotalElements
		).sum();

		_change = (double)(_count - _previousCount) / _previousCount;
	}

	private List<ActivityAggregation> _activityAggregations;
	private double _change;
	private long _count;
	private long _previousCount;

}