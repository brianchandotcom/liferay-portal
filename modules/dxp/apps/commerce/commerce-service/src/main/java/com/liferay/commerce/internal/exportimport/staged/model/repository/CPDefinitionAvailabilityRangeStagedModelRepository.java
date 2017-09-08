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

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CPDefinitionAvailabilityRange",
	service = StagedModelRepository.class
)
public class CPDefinitionAvailabilityRangeStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionAvailabilityRange> {

	@Override
	public CPDefinitionAvailabilityRange addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionAvailabilityRange);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionAvailabilityRange.getUuid());
		}

		return _cpDefinitionAvailabilityRangeLocalService.
			updateCPDefinitionAvailabilityRange(
				cpDefinitionAvailabilityRange.
					getCPDefinitionAvailabilityRangeId(),
				cpDefinitionAvailabilityRange.getCPDefinitionId(),
				cpDefinitionAvailabilityRange.getCommerceAvailabilityRangeId(),
				serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
		throws PortalException {

		_cpDefinitionAvailabilityRangeLocalService.
			deleteCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRange);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionAvailabilityRange != null) {
			deleteStagedModel(cpDefinitionAvailabilityRange);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpDefinitionAvailabilityRangeLocalService.
			deleteCPDefinitionAvailabilityRanges(
				portletDataContext.getScopeGroupId());
	}

	@Override
	public CPDefinitionAvailabilityRange fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionAvailabilityRangeLocalService.
			fetchCPDefinitionAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPDefinitionAvailabilityRange>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return _cpDefinitionAvailabilityRangeLocalService.
			getCPDefinitionAvailabilityRangesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CPDefinitionAvailabilityRange>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionAvailabilityRangeLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CPDefinitionAvailabilityRange saveStagedModel(
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
		throws PortalException {

		return _cpDefinitionAvailabilityRangeLocalService.
			updateCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRange);
	}

	@Override
	public CPDefinitionAvailabilityRange updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionAvailabilityRange);

		return _cpDefinitionAvailabilityRangeLocalService.
			updateCPDefinitionAvailabilityRange(
				cpDefinitionAvailabilityRange.
					getCPDefinitionAvailabilityRangeId(),
				cpDefinitionAvailabilityRange.getCPDefinitionId(),
				cpDefinitionAvailabilityRange.getCommerceAvailabilityRangeId(),
				serviceContext);
	}

	@Reference
	private CPDefinitionAvailabilityRangeLocalService
		_cpDefinitionAvailabilityRangeLocalService;

}