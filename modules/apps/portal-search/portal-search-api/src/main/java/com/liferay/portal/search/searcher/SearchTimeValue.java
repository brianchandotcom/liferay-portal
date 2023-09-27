/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.searcher;

import java.util.concurrent.TimeUnit;

/**
 * @author Wade Cao
 */
public final class SearchTimeValue {

	public long getDuration() {
		return _duration;
	}

	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	public static class Builder {

		public static Builder newBuilder() {
			return new Builder();
		}

		public SearchTimeValue build() {
			return _searchTimeValue;
		}

		public Builder duration(long duration) {
			_searchTimeValue._duration = duration;

			return this;
		}

		public Builder timeUnit(TimeUnit timeUnit) {
			_searchTimeValue._timeUnit = timeUnit;

			return this;
		}

		private Builder() {
		}

		private final SearchTimeValue _searchTimeValue = new SearchTimeValue();

	}

	private SearchTimeValue() {
	}

	private long _duration;
	private TimeUnit _timeUnit;

}