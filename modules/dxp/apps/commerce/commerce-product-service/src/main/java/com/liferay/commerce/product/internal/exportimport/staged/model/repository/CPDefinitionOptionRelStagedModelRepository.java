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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionRel"},
	service = StagedModelRepository.class
)
public class CPDefinitionOptionRelStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionOptionRel> {

	@Override
	public CPDefinitionOptionRel addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionOptionRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionOptionRel.getUuid());
		}

		return _cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionOptionRel.getCPDefinitionId(),
			cpDefinitionOptionRel.getCPOptionId(),
			cpDefinitionOptionRel.getTitleMap(),
			cpDefinitionOptionRel.getDescriptionMap(),
			cpDefinitionOptionRel.getDDMFormFieldTypeName(),
			cpDefinitionOptionRel.getPriority(),
			cpDefinitionOptionRel.isFacetable(),
			cpDefinitionOptionRel.isRequired(),
			cpDefinitionOptionRel.isSkuContributor(), false, serviceContext);
	}

	@Override
	public void deleteStagedModel(CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		_cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionOptionRel != null) {
			deleteStagedModel(cpDefinitionOptionRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionOptionRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionOptionRelLocalService.
			fetchCPDefinitionOptionRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPDefinitionOptionRel> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionOptionRelLocalService.
			getCPDefinitionOptionRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CPDefinitionOptionRel>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionOptionRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CPDefinitionOptionRel saveStagedModel(
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		return _cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel);
	}

	@Override
	public CPDefinitionOptionRel updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionOptionRel);

		return _cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
			cpDefinitionOptionRel.getCPOptionId(),
			cpDefinitionOptionRel.getTitleMap(),
			cpDefinitionOptionRel.getDescriptionMap(),
			cpDefinitionOptionRel.getDDMFormFieldTypeName(),
			cpDefinitionOptionRel.getPriority(),
			cpDefinitionOptionRel.isFacetable(),
			cpDefinitionOptionRel.isRequired(),
			cpDefinitionOptionRel.isSkuContributor(), serviceContext);
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

}