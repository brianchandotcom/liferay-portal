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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class ResourceBlockFinderUtil {
	public static com.liferay.portal.security.permission.ResourceBlockIdsBag findByC_G_N_R(
		long companyId, long groupId, java.lang.String name, long[] roleIds) {
		return getFinder().findByC_G_N_R(companyId, groupId, name, roleIds);
	}

	public static ResourceBlockFinder getFinder() {
		if (_finder == null) {
			_finder = (ResourceBlockFinder)PortalBeanLocatorUtil.locate(ResourceBlockFinder.class.getName());

			ReferenceRegistry.registerReference(ResourceBlockFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ResourceBlockFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ResourceBlockFinderUtil.class,
			"_finder");
	}

	private static ResourceBlockFinder _finder;
}