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

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPOptionCategory"},
	service = StagedModelRepository.class
)
public class CPOptionCategoryStagedModelRepository
	extends BaseStagedModelRepository<CPOptionCategory> {

	@Override
	public CPOptionCategory addStagedModel(
			PortletDataContext portletDataContext,
			CPOptionCategory cpOptionCategory)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpOptionCategory);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpOptionCategory.getUuid());
		}

		return _cpOptionCategoryLocalService.addCPOptionCategory(
			cpOptionCategory.getTitleMap(),
			cpOptionCategory.getDescriptionMap(),
			cpOptionCategory.getPriority(), cpOptionCategory.getKey(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(CPOptionCategory cpOptionCategory)
		throws PortalException {

		_cpOptionCategoryLocalService.deleteCPOptionCategory(cpOptionCategory);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPOptionCategory cpOptionCategory = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (cpOptionCategory != null) {
			deleteStagedModel(cpOptionCategory);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_cpOptionCategoryLocalService.deleteCPOptionCategories(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CPOptionCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpOptionCategoryLocalService.
			fetchCPOptionCategoryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPOptionCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpOptionCategoryLocalService.
			getCPOptionCategoriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpOptionCategoryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CPOptionCategory saveStagedModel(CPOptionCategory cpOptionCategory)
		throws PortalException {

		return _cpOptionCategoryLocalService.updateCPOptionCategory(
			cpOptionCategory);
	}

	@Override
	public CPOptionCategory updateStagedModel(
			PortletDataContext portletDataContext,
			CPOptionCategory cpOptionCategory)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpOptionCategory);

		return _cpOptionCategoryLocalService.updateCPOptionCategory(
			cpOptionCategory.getCPOptionCategoryId(),
			cpOptionCategory.getTitleMap(),
			cpOptionCategory.getDescriptionMap(),
			cpOptionCategory.getPriority(), cpOptionCategory.getKey(),
			serviceContext);
	}

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

}