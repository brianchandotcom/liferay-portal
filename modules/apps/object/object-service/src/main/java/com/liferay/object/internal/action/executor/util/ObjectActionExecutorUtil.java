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

package com.liferay.object.internal.action.executor.util;

import com.liferay.object.action.executor.ObjectActionExecutor;

import java.util.List;

/**
 * @author Murilo Stodolni
 */
public class ObjectActionExecutorUtil {

	public static boolean isAllowedCompany(
		long companyId, ObjectActionExecutor objectActionExecutor) {

		if ((objectActionExecutor.getCompanyId() == 0) ||
			(objectActionExecutor.getCompanyId() == companyId)) {

			return true;
		}

		return false;
	}

	public static boolean isAllowedObjectDefinition(
		ObjectActionExecutor objectActionExecutor,
		String objectDefinitionName) {

		List<String> objectDefinitionNames =
			objectActionExecutor.getObjectDefinitionNames();

		if (objectDefinitionNames.isEmpty()) {
			return true;
		}

		return objectDefinitionNames.contains(objectDefinitionName);
	}

}