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

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPSpecificationOption",
	service = StagedModelRepository.class
)
public class CPSpecificationOptionStagedModelRepository
	extends BaseStagedModelRepository<CPSpecificationOption> {

	@Override
	public CPSpecificationOption addStagedModel(
			PortletDataContext portletDataContext,
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpSpecificationOption);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpSpecificationOption.getUuid());
		}

		return _cpSpecificationOptionLocalService.addCPSpecificationOption(
			cpSpecificationOption.getCPOptionCategoryId(),
			cpSpecificationOption.getTitleMap(),
			cpSpecificationOption.getDescriptionMap(),
			cpSpecificationOption.isFacetable(), cpSpecificationOption.getKey(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		_cpSpecificationOptionLocalService.deleteCPSpecificationOption(
			cpSpecificationOption);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpSpecificationOption != null) {
			deleteStagedModel(cpSpecificationOption);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpSpecificationOptionLocalService.deleteCPSpecificationOptions(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CPSpecificationOption fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpSpecificationOptionLocalService.
			fetchCPSpecificationOptionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPSpecificationOption> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpSpecificationOptionLocalService.
			getCPSpecificationOptionsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return
			_cpSpecificationOptionLocalService.getExportActionableDynamicQuery(
				portletDataContext);
	}

	@Override
	public CPSpecificationOption saveStagedModel(
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		return _cpSpecificationOptionLocalService.updateCPSpecificationOption(
			cpSpecificationOption);
	}

	@Override
	public CPSpecificationOption updateStagedModel(
			PortletDataContext portletDataContext,
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpSpecificationOption);

		return _cpSpecificationOptionLocalService.updateCPSpecificationOption(
			cpSpecificationOption.getCPSpecificationOptionId(),
			cpSpecificationOption.getCPOptionCategoryId(),
			cpSpecificationOption.getTitleMap(),
			cpSpecificationOption.getDescriptionMap(),
			cpSpecificationOption.isFacetable(), cpSpecificationOption.getKey(),
			serviceContext);
	}

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

}