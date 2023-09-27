/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.ActivityAggregation;

import java.util.List;

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

		for (ActivityAggregation activityAggregation : activityAggregations) {
			_count += activityAggregation.getTotalElements();
		}

		for (ActivityAggregation previousActivityAggregation :
				previousActivityAggregations) {

			_previousCount += previousActivityAggregation.getTotalElements();
		}

		_change = (double)(_count - _previousCount) / _previousCount;
	}

	private List<ActivityAggregation> _activityAggregations;
	private double _change;
	private long _count;
	private long _previousCount;

}