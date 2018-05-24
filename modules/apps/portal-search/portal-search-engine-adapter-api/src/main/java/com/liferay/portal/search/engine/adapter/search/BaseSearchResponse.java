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

package com.liferay.portal.search.engine.adapter.search;

import aQute.bnd.annotation.ProviderType;

import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseSearchResponse implements SearchResponse {

	public long getCount() {
		return _count;
	}

	public Map<String, String> getExecutionProfile() {
		return _executionProfile;
	}

	public long getExecutionTime() {
		return _executionTime;
	}

	public String getSearchRequestString() {
		return _searchRequestString;
	}

	public boolean isTerminatedEarly() {
		return _terminatedEarly;
	}

	public boolean isTimedOut() {
		return _timedOut;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setExecutionProfile(Map<String, String> executionProfile) {
		_executionProfile = executionProfile;
	}

	public void setExecutionTime(long executionTime) {
		_executionTime = executionTime;
	}

	public void setSearchRequestString(String searchRequestString) {
		_searchRequestString = searchRequestString;
	}

	public void setTerminatedEarly(boolean terminatedEarly) {
		_terminatedEarly = terminatedEarly;
	}

	public void setTimedOut(boolean timedOut) {
		_timedOut = timedOut;
	}

	private long _count;
	private Map<String, String> _executionProfile;
	private long _executionTime;
	private String _searchRequestString;
	private boolean _terminatedEarly;
	private boolean _timedOut;

}