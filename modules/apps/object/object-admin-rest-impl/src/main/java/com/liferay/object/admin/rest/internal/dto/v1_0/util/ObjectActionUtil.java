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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectActionUtil {

	public static ObjectAction toObjectAction(
		Map<String, Map<String, String>> actions,
		com.liferay.object.model.ObjectAction serviceBuilderObjectAction) {

		if (serviceBuilderObjectAction == null) {
			return null;
		}

		ObjectAction objectAction = new ObjectAction() {
			{
				active = serviceBuilderObjectAction.isActive();
				dateCreated = serviceBuilderObjectAction.getCreateDate();
				dateModified = serviceBuilderObjectAction.getModifiedDate();
				id = serviceBuilderObjectAction.getObjectActionId();
				name = serviceBuilderObjectAction.getName();
				objectActionExecutorKey =
					serviceBuilderObjectAction.getObjectActionExecutorKey();
				objectActionTriggerKey =
					serviceBuilderObjectAction.getObjectActionTriggerKey();
				parameters =
					serviceBuilderObjectAction.getParametersUnicodeProperties();
			}
		};

		objectAction.setActions(actions);

		return objectAction;
	}

}