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

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPMeasurementUnit",
	service = StagedModelRepository.class
)
public class CPMeasurementUnitStagedModelRepository
	extends BaseStagedModelRepository<CPMeasurementUnit> {

	@Override
	public CPMeasurementUnit addStagedModel(
			PortletDataContext portletDataContext,
			CPMeasurementUnit cpMeasurementUnit)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpMeasurementUnit);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpMeasurementUnit.getUuid());
		}

		return _cpMeasurementUnitLocalService.addCPMeasurementUnit(
			cpMeasurementUnit.getNameMap(), cpMeasurementUnit.getKey(),
			cpMeasurementUnit.getRate(), cpMeasurementUnit.isPrimary(),
			cpMeasurementUnit.getPriority(), cpMeasurementUnit.getType(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(CPMeasurementUnit cpMeasurementUnit)
		throws PortalException {

		_cpMeasurementUnitLocalService.deleteCPMeasurementUnit(
			cpMeasurementUnit);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPMeasurementUnit cpMeasurementUnit = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (cpMeasurementUnit != null) {
			deleteStagedModel(cpMeasurementUnit);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpMeasurementUnitLocalService.deleteCPMeasurementUnits(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CPMeasurementUnit fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpMeasurementUnitLocalService.
			fetchCPMeasurementUnitByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPMeasurementUnit> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpMeasurementUnitLocalService.
			getCPMeasurementUnitsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CPMeasurementUnit>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpMeasurementUnitLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CPMeasurementUnit saveStagedModel(
			CPMeasurementUnit cpMeasurementUnit)
		throws PortalException {

		return _cpMeasurementUnitLocalService.updateCPMeasurementUnit(
			cpMeasurementUnit);
	}

	@Override
	public CPMeasurementUnit updateStagedModel(
			PortletDataContext portletDataContext,
			CPMeasurementUnit cpMeasurementUnit)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpMeasurementUnit);

		return _cpMeasurementUnitLocalService.updateCPMeasurementUnit(
			cpMeasurementUnit.getCPMeasurementUnitId(),
			cpMeasurementUnit.getNameMap(), cpMeasurementUnit.getKey(),
			cpMeasurementUnit.getRate(), cpMeasurementUnit.isPrimary(),
			cpMeasurementUnit.getPriority(), cpMeasurementUnit.getType(),
			serviceContext);
	}

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

}