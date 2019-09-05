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

package com.liferay.asset.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetLinkFinderUtil {

	public static java.util.List<com.liferay.asset.kernel.model.AssetLink>
		findByAssetEntryGroupId(long groupId, int start, int end) {

		return getFinder().findByAssetEntryGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetLink>
		findByG_C(
			long groupId, java.util.Date startDate, java.util.Date endDate,
			int start, int end) {

		return getFinder().findByG_C(groupId, startDate, endDate, start, end);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetLink>
		findByC_C(long classNameId, long classPK) {

		return getFinder().findByC_C(classNameId, classPK);
	}

	public static AssetLinkFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetLinkFinder)PortalBeanLocatorUtil.locate(
				AssetLinkFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(AssetLinkFinder finder) {
		_finder = finder;
	}

	private static AssetLinkFinder _finder;

}