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

package com.liferay.commerce.product.internal.exportimport.staged.model.repository;

import com.liferay.commerce.product.model.CPAvailabilityRange;
import com.liferay.commerce.product.service.CPAvailabilityRangeLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.product.model.CPAvailabilityRange",
	service = StagedModelRepository.class
)
public class CPAvailabilityRangeStagedModelRepository
	extends BaseStagedModelRepository<CPAvailabilityRange> {

	@Override
	public CPAvailabilityRange addStagedModel(
			PortletDataContext portletDataContext,
			CPAvailabilityRange cpAvailabilityRange)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpAvailabilityRange);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpAvailabilityRange.getUuid());
		}

		return _cpAvailabilityRangeLocalService.addCPAvailabilityRange(
			cpAvailabilityRange.getCPDefinitionId(),
			cpAvailabilityRange.getTitleMap(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CPAvailabilityRange cpAvailabilityRange)
		throws PortalException {

		_cpAvailabilityRangeLocalService.deleteCPAvailabilityRange(
			cpAvailabilityRange);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPAvailabilityRange cpAvailabilityRange = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (cpAvailabilityRange != null) {
			deleteStagedModel(cpAvailabilityRange);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpAvailabilityRangeLocalService.deleteCPAvailabilityRanges(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CPAvailabilityRange fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpAvailabilityRangeLocalService.
			fetchCPAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPAvailabilityRange> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpAvailabilityRangeLocalService.
			getCPAvailabilityRangesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CPAvailabilityRange>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpAvailabilityRangeLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CPAvailabilityRange saveStagedModel(
			CPAvailabilityRange cpAvailabilityRange)
		throws PortalException {

		return _cpAvailabilityRangeLocalService.updateCPAvailabilityRange(
			cpAvailabilityRange);
	}

	@Override
	public CPAvailabilityRange updateStagedModel(
			PortletDataContext portletDataContext,
			CPAvailabilityRange cpAvailabilityRange)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpAvailabilityRange);

		return _cpAvailabilityRangeLocalService.updateCPAvailabilityRange(
			cpAvailabilityRange.getCPAvailabilityRangeId(),
			cpAvailabilityRange.getTitleMap(), serviceContext);
	}

	@Reference
	private CPAvailabilityRangeLocalService _cpAvailabilityRangeLocalService;

}