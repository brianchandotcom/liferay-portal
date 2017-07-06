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

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionValueRel"},
	service = StagedModelRepository.class
)
public class CPDefinitionOptionValueRelStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionOptionValueRel> {

	@Override
	public CPDefinitionOptionValueRel addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionOptionValueRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionOptionValueRel.getUuid());
		}

		return _cpDefinitionOptionValueRelLocalService.
			addCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
				cpDefinitionOptionValueRel.getTitleMap(),
				cpDefinitionOptionValueRel.getPriority(),
				cpDefinitionOptionValueRel.getKey(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		_cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionOptionValueRel != null) {
			deleteStagedModel(cpDefinitionOptionValueRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionOptionValueRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionOptionValueRelLocalService.
			fetchCPDefinitionOptionValueRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPDefinitionOptionValueRel> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionOptionValueRelLocalService.
			getCPDefinitionOptionValueRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CPDefinitionOptionValueRel>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionOptionValueRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CPDefinitionOptionValueRel saveStagedModel(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		return _cpDefinitionOptionValueRelLocalService.
			updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public CPDefinitionOptionValueRel updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionOptionValueRel);

		return _cpDefinitionOptionValueRelLocalService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId(),
				cpDefinitionOptionValueRel.getTitleMap(),
				cpDefinitionOptionValueRel.getPriority(),
				cpDefinitionOptionValueRel.getKey(), serviceContext);
	}

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

}