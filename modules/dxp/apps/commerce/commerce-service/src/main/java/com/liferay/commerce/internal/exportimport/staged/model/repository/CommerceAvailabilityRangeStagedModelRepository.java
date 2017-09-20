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

import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.CommerceAvailabilityRangeLocalService;
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
	property = "model.class.name=com.liferay.commerce.model.CommerceAvailabilityRange",
	service = StagedModelRepository.class
)
public class CommerceAvailabilityRangeStagedModelRepository
	extends BaseStagedModelRepository<CommerceAvailabilityRange> {

	@Override
	public CommerceAvailabilityRange addStagedModel(
			PortletDataContext portletDataContext,
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceAvailabilityRange);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceAvailabilityRange.getUuid());
		}

		return
			_commerceAvailabilityRangeLocalService.addCommerceAvailabilityRange(
				commerceAvailabilityRange.getTitleMap(),
				commerceAvailabilityRange.getPriority(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws PortalException {

		_commerceAvailabilityRangeLocalService.deleteCommerceAvailabilityRange(
			commerceAvailabilityRange);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceAvailabilityRange commerceAvailabilityRange =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (commerceAvailabilityRange != null) {
			deleteStagedModel(commerceAvailabilityRange);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_commerceAvailabilityRangeLocalService.deleteCommerceAvailabilityRanges(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CommerceAvailabilityRange fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceAvailabilityRangeLocalService.
			fetchCommerceAvailabilityRangeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommerceAvailabilityRange> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceAvailabilityRangeLocalService.
			getCommerceAvailabilityRangesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CommerceAvailabilityRange>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commerceAvailabilityRangeLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CommerceAvailabilityRange saveStagedModel(
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws PortalException {

		return _commerceAvailabilityRangeLocalService.
			updateCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	@Override
	public CommerceAvailabilityRange updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceAvailabilityRange);

		return _commerceAvailabilityRangeLocalService.
			updateCommerceAvailabilityRange(
				commerceAvailabilityRange.getCommerceAvailabilityRangeId(),
				commerceAvailabilityRange.getTitleMap(),
				commerceAvailabilityRange.getPriority(), serviceContext);
	}

	@Reference
	private CommerceAvailabilityRangeLocalService
		_commerceAvailabilityRangeLocalService;

}