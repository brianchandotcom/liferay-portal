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

package com.liferay.batch.engine;

/**
 * @author Matija Petanjek
 */
public enum BatchEngineImportTaskStrategy {

	ON_ERROR_CONTINUE(1), ON_ERROR_FAIL(2);

	public static BatchEngineImportTaskStrategy valueOf(int strategy) {
		for (BatchEngineImportTaskStrategy batchEngineImportTaskStrategy :
				values()) {

			if (batchEngineImportTaskStrategy._strategy == strategy) {
				return batchEngineImportTaskStrategy;
			}
		}

		throw new IllegalArgumentException("Invalid strategy " + strategy);
	}

	public int getStrategy() {
		return _strategy;
	}

	private BatchEngineImportTaskStrategy(int strategy) {
		_strategy = strategy;
	}

	private final int _strategy;

}