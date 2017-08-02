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

package com.liferay.commerce.address.internal.exportimport.staged.model.repository;

import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.service.CommerceRegionLocalService;
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
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.address.model.CommerceRegion",
	service = StagedModelRepository.class
)
public class CommerceRegionStagedModelRepository
	extends BaseStagedModelRepository<CommerceRegion> {

	@Override
	public CommerceRegion addStagedModel(
			PortletDataContext portletDataContext,
			CommerceRegion commerceRegion)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceRegion);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceRegion.getUuid());
		}

		return _commerceRegionLocalService.addCommerceRegion(
			commerceRegion.getCommerceCountryId(), commerceRegion.getName(),
			commerceRegion.getCode(), commerceRegion.getPriority(),
			commerceRegion.isActive(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommerceRegion commerceRegion)
		throws PortalException {

		_commerceRegionLocalService.deleteCommerceRegion(commerceRegion);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceRegion commerceRegion = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (commerceRegion != null) {
			deleteStagedModel(commerceRegion);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommerceRegion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceRegionLocalService.fetchCommerceRegionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<CommerceRegion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceRegionLocalService.getCommerceRegionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<CommerceRegion>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commerceRegionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CommerceRegion saveStagedModel(CommerceRegion commerceRegion)
		throws PortalException {

		return _commerceRegionLocalService.updateCommerceRegion(commerceRegion);
	}

	@Override
	public CommerceRegion updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceRegion commerceRegion)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceRegion);

		return _commerceRegionLocalService.updateCommerceRegion(
			commerceRegion.getCommerceRegionId(), commerceRegion.getName(),
			commerceRegion.getCode(), commerceRegion.getPriority(),
			commerceRegion.isActive(), serviceContext);
	}

	@Reference
	private CommerceRegionLocalService _commerceRegionLocalService;

}