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
public class AssetTagPropertyKeyFinderUtil {
	public static int countByGroupId(long groupId) {
		return getFinder().countByGroupId(groupId);
	}

	public static java.lang.String[] findByGroupId(long groupId) {
		return getFinder().findByGroupId(groupId);
	}

	public static java.lang.String[] findByGroupId(long groupId, int start,
		int end) {
		return getFinder().findByGroupId(groupId, start, end);
	}

	public static AssetTagPropertyKeyFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetTagPropertyKeyFinder)PortalBeanLocatorUtil.locate(AssetTagPropertyKeyFinder.class.getName());

			ReferenceRegistry.registerReference(AssetTagPropertyKeyFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetTagPropertyKeyFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetTagPropertyKeyFinderUtil.class,
			"_finder");
	}

	private static AssetTagPropertyKeyFinder _finder;
}