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
		this.maxRetries = maxRetries;
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
			catch (Exception exception) {
				retryCount++;

				if (_verbose) {
					System.out.println("An error has occurred: " + exception);
				}

				if ((maxRetries >= 0) && (retryCount > maxRetries)) {
					if (_exceptionOnFail) {
						throw exception;
					}

					return null;
				}

				sleep(_retryPeriod * 1000);

				String retryMessage = getRetryMessage(retryCount);

				if (!JenkinsResultsParserUtil.isNullOrEmpty(retryMessage)) {
					System.out.println(retryMessage);
				}
			}
		}
	}

	public void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	protected String getRetryMessage(int retryCount) {
		return JenkinsResultsParserUtil.combine(
			"Retry attempt ", String.valueOf(retryCount), " of ",
			String.valueOf(maxRetries));
	}

	protected int maxRetries;

	private boolean _exceptionOnFail;
	private int _retryPeriod;
	private boolean _verbose;

}