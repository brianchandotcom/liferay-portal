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

package com.liferay.batch.engine.core.item.file;

/**
 * @author Ivica Cardic
 */
public class IncorrectTokenCountException extends RuntimeException {

	public IncorrectTokenCountException(
		int expectedCount, int actualCount, String input) {

		super(
			String.format(
				"Incorrect number of tokens found in row: expected %s, " +
					"actual %s",
				expectedCount, actualCount));

		_expectedCount = expectedCount;
		_actualCount = actualCount;
		_input = input;
	}

	public int getActualCount() {
		return _actualCount;
	}

	public int getExpectedCount() {
		return _expectedCount;
	}

	public String getInput() {
		return _input;
	}

	private final int _actualCount;
	private final int _expectedCount;
	private final String _input;

}