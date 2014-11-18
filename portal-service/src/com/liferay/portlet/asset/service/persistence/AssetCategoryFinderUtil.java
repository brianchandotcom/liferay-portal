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
 * @generated
 */
@ProviderType
public class AssetCategoryFinderUtil {
	public static int countByG_C_N(long groupId, long classNameId,
		java.lang.String name) {
		return getFinder().countByG_C_N(groupId, classNameId, name);
	}

	public static int countByG_N_P(long groupId, java.lang.String name,
		java.lang.String[] categoryProperties) {
		return getFinder().countByG_N_P(groupId, name, categoryProperties);
	}

	public static com.liferay.portlet.asset.model.AssetCategory findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portlet.asset.NoSuchCategoryException {
		return getFinder().findByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByG_N_P(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties) {
		return getFinder().findByG_N_P(groupId, name, categoryProperties);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByG_N_P(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end) {
		return getFinder()
				   .findByG_N_P(groupId, name, categoryProperties, start, end);
	}

	public static AssetCategoryFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetCategoryFinder)PortalBeanLocatorUtil.locate(AssetCategoryFinder.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetCategoryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetCategoryFinderUtil.class,
			"_finder");
	}

	private static AssetCategoryFinder _finder;
}