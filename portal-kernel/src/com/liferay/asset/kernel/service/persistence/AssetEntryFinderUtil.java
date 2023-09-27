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
public class AssetEntryFinderUtil {

	public static int countEntries(AssetEntryQuery entryQuery) {
		return getFinder().countEntries(entryQuery);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		findEntries(AssetEntryQuery entryQuery) {

		return getFinder().findEntries(entryQuery);
	}

	public static AssetEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetEntryFinder)PortalBeanLocatorUtil.locate(
				AssetEntryFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(AssetEntryFinder finder) {
		_finder = finder;
	}

	private static AssetEntryFinder _finder;

}