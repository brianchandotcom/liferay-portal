/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileRankFinderUtil {
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByFolder(folderId);
	}

	public static java.util.List<java.lang.Object[]> findByStaleRanks(int count)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByStaleRanks(count);
	}

	public static DLFileRankFinder getFinder() {
		if (_finder == null) {
			_finder = (DLFileRankFinder)PortalBeanLocatorUtil.locate(DLFileRankFinder.class.getName());

			ReferenceRegistry.registerReference(DLFileRankFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DLFileRankFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DLFileRankFinderUtil.class,
			"_finder");
	}

	private static DLFileRankFinder _finder;
}