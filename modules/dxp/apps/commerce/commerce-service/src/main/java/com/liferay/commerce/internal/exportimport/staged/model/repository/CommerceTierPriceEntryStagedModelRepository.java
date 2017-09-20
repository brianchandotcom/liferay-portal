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

import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.service.CommerceTierPriceEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CommerceTierPriceEntry",
	service = StagedModelRepository.class
)
public class CommerceTierPriceEntryStagedModelRepository
	extends BaseStagedModelRepository<CommerceTierPriceEntry> {

	@Override
	public CommerceTierPriceEntry addStagedModel(
			PortletDataContext portletDataContext,
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceTierPriceEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceTierPriceEntry.getUuid());
		}

		return _commerceTierPriceEntryLocalService.addCommerceTierPriceEntry(
			commerceTierPriceEntry.getCommercePriceEntryId(),
			commerceTierPriceEntry.getPrice(),
			commerceTierPriceEntry.getMinQuantity(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		_commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(
			commerceTierPriceEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (commerceTierPriceEntry != null) {
			deleteStagedModel(commerceTierPriceEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommerceTierPriceEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceTierPriceEntryLocalService.
			fetchCommerceTierPriceEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommerceTierPriceEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceTierPriceEntryLocalService.
			getCommerceTierPriceEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CommerceTierPriceEntry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return
			_commerceTierPriceEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);
	}

	@Override
	public CommerceTierPriceEntry saveStagedModel(
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		return _commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(
			commerceTierPriceEntry);
	}

	@Override
	public CommerceTierPriceEntry updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceTierPriceEntry);

		return _commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(
			commerceTierPriceEntry.getCommerceTierPriceEntryId(),
			commerceTierPriceEntry.getPrice(),
			commerceTierPriceEntry.getMinQuantity(), serviceContext);
	}

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

}