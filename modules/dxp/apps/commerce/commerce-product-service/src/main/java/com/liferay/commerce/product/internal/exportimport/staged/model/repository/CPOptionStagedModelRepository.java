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

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPOption"},
	service = StagedModelRepository.class
)
public class CPOptionStagedModelRepository
	extends BaseStagedModelRepository<CPOption> {

	@Override
	public CPOption addStagedModel(
			PortletDataContext portletDataContext, CPOption cpOption)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpOption);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpOption.getUuid());
		}

		return _cpOptionLocalService.addCPOption(
			cpOption.getName(), cpOption.getTitleMap(),
			cpOption.getDescriptionMap(), cpOption.getDDMFormFieldTypeName(),
			cpOption.isFacetable(), cpOption.isRequired(),
			cpOption.isSkuContributor(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CPOption cpOption) throws PortalException {
		_cpOptionLocalService.deleteCPOption(cpOption);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPOption cpOption = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpOption != null) {
			deleteStagedModel(cpOption);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpOptionLocalService.deleteCPOptions(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CPOption fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpOptionLocalService.fetchCPOptionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<CPOption> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpOptionLocalService.getCPOptionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpOptionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CPOption saveStagedModel(CPOption cpOption) throws PortalException {
		return _cpOptionLocalService.updateCPOption(cpOption);
	}

	@Override
	public CPOption updateStagedModel(
			PortletDataContext portletDataContext, CPOption cpOption)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpOption);

		return _cpOptionLocalService.updateCPOption(
			cpOption.getCPOptionId(), cpOption.getName(),
			cpOption.getTitleMap(), cpOption.getDescriptionMap(),
			cpOption.getDDMFormFieldTypeName(), cpOption.isFacetable(),
			cpOption.isRequired(), cpOption.isSkuContributor(), serviceContext);
	}

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

}