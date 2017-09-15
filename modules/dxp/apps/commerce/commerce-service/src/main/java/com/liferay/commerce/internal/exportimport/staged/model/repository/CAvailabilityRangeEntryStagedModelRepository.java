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

import com.liferay.commerce.model.CAvailabilityRangeEntry;
import com.liferay.commerce.service.CAvailabilityRangeEntryLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CAvailabilityRangeEntry",
	service = StagedModelRepository.class
)
public class CAvailabilityRangeEntryStagedModelRepository
	extends BaseStagedModelRepository<CAvailabilityRangeEntry> {

	@Override
	public CAvailabilityRangeEntry addStagedModel(
			PortletDataContext portletDataContext,
			CAvailabilityRangeEntry cAvailabilityRangeEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cAvailabilityRangeEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cAvailabilityRangeEntry.getUuid());
		}

		return
			_cAvailabilityRangeEntryLocalService.updateCAvailabilityRangeEntry(
				cAvailabilityRangeEntry.getCAvailabilityRangeEntryId(),
				cAvailabilityRangeEntry.getCPDefinitionId(),
				cAvailabilityRangeEntry.getCommerceAvailabilityRangeId(),
				serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CAvailabilityRangeEntry cAvailabilityRangeEntry)
		throws PortalException {

		_cAvailabilityRangeEntryLocalService.deleteCAvailabilityRangeEntry(
			cAvailabilityRangeEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CAvailabilityRangeEntry cAvailabilityRangeEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cAvailabilityRangeEntry != null) {
			deleteStagedModel(cAvailabilityRangeEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cAvailabilityRangeEntryLocalService.deleteCAvailabilityRangeEntries(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CAvailabilityRangeEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cAvailabilityRangeEntryLocalService.
			fetchCAvailabilityRangeEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CAvailabilityRangeEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cAvailabilityRangeEntryLocalService.
			getCAvailabilityRangeEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CAvailabilityRangeEntry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cAvailabilityRangeEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CAvailabilityRangeEntry saveStagedModel(
			CAvailabilityRangeEntry cAvailabilityRangeEntry)
		throws PortalException {

		return
			_cAvailabilityRangeEntryLocalService.updateCAvailabilityRangeEntry(
				cAvailabilityRangeEntry);
	}

	@Override
	public CAvailabilityRangeEntry updateStagedModel(
			PortletDataContext portletDataContext,
			CAvailabilityRangeEntry cAvailabilityRangeEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cAvailabilityRangeEntry);

		return
			_cAvailabilityRangeEntryLocalService.updateCAvailabilityRangeEntry(
				cAvailabilityRangeEntry.getCAvailabilityRangeEntryId(),
				cAvailabilityRangeEntry.getCPDefinitionId(),
				cAvailabilityRangeEntry.getCommerceAvailabilityRangeId(),
				serviceContext);
	}

	@Reference
	private CAvailabilityRangeEntryLocalService
		_cAvailabilityRangeEntryLocalService;

}