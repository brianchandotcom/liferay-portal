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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseAssetsInfoCollectionProvider {

	protected AssetEntryQuery getAssetEntryQuery(
		String orderByCol, String orderByType, Pagination pagination) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				serviceContext.getCompanyId(), true);

		availableClassNameIds = ArrayUtil.filter(
			availableClassNameIds,
			availableClassNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					portal.getClassName(availableClassNameId));

				if (indexer == null) {
					return false;
				}

				return true;
			});

		assetEntryQuery.setClassNameIds(availableClassNameIds);

		assetEntryQuery.setEnablePermissions(true);
		assetEntryQuery.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		assetEntryQuery.setOrderByCol1(orderByCol);
		assetEntryQuery.setOrderByType1(orderByType);

		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType2("DESC");

		return assetEntryQuery;
	}

	@Reference
	protected Portal portal;

}