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

package com.liferay.commerce.product.type.grouped.internal.exportimport.staged.model.repository;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry"},
	service = StagedModelRepository.class
)
public class CPDefinitionGroupedEntryStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionGroupedEntry> {

	@Override
	public CPDefinitionGroupedEntry addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionGroupedEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionGroupedEntry.getUuid());
		}

		return
			_cpDefinitionGroupedEntryLocalService.addCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntry.getCPDefinitionId(),
				cpDefinitionGroupedEntry.getEntryCPDefinitionId(),
				cpDefinitionGroupedEntry.getPriority(),
				cpDefinitionGroupedEntry.getQuantity(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry)
		throws PortalException {

		_cpDefinitionGroupedEntryLocalService.deleteCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionGroupedEntry != null) {
			deleteStagedModel(cpDefinitionGroupedEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionGroupedEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionGroupedEntryLocalService.
			fetchCPDefinitionGroupedEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPDefinitionGroupedEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionGroupedEntryLocalService.
			getCPDefinitionGroupedEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CPDefinitionGroupedEntry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionGroupedEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CPDefinitionGroupedEntry saveStagedModel(
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry)
		throws PortalException {

		return _cpDefinitionGroupedEntryLocalService.
			updateCPDefinitionGroupedEntry(cpDefinitionGroupedEntry);
	}

	@Override
	public CPDefinitionGroupedEntry updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry)
		throws PortalException {

		return _cpDefinitionGroupedEntryLocalService.
			updateCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId(),
				cpDefinitionGroupedEntry.getPriority(),
				cpDefinitionGroupedEntry.getQuantity());
	}

	@Reference
	private CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}