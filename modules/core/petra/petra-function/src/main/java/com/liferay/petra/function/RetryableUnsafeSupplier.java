/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.function;

/**
 * @author Shuyang Zhou
 */
public class RetryableUnsafeSupplier<T, E extends Throwable>
	implements UnsafeSupplier<T, E> {

	public RetryableUnsafeSupplier(
		ErrorHandler errorHandler, boolean exceptionOnFailure, int maxRetries,
		long retryInterval, UnsafeSupplier<T, E> unsafeSupplier) {

		_errorHandler = errorHandler;
		_exceptionOnFailure = exceptionOnFailure;
		_maxRetries = maxRetries;
		_retryInterval = retryInterval;
		_unsafeSupplier = unsafeSupplier;
	}

	public RetryableUnsafeSupplier(
		ErrorHandler errorHandler, UnsafeSupplier<T, E> unsafeSupplier) {

		this(errorHandler, true, _MAX_RETRIES, _RETRY_INTERVAL, unsafeSupplier);
	}

	@Override
	public T get() throws E {
		Throwable previousThrowable = null;

		int retryCount = 0;

		while (true) {
			try {
				return _unsafeSupplier.get();
			}
			catch (Exception exception) {
				if (previousThrowable != null) {
					exception.addSuppressed(previousThrowable);
				}

				previousThrowable = exception;

				_errorHandler.onError(exception, _maxRetries, ++retryCount);

				if (retryCount > _maxRetries) {
					if (_exceptionOnFailure) {
						throw exception;
					}

					return null;
				}

				try {
					Thread.sleep(_retryInterval);
				}
				catch (InterruptedException interruptedException) {
					exception.addSuppressed(interruptedException);

					throw exception;
				}
			}
		}
	}

	public interface ErrorHandler {

		public void onError(
			Exception exception, int maxRetries, int retryCount);

	}

	private static final int _MAX_RETRIES = 3;

	private static final long _RETRY_INTERVAL = 1000;

	private final ErrorHandler _errorHandler;
	private final boolean _exceptionOnFailure;
	private final int _maxRetries;
	private final long _retryInterval;
	private final UnsafeSupplier<T, E> _unsafeSupplier;

}