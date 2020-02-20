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

package com.liferay.analytics.reports.web.internal.data.model;

/**
 * @author David Arques
 */
public enum TimeSpan {

	LAST_7_DAYS("last-7-days", 2), LAST_24_HOURS("last-24-hours", 1),
	LAST_30_DAYS("last-30-days", 3);

	public String getLabel() {
		return _label;
	}

	public int getOrder() {
		return _order;
	}

	private TimeSpan(String label, int order) {
		_label = label;
		_order = order;
	}

	private final String _label;
	private final int _order;

}