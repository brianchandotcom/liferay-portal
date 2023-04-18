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

package com.liferay.batch.planner.batch.engine.task;

import com.liferay.petra.string.StringPool;

/**
 * @author Igor Beslic
 */
public class TaskItemUtil {

	public static String getDelegateName(String internalClassName) {
		int idx = internalClassName.indexOf(StringPool.POUND);

		if (idx < 0) {
			return "DEFAULT";
		}

		return internalClassName.substring(idx + 1);
	}

	public static String getInternalClassName(String internalClassName) {
		int idx = internalClassName.indexOf(StringPool.POUND);

		if (idx < 0) {
			return internalClassName;
		}

		return internalClassName.substring(0, idx);
	}

}