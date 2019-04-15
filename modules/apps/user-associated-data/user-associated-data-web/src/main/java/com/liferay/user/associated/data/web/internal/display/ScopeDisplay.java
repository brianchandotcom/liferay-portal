/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.user.associated.data.web.internal.display;

import java.util.List;

/**
 * @author Drew Brokke
 */
public class ScopeDisplay {

	public ScopeDisplay(
		String scopeName, long[] groupIds,
		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays) {

		_scopeName = scopeName;
		_groupIds = groupIds;
		_uadApplicationSummaryDisplays = uadApplicationSummaryDisplays;

		int count = 0;

		for (UADApplicationSummaryDisplay currentUADApplicationSummaryDisplay :
				uadApplicationSummaryDisplays) {

			count += currentUADApplicationSummaryDisplay.getCount();
		}

		_count = count;

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			uadApplicationSummaryDisplays.get(0);

		for (UADApplicationSummaryDisplay currentUADApplicationSummaryDisplay :
				uadApplicationSummaryDisplays) {

			if (currentUADApplicationSummaryDisplay.hasItems()) {
				uadApplicationSummaryDisplay =
					currentUADApplicationSummaryDisplay;

				break;
			}
		}

		_applicationKey = uadApplicationSummaryDisplay.getApplicationKey();
	}

	public String getApplicationKey() {
		return _applicationKey;
	}

	public int getCount() {
		return _count;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public String getScopeName() {
		return _scopeName;
	}

	public List<UADApplicationSummaryDisplay>
		getUADApplicationSummaryDisplays() {

		return _uadApplicationSummaryDisplays;
	}

	public boolean hasItems() {
		if (getCount() > 0) {
			return true;
		}

		return false;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private boolean _active;
	private final String _applicationKey;
	private final int _count;
	private final long[] _groupIds;
	private final String _scopeName;
	private final List<UADApplicationSummaryDisplay>
		_uadApplicationSummaryDisplays;

}