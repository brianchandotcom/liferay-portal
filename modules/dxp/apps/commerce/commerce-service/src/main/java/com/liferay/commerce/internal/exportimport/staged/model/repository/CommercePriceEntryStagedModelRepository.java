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

import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.service.CommercePriceEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CommercePriceEntry",
	service = StagedModelRepository.class
)
public class CommercePriceEntryStagedModelRepository
	extends BaseStagedModelRepository<CommercePriceEntry> {

	@Override
	public CommercePriceEntry addStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commercePriceEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commercePriceEntry.getUuid());
		}

		return _commercePriceEntryLocalService.addCommercePriceEntry(
			commercePriceEntry.getCPInstanceId(),
			commercePriceEntry.getCommercePriceListId(),
			commercePriceEntry.getPrice(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommercePriceEntry commercePriceEntry)
		throws PortalException {

		_commercePriceEntryLocalService.deleteCommercePriceEntry(
			commercePriceEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (commercePriceEntry != null) {
			deleteStagedModel(commercePriceEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommercePriceEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commercePriceEntryLocalService.
			fetchCommercePriceEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommercePriceEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commercePriceEntryLocalService.
			getCommercePriceEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CommercePriceEntry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commercePriceEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CommercePriceEntry saveStagedModel(
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		return _commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntry);
	}

	@Override
	public CommercePriceEntry updateStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commercePriceEntry);

		return _commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntry.getCommercePriceEntryId(),
			commercePriceEntry.getPrice(), serviceContext);
	}

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

}