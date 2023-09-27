/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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