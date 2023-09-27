/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetTagFinderUtil {

	public static int countByG_N(long groupId, String name) {
		return getFinder().countByG_N(groupId, name);
	}

	public static int countByG_C_N(
		long groupId, long classNameId, String name) {

		return getFinder().countByG_C_N(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetTag>
		findByG_C_N(
			long groupId, long classNameId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetTag> orderByComparator) {

		return getFinder().findByG_C_N(
			groupId, classNameId, name, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetTag>
		findByG_N_S_E(
			long groupId, String name, int startPeriod, int endPeriod,
			int periodLength) {

		return getFinder().findByG_N_S_E(
			groupId, name, startPeriod, endPeriod, periodLength);
	}

	public static AssetTagFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetTagFinder)PortalBeanLocatorUtil.locate(
				AssetTagFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(AssetTagFinder finder) {
		_finder = finder;
	}

	private static AssetTagFinder _finder;

}