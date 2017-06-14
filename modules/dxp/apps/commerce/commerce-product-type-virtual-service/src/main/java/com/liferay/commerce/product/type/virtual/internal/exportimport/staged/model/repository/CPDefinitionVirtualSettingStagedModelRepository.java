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

package com.liferay.commerce.product.type.virtual.internal.exportimport.staged.model.repository;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting"},
	service = StagedModelRepository.class
)
public class CPDefinitionVirtualSettingStagedModelRepository
	extends BaseStagedModelRepository<CPDefinitionVirtualSetting> {

	@Override
	public CPDefinitionVirtualSetting addStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionVirtualSetting);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(cpDefinitionVirtualSetting.getUuid());
		}

		return _cpDefinitionVirtualSettingLocalService.
			addCPDefinitionVirtualSetting(
				cpDefinitionVirtualSetting.getCPDefinitionId(),
				cpDefinitionVirtualSetting.getFileEntryId(),
				cpDefinitionVirtualSetting.getUrl(),
				cpDefinitionVirtualSetting.getActivationStatus(),
				cpDefinitionVirtualSetting.getDuration(),
				cpDefinitionVirtualSetting.getMaxUsages(),
				cpDefinitionVirtualSetting.isUseSample(),
				cpDefinitionVirtualSetting.getSampleFileEntryId(),
				cpDefinitionVirtualSetting.getSampleUrl(),
				cpDefinitionVirtualSetting.isTermsOfUseRequired(),
				cpDefinitionVirtualSetting.getTermsOfUseContentMap(),
				cpDefinitionVirtualSetting.
					getTermsOfUseJournalArticleResourcePrimKey(),
				serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws PortalException {

		_cpDefinitionVirtualSettingLocalService.
			deleteCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (cpDefinitionVirtualSetting != null) {
			deleteStagedModel(cpDefinitionVirtualSetting);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CPDefinitionVirtualSetting fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionVirtualSettingLocalService.
			fetchCPDefinitionVirtualSettingByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CPDefinitionVirtualSetting> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _cpDefinitionVirtualSettingLocalService.
			getCPDefinitionVirtualSettingsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CPDefinitionVirtualSetting>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _cpDefinitionVirtualSettingLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CPDefinitionVirtualSetting saveStagedModel(
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws PortalException {

		return _cpDefinitionVirtualSettingLocalService.
			updateCPDefinitionVirtualSetting(cpDefinitionVirtualSetting);
	}

	@Override
	public CPDefinitionVirtualSetting updateStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			cpDefinitionVirtualSetting);

		return _cpDefinitionVirtualSettingLocalService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId(),
				cpDefinitionVirtualSetting.getFileEntryId(),
				cpDefinitionVirtualSetting.getUrl(),
				cpDefinitionVirtualSetting.getActivationStatus(),
				cpDefinitionVirtualSetting.getDuration(),
				cpDefinitionVirtualSetting.getMaxUsages(),
				cpDefinitionVirtualSetting.isUseSample(),
				cpDefinitionVirtualSetting.getSampleFileEntryId(),
				cpDefinitionVirtualSetting.getSampleUrl(),
				cpDefinitionVirtualSetting.isTermsOfUseRequired(),
				cpDefinitionVirtualSetting.getTermsOfUseContentMap(),
				cpDefinitionVirtualSetting.
					getTermsOfUseJournalArticleResourcePrimKey(),
				serviceContext);
	}

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

	@Reference
	private UserLocalService _userLocalService;

}