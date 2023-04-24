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

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class ActivityAggregation {

	public Date getIntervalInitDate() {
		if (_intervalInitDate == null) {
			return null;
		}

		return new Date(_intervalInitDate.getTime());
	}

	public int getTotalElements() {
		return _totalElements;
	}

	public void setIntervalInitDate(Date intervalInitDate) {
		if (intervalInitDate != null) {
			_intervalInitDate = new Date(intervalInitDate.getTime());
		}
	}

	public void setTotalElements(int totalElements) {
		_totalElements = totalElements;
	}

	private Date _intervalInitDate;
	private int _totalElements;

}