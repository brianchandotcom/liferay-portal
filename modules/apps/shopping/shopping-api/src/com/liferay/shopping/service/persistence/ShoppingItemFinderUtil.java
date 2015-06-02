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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ShoppingItemFinderUtil {
	public static int countByG_C(long groupId,
		java.util.List<java.lang.Long> categoryIds) {
		return getFinder().countByG_C(groupId, categoryIds);
	}

	public static int countByFeatured(long groupId, long[] categoryIds) {
		return getFinder().countByFeatured(groupId, categoryIds);
	}

	public static int countByKeywords(long groupId, long[] categoryIds,
		java.lang.String keywords) {
		return getFinder().countByKeywords(groupId, categoryIds, keywords);
	}

	public static int countBySale(long groupId, long[] categoryIds) {
		return getFinder().countBySale(groupId, categoryIds);
	}

	public static int filterCountByG_C(long groupId,
		java.util.List<java.lang.Long> categoryIds) {
		return getFinder().filterCountByG_C(groupId, categoryIds);
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingItem> findByFeatured(
		long groupId, long[] categoryIds, int numOfItems) {
		return getFinder().findByFeatured(groupId, categoryIds, numOfItems);
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingItem> findByKeywords(
		long groupId, long[] categoryIds, java.lang.String keywords, int start,
		int end) {
		return getFinder()
				   .findByKeywords(groupId, categoryIds, keywords, start, end);
	}

	public static java.util.List<com.liferay.shopping.model.ShoppingItem> findBySale(
		long groupId, long[] categoryIds, int numOfItems) {
		return getFinder().findBySale(groupId, categoryIds, numOfItems);
	}

	public static ShoppingItemFinder getFinder() {
		if (_finder == null) {
			_finder = (ShoppingItemFinder)PortalBeanLocatorUtil.locate(ShoppingItemFinder.class.getName());

			ReferenceRegistry.registerReference(ShoppingItemFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ShoppingItemFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ShoppingItemFinderUtil.class,
			"_finder");
	}

	private static ShoppingItemFinder _finder;
}