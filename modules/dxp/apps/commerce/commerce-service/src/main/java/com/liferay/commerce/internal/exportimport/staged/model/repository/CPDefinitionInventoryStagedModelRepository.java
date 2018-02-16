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

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CPDefinitionInventory",
	service = StagedModelRepository.class
)
public class CPDefinitionInventoryStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionInventory> {

	@Override
	public CPDefinitionInventory addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionInventory cpDefinitionInventory)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionInventory);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionInventory.getUuid());
		}

		return _cpDefinitionInventoryLocalService.addCPDefinitionInventory(
			cpDefinitionInventory.getCPDefinitionId(),
			cpDefinitionInventory.getCPDefinitionInventoryEngine(),
			cpDefinitionInventory.getLowStockActivity(),
			cpDefinitionInventory.getDisplayAvailability(),
			cpDefinitionInventory.getDisplayStockQuantity(),
			cpDefinitionInventory.getMinStockQuantity(),
			cpDefinitionInventory.getBackOrders(),
			cpDefinitionInventory.getMinOrderQuantity(),
			cpDefinitionInventory.getMaxOrderQuantity(),
			cpDefinitionInventory.getAllowedOrderQuantities(),
			cpDefinitionInventory.getMultipleOrderQuantity(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CPDefinitionInventory cpDefinitionInventory)
		throws PortalException {

		_cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(
			cpDefinitionInventory);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionInventory != null) {
			deleteStagedModel(cpDefinitionInventory);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionInventory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionInventoryLocalService.
			fetchCPDefinitionInventoryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPDefinitionInventory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionInventoryLocalService.
			getCPDefinitionInventoriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CPDefinitionInventory>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return
			_cpDefinitionInventoryLocalService.getExportActionableDynamicQuery(
				portletDataContext);
	}

	@Override
	public CPDefinitionInventory saveStagedModel(
			CPDefinitionInventory cpDefinitionInventory)
		throws PortalException {

		return _cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
			cpDefinitionInventory);
	}

	@Override
	public CPDefinitionInventory updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionInventory cpDefinitionInventory)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionInventory);

		return _cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
			cpDefinitionInventory.getCPDefinitionInventoryId(),
			cpDefinitionInventory.getCPDefinitionInventoryEngine(),
			cpDefinitionInventory.getLowStockActivity(),
			cpDefinitionInventory.getDisplayAvailability(),
			cpDefinitionInventory.getDisplayStockQuantity(),
			cpDefinitionInventory.getMinStockQuantity(),
			cpDefinitionInventory.getBackOrders(),
			cpDefinitionInventory.getMinOrderQuantity(),
			cpDefinitionInventory.getMaxOrderQuantity(),
			cpDefinitionInventory.getAllowedOrderQuantities(),
			cpDefinitionInventory.getMultipleOrderQuantity(), serviceContext);
	}

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}