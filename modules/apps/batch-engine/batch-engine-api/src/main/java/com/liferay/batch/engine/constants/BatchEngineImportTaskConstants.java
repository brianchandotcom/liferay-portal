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

package com.liferay.batch.engine.constants;

/**
 * @author Matija Petanjek
 */
public class BatchEngineImportTaskConstants {

	public static final int IMPORT_STRATEGY_ON_ERROR_CONTINUE = 1;

	public static final int IMPORT_STRATEGY_ON_ERROR_FAIL = 2;

	public static final String IMPORT_STRATEGY_STRING_ON_ERROR_CONTINUE =
		"ON_ERROR_CONTINUE";

	public static final String IMPORT_STRATEGY_STRING_ON_ERROR_FAIL =
		"ON_ERROR_FAIL";

	public static String getImportStrategyString(int importStrategy) {
		if (importStrategy == IMPORT_STRATEGY_ON_ERROR_CONTINUE) {
			return IMPORT_STRATEGY_STRING_ON_ERROR_CONTINUE;
		}
		else if (importStrategy == IMPORT_STRATEGY_ON_ERROR_FAIL) {
			return IMPORT_STRATEGY_STRING_ON_ERROR_FAIL;
		}

		throw new IllegalArgumentException(
			"Invalid import strategy " + importStrategy);
	}

}