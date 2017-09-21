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

package com.liferay.commerce.internal.exportimport.staged.model.repository;

import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.service.CommerceInventoryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.model.CommerceInventory",
	service = StagedModelRepository.class
)
public class CommerceInventoryStagedModelRepository
	extends BaseStagedModelRepository<CommerceInventory> {

	@Override
	public CommerceInventory addStagedModel(
			PortletDataContext portletDataContext,
			CommerceInventory commerceInventory)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceInventory);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceInventory.getUuid());
		}

		return _commerceInventoryLocalService.addCommerceInventory(
			commerceInventory.getCPDefinitionId(),
			commerceInventory.getCommerceInventoryEngine(),
			commerceInventory.getLowStockActivity(),
			commerceInventory.getDisplayAvailability(),
			commerceInventory.getDisplayStockQuantity(),
			commerceInventory.getMinStockQuantity(),
			commerceInventory.getBackOrders(),
			commerceInventory.getMinCartQuantity(),
			commerceInventory.getMaxCartQuantity(),
			commerceInventory.getAllowedCartQuantities(),
			commerceInventory.getMultipleCartQuantity(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommerceInventory commerceInventory)
		throws PortalException {

		_commerceInventoryLocalService.deleteCommerceInventory(
			commerceInventory);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceInventory commerceInventory = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (commerceInventory != null) {
			deleteStagedModel(commerceInventory);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommerceInventory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceInventoryLocalService.
			fetchCommerceInventoryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommerceInventory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceInventoryLocalService.
			getCommerceInventoriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CommerceInventory>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commerceInventoryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CommerceInventory saveStagedModel(
			CommerceInventory commerceInventory)
		throws PortalException {

		return _commerceInventoryLocalService.updateCommerceInventory(
			commerceInventory);
	}

	@Override
	public CommerceInventory updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceInventory commerceInventory)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceInventory);

		return _commerceInventoryLocalService.updateCommerceInventory(
			commerceInventory.getCommerceInventoryId(),
			commerceInventory.getCommerceInventoryEngine(),
			commerceInventory.getLowStockActivity(),
			commerceInventory.getDisplayAvailability(),
			commerceInventory.getDisplayStockQuantity(),
			commerceInventory.getMinStockQuantity(),
			commerceInventory.getBackOrders(),
			commerceInventory.getMinCartQuantity(),
			commerceInventory.getMaxCartQuantity(),
			commerceInventory.getAllowedCartQuantities(),
			commerceInventory.getMultipleCartQuantity(), serviceContext);
	}

	@Reference
	private CommerceInventoryLocalService _commerceInventoryLocalService;

}