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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetVocabularyFinderUtil {
	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_N(groupId, name);
	}

	public static int filterCountByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().filterCountByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> filterFindByG_N(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getFinder().filterFindByG_N(groupId, name, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByG_N(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException {
		return getFinder().findByG_N(groupId, name, start, end, obc);
	}

	public static AssetVocabularyFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetVocabularyFinder)PortalBeanLocatorUtil.locate(AssetVocabularyFinder.class.getName());

			ReferenceRegistry.registerReference(AssetVocabularyFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetVocabularyFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetVocabularyFinderUtil.class,
			"_finder");
	}

	private static AssetVocabularyFinder _finder;
}