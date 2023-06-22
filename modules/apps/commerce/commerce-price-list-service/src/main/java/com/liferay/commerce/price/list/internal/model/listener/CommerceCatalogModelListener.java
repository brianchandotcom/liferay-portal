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

package com.liferay.commerce.price.list.internal.model.listener;

import com.liferay.commerce.price.list.internal.helper.CommerceBasePriceListHelper;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelListener.class)
public class CommerceCatalogModelListener
	extends BaseModelListener<CommerceCatalog> {

	@Override
	public void onAfterCreate(CommerceCatalog commerceCatalog) {
		try {
			_commerceBasePriceListHelper.addCatalogBaseCommercePriceList(
				commerceCatalog);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	@Override
	public void onAfterUpdate(
		CommerceCatalog originalCommerceCatalog,
		CommerceCatalog commerceCatalog) {

		try {
			_reindexPriceLists(commerceCatalog);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	@Override
	public void onBeforeRemove(CommerceCatalog commerceCatalog) {
		try {
			_commerceBasePriceListHelper.deleteCatalogBaseCommercePriceList(
				commerceCatalog);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private void _reindexPriceLists(CommerceCatalog commerceCatalog)
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			_commercePriceListLocalService.getCommercePriceLists(
				new long[] {commerceCatalog.getGroupId()},
				commerceCatalog.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (!commercePriceLists.isEmpty()) {
			Indexer<CommercePriceList> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

			for (CommercePriceList commercePriceList : commercePriceLists) {
				indexer.reindex(
					CommercePriceList.class.getName(),
					commercePriceList.getCommercePriceListId());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCatalogModelListener.class);

	@Reference
	private CommerceBasePriceListHelper _commerceBasePriceListHelper;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}