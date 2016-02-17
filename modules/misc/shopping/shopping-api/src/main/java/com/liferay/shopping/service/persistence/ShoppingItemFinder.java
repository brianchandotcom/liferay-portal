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

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface ShoppingItemFinder {
	public int countByG_C(long groupId,
		java.util.List<java.lang.Long> categoryIds);

	public int countByFeatured(long groupId, long[] categoryIds);

	public int countByKeywords(long groupId, long[] categoryIds,
		java.lang.String keywords);

	public int countByKeywords(long groupId, long[] categoryIds,
		java.lang.String keywords,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem> obc);

	public int countBySale(long groupId, long[] categoryIds);

	public int filterCountByG_C(long groupId,
		java.util.List<java.lang.Long> categoryIds);

	public java.util.List<com.liferay.shopping.model.ShoppingItem> findByFeatured(
		long groupId, long[] categoryIds, int numOfItems);

	public java.util.List<com.liferay.shopping.model.ShoppingItem> findByKeywords(
		long groupId, long[] categoryIds, java.lang.String keywords, int start,
		int end);

	public java.util.List<com.liferay.shopping.model.ShoppingItem> findByKeywords(
		long groupId, long[] categoryIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem> obc);

	public java.util.List<com.liferay.shopping.model.ShoppingItem> findBySale(
		long groupId, long[] categoryIds, int numOfItems);
}