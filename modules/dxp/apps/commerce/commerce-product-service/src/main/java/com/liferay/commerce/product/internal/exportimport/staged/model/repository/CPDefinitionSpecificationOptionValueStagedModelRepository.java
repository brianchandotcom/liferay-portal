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

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue",
	service = StagedModelRepository.class
)
public class CPDefinitionSpecificationOptionValueStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionSpecificationOptionValue> {

	@Override
	public CPDefinitionSpecificationOptionValue addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionSpecificationOptionValue);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(
				cpDefinitionSpecificationOptionValue.getUuid());
		}

		return _cpDefinitionSpecificationOptionValueLocalService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId(),
				cpDefinitionSpecificationOptionValue.
					getCPSpecificationOptionId(),
				cpDefinitionSpecificationOptionValue.getCPOptionCategoryId(),
				cpDefinitionSpecificationOptionValue.getValueMap(),
				cpDefinitionSpecificationOptionValue.getPriority(),
				serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws PortalException {

		_cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionSpecificationOptionValue != null) {
			deleteStagedModel(cpDefinitionSpecificationOptionValue);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionSpecificationOptionValue
		fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {

		return _cpDefinitionSpecificationOptionValueLocalService.
			fetchCPDefinitionSpecificationOptionValueByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	public List<CPDefinitionSpecificationOptionValue>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return _cpDefinitionSpecificationOptionValueLocalService.
			getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CPDefinitionSpecificationOptionValue>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionSpecificationOptionValueLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CPDefinitionSpecificationOptionValue saveStagedModel(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws PortalException {

		return _cpDefinitionSpecificationOptionValueLocalService.
			updateCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue);
	}

	@Override
	public CPDefinitionSpecificationOptionValue updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionSpecificationOptionValue);

		return _cpDefinitionSpecificationOptionValueLocalService.
			updateCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId(),
				cpDefinitionSpecificationOptionValue.getCPOptionCategoryId(),
				cpDefinitionSpecificationOptionValue.getValueMap(),
				cpDefinitionSpecificationOptionValue.getPriority(),
				serviceContext);
	}

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

}