/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.model.listener;

import com.liferay.commerce.price.list.internal.helper.CommerceBasePriceListHelper;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
			CommerceCatalog commerceCatalog)
		throws ModelListenerException {

		if (originalCommerceCatalog.getAccountEntryId() ==
				commerceCatalog.getAccountEntryId()) {

			return;
		}

		try {
			_reindexCPInstances(commerceCatalog);
			_reindexPriceLists(commerceCatalog);
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
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

	private void _reindexCPInstances(CommerceCatalog commerceCatalog)
		throws PortalException {

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		List<CPInstance> cpInstances = _cpInstanceLocalService.getCPInstances(
			commerceCatalog.getGroupId(), WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			indexer.reindex(cpInstance);
		}
	}

	private void _reindexPriceLists(CommerceCatalog commerceCatalog)
		throws PortalException {

		List<CommercePriceList> commercePriceLists =
			_commercePriceListLocalService.getCommercePriceLists(
				new long[] {commerceCatalog.getGroupId()},
				commerceCatalog.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (commercePriceLists.isEmpty()) {
			return;
		}

		Indexer<CommercePriceList> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceList.class);

		for (CommercePriceList commercePriceList : commercePriceLists) {
			indexer.reindex(
				CommercePriceList.class.getName(),
				commercePriceList.getCommercePriceListId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCatalogModelListener.class);

	@Reference
	private CommerceBasePriceListHelper _commerceBasePriceListHelper;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}