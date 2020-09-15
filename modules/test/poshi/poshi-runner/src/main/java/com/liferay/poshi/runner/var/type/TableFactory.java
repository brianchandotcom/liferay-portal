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

package com.liferay.poshi.runner.var.type;

import java.util.List;

/**
 * @author Yi-Chen Tsai
 */
public class TableFactory {

	public static BaseTable<?> newTable(
		List<List<String>> rawData, String tableType) {

		if (tableType.equals("HashesTable")) {
			return new HashesTable(rawData);
		}
		else if (tableType.equals("RawTable")) {
			return new RawTable(rawData);
		}
		else if (tableType.equals("RowsHashTable")) {
			return new RowsHashTable(rawData);
		}

		return new RawTable(rawData);
	}

}