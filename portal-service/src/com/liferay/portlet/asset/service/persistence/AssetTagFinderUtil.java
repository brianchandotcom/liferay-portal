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

package com.liferay.portlet.asset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetTagFinderUtil {
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_N_S_E(
		long groupId, java.lang.String name, int startPeriod, int endPeriod,
		int periodLength) {
		return getFinder()
				   .findByG_N_S_E(groupId, name, startPeriod, endPeriod,
			periodLength);
	}

	public static int countByG_N(long groupId, java.lang.String name) {
		return getFinder().countByG_N(groupId, name);
	}

	public static int countByG_C_N(long groupId, long classNameId,
		java.lang.String name) {
		return getFinder().countByG_C_N(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetTag> obc) {
		return getFinder()
				   .findByG_C_N(groupId, classNameId, name, start, end, obc);
	}

	public static AssetTagFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetTagFinder)PortalBeanLocatorUtil.locate(AssetTagFinder.class.getName());

			ReferenceRegistry.registerReference(AssetTagFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetTagFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetTagFinderUtil.class, "_finder");
	}

	private static AssetTagFinder _finder;
}