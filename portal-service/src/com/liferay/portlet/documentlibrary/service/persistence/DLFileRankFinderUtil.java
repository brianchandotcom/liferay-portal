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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class DLFileRankFinderUtil {
	public static java.util.List<java.lang.Object[]> findByStaleRanks(int count) {
		return getFinder().findByStaleRanks(count);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> findByFolderId(
		long folderId) {
		return getFinder().findByFolderId(folderId);
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