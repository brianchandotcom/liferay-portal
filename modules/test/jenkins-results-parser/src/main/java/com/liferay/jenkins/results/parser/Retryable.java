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

package com.liferay.jenkins.results.parser;

/**
 * @author Kenji Heigel
 */
public abstract class Retryable<T> {

	public Retryable() {
		this(5, 30, true);
	}

	public Retryable(
		boolean exceptionOnFail, int maxRetries, int retryPeriod,
		boolean verbose) {

		_exceptionOnFail = exceptionOnFail;
		_maxRetries = maxRetries;
		_retryPeriod = retryPeriod;
		_verbose = verbose;
	}

	public Retryable(int maxRetries, int retryPeriod, boolean verbose) {
		this(true, maxRetries, retryPeriod, verbose);
	}

	public abstract T execute();

	public final T executeWithRetries() {
		int retryCount = 0;

		while (true) {
			try {
				return execute();
			}
			catch (Exception e) {
				retryCount++;

				if (_verbose) {
					System.out.println("An error has occurred: " + e);
				}

				if ((_maxRetries >= 0) && (retryCount > _maxRetries)) {
					if (_exceptionOnFail) {
						throw e;
					}

					return null;
				}

				sleep(_retryPeriod * 1000);

				if (_verbose) {
					System.out.println(
						"Retry attempt " + retryCount + " of " + _maxRetries);
				}
			}
		}
	}

	public void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}
	}

	private boolean _exceptionOnFail;
	private int _maxRetries;
	private int _retryPeriod;
	private boolean _verbose;

}