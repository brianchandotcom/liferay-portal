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
public abstract class Retry<A> {

	public Retry() {
		this(5, 30);
	}

	public Retry(int maxRetries, int secondsRetryPeriod) {
		_maxRetries = maxRetries;
		_secondsRetryPeriod = secondsRetryPeriod;
	}

	public abstract A execute();

	public A realExecute() {
		int retryCount = 0;

		while (true) {
			try {
				return execute();
			}
			catch (Exception e) {
				retryCount++;

				System.out.println("An error has occurred: " + e);

				if ((_maxRetries >= 0) && (retryCount > _maxRetries)) {
					throw e;
				}

				sleep(_secondsRetryPeriod * 1000);

				System.out.println(
					"Retry attempt " + retryCount + " of " + _maxRetries);
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

	private int _maxRetries;
	private int _secondsRetryPeriod;

}