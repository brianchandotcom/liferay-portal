/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetBranchFinderUtil {
	public static com.liferay.portal.model.LayoutSetBranch findByMaster(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetBranchException,
			com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByMaster(groupId, privateLayout);
	}

	public static LayoutSetBranchFinder getFinder() {
		if (_finder == null) {
			_finder = (LayoutSetBranchFinder)PortalBeanLocatorUtil.locate(LayoutSetBranchFinder.class.getName());

			ReferenceRegistry.registerReference(LayoutSetBranchFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(LayoutSetBranchFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(LayoutSetBranchFinderUtil.class,
			"_finder");
	}

	private static LayoutSetBranchFinder _finder;
}