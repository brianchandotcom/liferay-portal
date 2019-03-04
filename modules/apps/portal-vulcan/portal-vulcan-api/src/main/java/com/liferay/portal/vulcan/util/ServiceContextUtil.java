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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class ServiceContextUtil {

	public static ServiceContext createServiceContext(
		long groupId, String viewableBy) {

		return createServiceContext(
			new String[0], new Long[0], groupId, viewableBy);
	}

	public static ServiceContext createServiceContext(
		String[] assetTags, Long[] categoryIds, long groupId,
		String viewableBy) {

		return new ServiceContext() {
			{
				if (Objects.equals(viewableBy, "anyone")) {
					setAddGuestPermissions(true);
					setAddGroupPermissions(true);
				}
				else if (Objects.equals(viewableBy, "members")) {
					setAddGuestPermissions(false);
					setAddGroupPermissions(true);
				}
				else {
					setAddGuestPermissions(false);
					setAddGroupPermissions(false);
				}

				if (categoryIds != null) {
					setAssetCategoryIds(ArrayUtil.toArray(categoryIds));
				}

				if (assetTags != null) {
					setAssetTagNames(assetTags);
				}

				setScopeGroupId(groupId);
			}
		};
	}

}