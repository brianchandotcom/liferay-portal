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

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPOptionValue"},
	service = StagedModelRepository.class
)
public class CPOptionValueStagedModelRepository
	extends BaseStagedModelRepository<CPOptionValue> {

	@Override
	public CPOptionValue addStagedModel(
			PortletDataContext portletDataContext, CPOptionValue cpOptionValue)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpOptionValue);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpOptionValue.getUuid());
		}

		return _cpOptionValueLocalService.addCPOptionValue(
			cpOptionValue.getCPOptionId(), cpOptionValue.getTitleMap(),
			cpOptionValue.getPriority(), cpOptionValue.getKey(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(CPOptionValue cpOptionValue)
		throws PortalException {

		_cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPOptionValue cpOptionValue = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (cpOptionValue != null) {
			deleteStagedModel(cpOptionValue);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPOptionValue fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpOptionValueLocalService.fetchCPOptionValueByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<CPOptionValue> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpOptionValueLocalService.getCPOptionValuesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpOptionValueLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CPOptionValue saveStagedModel(CPOptionValue cpOptionValue)
		throws PortalException {

		return _cpOptionValueLocalService.updateCPOptionValue(cpOptionValue);
	}

	@Override
	public CPOptionValue updateStagedModel(
			PortletDataContext portletDataContext, CPOptionValue cpOptionValue)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpOptionValue);

		return _cpOptionValueLocalService.updateCPOptionValue(
			cpOptionValue.getCPOptionValueId(), cpOptionValue.getTitleMap(),
			cpOptionValue.getPriority(), cpOptionValue.getKey(),
			serviceContext);
	}

	@Reference
	private CPOptionValueLocalService _cpOptionValueLocalService;

}