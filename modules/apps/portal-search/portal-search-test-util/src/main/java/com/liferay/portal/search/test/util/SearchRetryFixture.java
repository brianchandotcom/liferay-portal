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

package com.liferay.portal.search.test.util;

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author André de Oliveira
 */
public class SearchRetryFixture {

	public static Builder builder() {
		return new Builder();
	}

	public void assertSearch(Runnable runnable) {
		assertSearch(
			() -> {
				runnable.run();

				return null;
			});
	}

	public <T> T assertSearch(Supplier<T> function) {
		if (_getAttempts() == 1) {
			return function.get();
		}

		return retrySearch(function);
	}

	public static class Builder {

		public Builder attempts(Integer attempts) {
			_searchRetryFixture._attempts = attempts;

			return this;
		}

		public SearchRetryFixture build() {
			return new SearchRetryFixture(_searchRetryFixture);
		}

		public Builder timeout(Integer timeout, TimeUnit timeoutTimeUnit) {
			_searchRetryFixture._timeout = timeout;
			_searchRetryFixture._timeoutTimeUnit = timeoutTimeUnit;

			return this;
		}

		private final SearchRetryFixture _searchRetryFixture =
			new SearchRetryFixture();

	}

	protected <T> T retrySearch(Supplier<T> supplier) {
		try {
			return IdempotentRetryAssert.retryAssert(
				_getTimeout(), _getTimeoutTimeUnit(), supplier::get);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private SearchRetryFixture() {
	}

	private SearchRetryFixture(SearchRetryFixture searchRetryFixture) {
		_attempts = searchRetryFixture._attempts;
		_timeout = searchRetryFixture._timeout;
		_timeoutTimeUnit = searchRetryFixture._timeoutTimeUnit;
	}

	private int _getAttempts() {
		return GetterUtil.getInteger(_attempts);
	}

	private int _getTimeout() {
		return GetterUtil.getInteger(_timeout, 3);
	}

	private TimeUnit _getTimeoutTimeUnit() {
		return (TimeUnit)GetterUtil.getObject(
			_timeoutTimeUnit, TimeUnit.SECONDS);
	}

	private Integer _attempts;
	private Integer _timeout;
	private TimeUnit _timeoutTimeUnit;

}