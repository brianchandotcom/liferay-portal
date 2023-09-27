/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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