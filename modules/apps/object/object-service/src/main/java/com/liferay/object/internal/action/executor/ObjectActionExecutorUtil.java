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

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.exception.ObjectActionExecutorKeyException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Feliphe Marinho
 */
public class ObjectActionExecutorUtil {

	public static void validateObjectActionExecutor(
			long companyId, ObjectActionExecutor objectActionExecutor,
			String objectDefinitionName)
		throws PortalException {

		if (!objectActionExecutor.isAllowedCompany(companyId)) {
			throw new ObjectActionExecutorKeyException(
				StringBundler.concat(
					"The object action executor key ",
					objectActionExecutor.getKey(),
					" is not allowed for company ", companyId));
		}

		if (!objectActionExecutor.isAllowedObjectDefinition(
				objectDefinitionName)) {

			throw new ObjectActionExecutorKeyException(
				StringBundler.concat(
					"The object action executor key ",
					objectActionExecutor.getKey(),
					" is not allowed for object definition ",
					objectDefinitionName));
		}
	}

}