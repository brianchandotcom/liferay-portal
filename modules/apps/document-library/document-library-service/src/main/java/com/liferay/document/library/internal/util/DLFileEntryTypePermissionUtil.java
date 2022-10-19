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

package com.liferay.document.library.internal.util;

import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryTypePermissionUtil {

	public static Map<Long, String[]> getRoleIdsToActionIds(
		List<ResourceAction> resourceActions,
		List<ResourcePermission> resourcePermissions,
		Predicate<String> predicate) {

		Map<Long, String[]> roleIdsToActionIds = new HashMap<>();

		for (ResourcePermission resourcePermission : resourcePermissions) {
			long actionIds = resourcePermission.getActionIds();

			List<String> resourcePermissionActionIds = new ArrayList<>();

			for (ResourceAction resourceAction : resourceActions) {
				String actionId = resourceAction.getActionId();

				if (((actionIds & resourceAction.getBitwiseValue()) ==
						resourceAction.getBitwiseValue()) &&
					predicate.test(actionId)) {

					resourcePermissionActionIds.add(actionId);
				}
			}

			roleIdsToActionIds.put(
				resourcePermission.getRoleId(),
				resourcePermissionActionIds.toArray(new String[0]));
		}

		return roleIdsToActionIds;
	}

}