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

import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.CommerceTirePriceEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CommerceTirePriceEntry",
	service = StagedModelRepository.class
)
public class CommerceTirePriceEntryStagedModelRepository
	extends BaseStagedModelRepository<CommerceTirePriceEntry> {

	@Override
	public CommerceTirePriceEntry addStagedModel(
			PortletDataContext portletDataContext,
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceTirePriceEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceTirePriceEntry.getUuid());
		}

		return _commerceTirePriceEntryLocalService.addCommerceTirePriceEntry(
			commerceTirePriceEntry.getCommercePriceEntryId(),
			commerceTirePriceEntry.getPrice(),
			commerceTirePriceEntry.getMinQuantity(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommerceTirePriceEntry commerceTirePriceEntry)
		throws PortalException {

		_commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntry(
			commerceTirePriceEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceTirePriceEntry commerceTirePriceEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (commerceTirePriceEntry != null) {
			deleteStagedModel(commerceTirePriceEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommerceTirePriceEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceTirePriceEntryLocalService.
			fetchCommerceTirePriceEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommerceTirePriceEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceTirePriceEntryLocalService.
			getCommerceTirePriceEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CommerceTirePriceEntry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return
			_commerceTirePriceEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);
	}

	@Override
	public CommerceTirePriceEntry saveStagedModel(
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws PortalException {

		return _commerceTirePriceEntryLocalService.updateCommerceTirePriceEntry(
			commerceTirePriceEntry);
	}

	@Override
	public CommerceTirePriceEntry updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceTirePriceEntry);

		return _commerceTirePriceEntryLocalService.updateCommerceTirePriceEntry(
			commerceTirePriceEntry.getCommerceTirePriceEntryId(),
			commerceTirePriceEntry.getPrice(),
			commerceTirePriceEntry.getMinQuantity(), serviceContext);
	}

	@Reference
	private CommerceTirePriceEntryLocalService
		_commerceTirePriceEntryLocalService;

}