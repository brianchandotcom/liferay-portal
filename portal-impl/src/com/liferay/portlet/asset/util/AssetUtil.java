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

package com.liferay.portlet.asset.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author     Brian Wing Shun Chan
 * @author     Jorge Ferrer
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.asset.util.impl.AssetUtil}
 */
@Deprecated
public class AssetUtil {

	public static long[] filterCategoryIds(
			PermissionChecker permissionChecker, long[] categoryIds)
		throws PortalException {

		if (permissionChecker == null) {
			return categoryIds;
		}

		List<Long> viewableCategoryIds = new ArrayList<>();

		for (long categoryId : categoryIds) {
			AssetCategory category =
				AssetCategoryLocalServiceUtil.fetchCategory(categoryId);

			if ((category != null) &&
				AssetCategoryPermission.contains(
					permissionChecker, category, ActionKeys.VIEW)) {

				viewableCategoryIds.add(categoryId);
			}
		}

		return ArrayUtil.toArray(viewableCategoryIds.toArray(new Long[0]));
	}

}