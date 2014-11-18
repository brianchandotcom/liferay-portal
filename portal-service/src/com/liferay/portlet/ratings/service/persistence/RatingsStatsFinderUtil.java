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

package com.liferay.portlet.ratings.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class RatingsStatsFinderUtil {
	public static java.util.Map<java.io.Serializable, com.liferay.portlet.ratings.model.RatingsStats> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getFinder().fetchByPrimaryKeys(primaryKeys);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsStats> findByC_C(
		long classNameId, java.util.List<java.lang.Long> classPKs) {
		return getFinder().findByC_C(classNameId, classPKs);
	}

	public static RatingsStatsFinder getFinder() {
		if (_finder == null) {
			_finder = (RatingsStatsFinder)PortalBeanLocatorUtil.locate(RatingsStatsFinder.class.getName());

			ReferenceRegistry.registerReference(RatingsStatsFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(RatingsStatsFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(RatingsStatsFinderUtil.class,
			"_finder");
	}

	private static RatingsStatsFinder _finder;
}