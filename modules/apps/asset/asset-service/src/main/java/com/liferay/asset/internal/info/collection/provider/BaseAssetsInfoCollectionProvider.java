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

package com.liferay.asset.internal.info.collection.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseAssetsInfoCollectionProvider {

	protected AssetEntryQuery getAssetEntryQuery(
		long companyId, long groupId, Pagination pagination, Sort sort) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(
			AssetRendererFactoryRegistryUtil.getIndexableClassNameIds(
				companyId, true));
		assetEntryQuery.setEnablePermissions(true);
		assetEntryQuery.setGroupIds(new long[] {groupId});

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		assetEntryQuery.setOrderByCol1(
			(sort != null) ? sort.getFieldName() : Field.MODIFIED_DATE);
		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType1(
			(sort != null) ? _getOrderByType(sort) : "DESC");
		assetEntryQuery.setOrderByType2("DESC");

		return assetEntryQuery;
	}

	@Reference
	protected Portal portal;

	private String _getOrderByType(Sort sort) {
		if (sort.isReverse()) {
			return "DESC";
		}

		return "ASC";
	}

}