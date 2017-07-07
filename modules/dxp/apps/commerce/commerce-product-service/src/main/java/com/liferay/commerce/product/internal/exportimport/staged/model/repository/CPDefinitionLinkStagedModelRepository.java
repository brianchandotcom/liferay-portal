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

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinitionLink",
	service = StagedModelRepository.class
)
public class CPDefinitionLinkStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionLink> {

	@Override
	public CPDefinitionLink addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionLink cpDefinitionLink)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionLink);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionLink.getUuid());
		}

		return _cpDefinitionLinkLocalService.addCPDefinitionLink(
			cpDefinitionLink.getCPDefinitionId1(),
			cpDefinitionLink.getCPDefinitionId2(),
			cpDefinitionLink.getPriority(), cpDefinitionLink.getType(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(CPDefinitionLink cpDefinitionLink)
		throws PortalException {

		_cpDefinitionLinkLocalService.deleteCPDefinitionLink(cpDefinitionLink);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionLink cpDefinitionLink = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (cpDefinitionLink != null) {
			deleteStagedModel(cpDefinitionLink);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionLink fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return
			_cpDefinitionLinkLocalService.fetchCPDefinitionLinkByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	public List<CPDefinitionLink> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionLinkLocalService.
			getCPDefinitionLinksByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CPDefinitionLink>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionLinkLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CPDefinitionLink saveStagedModel(CPDefinitionLink cpDefinitionLink)
		throws PortalException {

		return _cpDefinitionLinkLocalService.updateCPDefinitionLink(
			cpDefinitionLink);
	}

	@Override
	public CPDefinitionLink updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionLink cpDefinitionLink)
		throws PortalException {

		return _cpDefinitionLinkLocalService.updateCPDefinitionLink(
			cpDefinitionLink.getCPDefinitionLinkId(),
			cpDefinitionLink.getPriority());
	}

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

}